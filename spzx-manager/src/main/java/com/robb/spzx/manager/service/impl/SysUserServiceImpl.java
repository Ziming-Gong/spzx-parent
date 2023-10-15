package com.robb.spzx.manager.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.robb.spzx.manager.mapper.SysUserMapper;
import com.robb.spzx.manager.service.SysUserService;
import com.robb.spzx.model.dto.system.LoginDto;
import com.robb.spzx.model.entity.system.SysUser;
import com.robb.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {

        //1. 获取提交过来的用户名LoginDto

        String userName = loginDto.getUserName();

        //2. 根据这个用户名查询表 sys_user
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);

        //3. 如果查不到， 登陆失败
        if (sysUser == null) {
            throw new RuntimeException("Not found User Info");
        }

        //4. 如果根据这个用户名 查询到用户信息， 用户存在
        //5. 获取输入的密码，比较输入的密码和数据库的密码是否一致
        String database_password = sysUser.getPassword();
        String input_password = loginDto.getPassword();
        // 把输入的密码进行加密，在进行比较 MD5加密
        input_password = DigestUtils.md5DigestAsHex(input_password.getBytes());
        if (!input_password.equals(database_password)) {
            throw new RuntimeException("wrong password");
        }

        //6. 如果密码一致，登陆成功
        //7. 登陆成功，生成用户的唯一标识Token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //8. 把登陆成功用户信息放入redis里面
        // key : token value: userInfo
        String userInfoJson = JSON.toJSONString(sysUser); // 把用户信息写成json
        redisTemplate.opsForValue().set("user:login" + token, userInfoJson, 7, TimeUnit.DAYS); // 7time有效


        //9. 返回loginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);


        return loginVo;
    }
}
