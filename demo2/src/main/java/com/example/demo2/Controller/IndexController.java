package com.example.demo2.Controller;

import com.example.demo2.domain.Room;
import com.example.demo2.domain.User;
import com.example.demo2.repository.RoomRepository;
import com.example.demo2.repository.userRepository;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("front/*")
public class IndexController {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private userRepository userRepository;
    //登录界面
    private List<Room> getRoomlist(){

        List<Room> rooms=new ArrayList<>();

        for(Room room : roomRepository.findAll())
        {
            rooms.add(room);

        }
        return rooms;
    }
    private List<Room> getRoomlist1(){

        List<Room> rooms=new ArrayList<>();
        int i=0;

        for(Room room : roomRepository.findAll())
        {
            int lg=1;
            if (i==0){
                rooms.add(room);
                i=1;
            }

            for(Room room1:rooms){

                if(room.getType().equals(room1.getType())) {
                    lg=0;


                }
            }
            if(lg==1){
                rooms.add(room);
            }
        }
        return rooms;
    }
    @RequestMapping("/index")
    public String index(){
        return "/hotelmain/Myhotel";
    }
    //注册页面
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    //登录页面
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    HttpSession session = null;
    //注册控制页面
    @RequestMapping("/addregister")
    public String register(HttpServletRequest request,HttpServletResponse response)throws IOException{
      try {
          response.setContentType("text/html");
          response.setCharacterEncoding("utf-8");
          String username = request.getParameter("username");
          String password = request.getParameter("password");
          String password2 = request.getParameter("password2");
          String sex = request.getParameter("sex");
          String card = request.getParameter("card");
          String phone = request.getParameter("phone");
          String auth = request.getParameter("auth");
          User user1 = userRepository.findByUsername(username);
          if (user1 == null) {
              if (password.equals(password2)) {
                  User user = new User();
                  user.setUsername(username);
                  user.setPassword(password);
                  user.setCard(card);
                  user.setSex(sex);
                  user.setPhone(phone);
                  user.setAuth(auth);
                  userRepository.save(user);
                  return "login";

              } else {
                  return "register";
              }
          } else {
           //   response.getWriter().print("您的用户名已注册");
              return "register";
          }
      }catch (Exception e){
          e.printStackTrace();
          return "";
      }

    }
    //注册ajax验证
    @RequestMapping("/res")
    public void res(HttpServletResponse response,HttpServletRequest request)throws IOException{

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        System.out.println("------");
        System.out.println(username);
        User user1 = userRepository.findByUsername(username);
        if(user1!= null){
            response.getWriter().print("您注册的用户名已存在");
        }
    }
    //登录方法
    @RequestMapping("/addlogin")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ModelAndView modelAndView=new ModelAndView();
        //将数据存储到session中
        session=request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("roomlist",getRoomlist1());

      //  response.sendRedirect("index.html?param=name");
        User user = userRepository.findByUsernameAndPassword(username,password);

        String str = "";
        session.setAttribute("user",user);
        if (user!=null){

//            if(user.getAuth()=="1")
            if("1".equals(user.getAuth())){
                //User user1=userRepository.findByUsername(username);
                modelAndView.addObject("password",user.getPassword());
                modelAndView.addObject("sex",user.getSex());
                modelAndView.addObject("card",user.getCard());
                modelAndView.addObject("phone",user.getPhone());
                modelAndView.setViewName("hotelmain/Myhotellogin");
             }
            else{
                modelAndView.setViewName("admins/admin");
               }
        }else {
            session.setAttribute("no",1);
            modelAndView.setViewName("login");

        }

        return modelAndView;
    }
    @RequestMapping("/zhuye")
    public String zhuye(){
        String string="hotelmain/Myhotel";
        return string;
    }
    @RequestMapping("/zhuye2")
    public String zhuye2(){
        return "hotelmain/Myhotellogin";
    }
}



