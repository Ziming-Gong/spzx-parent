package com.robb.spzx.manager.service;

import com.robb.spzx.model.dto.system.LoginDto;
import com.robb.spzx.model.vo.system.LoginVo;

public interface SysUserService {


    LoginVo login(LoginDto loginDto);
}
