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
@TableName("article")
public class ArticleEntity {


    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 文章标题
     */
    @TableField("title")
    private String title;


    /**
     * 文章内容
     */
    @TableField("content")
    private String content;


    /**
     * 文章封面
     */
    @TableField("cover_img")
    private String coverImg;


    /**
     * 文章状态 :只能是[已发布]或者[草稿]
     */
    @TableField("state")
    private String state;


    /**
     * 文章分类 ID
     */
    @TableField("category_id")
    private Integer categoryId;


    /**
     * 创建人 ID
     */
    @TableField("create_user")
    private Integer createUser;


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
