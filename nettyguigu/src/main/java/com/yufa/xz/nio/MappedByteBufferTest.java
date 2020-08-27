package com.yufa.xz.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author admin
 * @data 2020/8/27
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        // MappedByteBuffer 可以让文件直接在内存（堆外）修改，操作系统不需要拷贝一次

        RandomAccessFile randomAccessFile = new RandomAccessFile("file01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        // 参数1：读写模式
        // 参数2：可以直接修改的起始位置
        // 参数3：是映射到内存的大小(不是索引位置)，即将file01.txt的多少个字节映射到内存
        // 可以直接修改的范围就是 0-5
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0,(byte)'H');
        mappedByteBuffer.put(3,(byte)'9');
//        mappedByteBuffer.putInt(4, 5);

        randomAccessFile.close();
    }
}
