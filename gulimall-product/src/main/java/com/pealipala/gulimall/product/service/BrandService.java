package com.pealipala.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.gulimall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-01 20:31:28
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}

