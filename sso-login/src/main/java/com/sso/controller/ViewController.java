package com.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping("/login")
    public String toLogin(@RequestParam(required = false,defaultValue = "")String target,
                          HttpSession session){
        if (StringUtils.isEmpty(target)){
            target = "http://www.codeshop.com:9010";
        }
        // TODO:校验target地址是否合法
        //重定向地址
        session.setAttribute("target",target);
        return "login";
    }
}
