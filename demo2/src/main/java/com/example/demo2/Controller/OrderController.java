package com.example.demo2.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo2.Configure.JsonDateValueProcessor;
import com.example.demo2.domain.*;
import com.example.demo2.repository.*;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.aspectj.weaver.ast.Or;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderPageRepository dao;
    @Autowired
    private RoomAdminRepository roomAdminRepository;
    @Autowired
    private historyRepository  historyRepository;
    @Autowired
    private userRepository userRepository;
    private List<Order> getorderlist() {
        List<Order> ra = new ArrayList<>();


        for(Order order : orderRepository.findAll()){



            ra.add(order);



        }



//        for (User user : userRepository.findAll()) {
//            users.add(user);
//        }
        return ra;
    }
    private List<Order> findusername(String username){
        List<Order> orders=new ArrayList<>();
        for(Order order:orderRepository.findByUsername(username)){
            orders.add(order);
        }
        return orders;
    }
    //查询当前表中的数据数量
    @RequestMapping("/count")
    public void count(HttpServletResponse response)throws IOException {
        int count=0;
        for (Order order:orderRepository.findAll()){
            count++;
        }
        response.getWriter().print(count);
    }
    @RequestMapping("/list")
    public String ll(){

        return "admins/order/list";
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

        Page<Order> page =dao.findAll(pageable);

        List<Order> list=page.getContent();
      /*  for(int i=0;i<list.size();i++){
            Date date= list.get(i).getInday();
            //date.getTime();
            System.out.println(date);
        }*/

        //    List<Room> list=roomRepository.page(page,pageIndex);
        //   String total = "";
        List<Order> list1=orderRepository.findAll();

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
    public ModelAndView modifyuser(@PathVariable("id") Long id, Model model){

        Order order =orderRepository.getOne(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("order",order);
        mv.addObject("title","修改用户");
        mv.setViewName("admins/order/rmodify");
        return mv;
    }
    @PostMapping(value="/fresh")
    public ModelAndView create(HttpServletRequest request) {
        String Id1=request.getParameter("id");
        Long Id=Long.parseLong(Id1);
        String username=request.getParameter("username");
        String card=request.getParameter("card");
        //String type=request.getParameter("type");
       String number1=request.getParameter("number");
       Long number=Long.parseLong(number1);
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
        for(Order order:orderRepository.findByUsername(username)){
           // order.setType(type);
           // order.setMoney(money);
            //order.setInday(inday);
            order.setCard(card);
            //order.setOutday(outday);
            //order.setSoutday(soutday);
            //order.setRoomid(roomid);
            //order.setNumber(number);
            orderRepository.save(order);
        }
        Order order=orderRepository.getOne(Id);
        order.setNumber(number);
        order.setMoney(money);
        order.setSoutday(soutday);
        for(History history: historyRepository.findByUsername(username)){
            //history.setType(type);
            history.setCard(card);
            //history.setMoney(money);
            //history.setSoutday(soutday);
            //history.setRoomid(roomid);
            //history.setOutday(outday);
            //history.setInday(inday);
            historyRepository.save(history);
        }
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/Order/list");
        return  mv;
    }
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
        orderRepository.deleteById(id);
        ModelAndView mv=new ModelAndView();
//        Gson gson = new Gson();
//        String json = gson.toJson(getUserlist());
      //  mv.addObject("roomadminlist",getroomadminlist());
     //   mv.addObject("title","住房管理");
        mv.setViewName("redirect:/Order/list");
        //遍历订单表中数据，当表中还存在和要删除的订单相同的用户名时，则直接返回退出，负责就查询并删除用户表中的数据
        for(RoomAdmin roomAdmin : roomAdminRepository.findAll()){

            if(roomAdmin.getUsername().equals(username)){
                return mv;
            }
        }
        //当Order表中还存在相同用户的订单时，同样不删除用户表中的数据
        for (Order order:orderRepository.findAll()){
            if(order.getUsername().equals(username)){
                return mv;
            }
        }
        User user = userRepository.findByUsername(username);
        Long b = user.getId();
        userRepository.deleteById(b);


        return mv;

    }
    //入住酒店，从Order表中删除并插入roomadmin表中和history表中。user表中不做修改
    @RequestMapping("inhotel/{id}")
    public ModelAndView inhotel(@PathVariable("id") Long id){
        Order order=orderRepository.getOne(id);
        String username=order.getUsername();
        String card=order.getCard();
        Date inday=order.getInday();
        Date outday=order.getOutday();
        Date soutday=order.getSoutday();
        String money=order.getMoney();
        Long number=order.getNumber();
        String roomid=order.getRoomid();
        String type=order.getType();
        User user=userRepository.findByUsername(username);
        String phone=user.getPhone();
        String sex=user.getSex();
        RoomAdmin roomAdmin=new RoomAdmin();
        roomAdmin.setUsername(username);
        roomAdmin.setCard(card);
        roomAdmin.setInday(inday);
        roomAdmin.setOutday(outday);
        roomAdmin.setSoutday(soutday);
        roomAdmin.setMoney(money);
        roomAdmin.setNumber(number);
        roomAdmin.setRoomid(roomid);
        roomAdmin.setType(type);
        roomAdminRepository.save(roomAdmin);//存入已入住订单表
        History history=new History();
        history.setUsername(username);
        history.setCard(card);
        history.setInday(inday);
        history.setOutday(outday);
        history.setSoutday(soutday);
        history.setMoney(money);
        history.setNumber(number);
        history.setRoomid(roomid);
        history.setType(type);
        history.setSex(sex);
        history.setPhone(phone);
        historyRepository.save(history);//存入历史信息表
        orderRepository.deleteById(id);//删除未入住订单表中信息
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("redirect:/Order/list");
        return  modelAndView;


    }
    @RequestMapping(value = "/search/{username}")
    @ResponseBody
    public String getsea(HttpServletRequest request,@PathVariable("username") String username){

        //String username=request.getParameter("username");


        //   username="李华";
        List<Order> list2=new ArrayList<>();
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
}
