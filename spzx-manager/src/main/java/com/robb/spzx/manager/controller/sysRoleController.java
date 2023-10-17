package com.robb.spzx.manager.controller;


import com.github.pagehelper.PageInfo;
import com.robb.spzx.manager.service.SysRoleService;
import com.robb.spzx.model.dto.system.SysRoleDto;
import com.robb.spzx.model.entity.system.SysRole;
import com.robb.spzx.model.vo.common.Result;
import com.robb.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class sysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 角色列表方法
     * current：当前页 limit：每页的限制数
     * systemRoleDto：条件角色名称对象
     */
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto) {
        //PageHelper来实现分页
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto, current, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

}
