package com.sso.controller;

import com.sso.pojo.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
public class LoginController {
    @Resource
    private RedisTemplate redisTemplate;
    private static Set<User> set;
    static {
        set = new HashSet<>();
        set.add(new User(1,"liquor","123456"));
        set.add(new User(2,"admin","123456"));
    }
    @RequestMapping("/login")
    public String doLogin(User user, HttpSession session){
        String target = (String) session.getAttribute("target");
        //通过set集合模拟数据库中的用户，判断前台传过来的用户是否存在
         Optional<User> first = set.stream().filter(set -> set.getUsername().equals(user.getUsername()) &&
                set.getPassword().equals(user.getPassword())).findFirst();

         if (first.isPresent()){
             //说明数据库内有该用户，所以保存用户的信息
             String token = UUID.randomUUID().toString();
             redisTemplate.opsForValue().set(token,first.get());
         }else{
             //登录失败
             session.setAttribute("msg","用户名或者密码错误");
             return "login";
         }

        //重定向到taget地址
        return "redirect:/"+target;
    }
}
