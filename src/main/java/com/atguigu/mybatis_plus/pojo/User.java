package com.atguigu.mybatis_plus.pojo;

import com.atguigu.mybatis_plus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ChenCheng
 * @create 2022-06-09 15:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("age")
    private Integer age;
    @TableField("email")
    private String email;

    private SexEnum sex;

    @TableLogic
    private Integer isDeleted;


}
