package study.netty.sample.ChatServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

	public static final ChannelGroup group = new DefaultChannelGroup(
			GlobalEventExecutor.INSTANCE);
	public static Map<String,String> channelAddress_NickName = Maps.newHashMap();
	public static Map<String,Channel> nickName_Channel = Maps.newHashMap();

	protected void channelRead0(ChannelHandlerContext arg0, String arg1)
			throws Exception {
		Channel channel = arg0.channel();
		if(arg1.startsWith(MessageUtils.MESG_PRE_NICK_NAME)){
			String nickName = arg1.split(":")[1];
			channelAddress_NickName.put(channel.remoteAddress().toString(),nickName);
			nickName_Channel.put(nickName,channel);
			return;
		}
		if(arg1.startsWith("To")){
			String nickName = getNikeNameFromAimMessage(arg1);
			String content = getCotnentFromAimMessage(arg1);
			Channel aimChannel = nickName_Channel.get(nickName);
			aimChannel.writeAndFlush("{"+channelAddress_NickName.get(channel.remoteAddress().toString())+"}:"+content+"\n");
			return;
		}
		for (Channel ch : group) {
			if (ch == channel) {
				ch.writeAndFlush("[you]:" + arg1 + "\n");
			} else {
				ch.writeAndFlush(
						"[" + getNickName(channel) + "]: " + arg1 + "\n");
			}

		}
	}

	private String getCotnentFromAimMessage(String arg1) {
		String[] args = StringUtils.split(arg1,":",2);
		if(args.length<2){
			return null;
		}
		return args[1];
	}

	private String getNikeNameFromAimMessage(String arg1) {
		String[] args = StringUtils.split(arg1,":",2);
		if(args.length<2){
			return null;
		}
		return StringUtils.removeStart(args[0],"To ");
	}

	private String getNickName(Channel ch) {
		String nickName = channelAddress_NickName.get(ch.remoteAddress().toString());
		return StringUtils.isBlank(nickName) ? ch.remoteAddress().toString():nickName;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		for (Channel ch : group) {
			ch.writeAndFlush(
					"[" + getNickName(channel) + "] " + "is coming");
		}
		group.add(channel);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		for (Channel ch : group) {
			ch.writeAndFlush(
					"[" + getNickName(channel) + "] " + "is back");
		}
		group.remove(channel);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println("[" + getNickName(channel) + "] " + "online");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println("[" + getNickName(channel) + "] " + "offline");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println(
				"[" + getNickName(ctx.channel()) + "]" + "exit the room");
		ctx.close().sync();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
		channelRead0(channelHandlerContext,s);
	}
}
