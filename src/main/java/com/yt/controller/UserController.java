package com.yt.controller;

import com.yt.common.Const;
import com.yt.pojo.User;
import com.yt.service.IUserService;
import com.yt.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/portal/")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping("user/login.do")
    public ServerResponse login1(String username, String password, HttpSession session){
        ServerResponse serverResponse = userService.loginlogic(username, password);
        if (serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping("user/register.do")
    public ServerResponse register(User user){
        return userService.registerLogic(user);

    }


}
