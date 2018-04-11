package com.example.demo2.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo2.Configure.JsonDateValueProcessor;
import com.example.demo2.domain.*;
import com.example.demo2.repository.historyRepository;
import com.example.demo2.repository.historypageRepository;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.applet.Main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/history/*")
public class historyController {
    @Autowired
    private historyRepository historyRepository;
    @Autowired
    private historypageRepository dao;
    private List<History> gethistorylist() {
        List<History> ra = new ArrayList<>();


        for(History history : historyRepository.findAll()){



            ra.add(history);



        }



//        for (User user : userRepository.findAll()) {
//            users.add(user);
//        }
        return ra;
    }
    private List<History> findusername(String username){
        List<History> histories=new ArrayList<>();
        for(History history:historyRepository.findByUsername(username)){
            histories.add(history);
        }
        return histories;
    }
    @RequestMapping("/historylist")
    public String ll(){
        return "/admins/history/list";
    }
    @RequestMapping("/list")
    @ResponseBody
    public ModelAndView getuser(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if (request.getParameter("username") == null) {

        }
        mv.addObject("userList1", gethistorylist());
        mv.addObject("title", "住房管理");
        mv.setViewName("admins/history/list");
        return mv;
 /*       else{
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
    }*/
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

        Page<History> page = dao.findAll(pageable);

        List<History> list=page.getContent();
      /*  for(int i=0;i<list.size();i++){
            Date date= list.get(i).getInday();
            //date.getTime();
            System.out.println(date);
        }*/

        //    List<Room> list=roomRepository.page(page,pageIndex);
        //   String total = "";
        List<History> list1=historyRepository.findAll();

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
    @RequestMapping(value = "/search/{username}")
    @ResponseBody
    public String getsea(HttpServletRequest request,@PathVariable("username") String username){

        //String username=request.getParameter("username");

        System.out.println(username);
        //   username="李华";
        List<History> list2=new ArrayList<>();
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
    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyuser(@PathVariable("id") Long id,Model model){
        System.out.println("77777777777777");
        History history =historyRepository.getOne(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("history",history);
        mv.addObject("title","修改用户");
        mv.setViewName("admins/history/rmodify");
        return mv;
    }
    @PostMapping(value="/fresh")
    public ModelAndView create(HttpServletRequest request) {
        String Id1=request.getParameter("id");
        Long Id=Long.parseLong(Id1);
        String username=request.getParameter("username");
        String card=request.getParameter("card");
        String phone=request.getParameter("phone");
        String sex=request.getParameter("sex");
        String soutday1=request.getParameter("soutday");
        String money=request.getParameter("money");
        Date soutday=null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        format1.setLenient(false);
        try{

            soutday=format1.parse(soutday1);
        }catch(ParseException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,e);
        }
        History history =historyRepository.getOne(Id);
     //   history.setType(type);
        history.setMoney(money);
        history.setUsername(username);
       // history.setInday(inday);
        history.setCard(card);
        //history.setOutday(outday);
        history.setSoutday(soutday);
        //history.setRoomid(roomid);
        //history.setType(type);
        history.setSex(sex);
        history.setPhone(phone);


        historyRepository.save(history);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/history/list");
        return  mv;
    }
    @GetMapping(value="delete/{id}&{username}")
    public ModelAndView deleteuser(@PathVariable("id")Long id,@PathVariable("username")String username,Model model) {

        historyRepository.deleteById(id);
        ModelAndView mv = new ModelAndView();


        mv.setViewName("redirect:/history/list");
        return mv;
        //遍历订单表中数据，当表中还存在和要删除的订单相同的用户名时，则直接返回退出，负责就查询并删除用户表中的数据

    }
}
