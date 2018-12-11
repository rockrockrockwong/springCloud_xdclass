package localTest.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class JavaNIO_Test {


    /*RandomAccessFile randomAccessFile = null;
    FileChannel fileChannel = randomAccessFile.getChannel();
    CharBuffer charBuffer = CharBuffer.allocate(1024);*/


    public void test_FileNio(){
        RandomAccessFile local_randomAccessFile = null;
        FileChannel local_fileChannel = null;
        CharBuffer local_charBuffer;
        ByteBuffer local_byteBuffer = ByteBuffer.allocate(256);

        CharsetDecoder charsetDecoder = Charset.forName("GBK").newDecoder();
        CharsetEncoder charsetEncoder = Charset.forName("GBK").newEncoder();

        try {
            local_randomAccessFile = new RandomAccessFile("E:\\Files\\IDEAWorkSpace\\javaStudy\\testinput.txt","rw");
            local_fileChannel = local_randomAccessFile.getChannel();

            int byteRead = local_fileChannel.read(local_byteBuffer);
            int i=0;
            while(byteRead > 0){
                System.out.println(++i);

                local_charBuffer = charsetDecoder.decode(local_byteBuffer);
                System.out.println(local_charBuffer);
                local_charBuffer.flip();

                local_byteBuffer.flip();
                byteRead = local_fileChannel.read(local_byteBuffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                local_fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public JavaNIO_Test() throws FileNotFoundException {
    }
}
