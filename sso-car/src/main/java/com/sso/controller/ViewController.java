package com.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import com.sso.constant.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class ViewController {
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/car")
    public String toCar(@CookieValue(required = false,value = "TOKEN")Cookie cookie,
                        HttpSession session){
        if (cookie != null){
            String value = cookie.getValue();
            if (!StringUtils.isEmpty(value)){
                Map resoult = restTemplate.getForObject(Constants.LOGIN_INFO_URL + value, Map.class);
                session.setAttribute("loginUser",resoult);
            }
        }
        return "index";
    }
}
