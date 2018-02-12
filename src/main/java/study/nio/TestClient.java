package study.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by fankun on 2017/1/19.
 */
public class TestClient {
    private static String LocalCharsetName = "UTF-8";
    private static final int Buffer_Size = 1024;

    public static void main(String[] args) throws Exception{
//        Socket s=new Socket("127.0.0.1",7788);
//
//        InputStream inStrem=s.getInputStream();
//        OutputStream outStream=s.getOutputStream();
//
//        // 输出
//        PrintWriter out=new PrintWriter(outStream,true);
//
//        out.print("你好！NIO");
//        out.flush();
//
//        s.shutdownOutput();// 输出结束
//
//        // 输入
//        Scanner in=new Scanner(inStrem);
//        StringBuilder sb=new StringBuilder();
//        while(in.hasNextLine()){
//            String line=in.nextLine();
//            sb.append(line);
//        }
//        String response=sb.toString();
//        System.out.println("response="+response);



        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1",7788));
        Selector selector = Selector.open();
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put("hey".getBytes());
        writeBuffer.flip();
        sc.write(writeBuffer);

        while (true){
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()){
                SelectionKey k = keys.next();
                if(k.isReadable()){
                    sc.write(readBuffer);
                    String receivedString =
                            Charset.forName(LocalCharsetName).newDecoder().decode(readBuffer).toString();
                    System.out.println(receivedString);
                    k.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                }
                if(k.isWritable()){
                    writeBuffer.put("hey".getBytes());
                    writeBuffer.flip();
                    sc.write(writeBuffer);
                    writeBuffer.clear();
                }
                keys.remove();
            }

        }

    }
}
