package com.example.userserivce.controller;

import com.example.userserivce.dto.UserDto;
import com.example.userserivce.jpa.UserEntity;
import com.example.userserivce.service.UserService;
import com.example.userserivce.vo.Greeting;
import com.example.userserivce.vo.RequestUser;
import com.example.userserivce.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/user-service")
public class UserController {

    private UserService userService;
    private Environment env;

    @Autowired
    private Greeting greeting;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/heath_check")
    public String status() {
        return String.format("It's Working in User Service On Port %s", env.getProperty("local.server.port"));
    }


    @GetMapping("/welcome")
    public String welcome() {
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser user){
        // 클라이언트로 받은 Request를 Dto 타입으로 변환 -> 이후 service에서 entity로 변환 예정
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return  ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUserByAll(){
        Iterable<UserEntity> userList =userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
        UserDto userDto =userService.getUserByUserId(userId);


        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);


        return ResponseEntity.status(HttpStatus.OK).body(returnValue);

    }
}
