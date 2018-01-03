package study.netty.sample.TimeServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

/**
 * Created by fankun on 2017/1/23.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    public static final ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, "UTF-8");

        String result = body+ "|"+new java.util.Date(System.currentTimeMillis()).toString() + "\n";
        String res;
        for (Channel ch : group) {
            if (ch == ctx.channel()) {
                res = "[you]"+result+"\n";
            } else {
                res = "[" + ctx.channel().remoteAddress() + "]: " + result + "\n";
            }
            ByteBuf rsp = Unpooled.copiedBuffer(res.getBytes());
            ch.writeAndFlush(rsp);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String off = "["+ctx.channel().remoteAddress()+"] is offline";
        for (Channel ch : group) {
            ch.writeAndFlush(Unpooled.copiedBuffer(off.getBytes()));
        }
        ctx.close().sync();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("new person:"+ctx.channel().remoteAddress());
        String welcome = "welcome ["+ctx.channel().remoteAddress()+"] join!";
        group.add(ctx.channel());
        for (Channel ch : group) {
            ch.writeAndFlush( Unpooled.copiedBuffer(welcome.getBytes()));
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handle added");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        group.remove(ctx.channel());
        String off = "["+ctx.channel().remoteAddress()+"] is offline";
        for (Channel ch : group) {
            ch.writeAndFlush( Unpooled.copiedBuffer(off.getBytes()));
        }
    }

}
