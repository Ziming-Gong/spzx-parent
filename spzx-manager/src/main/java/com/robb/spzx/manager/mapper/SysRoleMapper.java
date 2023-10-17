package com.robb.spzx.manager.mapper;

import com.robb.spzx.model.dto.system.SysRoleDto;
import com.robb.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);
}
