package com.shammocodes.BhaiCycle.controller;

import com.shammocodes.BhaiCycle.entity.User;
import com.shammocodes.BhaiCycle.repository.UserRepository;
import com.shammocodes.BhaiCycle.security.JwtUtil;
import com.shammocodes.BhaiCycle.service.UserService;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




@CrossOrigin(origins = "*")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MessengerController messengerController;
    @PostMapping("/addUser")
    public String add(@RequestBody User user) {
        userService.saveUser(user);
        return "New User is Added";
    }

    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/availableCycles")
    public List<User> getAvailableCycles() {return userRepository.getAvailableCycles();}

    @PostMapping("/makeAvailable/{username}/{checked}")
    public String makeAvailable(@PathVariable String username ,@PathVariable String checked){
        User cur = userService.findByUsername(username);
        cur.setCycle_status(checked);
        userService.updateUser(cur);
        return "done makeavailable";
    }

    @PostMapping("/sendRequest/{usernameFrom}/{usernameTo}/{tim}/{min}")
    public String sendRequest(@PathVariable String usernameFrom,@PathVariable String usernameTo , @PathVariable long tim ,  @PathVariable long min) {
        User to = userService.findByUsername(usernameTo);
        User from = userService.findByUsername(usernameFrom);
        from.setReq("0");
        from.setH(0);
        long currentTimestamp = System.currentTimeMillis() + 6 * 60 * 60 * 1000;
        long endTimestamp = currentTimestamp + tim * 60 * 60 * 1000 + min * 60 * 1000;
        System.out.println(currentTimestamp);

        System.out.println(endTimestamp);
        from.setEndinstance(endTimestamp);
        Date endDate = new Date(endTimestamp);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");


       // Instant.now().plus(tim * 60 * 60 * 1000);
      //  DateTime dateTimeFromInstant = endTimestamp.toDateTime();
        userService.updateUser(from);
        String text = usernameFrom + " Send a Request to borrow Your Cycle. Message back " + usernameFrom + " for acknowledgement" + " He/she will return back your cycle before " + df.format(endDate) ;
        messengerController.sendTextMessage(to.getMessengerid(),text);

        return text;
    }

    @GetMapping("/test")
    public long test() {
        return System.currentTimeMillis();
    }

    @GetMapping("/dashboard")
    public List<?> dashboard(){
        List<User> cur = userService.getUsers();
        List<User> ret = new ArrayList<>();
        for(User it : cur) {
            User temp = it;
            if(temp.getReq() != null) {
                long currentTimestamp = System.currentTimeMillis() + 6 * 60 * 60 * 1000;
                long endTimestamp = temp.getEndinstance();
                Duration duration = new Duration(currentTimestamp , endTimestamp);


                temp.setS(duration.getStandardSeconds());
                long totalSecs = duration.getStandardSeconds();
                long hours = totalSecs / 3600;
                long minutes = (totalSecs % 3600) / 60;
                long seconds = totalSecs % 60;
                temp.setH(hours);
                temp.setM(minutes);





                User lol = userService.findByUsername(it.getReq());
                if(duration.getStandardMinutes() < 0 || duration.getStandardHours() < 0 || duration.getStandardSeconds() < 0){
                    continue;
                }

                if(lol == null) continue;



                temp.setCycle(lol.getName());
                if(hours == 0 && minutes <= 5 && it.getH() == 0){
                    String text = "You must return Cycle within " + hours + " hours : " + minutes + " minutes";
                    User tmp = it;
                    tmp.setH(1);
                    userService.updateUser(tmp);
                    messengerController.sendTextMessage(it.getMessengerid(),text);
                }
                ret.add(temp);
            }
        }
        return ret;
    }

    @GetMapping("/contribution")
    public List<User> contribution() {
        List<User> cur =  userService.findTop10ByContributionDesc();
        List<User> ret = new ArrayList<User>();
        for(User it : cur) {
            if(it.getContribution() > 0){
                ret.add(it);
            }
        }
        return ret;
    }



}
