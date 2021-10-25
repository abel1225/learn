package me.abel.fileMerge;

import static java.lang.System.out;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class Test {
    public static final int BUFSIZE = 1024 * 8;

    public static void mergeFiles(String outFile, String[] files) {
        FileChannel outChannel = null;
        out.println("Merge " + Arrays.toString(files) + " into " + outFile);
        try {
            outChannel = new FileOutputStream(outFile).getChannel();
            for(String f : files){
                FileChannel fc = new FileInputStream(f).getChannel();
                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
                while(fc.read(bb) != -1){
                    bb.flip();
                    outChannel.write(bb);
                    bb.clear();
                }
                fc.close();
            }
            out.println("Merged!! ");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {if (outChannel != null) {outChannel.close();}} catch (IOException ignore) {}
        }
    }
    public static void main(String[] args) {
        mergeFiles("/Users/lizhen/Desktop/Netty/output.pdf", new String[]{"/Users/lizhen/Desktop/Netty/1.pdf",
                "/Users/lizhen/Desktop/Netty/2.pdf", "/Users/lizhen/Desktop/Netty/3.pdf", "/Users/lizhen/Desktop/Netty/4.pdf", "/Users/lizhen/Desktop/Netty/5.pdf",
                "/Users/lizhen/Desktop/Netty/6.pdf", "/Users/lizhen/Desktop/Netty/7.pdf", "/Users/lizhen/Desktop/Netty/8.pdf", "/Users/lizhen/Desktop/Netty/9.pdf",
                "/Users/lizhen/Desktop/Netty/10.pdf", "/Users/lizhen/Desktop/Netty/11.pdf", "/Users/lizhen/Desktop/Netty/12.pdf", "/Users/lizhen/Desktop/Netty/13.pdf"});
    }
}


