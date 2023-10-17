package com.robb.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.robb.spzx.manager.mapper.SysRoleMapper;
import com.robb.spzx.manager.service.SysRoleService;
import com.robb.spzx.model.dto.system.SysRoleDto;
import com.robb.spzx.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有数据
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        //封装pageInfo对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
