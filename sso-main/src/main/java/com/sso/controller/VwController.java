package com.sso.controller;

import com.sso.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class VwController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/index")
    public String toIndex(@CookieValue(required = false,value = "TOKEN")Cookie cookie,
                          HttpSession session){
        if (cookie != null){
            String value = cookie.getValue();
            if (!StringUtils.isEmpty(value)){
                Map map = restTemplate.getForObject(Constants.LOGIN_INFO_URL + value, Map.class);
                session.setAttribute("loginUser",map);
            }
        }
        return "index";
    }

}
