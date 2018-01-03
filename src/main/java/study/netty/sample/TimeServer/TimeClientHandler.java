package study.netty.sample.TimeServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by fankun on 2017/1/23.
 */
public class TimeClientHandler extends ChannelHandlerAdapter  {

//    private ByteBuf firstMessage;

//    public void sendMsg(String msg){
//        byte[] req = msg.getBytes();
//        firstMessage= Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
//    }

//    public TimeClientHandler(){
//        firstMessage= Unpooled.buffer();
//        firstMessage.writeBytes("con".getBytes());
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(firstMessage);
//    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println(body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
