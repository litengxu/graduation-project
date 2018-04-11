package com.example.demo2.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo2.Configure.JsonDateValueProcessor;
import com.example.demo2.domain.*;
import com.example.demo2.repository.*;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.applet.Main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("roomadmins/*")
public class roomadminsController {
    @Autowired
    private RoomAdminRepository roomAdminRepository;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private JuserRepository juserRepository;
    @Autowired
    private RoomadminPageRepository dao;
    @Autowired
    private historyRepository historyRepository;
    /**
     *
     * 从 用户存储库 获取用户列表
     * @return
     */
    private List<RoomAdmin> getroomadminlist() {
        List<RoomAdmin> ra = new ArrayList<>();


        for(RoomAdmin roomAdmin : roomAdminRepository.findAll()){



            ra.add(roomAdmin);



        }



//        for (User user : userRepository.findAll()) {
//            users.add(user);
//        }
        return ra;
    }
    private List<RoomAdmin> findusername(String username){
        List<RoomAdmin> roomAdmins=new ArrayList<>();
        for(RoomAdmin roomAdmin:roomAdminRepository.findByUsername(username)){
            roomAdmins.add(roomAdmin);
        }
        return roomAdmins;
    }
    //查询当前表中的数据数量
    @RequestMapping("/count")
    public void count(HttpServletResponse response)throws IOException{
        int count=0;
        for (RoomAdmin roomAdmin:roomAdminRepository.findAll()){
            count++;
        }
        System.out.println(count);
        response.getWriter().print(count);
    }

    //当前订单表,为实现异步刷新，当搜索时也是此链接
    @RequestMapping("/roomadmin")
    @ResponseBody
    public ModelAndView getuser(HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        if(request.getParameter("username")==null){
            mv.addObject("userList1",getroomadminlist());
            mv.addObject("title","住房管理");
            mv.setViewName("admins/roomadmins/roomadmin");
            return mv;
        }
        else{
            //未找到
            List<Room> list=new ArrayList<>();
            String username=request.getParameter("username");
            List<RoomAdmin> list2=new ArrayList<>();
            list2=findusername(username);
            if(list2.size()==0){
                System.out.println("============="+list);
                mv.addObject("userList1",list);
                mv.addObject("title","住房管理");
                mv.setViewName("admins/roomadmins/roomadmin");
                return mv;
            }
            else {
                //找到了表中的顾客，并跳转
                mv.addObject("username",username);
                mv.addObject("title","住房管理");
                mv.setViewName("admins/roomadmins/selectroomAdmin");
                return mv;
            }
        }
    }
    @RequestMapping(value = "/search/{username}")
    @ResponseBody
    public String getsea(HttpServletRequest request,@PathVariable("username") String username){

        //String username=request.getParameter("username");

        System.out.println(username);
    //   username="李华";
        List<RoomAdmin> list2=new ArrayList<>();
        System.out.println("=================++++++++++++++==");
        list2=findusername(username);
        int total=list2.size();
        JSONObject jsonObject = new JSONObject();
        JsonConfig jsonConfig = new JsonConfig();   //JsonConfig是net.sf.json.JsonConfig中的这个，为固定写法
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
        //  Collection<JsonBean> list2 = JSONArray
        JSONArray jo = JSONArray.fromObject(list2, jsonConfig);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", total);
        jsonObject.put("data", jo);
        //System.out.println("json:----" + jsonObject.toString());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();

    }
    @RequestMapping(value="/list2")
    @ResponseBody
    public String getrosom(HttpSession session, HttpServletRequest request, HttpServletResponse response){
       // Map<String, Object> map = new HashMap<String, Object>();
        int pages = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("limit"));
       // int pageIndex = (pages - 1) * rows;
        //map.put("page", pageIndex);// 起始条数
        //map.put("size", rows);// 每页条数
        //   int page=pageIndex+rows;
        Pageable pageable = new PageRequest(pages-1, rows);
//        Gson gson = new Gson();
//        String json = gson.toJson(getRoomlist());
//        System.out.println(json);
//        String str="{\"code\":0,\"msg\":\"\",\"count\":34,\"data\":"+json+"}";
//        System.out.println(str);
       // Sort sort = new Sort(Sort.Direction.DESC, "id");

        Page<RoomAdmin> page = dao.findAll(pageable);

        List<RoomAdmin> list=page.getContent();
      /*  for(int i=0;i<list.size();i++){
            Date date= list.get(i).getInday();
            //date.getTime();
            System.out.println(date);
        }*/

        //    List<Room> list=roomRepository.page(page,pageIndex);
        //   String total = "";
        List<RoomAdmin> list1=roomAdminRepository.findAll();

