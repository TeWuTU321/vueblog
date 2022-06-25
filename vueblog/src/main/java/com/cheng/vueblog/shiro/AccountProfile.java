package com.cheng.vueblog.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * 表示可以公开的用户信息
 */
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

}
