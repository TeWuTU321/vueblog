package com.cheng.vueblog.muser.controller;


import com.cheng.vueblog.commom.Result;
import com.cheng.vueblog.muser.pojo.User;
import com.cheng.vueblog.muser.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houcheng
 * @since 2022-06-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @RequiresAuthentication
    @GetMapping("/index")
    public Result findUserById(){
        User user = userService.getById(1);
        return Result.success(user);
    };

    /**
     * @Validated 实体校验注解
     * @param user
     * @return
     */
    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        return Result.success(user);
    }
}

