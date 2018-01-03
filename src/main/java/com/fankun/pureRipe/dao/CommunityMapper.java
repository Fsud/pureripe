package com.fankun.pureRipe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fankun.pureRipe.entity.Post;
import com.fankun.pureRipe.entity.Reply;

/**
 * Created by fankun on 2017/6/13.
 */
@Mapper
public interface CommunityMapper {
    void insertSubject(Post post);

    List<Post> listSubjects();

    Post getSubject(@Param("postId") String postId);

    void updateSubject(Post post);

    void insertReply(Reply reply);

    List<Reply> getRepliesByPostId(@Param("postId") String postId);

    Long countSubject();
}
