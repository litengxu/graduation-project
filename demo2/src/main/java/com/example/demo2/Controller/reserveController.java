package com.example.demo2.Controller;

import com.example.demo2.domain.*;
import com.example.demo2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.applet.Main;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(value="reserveroom/*")
public class reserveController {
    @Autowired
    private JuserRepository juserRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomAdminRepository roomAdminRepository;
    @Autowired
    private userRepository userRepository;
    @Autowired
    private historyRepository historyRepository;
    @Autowired
    private OrderRepository orderRepository;
    private List<Room> getRoomlist(){

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
    @RequestMapping("/reserve")
    public ModelAndView yuding(){

        ModelAndView mv=new ModelAndView();
        mv.addObject("error",0);
        mv.addObject("roomlist",getRoomlist());
        mv.setViewName("admins/reserveroom/reserve");
        return mv;
    }
    @RequestMapping("/yuding")
    public ModelAndView reserve(HttpServletRequest request){
        String username=request.getParameter("username");
        String sex=request.getParameter("sex");
        String phone=request.getParameter("phone");
        String card=request.getParameter("card");
        String inday1=request.getParameter("inday");

        String outday1=request.getParameter("outday");
        String soutday1=request.getParameter("soutday");
        Date inday2=null;
        Date outday2=null;
        Date soutday=null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        format1.setLenient(false);
        try{
            inday2 = format1.parse(inday1);
            outday2=format1.parse(outday1);
            soutday=format1.parse(soutday1);

        }catch(ParseException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,e);
        }

        String type=request.getParameter("type");
        System.out.println(type);
        String number1=request.getParameter("number");
        Long number=Long.parseLong(number1);
        String money=request.getParameter("money");
        ModelAndView mv=new ModelAndView();
        int i=0;

        //找到相应房间类型的所有房间号
        for(Room room:roomRepository.findAll()){
            if(room.getType().equals(type)){
                i++;
                }
        }
        String[] count=new String[i];
        int k=0;
        for(Room room:roomRepository.findAll()){
            if(room.getType().equals(type)){
                count[k]=room.getRoomid();
                System.out.println(count[k]);
                k++;
            }
        }
        //按房间号逐个遍历
        int tt=0;
        System.out.println(count.length);
        for(int j=0;j<count.length;j++){
            //从订单表中根据房间号找到所有对应的订单，并存入list
            if(tt==1){
                break;
            }
            System.out.println("-------"+count[j]);
            List<RoomAdmin> roomAdmins=new ArrayList<>();
                for(RoomAdmin roomAdmin:roomAdminRepository.findAll()){
                    if(roomAdmin.getRoomid().equals(count[j])){
                        roomAdmins.add(roomAdmin);
                    }
                }
                //从list中取出已有的预订日期和预订者的日期进行比较，当不满足四种错误的情况时
            //则可以进行预订
                int kk=0;
                for(RoomAdmin roomAdmin:roomAdmins){
                    Date inday=roomAdmin.getInday();
                    Date outday=roomAdmin.getOutday();

                   if(inday.compareTo(inday2)<=0&&outday2.compareTo(outday)<=0){
                       System.out.println("1失败"+count[j]+roomAdmin.getId());
                       kk=1;
                       break;
                   }
                    if(inday.compareTo(inday2)<0&&inday2.compareTo(outday)<0&&outday.compareTo(outday2)<0){
                        System.out.println("2失败"+count[j]);
                        kk=1;
                       break;
                    }
                    if(inday2.compareTo(inday)<=0&&outday.compareTo(outday2)<=0){
                        System.out.println("3失败"+count[j]);
                        kk=1;
                       break;
                    }
                    if(inday2.compareTo(inday)<0&&inday.compareTo(outday2)<0&&outday2.compareTo(outday)<0){
                        System.out.println("4失败"+count[j]);
                        kk=1;
                       break;
                    }


                }

                    if(kk==0){
                        System.out.println("预订成功"+count[j]);
                        String roomid=count[j];
                        Juser juser1=juserRepository.findByUsername(username);//若到店预订表中不存在该用户，则新建。
                        if(juser1==null){
                            Juser juser=new Juser();
                            juser.setCard(card);
                            juser.setUsername(username);
                            juser.setSex(sex);
                            juser.setPhone(phone);
                            juserRepository.save(juser);
                        }
                        RoomAdmin roomAdmin2=new RoomAdmin();
                        roomAdmin2.setCard(card);
                        roomAdmin2.setUsername(username);
                        roomAdmin2.setInday(inday2);
                        roomAdmin2.setSoutday(soutday);
                        roomAdmin2.setOutday(outday2);
                        roomAdmin2.setMoney(money);
                        roomAdmin2.setNumber(number);
                        roomAdmin2.setType(type);
                        roomAdmin2.setRoomid(roomid);
                        roomAdminRepository.save(roomAdmin2);
                        History history=new History();
                        history.setCard(card);
                        history.setInday(inday2);
                        history.setMoney(money);
                        history.setNumber(number);
                        history.setOutday(outday2);
                        history.setPhone(phone);
                        history.setRoomid(roomid);
                        history.setSex(sex);
                        history.setSoutday(soutday);
                        history.setUsername(username);
                        history.setType(type);
                        historyRepository.save(history);
                        break;

                    }
                    if(j==count.length-1){
                        mv.addObject("error",1);
                        mv.addObject("roomlist",getRoomlist());
                        mv.setViewName("admins/reserveroom/reserve");
                        return mv;
                    }

        }


       /* Room room=roomRepository.findByType(type);
        Long count=room.getCount();
        room.setCount(count--);
         roomRepository.save(room);*/

        mv.addObject("error",0);
        mv.setViewName("redirect:/roomadmins/roomadmin");
        return mv;
    }
    //用户自行预订，成功后加入Order表中
    @RequestMapping("/qyuding")
    public ModelAndView qreserve(HttpServletRequest request){
        String username=request.getParameter("username");
        String inday1=request.getParameter("inday");
        String outday1=request.getParameter("outday");
        Date inday2=null;
        Date outday2=null;
        Date soutday=null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        format1.setLenient(false);
        try{
            inday2 = format1.parse(inday1);
            outday2=format1.parse(outday1);
            soutday=format1.parse(outday1);

        }catch(ParseException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,e);
        }

