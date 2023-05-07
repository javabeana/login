package com.neau.login.service;

import com.neau.login.entity.SysUserEntity;
import com.neau.login.req.SysUserLoginReq;
import com.neau.login.req.SysUserSaveReq;
import com.neau.login.resp.SysUserLoginResp;

public interface SysUserService {
    SysUserEntity register(SysUserSaveReq req);

    SysUserLoginResp login(SysUserLoginReq req);
}
