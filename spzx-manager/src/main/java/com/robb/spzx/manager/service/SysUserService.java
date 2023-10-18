package com.robb.spzx.manager.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.pagehelper.PageInfo;
import com.robb.spzx.model.dto.system.LoginDto;
import com.robb.spzx.model.dto.system.SysUserDto;
import com.robb.spzx.model.entity.system.SysUser;
import com.robb.spzx.model.vo.system.LoginVo;

public interface SysUserService {


    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userid);
}
