package study.netty.sample.ChatServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

	protected void channelRead0(ChannelHandlerContext arg0, String arg1)
			throws Exception {
		System.out.println(arg1);
	}

	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
		channelRead0(channelHandlerContext,s);
	}
}
