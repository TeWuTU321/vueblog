package com.cheng.vueblog.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.cheng.vueblog.muser.pojo.User;
import com.cheng.vueblog.muser.service.UserService;
import com.cheng.vueblog.util.JwtUtils;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author:tutu
 * version:1.0
 */
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authenticationToken;

        //        获取用户id，token.getPrincipal()转换为String类型，Object类型，
        //        etSubject()，获取在登录页面中存入其中的值
        String userId = jwtUtils.getClaimByToken((String) token.getPrincipal()).getSubject();

        User user = userService.getById(Long.parseLong(userId));

        if (user == null) {
            throw new UnknownAccountException("账户不存在！");
        }
        if (user.getStatus() == -1){
            throw new LockedAccountException("账户已被锁定！");
        }
//        重新编写一个类，用于暴露可见的用户信息
        AccountProfile accountProfile = new AccountProfile();
//        hutool的属性复制
        BeanUtil.copyProperties(user,accountProfile);

        return new SimpleAuthenticationInfo(accountProfile,token.getCredentials(),"");
    }
}
