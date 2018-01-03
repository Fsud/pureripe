//package com.fankun.pureRipe.netty.sample.TimeServer;
//
//import io.netty.channel.Channel;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.util.concurrent.GlobalEventExecutor;
//
///**
// * Created by fankun on 2017/1/24.
// */
//public class ChatChannelGroup {
//    public static final ChannelGroup cg = new DefaultChannelGroup("ChannelGroups", GlobalEventExecutor.INSTANCE);
//
//    public static void add(Channel channel){
//        cg.add(channel);
//    }
//
//    public static void boardCast(Object msg){
//        cg.writeAndFlush(msg);
//    }
//}
