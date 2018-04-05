package com.example.demo2.Controller;

import jdk.nashorn.internal.runtime.GlobalConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("con/**")
public class configureController {
    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param name
     */
    @RequestMapping("/delCookie")
    public ModelAndView delCookie(HttpServletRequest request, HttpServletResponse response, String name) {

        request.getSession().removeAttribute("username");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/hotelmain/Myhotel");
        return modelAndView;
       /* Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            System.out.println("没有cookie==============");
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    System.out.println("被删除的cookie名字为:" + cookie.getName());
                    response.addCookie(cookie);
                    break;
                }
            }
        }*/
    }
}
