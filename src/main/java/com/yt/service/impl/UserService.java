package com.yt.service.impl;

import com.yt.common.Const;
import com.yt.common.ResponseCode;
import com.yt.mapper.UserMapper;
import com.yt.pojo.User;
import com.yt.service.IUserService;
import com.yt.utils.DateUtil;
import com.yt.utils.MD5Utils;
import com.yt.utils.ServerResponse;
import com.yt.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service

public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public ServerResponse loginlogic(String username, String password) {
        //1.用户名密码非空判断
        if (StringUtils.isBlank(username)) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(), ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if (StringUtils.isBlank(password)) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(), ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }

        //2.查看用户名是否存在
        Integer count = userMapper.findByUsername(username);
        if (count == 0) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.USERNAME_NOT_EXISTS.getCode(), ResponseCode.USERNAME_NOT_EXISTS.getMsg());
        }
        //3.根据用户名和密码是否存在
        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.getMD5Code(password));

        if (user == null) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.PASSWORD_ERROR.getCode(), ResponseCode.PASSWORD_ERROR.getMsg());
        }
        //4.返回结果
        return ServerResponse.creatServerResponseBySuccess(convert(user));

    }

    private UserVo convert(User user){
        UserVo userVo=new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setEmail(user.getEmail());
        userVo.setPhone(user.getPhone());
        userVo.setCreateTime(DateUtil.date2String(user.getCreateTime()));
        userVo.setUpdateTime(DateUtil.date2String(user.getUpdateTime()));
        return userVo;
    }
    @Override
    public ServerResponse registerLogic(User user) {
        user.setRole(Const.NORMAL_USER);//设置角色用户
        if (user == null) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.PARAMTER_NOT_EMPTY.getCode(), ResponseCode.PARAMTER_NOT_EMPTY.getMsg());
        }
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String question = user.getQuestion();
        String answer = user.getAnswer();
        String phone = user.getPhone();
        if (StringUtils.isBlank(username)) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(), ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if (StringUtils.isBlank(password)) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(), ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }

        if (StringUtils.isBlank(email)) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.EMAIL_NOT_EMPTY.getCode(), ResponseCode.EMAIL_NOT_EMPTY.getMsg());
        }
        if (StringUtils.isBlank(phone)) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.PHONE_NOT_EMPTY.getCode(), ResponseCode.PHONE_NOT_EMPTY.getMsg());
        }
        if (StringUtils.isBlank(question)) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.QUESTION_NOT_EMPTY.getCode(), ResponseCode.QUESTION_NOT_EMPTY.getMsg());
        }
        if (StringUtils.isBlank(answer)) {
            return ServerResponse.creatServerResponseByFail(ResponseCode.ANSWER_NOT_EMPTY.getCode(), ResponseCode.ANSWER_NOT_EMPTY.getMsg());
        }


        //判断用户名是否存在
        Integer count = userMapper.findByUsername(username);
        if (count>0){
            return ServerResponse.creatServerResponseByFail(ResponseCode.USERNAME_EXISTS.getCode(),ResponseCode.USERNAME_EXISTS.getMsg());
        }
        //邮箱是否存在
        Integer email1 = userMapper.findByEmail(email);
        if (email1>0){
            return ServerResponse.creatServerResponseByFail(ResponseCode.EMAIL_EXISTS.getCode(),ResponseCode.EMAIL_EXISTS.getMsg());
        }
        //注册
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        int i = userMapper.insert(user);
        if (i==0){
            //注册失败
            return ServerResponse.creatServerResponseByFail(ResponseCode.REGISTER_FALL.getCode(),ResponseCode.REGISTER_FALL.getMsg());
        }
        return ServerResponse.creatServerResponseBySuccess();

    }
}
