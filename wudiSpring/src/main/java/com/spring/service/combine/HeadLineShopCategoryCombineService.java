package com.spring.service.combine;

import com.spring.entity.dto.MainPageInfoDto;
import com.spring.entity.dto.Result;

public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDto> getMainPageInfo();
}
