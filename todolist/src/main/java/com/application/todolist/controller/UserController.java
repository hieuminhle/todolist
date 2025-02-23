package com.application.todolist.controller;

import com.application.todolist.entity.User;
import com.application.todolist.service.UserService;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final String salt = "0cd62aff-203a-4282-8025-170b5cdd70a0";
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String Login(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();

        User findUser = userService.getUserByEmail(user.getEmail());
        if (findUser != null) {
            String password = new SimpleHash("md5", user.getPassword(), salt,2).toString();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), password);
            subject.login(token);
            Map<String, String> result = new HashMap<>();
            result.put("token", subject.getSession().getId().toString());
            return new Gson().toJson(result);
        } else {
            throw new IncorrectCredentialsException();
        }
    }

    @PostMapping("/sign-up")
    public User SignUp(@RequestBody User paramUser) {
        User user = new User();
        user.setEmail(paramUser.getEmail());
        user.setName(paramUser.getName());


        String password= new SimpleHash("md5", paramUser.getPassword(),salt,2).toString();
        user.setPassword(password);;

        User hasUser = userService.getUserByEmail(user.getEmail());
        if (hasUser != null) {
            return null;
        }
        return userService.createUser(user);
    }

    @GetMapping("/info")
    public User GetUserInfo() {
        Subject subject = SecurityUtils.getSubject();

        User user = (User) subject.getPrincipal();

        if (user != null) {
            user.setPassword(null);
            return user;
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/ordinary")
    @RequiresRoles("admin")
    public List<User> getOrdinaryUser() {
        return userService.getOrdinaryUser();
    }

    @DeleteMapping("/{id}")
    @RequiresRoles("admin")
    public void DeleteUser(@PathVariable int id) {
        Optional<User> findUser = userService.getUserById(id);
        if (findUser.isPresent()) {
            userService.deleteUser(id);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
    }

    @PutMapping("/{id}")
    @RequiresRoles("admin")
    public User UpdateUser(@PathVariable int id, @RequestBody User paramUser) {
        Optional<User> findUser = userService.getUserById(id);
        if (findUser.isPresent()) {
            User user = findUser.get();
            user.setName(paramUser.getName());

            String password= new SimpleHash("md5", paramUser.getPassword(),salt,2).toString();
            user.setPassword(password);
            return userService.updateUser(user);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
    }
}
