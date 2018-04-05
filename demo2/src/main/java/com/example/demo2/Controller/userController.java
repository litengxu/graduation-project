package com.example.demo2.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo2.domain.Juser;
import com.example.demo2.domain.Room;
import com.example.demo2.domain.RoomAdmin;
import com.example.demo2.domain.User;
import com.example.demo2.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users/*")
public class userController {

    @Autowired
    private RoomAdminRepository roomAdminRepository;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private JuserRepository juserRepository;
    @Autowired
    private userPageRepository dao;
    @Autowired
    private juserPageRepository juserPageRepository;
    /**
     * 从 用户存储库 获取用户列表
     * @return
     */

    public List<User> getUserlist() {
            List<User> users = new ArrayList<>();


            for(User user : userRepository.findAll()){

                if("1".equals(user.getAuth())){

                    users.add(user);
                }


            }



//        for (User user : userRepository.findAll()) {
//            users.add(user);
//        }
        return users;
    }

    @RequestMapping("/admin")
    @ResponseBody
    public ModelAndView getuser(Model model,HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
//          Gson gson = new Gson();
//          String json = gson.toJson(getUserlist());
        if(request.getParameter("username")==null)
        {   System.out.println("12");
            mv.addObject("userList1",getUserlist());
            mv.addObject("userList", getUserlist());
            mv.addObject("title", "用户管理");
            mv.setViewName("admins/useradmins/list");
            return mv;
        }
        else{
            List<User> users = new ArrayList<>();
            System.out.println("898");
            String username=request.getParameter("username");
            User user = userRepository.findByUsername(username);
            System.out.println(user);
            if (user==null)
            {
                //users=null;
                List<User> users1=new ArrayList<>();
                mv.addObject("userList1",users1);
                mv.addObject("userList", getUserlist());
                mv.addObject("title", "用户管理");
                mv.setViewName("admins/useradmins/list");
                return mv;
            }
           else {
                String username1=request.getParameter("username");
                User user1 = userRepository.findByUsername(username);
                users.add(user);
                System.out.println(users.size());
               // mv.addObject("userList1",users);
                mv.addObject("user", user1);
                System.out.println(users.size());
                mv.addObject("title", "用户管理");
                mv.setViewName("admins/useradmins/selectuser");
                return mv;
            }

        }
        /*System.out.println("asdasdasds");
        model.addAttribute("userlist",getUserlist());
         model.addAttribute("title","用户管理");

        System.out.println("daoledale");
        return new ModelAndView("users/list", "userModel", model);*/

    }
    @RequestMapping(value="/list2")
    @ResponseBody
    public String getrosom(HttpSession session, HttpServletRequest request, HttpServletResponse response){

        int pages = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("limit"));


        //   int page=pageIndex+rows;
        Pageable pageable = new PageRequest(pages-1, rows);
//        Gson gson = new Gson();
//        String json = gson.toJson(getRoomlist());
//        System.out.println(json);
//        String str="{\"code\":0,\"msg\":\"\",\"count\":34,\"data\":"+json+"}";
//        System.out.println(str);


        Page<User> page = dao.findAll(pageable);
        List<User> list=page.getContent();

        System.out.println("----------------");
        //    List<Room> list=roomRepository.page(page,pageIndex);
        //   String total = "";
        List<User> list1=userRepository.findAll();
        int total=list1.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", total);
        jsonObject.put("data", list);
        //System.out.println("json:----" + jsonObject.toString());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();


    }
    private List<Juser> getUserlist1() {

        List<Juser> users = new ArrayList<>();

        for(Juser user : juserRepository.findAll()){



                users.add(user);



        }



//        for (User user : userRepository.findAll()) {
//            users.add(user);
//        }
        return users;
    }



    //    private void  dad(){
//        System.out.println("111");
//
//
//    }
    @RequestMapping("/juser")
    @ResponseBody
    public ModelAndView getuser1(Model model){
        ModelAndView mv=new ModelAndView();
//          Gson gson = new Gson();
//          String json = gson.toJson(getUserlist());
        mv.addObject("userList",getUserlist1());
        mv.addObject("title","用户管理");
        mv.setViewName("admins/useradmins/juserlist");
        return mv;
        /*System.out.println("asdasdasds");
        model.addAttribute("userlist",getUserlist());
         model.addAttribute("title","用户管理");

        System.out.println("daoledale");
        return new ModelAndView("users/list", "userModel", model);*/

    }
    @RequestMapping(value="/list3")
    @ResponseBody
    public String getrosom1(HttpSession session, HttpServletRequest request, HttpServletResponse response){

        int pages = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("limit"));


        //   int page=pageIndex+rows;
        Pageable pageable = new PageRequest(pages-1, rows);
