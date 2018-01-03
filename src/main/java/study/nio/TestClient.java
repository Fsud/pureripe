package study.nio;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * Created by fankun on 2017/1/19.
 */
public class TestClient {
    public static void main(String[] args) throws Exception{
        Socket s=new Socket("127.0.0.1",7788);

        InputStream  inStrem=s.getInputStream();
        OutputStream outStream=s.getOutputStream();

        // 输出
        PrintWriter out=new PrintWriter(outStream,true);

        out.print("你好！NIO");
        out.flush();

        s.shutdownOutput();// 输出结束

        // 输入
        Scanner in=new Scanner(inStrem);
        StringBuilder sb=new StringBuilder();
        while(in.hasNextLine()){
            String line=in.nextLine();
            sb.append(line);
        }
        String response=sb.toString();
        System.out.println("response="+response);
    }
}
