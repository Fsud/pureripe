package com.fankun.pureRipe.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fankun.pureRipe.entity.Post;
import com.fankun.pureRipe.entity.Reply;
import com.fankun.pureRipe.entity.User;
import com.fankun.pureRipe.security.MyUser;
import com.fankun.pureRipe.service.interf.CommunityService;
import com.fankun.pureRipe.service.interf.UserService;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;

/**
 * Created by fankun on 2017/6/12.
 */
@RequestMapping(value = "/cm")
@Controller
public class CommunityController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    CommunityService communityService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(Model model){
        return index(model,1L);
    }

    @RequestMapping(value ="/{pageNum}",method = RequestMethod.GET)
    public String index(Model model,@PathVariable Long pageNum){
        Page<Post> posts = communityService.listSubjects(pageNum);
        model.addAttribute("listSubjects",posts);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPage",posts.getPages());
        return "cm/index";
    }

    @RequestMapping(value ="/{pageNum}/cache",method = RequestMethod.GET)
    public String indexcache(Model model,@PathVariable Long pageNum){
        Page<Post> posts = communityService.listSubjectsByCache(pageNum);
        model.addAttribute("listSubjects",posts);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPage",posts.getPages());
        return "cm/index";
    }

    @RequestMapping(value = "/addSubject",method = RequestMethod.GET)
    public String addSubject(Model model){
        return "cm/newSubject";
    }

    @RequestMapping(value = "/addSubject",method = RequestMethod.POST)
    public String addSubjectP(Model model, String subject, String content){
        MyUser myUser = getMyUser();
        if(myUser==null){
            //如果用户编辑的时间太长，致使登录过期，则会丢失所填的内容
            return "redirect:/login";
        }
        communityService.addSubject(subject,content,myUser);
        return "redirect:/cm";
    }

    @RequestMapping(value = "/detail/{postId}",method = RequestMethod.GET)
    public String addSubject(Model model, @PathVariable String postId, HttpServletRequest request){
        addMyUser(model);
        Post post = communityService.getSubject(postId);
        if(post == null){
            model.addAttribute("refer",request.getHeader("Refer"));
            return "404";
        }
        List<Reply> replies = communityService.getReplysByPostId(postId);
        model.addAttribute("post",post);
        model.addAttribute("postUser",userService.getUserById(post.getUserId()));
        model.addAttribute("replies",replies);
        return "cm/subjectDetail";
    }



    @RequestMapping(value = "/editSubject/{postId}",method = RequestMethod.GET)
    public String editSubject(Model model, @PathVariable String postId){
        Post post = communityService.getSubject(postId);
        model.addAttribute("post",post);
        model.addAttribute("postUser",userService.getUserById(post.getUserId()));
        return "cm/editSubject";
    }

    @RequestMapping(value = "/editSubject/{postId}",method = RequestMethod.POST)
    public String editSubjectP(Model model, @PathVariable String postId,String subject, String content){
        MyUser myUser = getMyUser();
        if(myUser==null){
            //如果用户编辑的时间太长，致使登录过期，则会丢失所填的内容
            return "redirect:/login";
        }
        communityService.editSubject(subject,content,myUser,postId);
        return "redirect:/cm/detail/"+postId;
    }

    @RequestMapping(value = "/addReply/{postId}",method = RequestMethod.POST)
    public String addReply(Model model,@PathVariable Long postId,String content){
        communityService.addReply(postId,content,getMyUser());
        return "redirect:/cm/detail/"+postId;
    }

    @ResponseBody
    @RequestMapping("testValidation")
    public Map testValidation(@Validated User user,BindingResult bindingResult){
        Map map = Maps.newHashMap();
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getAllErrors().get(0).getDefaultMessage());
            return map;
        }

        map.put("status",1);
        return map;
    }

}
