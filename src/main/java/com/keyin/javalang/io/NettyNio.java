package com.keyin.javalang.io;

/**
 * @description: 仍然是对Java NIO的封装，所以底层还是epoll的功劳，在业务层，是Netty线程模型的优化，
 *    Reactor pattern， 使用了线程池，同时BossGroup设置多个，可以将连接和读写分离，解决高IO/或者高连接的场景
 *    比如 boss 设置subReactor 可以单独处理读写
 * @author: yinke
 * @create: 2021-04-26 13:01
 **/
public class NettyNio {
}
