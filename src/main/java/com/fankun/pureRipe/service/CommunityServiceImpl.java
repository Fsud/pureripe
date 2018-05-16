package com.fankun.pureRipe.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fankun.pureRipe.dao.CommunityMapper;
import com.fankun.pureRipe.entity.Post;
import com.fankun.pureRipe.entity.Reply;
import com.fankun.pureRipe.security.MyUser;
import com.fankun.pureRipe.service.interf.CommunityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

/**
 * Created by fankun on 2017/6/13.
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

    RedisTemplate redisTemplate;

    @Autowired
    CommunityMapper communityMapper;
    private static final Long PAGESIZE = 5L;


    LoadingCache<Long, Page<Post>> subjectCache =
            CacheBuilder.newBuilder().maximumSize(1000).refreshAfterWrite(10, TimeUnit.SECONDS)
                    // .removalListener(MY_LISTENER)
                    .build(new CacheLoader<Long, Page<Post>>() {

                        //第一次加载的时候，请求load方法
                        public Page<Post> load(Long page) throws Exception {
                            return listSubjects(page);
                        }

                        //刷新的时候，异步请求reload方法，并返回旧值
                        public ListenableFuture<Page<Post>> reload(final Long page,Page<Post> result) {

                            ListenableFutureTask<Page<Post>> task =
                                    ListenableFutureTask.create(()->  {
                                        System.out.println("yibu");
                                            try {
                                                Thread.sleep(10000);
                                            }catch (Exception e){
                                            }
                                        System.out.println("yibu");
                                            return listSubjects(page);
                                    });

                            newCachedThreadPool.execute(task);

                            return task;

                        }

                    });

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
        System.out.println("查询数据库");
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

    @Override
    public Page<Post> listSubjectsByCache(Long pageNum) {
        try {
            return subjectCache.get(pageNum);
        }
        catch (ExecutionException e) {
            return listSubjects(pageNum);
        }
    }

}
