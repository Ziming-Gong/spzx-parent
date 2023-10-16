package com.robb.spzx.manager.controller;

import com.robb.spzx.manager.service.SysUserService;
import com.robb.spzx.manager.service.ValidateCodeService;
import com.robb.spzx.model.dto.system.LoginDto;
import com.robb.spzx.model.entity.system.SysUser;
import com.robb.spzx.model.vo.common.Result;
import com.robb.spzx.model.vo.common.ResultCodeEnum;
import com.robb.spzx.model.vo.system.LoginVo;
import com.robb.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/index")
@Tag(name = "User Interface")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    // User Login
    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);

    }

    /**
     * 生成图片验证码 generate validate picture code
     *
     * @return
     */
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);

    }


    /**
     * 获取当前登陆的用户信息
     *
     * @return
     */
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(@RequestHeader(name = "token") String token) {
        //1. 从请求头获取token
        //方法一： HttpServletRequest request
//        String token = request.getHeader(("token"));
        // 方法2
        // @RequestHeader(name = "token") String token

        //2. 根据token查询redis获取用户信息
        SysUser sysUser = sysUserService.getUserInfo(token);

        //返回用户信息

        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(name = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
