package com.pealipala.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.gulimall.product.entity.SkuInfoEntity;
import com.pealipala.gulimall.product.entity.SpuInfoEntity;
import com.pealipala.gulimall.product.vo.Skus;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-01 20:31:27
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity entity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

