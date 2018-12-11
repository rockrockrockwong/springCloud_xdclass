package localTest.io;

import java.io.*;
import java.nio.charset.Charset;

public class JavaIO_GO {


    /**
     * 文件
     */
    File file = new File("");


    /**
     * 字节流
     */
    BufferedInputStream bufferedInputStream;
    BufferedOutputStream bufferedOutputStream;
    FileInputStream fileInputStream = new FileInputStream(file);
    FileOutputStream fileOutputStream;


    /**
     * 字符流
     */
    FileReader fileReader = new FileReader("");
    FileWriter fileWriter = new FileWriter("");
    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("gb2312"));
    BufferedReader bufferedReader = new BufferedReader(fileReader);


    public JavaIO_GO() throws IOException {
    }


    public void readAFile(String fullFilePath){
        File local_file = new File(fullFilePath);
        FileReader local_fileReader = null;
        BufferedReader local_bufferedReader = null;
        try {
            //使用默认的编码方式解析，FileReader还是需要从FileInputStream这个
            //字节流读取内容，然后使用默认的编码方式解码
            local_fileReader = new FileReader(local_file);
            //这里使用指定的编码方式解码文件,依然需要指定使用哪个字节流读取，可以看出
            //字符流基于字节流读取内容，然后根据编码方式解码内容
            FileInputStream local_fileInputStream = new FileInputStream(local_file);
            InputStreamReader local_nputStreamReader = new InputStreamReader(local_fileInputStream,Charset.forName("gb2312"));
            local_bufferedReader = new BufferedReader(local_nputStreamReader,1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                local_fileReader.close();
                local_bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
