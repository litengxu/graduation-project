package com.example.demo2.Controller;

import ch.qos.logback.core.util.FileUtil;
import com.example.demo2.domain.User;
import com.example.demo2.repository.userRepository;
import com.example.demo2.Configure.upload;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import java.io.File;
import java.io.IOException;

import static sun.plugin.javascript.navig.JSType.Location;

@Controller
@RequestMapping("quser/")
public class quserController {

    @Autowired
    private userRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(quserController.class);
    @GetMapping("/xiugai/{username}")
    public ModelAndView xiugai(HttpServletRequest request, @PathVariable("username")String username){
        ModelAndView mv=new ModelAndView();
        User user=userRepository.findByUsername(username);
        mv.addObject("password",user.getPassword());
        mv.addObject("sex",user.getSex());
        mv.addObject("card",user.getCard());
        mv.addObject("phone",user.getPhone());
        mv.setViewName("/hotelmain/usermes");
        return mv;
    }
    @PostMapping("/xiugai2")
    @ResponseBody
    public ModelAndView  xiugai2(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String sex=request.getParameter("sex");
        String phone=request.getParameter("phone");
        String card=request.getParameter("card");
        User user=userRepository.findByUsername(username);
        user.setPhone(phone);
        user.setSex(sex);
        user.setCard(card);
        user.setPassword(password);
        userRepository.save(user);
        mv.addObject("password",user.getPassword());
        mv.addObject("sex",user.getSex());
        mv.addObject("card",user.getCard());
        mv.addObject("phone",user.getPhone());
        mv.setViewName("/hotelmain/usermes");
        return mv;
    }
    @RequestMapping("/upload")
   public String upload(){
        return "/hotelmain/upload";
    }
    @RequestMapping("/upup")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        String username=request.getParameter("username");
            if (file.isEmpty()) {
        return "文件为空";
    }
    // 获取文件名
    String fileName = file.getOriginalFilename(); logger.info("上传的文件名为:" + fileName);
    // 获取文件的后缀名
    String suffixName = fileName.substring(fileName.lastIndexOf(".")); logger.info("上传的后缀名为:" + suffixName);
    // 文件上传路径
    String filePath = "E:/biyesheji/demo2/src/main/resources/static/images/";
    // 解决中文问题,liunx 下中文路径,图片显示问题
    //fileName = UUID.randomUUID() + suffixName;
    File dest = new File(filePath + fileName);
    // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
        dest.getParentFile().mkdirs();
    }
            try {
        file.transferTo(dest);
        System.out.println(username);
        User user=userRepository.findByUsername(username);
        String icon="/images/"+fileName;
        System.out.println(icon);
        user.setIcon(icon);
        userRepository.save(user);
        return "上传成功";
    } catch (IllegalStateException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
            return "上传失败";
}
    }


/*    public String upup(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();

        *//*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*//*
        String filePath = request.getSession().getServletContext().getRealPath("../E:/biyesheji/demo2/src/main/resources/static/ images");
        try {
            upload.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("错误");
        }
        //返回json
        return "uploadimg success";
    }*/

