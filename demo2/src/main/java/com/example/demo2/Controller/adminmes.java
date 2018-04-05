package com.example.demo2.Controller;

import com.example.demo2.domain.User;
import com.example.demo2.repository.userRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("adminsmes/*")
public class adminmes {
    @Autowired
    private userRepository userRepository;
    HttpSession session = null;
    private static final Logger logger = LoggerFactory.getLogger(quserController.class);

    public List<User> getUserlist() {
        List<User> users = new ArrayList<>();


        for(User user : userRepository.findAll()){

            if("2".equals(user.getAuth())){

                users.add(user);
            }


        }
        return users;
    }
    @RequestMapping("/mes")
    public ModelAndView mes(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("admin",getUserlist());

        modelAndView.setViewName("admins/adminmes/adminm");
        return modelAndView;
    }
    @RequestMapping("/xiugai")
    public ModelAndView xiu( HttpServletRequest httpServletRequest){
        String username=httpServletRequest.getParameter("username");
        String password=httpServletRequest.getParameter("password");
        String card=httpServletRequest.getParameter("card");
        String phone=httpServletRequest.getParameter("phone");
        String sex=httpServletRequest.getParameter("sex");
        User user=userRepository.findByUsername(username);
        user.setPassword(password);
        user.setCard(card);
        user.setSex(sex);
        user.setPhone(phone);
        userRepository.save(user);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("admins/admin");
      return modelAndView;
    }

    @RequestMapping("/icon")

    public String upload(@RequestParam("file") MultipartFile file, org.apache.catalina.servlet4preview.http.HttpServletRequest request) {
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
            session=request.getSession();
            session.setAttribute("user",user);
            return "admins/admin";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

}
