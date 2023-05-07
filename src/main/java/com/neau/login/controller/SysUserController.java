package com.neau.login.controller;

import com.neau.login.entity.SysUserEntity;
import com.neau.login.req.SysUserLoginReq;
import com.neau.login.req.SysUserSaveReq;
import com.neau.login.resp.CommonResp;
import com.neau.login.resp.SysUserLoginResp;
import com.neau.login.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("register")
//    zxcv1234
    //@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
    public CommonResp register(@RequestBody  SysUserSaveReq req){
        //对密码进行md5加密
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        //resp为返回数据
        CommonResp resp = new CommonResp<>();
        //真正的注册
        SysUserEntity saveResp = sysUserService.register(req);
        if(saveResp ==null){
            resp.setSuccess(false);
        }
        return resp;
    }

    @PostMapping("login")
    public CommonResp login(@RequestBody SysUserLoginReq req){
//        zxcv1234
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        SysUserLoginResp loginResp = sysUserService.login(req);
        if (loginResp == null) {
            resp.setSuccess(false);
        }
        resp.setContent(loginResp);
        return resp;
    }

}
