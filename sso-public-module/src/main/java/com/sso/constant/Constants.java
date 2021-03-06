package com.sso.constant;

/**
 * @program: sso-cookie
 * @description:
 * @author: liuzhenqi
 * @create: 2020-06-17 16:08
 **/
public class Constants {
    //通过token获取用户信息
    public final static String LOGIN_INFO_URL = "http://login.ssologin.com:9000/info?token=";

    //cookie要想在子系统中互相访问的话，要在同一个域也就是域相同
    public final static String REAL_NAME = "ssologin.com";
}