        int total=list1.size();
        JSONObject jsonObject = new JSONObject();
        JsonConfig jsonConfig = new JsonConfig();   //JsonConfig是net.sf.json.JsonConfig中的这个，为固定写法
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
        //  Collection<JsonBean> list2 = JSONArray
        JSONArray jo = JSONArray.fromObject(list, jsonConfig);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", total);
        jsonObject.put("data", jo);
        //System.out.println("json:----" + jsonObject.toString());
        System.out.println(jsonObject.toString());
        return jsonObject.toString();


    }


    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyuser(@PathVariable("id") Long id,Model model){
        RoomAdmin roomAdmin =roomAdminRepository.getOne(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("roomAdmin",roomAdmin);
        mv.addObject("title","修改用户");
        mv.setViewName("admins/roomadmins/rmodify");
        return mv;
    }
    @PostMapping(value="/fresh")
    public ModelAndView create(HttpServletRequest request) {
       String Id1=request.getParameter("id");
       Long Id=Long.parseLong(Id1);
        String username=request.getParameter("username");
        String card=request.getParameter("card");
        /*String type=request.getParameter("type");*/

       /* String roomid=request.getParameter("roomid");
        String inday1=request.getParameter("inday");
        String outday1=request.getParameter("outday");*/
        String soutday1=request.getParameter("soutday");
        String money=request.getParameter("money");

        Date soutday=null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        format1.setLenient(false);
        try{
           /* inday = format1.parse(inday1);
            outday=format1.parse(outday1);*/
            soutday=format1.parse(soutday1);
        }catch(ParseException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,e);
        }
        RoomAdmin roomAdmin1=roomAdminRepository.getOne(Id);
        roomAdmin1.setMoney(money);
        roomAdmin1.setSoutday(soutday);
       for(RoomAdmin roomAdmin:roomAdminRepository.findByUsername(username)){
             //  roomAdmin.setType(type);
               //roomAdmin.setMoney(money);
              // roomAdmin.setInday(inday);
               roomAdmin.setCard(card);
               //roomAdmin.setOutday(outday);
               //roomAdmin.setSoutday(soutday);
               //roomAdmin.setRoomid(roomid);
               roomAdminRepository.save(roomAdmin);
       }

        for(History history:historyRepository.findByUsername(username)){
            System.out.println("-=-=-===============");
           // history.setType(type);
            history.setCard(card);
            //history.setMoney(money);
          /*  history.setSoutday(soutday);
            history.setRoomid(roomid);
            history.setOutday(outday);
            history.setInday(inday);*/
            historyRepository.save(history);
        }
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/roomadmins/roomadmin");
        return  mv;
    }
    @Transactional
    @GetMapping(value="delete/{id}&{username}")
    public ModelAndView deleteuser(@PathVariable("id")Long id,@PathVariable("username")String username,Model model){
//        System.out.println("000000012");
//        roomAdminRepository.deleteByUsername("username");
//        System.out.println("12");
//
//        userRepository.deleteByUsername("username");
//        System.out.println("45");

      // RoomAdmin roomAdmin=roomAdminRepository.findById(id);
      //  Long a=roomAdmin.getId();
      /*  String type=roomAdmin.getType();
        Room room = roomRepository.findByType(type);
        Long count=room.getCount();
        count--;
        room.setCount(count);
        roomRepository.save(room);*/
        //删除房间订单表中的数据
        roomAdminRepository.deleteById(id);
        ModelAndView mv=new ModelAndView();
//        Gson gson = new Gson();
//        String json = gson.toJson(getUserlist());
        mv.addObject("roomadminlist",getroomadminlist());
        mv.addObject("title","住房管理");
        mv.setViewName("admins/roomadmins/roomadmin");
        //遍历订单表中数据，当表中还存在和要删除的订单相同的用户名时，则直接返回退出，负责就查询并删除用户表中的数据
        for(RoomAdmin roomAdmin : roomAdminRepository.findAll()){

                if(roomAdmin.getUsername().equals(username)){
                    return mv;
                }
        }

        if(userRepository.findByUsername(username)!=null) {
                User user = userRepository.findByUsername(username);
                Long b = user.getId();
                userRepository.deleteById(b);
            }
                else{

                        Juser juser = juserRepository.findByUsername(username);
                        Long b = juser.getId();
                        juserRepository.deleteById(b);
                    }
        return mv;

    }
   /* @RequestMapping("/selectroomadmin")
    public ModelAndView selectroomadmin(HttpServletRequest request){
        try{
            ModelAndView mv=new ModelAndView();
            String username=request.getParameter("username");
           RoomAdmin roomAdmin=roomAdminRepository.findByUsername(username);
            mv.addObject("user",roomAdmin);
            mv.addObject("title","选择住宿管理");
            mv.setViewName("admins/roomadmins/selectroomAdmin");
            return mv;
        }
        catch (Exception e){
            return null;
        }
    }*/

}
