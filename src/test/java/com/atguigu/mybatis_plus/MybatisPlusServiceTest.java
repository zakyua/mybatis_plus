package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.mapper.UserMapper;
import com.atguigu.mybatis_plus.pojo.User;
import com.atguigu.mybatis_plus.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenCheng
 * @create 2022-06-09 15:58
 */
@SpringBootTest
public class MybatisPlusServiceTest {


    @Autowired
    private UserService userService;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    void testGetCount(){
        // SELECT COUNT( * ) FROM user
        long count = userService.count();
        System.out.println(count);
    }

    @Test
    void testSaveBatch(){
        // SQL长度有限制，海量数据插入单条SQL无法实行，
        // 因此MP将批量插入放在了通用Service中实现，而不是通用Mapper
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("cc" + i);
            user.setAge(20 + i);
            users.add(user);
        }
        // INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
        userService.saveBatch(users);
    }


    @Test
    void testIsDeleted(){
        // UPDATE user SET is_deleted=1 WHERE is_deleted=0 AND (name = ?)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","Billie");
        boolean remove = userService.remove(queryWrapper);
        System.out.println(remove);
    }


    @Test
    void testSeclectAll(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }





}
