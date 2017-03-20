package com.birthright.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by birth on 27.01.2017.
 */
//only for java config annotation
@Component("site_interceptor")
public class SiteInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("SiteInterceptor preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("SiteInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("SiteInterceptor afterCompletion");
    }
}
