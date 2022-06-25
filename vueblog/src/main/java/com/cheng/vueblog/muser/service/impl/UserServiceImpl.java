package com.cheng.vueblog.muser.service.impl;

import com.cheng.vueblog.muser.pojo.User;
import com.cheng.vueblog.muser.mapper.UserMapper;
import com.cheng.vueblog.muser.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houcheng
 * @since 2022-06-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
