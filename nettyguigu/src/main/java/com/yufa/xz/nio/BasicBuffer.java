package com.yufa.xz.nio;

import java.nio.IntBuffer;

/**
 * @author admin
 * @data 2020/8/26
 */
public class BasicBuffer {
    public static void main(String[] args) {
        // create a Buffer,size is 5,means it can hold 5 int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // store data in buffer
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // how can we read the data from Buffer
        // flip the Buffer to read or write
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