//        Gson gson = new Gson();
//        String json = gson.toJson(getRoomlist());
//        System.out.println(json);
//        String str="{\"code\":0,\"msg\":\"\",\"count\":34,\"data\":"+json+"}";
//        System.out.println(str);


        Page<Juser> page = juserPageRepository.findAll(pageable);
        List<Juser> list=page.getContent();

        System.out.println("----------------");
        //    List<Room> list=roomRepository.page(page,pageIndex);
        //   String total = "";
        List<Juser> list1=juserRepository.findAll();
        int total=list1.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", total);
        jsonObject.put("data", list);
        //System.out.println("json:----" + jsonObject.toString());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();


    }
   /* @RequestMapping("/selectusername")
    public ModelAndView selectusername(HttpServletRequest request,Model model){

            ModelAndView mv=new ModelAndView();
            String username=request.getParameter("username");
            if (userRepository.findByUsername(username)!=null) {
                User user = userRepository.findByUsername(username);
                mv.addObject("user", user);
                mv.setViewName("admins/useradmins/selectuser");
                return mv;
            }
            else{
                mv.setViewName("admins/useradmins/selectuser");
                return mv;
            }

    }*/

    @GetMapping(value="delete/{username}")
    public ModelAndView deleteuser(@PathVariable("username") String username,Model model){
        RoomAdmin roomAdmin=roomAdminRepository.findByUsername(username);
        Long a=roomAdmin.getId();
       /* String type=roomAdmin.getType();
        Room room = roomRepository.findByType(type);
        Long count=room.getCount();
        count++;
        room.setCount(count);
        roomRepository.save(room);*/
        roomAdminRepository.deleteById(a);
        User user=userRepository.findByUsername(username);
        Long b=user.getId();
        userRepository.deleteById(b);
        ModelAndView mv=new ModelAndView();
        mv.addObject("userList",getUserlist());
        mv.addObject("title","用户管理");
        mv.setViewName(" users/admin");
        return mv;

    }
    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyuser(@PathVariable("id") Long id,Model model){
        User user =userRepository.getOne(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("user",user);
        mv.addObject("title","修改用户");
        mv.setViewName("admins/useradmins/modify");
        return mv;
    }
    @PostMapping(value="/fresh")
    public ModelAndView create(User user ) {
        userRepository.save(user);
        ModelAndView mv=new ModelAndView();

        mv.setViewName("redirect:/users/admin");
        return  mv;
    }
  /*  @RequestMapping(value = "/getExamsByPage")
    @ResponseBody
    public Page<User> getExamsByPage(@RequestParam(value = "currentPage",defaultValue = "0") Integer page,
                                     @RequestParam(value = "numPerPage",defaultValue = "10") Integer pageSize,
    HttpServletResponse response)throws Exception  {
        Sort sort = new Sort(Sort.Direction.DESC, "id");//设置排序方式
        Pageable pageable = new PageRequest(page, pageSize, sort);//构建Pageable对象，改分页查询是通过jpa的PagingAndSortingRepository接口完成的
        Page<User> Exams = userRepository.findAll(pageable);
           //将java类对象转换为json字符串

        PrintWriter pw=response.getWriter();
        pw.print(Exams);
        pw.flush();
        pw.close();
        return Exams;
    }*/

   /* @RequiresPermissions("sys:table:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<User> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                   @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                   ServletRequest request){
        Map<String,Object> map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<User> tableLayerData = new LayerData<>();
        if(!map.isEmpty()){
            String name = (String) map.get("name");
            if(StringUtils.isBlank(name)) {
                map.remove("name");
            }
        }
        Page<User> tablePage = tableService.selectTablePage(new Page<>(page,limit),map);
        tableLayerData.setCount(tablePage.getTotal());
        tableLayerData.setData(tablePage.getRecords());
        return tableLayerData;
    }*/
//    @RequestMapping("/adminJson")
////    public void toJson(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="page",required=false,defaultValue="1") int page,
////                       @RequestParam(value="limit",required=false,defaultValue="10") int limit){
////        response.setCharacterEncoding("UTF-8");
////        response.setContentType("text/html;charset=UTF-8");
////        PrintWriter wirte = null;
////        Gson gson = new Gson();
////        String json = gson.toJson(getUserlist());
////        wirte.println(json);
////        wirte.flush();
////        wirte.close();
////    }
}
