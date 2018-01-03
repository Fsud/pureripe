package study.netty.sample.ChatServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerMain {

    private int port;

    public ServerMain(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ServerMain(Integer.parseInt(args[0])).run();
    }

    public void run() {
        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024).group(acceptor, worker).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("docode", new StringDecoder()).addLast("encode", new StringEncoder())
                                .addLast("chat", new ChatServerHandler());
                    }
                });
        try {
            Channel channel = bootstrap.bind(port).sync().channel();
            System.out.println("server start running in port:" + port);
            channel.closeFuture().sync();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            acceptor.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
