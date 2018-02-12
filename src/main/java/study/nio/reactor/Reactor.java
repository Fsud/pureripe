package study.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Reactor模式
 * http://ifeve.com/netty-reactor-4/
 * Duang Lea pdf。
 * Created by fankun on 2018/2/9.
 */
public class Reactor implements Runnable {
    final ServerSocketChannel serverSocket;
    final Selector selector;

    /**
     * Reactor 初始化时，注册一个OP_ACCEPT，
     * @param port
     * @throws Exception
     */
    public Reactor(int port) throws Exception {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                while (it.hasNext()) {
                    dispatch((SelectionKey) it.next());
                }
                selectedKeys.clear();
            }
        }
        catch (IOException e) {

        }
    }

    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) key.attachment();
        if (r != null) {
            r.run();
        }
    }

    class Acceptor implements Runnable {
        @Override
        public void run() {
            try{
                SocketChannel c = serverSocket.accept();
                if(c!=null){
                    new Handler(selector,c);
                }
            }catch (IOException e){

            }
        }
    }

    final class Handler implements Runnable{

        ByteBuffer input = ByteBuffer.allocate(1024);
        ByteBuffer output = ByteBuffer.allocate(1024);

        final SocketChannel socket;
        final SelectionKey sk;

        static final int READING = 0,SENDING = 1;
        int state = READING;

        public Handler(Selector selector,SocketChannel socket) throws IOException{
            this.socket = socket;
            socket.configureBlocking(false);
            sk = socket.register(selector,0);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }

        @Override
        public void run() {
            try{
                if(state==READING)
                    read();
                else if(state==SENDING)
                    send();
            }catch (IOException e){

            }

        }

        private void read() throws IOException{
            socket.read(input);
            if(inputIsComplete()){
                process();
                state =SENDING;
                sk.interestOps(SelectionKey.OP_WRITE);
            }
        }



        private void send() throws IOException{
            socket.write(output);
            if(outputIsComplete())
                sk.cancel();
        }

        private boolean outputIsComplete() {
            return true;
        }
        private boolean inputIsComplete() {
            return true;
        }

        private void process() {
        }
    }
}
