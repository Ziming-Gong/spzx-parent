package com.robb.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.robb.spzx.manager.service.ValidateCodeService;
import com.robb.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {

        //1. 通过工具生成如骗的验证码 hutool
        //    public static CircleCaptcha createCircleCaptcha(int width, int height, int codeCount, int circleCount) {
        //  高度， 长度， code数量，干扰线的数量

        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);//
        String codeVal = circleCaptcha.getCode(); //4位验证码的值
        String imageBase64 = circleCaptcha.getImageBase64(); //把图片做成base64的编码

        //2. 把验证码存储到redis里面去设置 key：UUID value：验证码的值
        //设置过期时间

        String key = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:validate" + key, codeVal, 5, TimeUnit.MINUTES);

        //3. 返回validateVo数据
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64); // data:image/png;base64可以把编码的图片显示出来


        return validateCodeVo;
    }
}
