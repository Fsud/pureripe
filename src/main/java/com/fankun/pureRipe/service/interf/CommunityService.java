package com.fankun.pureRipe.service.interf;

import java.util.List;

import com.fankun.pureRipe.entity.Post;
import com.fankun.pureRipe.entity.Reply;
import com.fankun.pureRipe.security.MyUser;
import com.github.pagehelper.Page;

/**
 * Created by fankun on 2017/6/13.
 */
public interface CommunityService {
    void addSubject(String subject, String content, MyUser myUser);

    Page<Post> listSubjects(Long pageNum);

    Post getSubject(String postId);

    void editSubject(String subject, String content, MyUser myUser, String postId);

    void addReply(Long postId, String content, MyUser myUser);

    List<Reply> getReplysByPostId(String postId);

    Long getTotalPage();

    Page<Post> listSubjectsByCache(Long pageNum);
}
