package com.robb.spzx.manager.controller;

import com.robb.spzx.manager.service.SysUserService;
import com.robb.spzx.model.dto.system.LoginDto;
import com.robb.spzx.model.vo.common.Result;
import com.robb.spzx.model.vo.common.ResultCodeEnum;
import com.robb.spzx.model.vo.system.LoginVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/system/index")
@Tag(name = "User Interface")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    // User Login
    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto){
        LoginVo loginVo =  sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);

    }

}
