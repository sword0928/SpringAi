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
@TableName("category")
public class CategoryEntity {


    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;


    /**
     * 分类别名
     */
    @TableField("category_alias")
    private String categoryAlias;


    /**
     * 创建人ID
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
