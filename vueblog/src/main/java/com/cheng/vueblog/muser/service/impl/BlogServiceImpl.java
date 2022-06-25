package com.cheng.vueblog.muser.service.impl;

import com.cheng.vueblog.muser.pojo.Blog;
import com.cheng.vueblog.muser.mapper.BlogMapper;
import com.cheng.vueblog.muser.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
