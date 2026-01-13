package com.asksword.ai.biz.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@TableName("user")
public class UserEntity {


    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 用户名
     */
    @TableField("username")
    private String username;


    /**
     * 密码
     */
    @TableField("password")
    private String password;


    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;


    /**
     * 邮箱
     */
    @TableField("email")
    private String email;


    /**
     * 头像
     */
    @TableField("user_pic")
    private String userPic;

    /**
     * 状态
     */
    @TableField("record_status")
    private Integer recordStatus;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

}
