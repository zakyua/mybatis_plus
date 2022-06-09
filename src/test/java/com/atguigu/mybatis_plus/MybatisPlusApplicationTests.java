package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.mapper.UserMapper;
import com.atguigu.mybatis_plus.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import javax.xml.transform.Source;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    void contextLoads() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("数据库连接池"+connection);
    }

    @Test
    void testSelectList(){

        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    void testInsert(){
     /*   User user = new User(null, "张三", 23, "zhangsan@atguigu.com",0);


        int insert = userMapper.insert(user);
        System.out.println("受影响的行数"+insert);
        Long id = user.getId();
        // mybatis_plus 默认使用雪花算法来进行主键的插入,并且可以自动获取这个主键值
        System.out.println("主键是"+id);*/
    }

    @Test
    void testDeleteById(){
        // 1534800479059664898
        int i = userMapper.deleteById(1534800479059664898L);
        System.out.println("受影响的行数"+i);
    }

    @Test
    void testDeleteBatchIds(){
        // DELETE FROM user WHERE id IN ( ? , ? , ? )
      List<Long> ids =  Arrays.asList(1L,2L,3L);
        int i = userMapper.deleteBatchIds(ids);
        System.out.println("受影响的行数"+i);

    }


    @Test
    void testDeleteByMap(){

        // 根据map集合中所设置的条件删除记录
        // DELETE FROM user WHERE name = ? AND age = ?
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age",23);
        int i = userMapper.deleteByMap(map);
        System.out.println("受影响的行数"+i);
    }

    @Test
    void testUpdateById(){
/*

        // UPDATE user SET name=?, age=? WHERE id=?
        User user = new User(null,"admin", 22, "", null);
        int i = userMapper.updateById(user);
        System.out.println("受影响的行数"+i);
*/

    }

    @Test
    void testSelectById(){
        // 根据id查询用户信息
        // SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(4L);
        System.out.println(user);
    }

    @Test
    void testSelectBatchIds(){
        // SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
        List<Long> list = Arrays.asList(4L, 5L);
        List<User> users = userMapper.selectBatchIds(list);
        users.forEach(System.out::println);

    }

    @Test
    void testSelectByMap(){

        // SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        // 通过map条件查询用户信息
        Map<String,Object> map = new HashMap<>();
        map.put("age",22);
        map.put("name","admin");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);

    }





}
