package com.cheng.vueblog.muser.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cheng.vueblog.commom.Result;
import com.cheng.vueblog.commom.dto.AccountDto;
import com.cheng.vueblog.muser.pojo.User;
import com.cheng.vueblog.muser.service.UserService;
import com.cheng.vueblog.util.JwtUtils;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 编写用户登录等
 * author:tutu
 * version:1.0
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody AccountDto accountDto, HttpServletResponse response){

        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, accountDto.getUsername()));
       if (BeanUtil.isEmpty(user)){
           return Result.error("-1","用户不存在");
       }
        if (!user.getPassword().equals(SecureUtil.md5(accountDto.getPassword()))){
            return Result.error("-1","密码错误");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("jwt",jwt)
                .map()
        );


    }
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success();
    }

}
