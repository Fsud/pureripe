package study.netty.sample.ChatServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMain {
    private String host;
    private int port;
    private String nickName;
    private boolean stop = false;

    public ClientMain(String host, int port, String nickName) {
        this.host = host;
        this.port = port;
        this.nickName = nickName;
    }

    public static void main(String[] args) throws IOException {
        new ClientMain(args[0], Integer.parseInt(args[1]), args[2]).run();
    }

    public void run() throws IOException {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("stringD", new StringDecoder())
						.addLast("stringC", new StringEncoder())
                        .addLast("chat", new ChatClientHandler());
                // pipeline.addLast("http", new HttpClientCodec());

            }
        });

        try {
            Channel channel = bootstrap.connect(host, port).sync().channel();
            channel.writeAndFlush(MessageUtils.MESG_PRE_NICK_NAME  + nickName);
            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();
                if (input != null) {
                    if (MessageUtils.QUIT.equals(input)) {
                        System.exit(1);
                    }
                    channel.writeAndFlush(input);
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

}
