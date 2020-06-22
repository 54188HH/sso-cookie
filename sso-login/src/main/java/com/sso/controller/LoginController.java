package com.sso.controller;

import com.sso.pojo.User;
import com.sso.utils.CookieUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    public String doLogin(User user, HttpSession session, HttpServletResponse response){
        String target = (String) session.getAttribute("target");
        //通过set集合模拟数据库中的用户，判断前台传过来的用户是否存在
         Optional<User> first = set.stream().filter(set -> set.getUsername().equals(user.getUsername()) &&
                set.getPassword().equals(user.getPassword())).findFirst();

         if (first.isPresent()){
             //说明数据库内有该用户，所以保存用户的信息
             String token = UUID.randomUUID().toString();
             //通过Cookie工具类把Cookie写入到浏览器中
             CookieUtil.addCookie(response,"TOKEN",token,3600);
             //将生成的token作为key值将登陆成功的用户存入到redis中
             redisTemplate.opsForValue().set(token,first.get());
         }else{
             //登录失败
             session.setAttribute(" ","用户名或者密码错误");
             return "login";
         }
        System.out.println("target:" + target);
        //重定向到taget地址
        /*return "redirect:http://127.0.0.1:9030";*/
        return "redirect:"+target;
    }

    @GetMapping("/info")
    @ResponseBody
    public User getUserInfo(String token){
        //如果token不为空，就以token为key值去redis里面取value值（用户对象）
        if (!StringUtils.isEmpty(token)){
            return (User) redisTemplate.opsForValue().get(token);
        }else {
            return new User(0,"error","请求错误");
        }
    }
}
