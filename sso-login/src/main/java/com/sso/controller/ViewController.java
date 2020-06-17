package com.sso.controller;

import com.sso.pojo.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view")
public class ViewController {
    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/login")
    public String toLogin(@RequestParam(required = false,defaultValue = "")String target,
                          HttpSession session,@CookieValue(required = false,value = "TOKEN") Cookie cookie){
        if (StringUtils.isEmpty(target)){
            target = "http://main.ssologin.com:9010";
        }

        if (cookie != null){
            String val = cookie.getValue();
            User user = (User) redisTemplate.opsForValue().get(val);
            return "redirect:"+target;
        }
        // TODO:校验target地址是否合法
        //重定向地址
        session.setAttribute("target",target);
        return "login";
    }
    @RequestMapping("/loginout")
    public String loginOut(@CookieValue(value = "TOKEN")Cookie cookie){
        cookie.setMaxAge(0);
        return "login";
    }
}
