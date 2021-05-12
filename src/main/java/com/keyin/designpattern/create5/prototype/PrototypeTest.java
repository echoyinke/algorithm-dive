package com.keyin.designpattern.create5.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;

/**
 * @description:
 * @author: yinke
 * @create: 2021-04-28 15:30
 **/
@AllArgsConstructor
public class PrototypeTest implements Cloneable, Serializable {
    public String sneak;

    @Override
    public Object clone() {
            Object object = null;
        try {
            object= super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
        
    }

    public static void main(String[] args)  {
         PrototypeTest prototypeTest = new PrototypeTest("fuck");
        final Object clone = prototypeTest.clone();
        byte[] s = SerializationUtils.serialize(prototypeTest);
        final Object deserialize = SerializationUtils.deserialize(s);
        Assert.state(prototypeTest.equals(clone) , "not equals");
        
    }
}
