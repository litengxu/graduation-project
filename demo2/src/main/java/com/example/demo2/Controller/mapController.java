package com.example.demo2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("map/*")
@Controller
public class mapController {
    @RequestMapping("/baidu")
    public String map(){
        return "/hotelmain/baiduditu";
    }
}
