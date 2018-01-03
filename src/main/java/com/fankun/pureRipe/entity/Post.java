package com.fankun.pureRipe.entity;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Created by fankun on 2017/6/13.
 */
public class Post {
    public static enum POST_STATUS_ENUM{
        valid("0"),delete("1");
        private POST_STATUS_ENUM(String value){
            this.value = value;
        }
        private String value;
        public String getValue(){
            return this.value;
        }
    }

    private Long postId;
    private Long userId;
    private String subject;
    private String content;
    private Date addTime;
    private Date updateTime;
    private String status;
    private Long replyCount;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String addTimeShow(){
        //TODO 时间友好
        return new PrettyTime().format(addTime);
    }

    public Long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Long replyCount) {
        this.replyCount = replyCount;
    }
}
