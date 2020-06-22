package com.sso.controller;

import com.sso.pojo.User;
import com.sso.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view")
public class ViewController {
  @Autowired
  private RedisTemplate redisTemplate;

  @RequestMapping("/loginout")
  public String loginOut(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpSession session,
      @CookieValue(value = "TOKEN") Cookie cookie) {
    //调用Cookie工具类删除浏览器中的Cookie
    CookieUtil.removeCookie(response,"TOKEN");
    return "login";
  }

  @RequestMapping("/login")
  public String toLogin(
      @RequestParam(required = false, defaultValue = "") String target,
      HttpSession session,
      @CookieValue(required = false, value = "TOKEN") Cookie cookie) {
    if (StringUtils.isEmpty(target)) {
      target = "http://main.ssologin.com:9010";
    }

    if (cookie != null) {
      String val = cookie.getValue();
      User user = (User) redisTemplate.opsForValue().get(val);
      return "redirect:" + target;
    }
    // TODO:校验target地址是否合法
    // 重定向地址
    session.setAttribute("target", target);
    return "login";
  }
}
