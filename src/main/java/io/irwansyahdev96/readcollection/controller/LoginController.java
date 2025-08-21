package io.irwansyahdev96.readcollection.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.irwansyahdev96.readcollection.dto.LoginDto;
import io.irwansyahdev96.readcollection.util.JwtUtil;

@RestController
@RequestMapping("login")
public class LoginController {

    // @Autowired
    // private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // @Autowired
    // private UserService userService;

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        // authenticationManager.authenticate(authentication);

        // final Optional<User> userOpt = userService.getByUsername(loginDto.getUsername());
        // final User user = userOpt.get();

        final Map<String,Object> claims = new HashMap<>() ;
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 1);

        claims.put("exp", cal.getTime());
        // claims.put("id", user.getId());
        // claims.put("rc", user.getRole());

        Map<String, String> message = new HashMap<>();

        // message.put("id", user.getId());
        // message.put("name", user.getName());
        // message.put("role",user.getRole());
        message.put("token",jwtUtil.generated(claims));

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
