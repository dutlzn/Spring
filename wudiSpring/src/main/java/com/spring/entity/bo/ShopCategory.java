package com.spring.entity.bo;

import lombok.Data;

import java.util.Date;
@Data
public class ShopCategory {
    // 商品目录id
    private Long shopCategoryId;
    // 商品目录名称
    private String shopCategoryName;
    // 商品目录描述
    private String shopCategoryDesc;
    // 商品目录展示图片
    private String shopCategoryImg;
    // 权重
    private Integer priority;
    // 创建时间
    private Date createTime;
    // 最近一次更新时间
    private Date lastEditTime;
    // 父类别
    private ShopCategory parent;
}
