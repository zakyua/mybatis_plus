package com.atguigu.mybatis_plus.service.impl;

import com.atguigu.mybatis_plus.mapper.UserMapper;
import com.atguigu.mybatis_plus.pojo.User;
import com.atguigu.mybatis_plus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ChenCheng
 * @create 2022-06-09 15:57
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
