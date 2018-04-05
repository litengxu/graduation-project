package com.example.demo2.Controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo2.domain.Room;
import com.example.demo2.domain.User;
import com.example.demo2.repository.RoomAdminRepository;
import com.example.demo2.repository.RoomRepository;
import com.example.demo2.repository.RoompageRepository;
import com.example.demo2.repository.userRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("room/*")
public class roomController {


    @Autowired
    private RoomAdminRepository roomAdminRepository;
    @Autowired
    private com.example.demo2.repository.userRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoompageRepository dao;
    /**
     * 从 用户存储库 获取用户列表
     * @return
     */
    private List<Room> getRoomlist(){

        List<Room> rooms=new ArrayList<>();

        for(Room room : roomRepository.findAll())
        {
            rooms.add(room);

        }
        return rooms;
    }
    @RequestMapping(value="/list2")
    @ResponseBody
    public String getrosom(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<String, Object>();
        int pages = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("limit"));
        int pageIndex = (pages - 1) * rows;
        map.put("page", pageIndex);// 起始条数
        map.put("size", rows);// 每页条数
     //   int page=pageIndex+rows;
        Pageable pageable = new PageRequest(pages-1, rows);
//        Gson gson = new Gson();
//        String json = gson.toJson(getRoomlist());
//        System.out.println(json);
//        String str="{\"code\":0,\"msg\":\"\",\"count\":34,\"data\":"+json+"}";
//        System.out.println(str);
       Sort sort = new Sort(Sort.Direction.DESC, "id");

        Page<Room> page = dao.findAll(pageable);
        List<Room> list=page.getContent();

        System.out.println("----------------");
    //    List<Room> list=roomRepository.page(page,pageIndex);
     //   String total = "";
       List<Room> list1=roomRepository.findAll();
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

    @RequestMapping(value="/list")
    @ResponseBody
    public ModelAndView getroom(){
        System.out.println("---------------");
        ModelAndView mv=new ModelAndView();
        mv.addObject("roomlist",getRoomlist());

        mv.setViewName("admins/room/roomlist");
        return mv;
    }
    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyroom( @PathVariable("id") Long id){
       Room room=roomRepository.getOne(id);
       ModelAndView mv=new ModelAndView();
       mv.addObject("room",room);
       mv.addObject("title","修改房间信息");
       mv.setViewName("admins/room/modify");
       return mv;

    }
    @PostMapping(value="/fresh")
    public ModelAndView fresh(Room room){
        roomRepository.save(room);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/room/list");
        return mv;
    }
    @RequestMapping(value="/add")
    public ModelAndView add(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("admins/room/add");
        return mv;
    }
    @RequestMapping(value = "/addroom")
    public ModelAndView addroom(HttpServletRequest request){
        System.out.println("12121");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/room/list");
        String type=request.getParameter("type");
        String count1=request.getParameter("count");
        Long count=Long.parseLong(count1);
        String price=request.getParameter("price");
        Room room=new Room();
        room.setType(type);
        room.setPrice(price);
        room.setCount(count);
        roomRepository.save(room);
        return mv;
    }

}
