package com.shammocodes.BhaiCycle.controller;


import com.shammocodes.BhaiCycle.entity.User;
import com.shammocodes.BhaiCycle.service.AuthService;
import com.shammocodes.BhaiCycle.service.ValidationException;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {
    @Value("${BASE_URL:http://localhost:8080}")
    String BASE_URL;

    @Value("${FRONTEND_URL:http://localhost:3000}")
    String FRONTEND_URL;
    @Autowired
    public AuthService authService;
    @PostMapping(path = "/register/user")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws IOException {
        try {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();

            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            user.setVerificationcode(generatedString);
            authService.registerNewUser(user);
            HashMap<String, String> map = new HashMap<>();
            map.put("verify",generatedString);
            return ResponseEntity.ok(map);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
