package com.keyin.javalang.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @description: selector上，让出cpu，直到有事件发生，而且只处理事件
 * <p>
 * linux 底层实现epoll selector
 * epoll的核心是3个API，核心数据结构是：1个红黑树和1个链表
 * 对于需要监视的文件描述符集合，epoll_ctl对红黑树进行管理，红黑树中每个成员由描述符值和所要监控的文件描述符指向的文件表项的引用等组成。
 * 2. 内核态检测文件描述符读写状态的方式
 * select：采用轮询方式，遍历所有fd，最后返回一个描述符读写操作是否就绪的mask掩码，根据这个掩码给fd_set赋值。
 * poll：同样采用轮询方式，查询每个fd的状态，如果就绪则在等待队列中加入一项并继续遍历。
 * epoll：采用回调机制。在执行epoll_ctl的add操作时，不仅将文件描述符放到红黑树上，而且也注册了回调函数，内核在检测到某文件描述符可读/可写时会调用回调函数，该回调函数将文件描述符放在就绪链表中。
 * 3. 找到就绪的文件描述符并传递给用户态的方式
 * select：将之前传入的fd_set拷贝传出到用户态并返回就绪的文件描述符总数。用户态并不知道是哪些文件描述符处于就绪态，需要遍历来判断。
 * poll：将之前传入的fd数组拷贝传出用户态并返回就绪的文件描述符总数。用户态并不知道是哪些文件描述符处于就绪态，需要遍历来判断。
 * epoll：epoll_wait只用观察就绪链表中有无数据即可，最后将链表的数据返回给数组并返回就绪的数量。内核将就绪的文件描述符放在传入的数组中，所以只用遍历依次处理即可。这里返回的文件描述符是通过mmap让内核和用户空间共享同一块内存实现传递的，减少了不必要的拷贝。
 * 4. 重复监听的处理方式
 * select：将新的监听文件描述符集合拷贝传入内核中，继续以上步骤。
 * poll：将新的struct pollfd结构体数组拷贝传入内核中，继续以上步骤。
 * epoll：无需重新构建红黑树，直接沿用已存在的即可。
 * 5. epoll更高效的原因
 * select和poll的动作基本一致，只是poll采用链表来进行文件描述符的存储，而select采用fd标注位来存放，所以select会受到最大连接数的限制，而poll不会。
 * select、poll、epoll虽然都会返回就绪的文件描述符数量。但是select和poll并不会明确指出是哪些文件描述符就绪，而epoll会。造成的区别就是，系统调用返回后，调用select和poll的程序需要遍历监听的整个文件描述符找到是谁处于就绪，而epoll则直接处理即可。
 * select、poll都需要将有关文件描述符的数据结构拷贝进内核，最后再拷贝出来。而epoll创建的有关文件描述符的数据结构本身就存于内核态中，系统调用返回时利用mmap()文件映射内存加速与内核空间的消息传递：即epoll使用mmap减少复制开销。
 * select、poll采用轮询的方式来检查文件描述符是否处于就绪态，而epoll采用回调机制。造成的结果就是，随着fd的增加，select和poll的效率会线性降低，而epoll不会受到太大影响，除非活跃的socket很多。
 * epoll的边缘触发模式效率高，系统不会充斥大量不关心的就绪文件描述符
 * @author: yinke
 * @create: 2021-04-25 23:03
 * @see "https://www.jianshu.com/p/31cdfd6f5a48"
 * <p>
 * 作者：全菜工程师小辉
 * 链接：https://www.jianshu.com/p/31cdfd6f5a48
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * <p>
 * 作者：全菜工程师小辉
 * 链接：https://www.jianshu.com/p/31cdfd6f5a48
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。 *
 * 作者：全菜工程师小辉
 * 链接：https://www.jianshu.com/p/31cdfd6f5a48
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 **/
public class NIOSocketServer {


    private static Logger log = LoggerFactory.getLogger(NIOSocketServer.class);
    public static List<SocketChannel> channelList = new ArrayList<>();

    
    /**
    * 使用Epoll模型实现，epoll模型采用红黑树加链表，红黑树注册删除方便，每个事件都又回调，回调回加入到链表，不用每个遍历
     * 
    * */
    public static void nioLikeEpoll() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("server started");
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    log.info("client connected");
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int len = socketChannel.read(byteBuffer);
                    if (len > 0) {
                        byteBuffer.flip();
                        String str = StandardCharsets.ISO_8859_1.decode(byteBuffer).toString();
                        log.info("receive data: {}", str);
                    } else if (len == -1) {
                        log.info("close connection");
                        socketChannel.close();
                    }
                }
                iterator.remove();
            }
        }
    }

    /**
     * 模拟底层select模形（对于poll模型区别在于把array改链表），这种模型是用一个array来存放就绪channel，一个是长度有限，在一个每次都要遍历整个array,大量连接性能瓶颈
     * 
     * 
     * */
    public static void nioLikeSelect() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        serverSocketChannel.configureBlocking(false);
        log.info("server started");

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            if (socketChannel != null) {
                log.info("connect success");
                socketChannel.configureBlocking(false);
                channelList.add(socketChannel);
            }

            Iterator<SocketChannel> socketChannelIterator = channelList.listIterator();
            while (socketChannelIterator.hasNext()) {
                SocketChannel sc = socketChannelIterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                int len = socketChannel.read(byteBuffer);
                if (len > 0) {
                    byteBuffer.flip();
                    String str = StandardCharsets.ISO_8859_1.decode(byteBuffer).toString();
                    log.info("receive data: {}", str);
                } else if (len == -1) {
                    log.info("close connection");
                    socketChannel.close();
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        nioLikeEpoll();

    }
}
