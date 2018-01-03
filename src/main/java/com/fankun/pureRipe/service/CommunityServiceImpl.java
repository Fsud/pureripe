package com.fankun.pureRipe.service;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fankun.pureRipe.dao.CommunityMapper;
import com.fankun.pureRipe.entity.Post;
import com.fankun.pureRipe.entity.Reply;
import com.fankun.pureRipe.security.MyUser;
import com.fankun.pureRipe.service.interf.CommunityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * Created by fankun on 2017/6/13.
 */
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    CommunityMapper communityMapper;
    private static final Long PAGESIZE = 5L;

    @Override
    public void addSubject(String subject, String content, MyUser myUser) {
        Post post = new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setStatus(Post.POST_STATUS_ENUM.valid.getValue());
        post.setUserId(myUser.getUserId());
        communityMapper.insertSubject(post);
    }

    @Override
    public Page<Post> listSubjects(Long pageNum) {
        // return communityMapper.listSubjects((pageNum-1) * PAGESIZE,PAGESIZE);
        Page<Post> posts = PageHelper
                .startPage(NumberUtils.toInt(String.valueOf(pageNum)), NumberUtils.toInt(String.valueOf(PAGESIZE)))
                .doSelectPage(() -> communityMapper.listSubjects());
        return posts;
    }

    @Override
    public Post getSubject(String postId) {
        return communityMapper.getSubject(postId);
    }

    @Override
    public void editSubject(String subject, String content, MyUser myUser, String postId) {
        Post post = getSubject(postId);
        post.setSubject(subject);
        post.setContent(content);
        communityMapper.updateSubject(post);
    }

    @Override
    public void addReply(Long postId, String content, MyUser myUser) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setUserId(myUser.getUserId());
        reply.setPostId(postId);
        communityMapper.insertReply(reply);
    }

    @Override
    public List<Reply> getReplysByPostId(String postId) {
        return communityMapper.getRepliesByPostId(postId);
    }

    @Override
    public Long getTotalPage() {
        Long count = communityMapper.countSubject();
        return count % PAGESIZE == 0 ? count / PAGESIZE : count / PAGESIZE + 1;
    }

}
