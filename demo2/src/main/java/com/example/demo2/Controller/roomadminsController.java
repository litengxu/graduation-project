package com.example.demo2.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo2.Configure.JsonDateValueProcessor;
import com.example.demo2.domain.Juser;
import com.example.demo2.domain.Room;
import com.example.demo2.domain.RoomAdmin;
import com.example.demo2.domain.User;
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
            List<Room> list=new ArrayList<>();
            String username=request.getParameter("username");
            RoomAdmin roomAdmin=roomAdminRepository.findByUsername(username);
            if(roomAdmin==null){
                System.out.println("============="+list);
                mv.addObject("userList1",list);
                mv.addObject("title","住房管理");
                mv.setViewName("admins/roomadmins/roomadmin");
                return mv;
            }
            else {
                RoomAdmin roomAdmin1=roomAdminRepository.findByUsername(username);
                System.out.println(roomAdmin1);
                mv.addObject("user",roomAdmin1);
                mv.addObject("title","住房管理");
                mv.setViewName("admins/roomadmins/selectroomAdmin");
                return mv;
            }
        }
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
        String username=request.getParameter("username");
        String card=request.getParameter("card");
        String type=request.getParameter("type");
        String price=request.getParameter("price");
        String roomid=request.getParameter("roomid");
        String inday1=request.getParameter("inday");
        String outday1=request.getParameter("outday");
        String soutday1=request.getParameter("soutday");
        String money=request.getParameter("money");
        Date inday=null;
        Date outday=null;
        Date soutday=null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        format1.setLenient(false);
        try{
            inday = format1.parse(inday1);
            outday=format1.parse(outday1);
            soutday=format1.parse(soutday1);
        }catch(ParseException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,e);
        }
        RoomAdmin roomAdmin =roomAdminRepository.findByUsername(username);
        roomAdmin.setType(type);
        roomAdmin.setMoney(money);
        roomAdmin.setUsername(username);
        roomAdmin.setInday(inday);
        roomAdmin.setCard(card);
        roomAdmin.setOutday(outday);
        roomAdmin.setSoutday(soutday);
        roomAdmin.setRoomid(roomid);

        roomAdminRepository.save(roomAdmin);
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

        roomAdminRepository.deleteById(id);
        ModelAndView mv=new ModelAndView();
//        Gson gson = new Gson();
//        String json = gson.toJson(getUserlist());
        mv.addObject("roomadminlist",getroomadminlist());
        mv.addObject("title","住房管理");
        mv.setViewName("admins/roomadmins/roomadmin");

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
    @RequestMapping("/selectroomadmin")
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
    }

}
