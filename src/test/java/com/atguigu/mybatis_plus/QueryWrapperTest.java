package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.enums.SexEnum;
import com.atguigu.mybatis_plus.mapper.UserMapper;
import com.atguigu.mybatis_plus.pojo.User;
import com.atguigu.mybatis_plus.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author ChenCheng
 * @create 2022-06-09 16:23
 */
@SpringBootTest
public class QueryWrapperTest {

    @Autowired
    private UserService userService;
    @Autowired(required = false)
    private UserMapper userMapper;



    @Test
    void test01(){
        //查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","a")
                .between("age",20,30)
                .isNotNull("email");

        // SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0
        // AND (name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        List<User> users =
                userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }



    @Test
    void test02(){
        // 按年龄降序查询用户，如果年龄相同则按id升序排列
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

        // SELECT id,name,age,email,is_deleted
        // FROM user
        // WHERE is_deleted=0
        // ORDER BY age DESC,id ASC

    }

    @Test
    void test03(){

        //        删除email为空的用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        userMapper.delete(queryWrapper);
        // UPDATE user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)

    }

    @Test
    void test04(){
        // 将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",20)
                .like("name","a")
                .or()
                .isNull("email");
        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");

        userMapper.update(user,queryWrapper);

        // UPDATE user
        // SET age=?, email=?
        // WHERE is_deleted=0 AND (age >= ? AND name LIKE ? OR email IS NULL)

    }


    @Test
    void test4(){
        // 将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","a")
                .and(i -> i.gt("age", 20).or().isNull("email"));


        User user = new User();
        user.setAge(18);
        user.setEmail("user@atguigu.com");

        userMapper.update(user,queryWrapper);

        // UPDATE user
        // SET age=?, email=?
        // WHERE is_deleted=0 AND
        // (name LIKE ? AND (age > ? OR email IS NULL))
    }

    @Test
    void test05(){
        // 查询用户信息的name和age字段

        // SELECT name,age FROM user WHERE is_deleted=0
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name","age").like("name","a");
        List<Map<String, Object>> maps =
                userMapper.selectMaps(queryWrapper);
        // {name=admin, age=18}
        maps.forEach(System.out::println);
    }

    @Test
    void test06(){
        //  查询id小于等于3的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("id",3);

        userMapper.selectList(queryWrapper);
        // SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0 AND (id <= ?)

    }


    @Test
    void test07(){

        // 组装子查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id","SELECT id FROM USER WHERE id <= 8");
        userMapper.selectList(queryWrapper);

        //  SELECT id,name,age,email,is_deleted FROM user
        //  WHERE is_deleted=0 AND (id IN (SELECT id FROM USER WHERE id <= 8))

    }


    @Test
    void test08(){
        //  将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
        User user = new User();
        user.setName("张三");

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.gt("age",20)
                .or().isNull("email")
                .and(i -> i.like("name","a"));

        // UPDATE user SET name=? WHERE is_deleted=0
        // AND (age >= ? OR email IS NULL AND (name LIKE ?))


        userMapper.update(user,updateWrapper);


    }

    @Test
    void test09(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("age",20).set("name","张三")
                     .like("name","a")
                     .and(i -> i.gt("age",20).or().isNull("email"));
        //  UPDATE user SET age=?,name=? WHERE is_deleted=0
        //  AND (name LIKE ? AND (age > ? OR email IS NULL))
        userMapper.update(null,updateWrapper);

    }


    @Test
    void test10(){

        // 定义查询条件，有可能为null（用户未输入或未选择）
        String username = null;
        Integer ageBegin = 10;
        Integer ageEnd = 24;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // StringUtils.isNotBlank()判断某字符串是否不为空且长度不为0且不由空白符(whitespace)
        //构成
        // 方式一：
/*        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("name",username);
        }
        if(ageBegin != null){
            queryWrapper.ge("age",ageBegin);
        }
        if(ageEnd != null){
            queryWrapper.le("age",ageEnd);
        }*/

        queryWrapper.like(StringUtils.isNotBlank(username),"name",username)
                     .ge(ageBegin != null, "age",ageBegin)
                     .le(ageEnd != null,"age",ageEnd);


        // SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0
        // AND (age >= ? AND age <= ?)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);


    }

    @Test
    void test11(){

        String username = null;
        Integer ageBegin = 10;
        Integer ageEnd = 24;

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),User::getName,username)
                .ge(ageBegin != null,User::getAge,ageBegin)
                .le(ageEnd != null,User::getAge,ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
        // SELECT id,name,age,email,is_deleted FROM user WHERE is_deleted=0
        // AND (age >= ? AND age <= ?)
    }


    @Test
    void test12(){

        User user = new User();
        user.setName("Enum");
        user.setAge(20);
        // 设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
        user.setSex(SexEnum.MALE);
        userMapper.insert(user);
        // INSERT INTO user ( name, age, sex ) VALUES ( ?, ?, ? )

    }




}