        String type=request.getParameter("type");
        System.out.println(type);
        String number1=request.getParameter("number");
        Long number=Long.parseLong(number1);
        User user=userRepository.findByUsername(username);
        String card=user.getCard();
        String money="";
        for(Room room : roomRepository.findAll())
        {
            if(room.getType().equals(type)){
                money=room.getPrice();
            }

        }
        System.out.println("++++++++++++++++++++=");
       // Room room2=roomRepository.findByType(type);
       // String money=room2.getPrice();
        System.out.println(money);
        ModelAndView mv=new ModelAndView();
        int i=0;

        //找到相应房间类型的所有房间号
        for(Room room:roomRepository.findAll()){
            if(room.getType().equals(type)){
                i++;
            }
        }
        String[] count=new String[i];
        int k=0;
        for(Room room:roomRepository.findAll()){
            if(room.getType().equals(type)){
                count[k]=room.getRoomid();
                System.out.println(count[k]);
                k++;
            }
        }
        //按房间号逐个遍历
        int tt=0;
        System.out.println(count.length);
        for(int j=0;j<count.length;j++){
            //从订单表中根据房间号找到所有对应的订单，并存入list
            if(tt==1){
                break;
            }
            System.out.println("-------"+count[j]);
            List<RoomAdmin> roomAdmins=new ArrayList<>();
            for(RoomAdmin roomAdmin:roomAdminRepository.findAll()){
                if(roomAdmin.getRoomid().equals(count[j])){
                    roomAdmins.add(roomAdmin);
                }
            }
            //从list中取出已有的预订日期和预订者的日期进行比较，当不满足四种错误的情况时
            //则可以进行预订
            int kk=0;
            for(RoomAdmin roomAdmin:roomAdmins){
                Date inday=roomAdmin.getInday();
                Date outday=roomAdmin.getOutday();

                if(inday.compareTo(inday2)<=0&&outday2.compareTo(outday)<=0){
                    System.out.println("1失败"+count[j]+roomAdmin.getId());
                    kk=1;
                    break;
                }
                if(inday.compareTo(inday2)<0&&inday2.compareTo(outday)<0&&outday.compareTo(outday2)<0){
                    System.out.println("2失败"+count[j]);
                    kk=1;
                    break;
                }
                if(inday2.compareTo(inday)<=0&&outday.compareTo(outday2)<=0){
                    System.out.println("3失败"+count[j]);
                    kk=1;
                    break;
                }
                if(inday2.compareTo(inday)<0&&inday.compareTo(outday2)<0&&outday2.compareTo(outday)<0){
                    System.out.println("4失败"+count[j]);
                    kk=1;
                    break;
                }


            }

            if(kk==0){
                System.out.println("预订成功"+count[j]);
                String roomid=count[j];
                Order order=new Order();
                order.setCard(card);
                order.setUsername(username);
                order.setInday(inday2);
                order.setSoutday(soutday);
                order.setOutday(outday2);
                order.setMoney(money);
                order.setNumber(number);
                order.setType(type);
                order.setRoomid(roomid);
                orderRepository.save(order);
                User user1=userRepository.findByUsername(username);
                String phone=user1.getPhone();
                String sex=user1.getSex();
               /* History history=new History();
                history.setCard(card);
                history.setInday(inday2);
                history.setMoney(money);
                history.setNumber(number);
                history.setOutday(outday2);
                history.setPhone(phone);
                history.setRoomid(roomid);
                history.setSex(sex);
                history.setSoutday(soutday);
                history.setUsername(username);
                history.setType(type);
                historyRepository.save(history);*/
                break;

            }
            if(j==count.length-1){
                mv.addObject("inday",inday2);
                mv.addObject("outday",outday2);
                mv.addObject("type",type);
                mv.addObject("error",1);
                mv.addObject("roomlist",getRoomlist());
                mv.setViewName("/hotelmain/searchroom");
                return mv;
            }

        }


       /* Room room=roomRepository.findByType(type);
        Long count=room.getCount();
        room.setCount(count--);
         roomRepository.save(room);*/
        mv.addObject("inday",inday2);
        mv.addObject("outday",outday2);
        mv.addObject("type",type);
        mv.addObject("error",2);
        mv.addObject("roomlist",getRoomlist());
        mv.setViewName("/hotelmain/searchroom");
        return mv;
    }

}
