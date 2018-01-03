package study.netty.sample.TimeServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by fankun on 2017/1/23.
 */
public class TimeClient {
    public static void main(String[] args) throws Exception{
        int port = 9888;
        new TimeClient.connect(port,"127.0.0.1");
    }

    private static class connect {
        public connect(int port, String host) throws InterruptedException, IOException {
            EventLoopGroup group = new NioEventLoopGroup();
            try{
                Bootstrap b = new Bootstrap();
                b.group(group).channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY,true)
                        .handler(new ChannelInitializer<SocketChannel>() {

                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                socketChannel.pipeline().addLast(new TimeClientHandler());
                            }

                        });

                Channel channel = b.connect(host,port).sync().channel();
                while(true){
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(System.in));
                    String input = reader.readLine();
                    if (input != null) {
                        ByteBuf inp = Unpooled.copiedBuffer(input.getBytes());
                        channel.writeAndFlush(inp);
                    }
                }
                //f.channel().closeFuture().sync();
            }finally {
                group.shutdownGracefully();
            }

        }
    }
}
