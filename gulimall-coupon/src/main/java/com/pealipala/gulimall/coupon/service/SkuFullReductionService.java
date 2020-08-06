package com.pealipala.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-02 10:53:02
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

