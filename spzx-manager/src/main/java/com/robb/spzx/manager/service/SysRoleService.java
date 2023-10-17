package com.robb.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.robb.spzx.model.dto.system.SysRoleDto;
import com.robb.spzx.model.entity.system.SysRole;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);
}
