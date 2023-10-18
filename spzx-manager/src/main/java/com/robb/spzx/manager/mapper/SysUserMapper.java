package com.robb.spzx.manager.mapper;

import com.robb.spzx.model.dto.system.SysUserDto;
import com.robb.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    //根据用户名得到用户信息
    SysUser selectUserInfoByUserName(String userName);


    List<SysUser> findByPage(SysUserDto sysUserDto);

    void save(SysUser sysUser);

    void update(SysUser sysUser);

    void delete(Long userid);
}
