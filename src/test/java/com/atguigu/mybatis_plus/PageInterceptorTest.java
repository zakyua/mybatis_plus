package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.mapper.UserMapper;
import com.atguigu.mybatis_plus.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author ChenCheng
 * @create 2022-06-09 20:04
 */

@SpringBootTest
public class PageInterceptorTest {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    void test01(){
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPage(page, null);
        List<User> records =
                page.getRecords();
        records.forEach(System.out::println);
        System.out.println("总页数"+page.getPages());
        System.out.println("总记录数"+page.getTotal());
        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("是否有上一页："+page.hasPrevious());
        System.out.println("是否有下一页："+page.hasNext());
    }




}
