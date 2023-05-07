package com.neau.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neau.login.entity.SysUserEntity;
import com.neau.login.mapper.SysUserMapper;
import com.neau.login.req.SysUserLoginReq;
import com.neau.login.req.SysUserSaveReq;
import com.neau.login.resp.SysUserLoginResp;
import com.neau.login.service.SysUserService;
import com.neau.login.utils.CopyUtil;
import com.neau.login.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
//implements表示接口的实现,必须实现接口中的所有方法
//此处继承SysUserService的方法并改写
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private SnowFlake snowFlake;


    @Override
    public SysUserEntity register(SysUserSaveReq req) {
        //
        SysUserEntity user = CopyUtil.copy(req, SysUserEntity.class);
        //ObjectUtils.isEmpty判断对象是否为空
        if(ObjectUtils.isEmpty(req.getId())){
            //查看用户名是否已注册
            SysUserEntity userDb = selectByLoginName(req.getLoginName());
            //用户名未被注册
            if(ObjectUtils.isEmpty(userDb)){
                //用算法给帮它设置一个新id
                user.setId(snowFlake.nextId());
                //这步是真正的往数据库里插数据了
                sysUserMapper.insert(user);
                //哈哈,没东西返回了
            }else{
                //用户名已被注册,返回null
                return null;
            }
        }
        //无用返回
        return user;
    }

    @Override
    public SysUserLoginResp login(SysUserLoginReq req) {
        SysUserEntity userDb = selectByLoginName(req.getLoginName());
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        if(ObjectUtils.isEmpty(userDb)){
            //用户不存在
            return null;
        }else {
            String s1 = userDb.getPassword();
            String s2 = req.getPassword();
            //进行字符串校验
            if(s1.equals(s2)){
                //密码验证成功
                SysUserLoginResp userLoginResp = CopyUtil.copy(userDb, SysUserLoginResp.class);
                return userLoginResp;
            }else {
                //密码校验失败
                return null;
            }
        }
    }

    //查询loginName是否被注册
    public SysUserEntity selectByLoginName(String loginName){
        //QueryWrapper<>()
        QueryWrapper<SysUserEntity> wrapper = new QueryWrapper<>();
        //wrapper.lambda().eq()单表查询,在"SysUserEntity::getLoginName"找一个指定的"loginName"
        wrapper.lambda().eq(SysUserEntity::getLoginName,loginName);
        //BaseMapper的selectList()方法,根据id批量查询
        List<SysUserEntity> userEntityList = sysUserMapper.selectList(wrapper);
        //CollectionUtils.isEmpty()判断集合是否为空
        if(CollectionUtils.isEmpty(userEntityList)){
            //若判断为空，则返回null,意思是没被注册过
            return  null;
        }else {
            //查到了,将这条数据返回
            return userEntityList.get(0);
        }
    }
}
