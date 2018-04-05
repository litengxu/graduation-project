package com.example.demo2.Configure;

import com.example.demo2.domain.User;
import com.example.demo2.repository.userRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


public class InterceptorConfig  implements HandlerInterceptor {

    @Autowired
    private userRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);

    /**
     * 进入controller层之前拦截请求
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        log.info("---------------------开始进入请求地址拦截----------------------------");

        Object object=httpServletRequest.getSession().getAttribute("username");
        if(object==null){
            PrintWriter printWriter = httpServletResponse.getWriter();
            /*printWriter.write("{code:0,message:\"session is invalid,please login again!\"}");*/
            printWriter.write("Not Logged In,Do not have access to");
            return false;
        }
      /*  else{
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("{code:0,message:\"session is invalid,please login again!\"}");
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("---------------视图渲染之后的操作-------------------------0");
    }
}