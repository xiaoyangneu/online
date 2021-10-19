package com.yt.service;

import com.yt.pojo.User;
import com.yt.utils.ServerResponse;

public interface IUserService {
    /*
    * 登录
    * */
    public ServerResponse loginlogic(String username,String password);
    /*
    * 注册
    * */
    public ServerResponse registerLogic(User user);

}
