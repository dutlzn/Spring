package com.spring.entity.dto;

import com.spring.entity.bo.HeadLine;
import com.spring.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

@Data
public class MainPageInfoDto {
    private List<HeadLine> headLineList;
    private List<ShopCategory> shopCategoryList;
}
