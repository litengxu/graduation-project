package com.example.demo2.Controller;

import com.example.demo2.domain.Room;
import com.example.demo2.domain.RoomAdmin;
import com.example.demo2.repository.RoomAdminRepository;
import com.example.demo2.repository.RoomRepository;
import org.apache.catalina.Session;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.applet.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("qsea/*")
public class qseaController {
    @Autowired
    private RoomAdminRepository roomAdminRepository;
    @Autowired
    private RoomRepository roomRepository;
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
    @RequestMapping("/search")
    public ModelAndView sea(HttpServletRequest request, Session session){
        String inday=request.getParameter("inday");
        System.out.println(inday);
        String outday=request.getParameter("outday");
        String type=request.getParameter("type");
        System.out.println(type);
        ModelAndView mv=new ModelAndView();
        mv.addObject("inday",inday);
        mv.addObject("outday",outday);
        mv.addObject("type",type);
        mv.addObject("roomlist",getRoomlist1());
        mv.addObject("error",0);
        mv.setViewName("/hotelmain/searchroom");
        return mv;
    }
    @RequestMapping("/sosuo")
    public ModelAndView sosuo(HttpServletRequest request, Session session){
        String inday1=request.getParameter("inday");
        String outday1=request.getParameter("outday");
        String number=request.getParameter("number");
        String type=request.getParameter("type");
       /* Date inday=null;
        Date outday=null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        format1.setLenient(false);
        try{
            inday = format1.parse(inday1);
            outday=format1.parse(outday1);
        }catch(ParseException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,e);
        }
        List<RoomAdmin> roomAdmins=roomAdminRepository.findByInday(inday);
        int c=0;
        for(RoomAdmin roomAdmin : roomAdmins){



           if (roomAdmin.getType()==type){

           }



        }*/

        ModelAndView mv=new ModelAndView();
        mv.addObject("inday",inday1);
        mv.addObject("outday",outday1);
        mv.addObject("username");
        mv.setViewName("/hotelmain/rooom1");
        return mv;
    }
    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,HttpServletRequest request){
        if(id==1){
            return "/hotelmain/room1";
        }
        else {
            return "/hotelmain/room2";
        }

    }
}
