package com.fankun.pureRipe.entity;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Created by fankun on 2017/6/15.
 */
public class Reply {
    private Long replyId;
    private Long postId;
    private Long userId;
    private String content;
    private Date addTime;
    private String replyUserName;

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String addTimeShow(){
        //TODO 时间友好
        return new PrettyTime().format(addTime);
    }
}
