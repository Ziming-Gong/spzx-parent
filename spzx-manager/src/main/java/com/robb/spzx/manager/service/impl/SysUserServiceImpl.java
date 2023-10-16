package com.robb.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.robb.spzx.common.exception.CustomException;
import com.robb.spzx.manager.mapper.SysUserMapper;
import com.robb.spzx.manager.service.SysUserService;
import com.robb.spzx.model.dto.system.LoginDto;
import com.robb.spzx.model.entity.system.SysUser;
import com.robb.spzx.model.vo.common.ResultCodeEnum;
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


        //验证码验证
        //1. 获取输入验证码和存储到redis的key名称 loginDto获取到
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        //2. 根据获取到的redis里面的key，查询redis里面存储的验证码 !!!key的前缀一定要一样
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        //3. 比较输入的验证码和redis中的验证码是否一致
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsAnyIgnoreCase(redisCode, captcha)) {
            throw new CustomException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //4. 如果不一致测失败
        //5. 如果一致，删除redis里面的验证码
        redisTemplate.delete("user:validate" + key);

        //1. 获取提交过来的用户名LoginDto
        String userName = loginDto.getUserName();

        //2. 根据这个用户名查询表 sys_user
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);

        //3. 如果查不到， 登陆失败
        if (sysUser == null) {
//            throw new RuntimeException("Not found User Info");
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4. 如果根据这个用户名 查询到用户信息， 用户存在
        //5. 获取输入的密码，比较输入的密码和数据库的密码是否一致
        String database_password = sysUser.getPassword();
        String input_password = loginDto.getPassword();
        // 把输入的密码进行加密，在进行比较 MD5加密
        input_password = DigestUtils.md5DigestAsHex(input_password.getBytes());
        if (!input_password.equals(database_password)) {
//            throw new RuntimeException("wrong password");
            throw new CustomException(ResultCodeEnum.LOGIN_ERROR);
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

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }
}
