package com.robb.spzx.manager.mapper;

import com.robb.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    //根据用户名得到用户信息
    SysUser selectUserInfoByUserName(String userName);



}
