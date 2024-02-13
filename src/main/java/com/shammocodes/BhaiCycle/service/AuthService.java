package com.shammocodes.BhaiCycle.service;

import com.shammocodes.BhaiCycle.entity.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class AuthService {

    public static final Pattern USERNAME_MATCHER = Pattern.compile(
            "^[0-9]{7}$"
    );
    public static final Pattern PASSWORD_MATCHER = Pattern.compile(
            "^.{8,20}$"
    );

    @Value("${BASE_URL:http://localhost:8080}")
    String BASE_URL;

    @Value("${FRONTEND_URL:http://localhost:3000}")
    String FRONTEND_URL;
    @Autowired
    public UserService userService;

    private boolean isValid(User user) {
        String username = user.getUsername(), password = user.getPassword();
        return USERNAME_MATCHER.matcher(username).matches() &&
                PASSWORD_MATCHER.matcher(password).matches();
    }

    public String setUserActive(String actionId , String senderId) {
        User user = userService.findByVerificationcode(actionId);
          if(user != null){
              user.setActive(1);
              user.setMessengerid(senderId);
              userService.updateUser(user);
              return "Yay! Your account is activated";
          }else
          return "Wrong code. Try Again!";
    }

    @Transactional
    public void registerNewUser(User signUpData) throws ValidationException {

        if (userService.hasUser(signUpData) || !isValid(signUpData))
            throw new ValidationException();
        userService.saveUser(signUpData);


    }

}
