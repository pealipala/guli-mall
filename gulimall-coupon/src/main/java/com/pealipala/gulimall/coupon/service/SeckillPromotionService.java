package com.pealipala.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.gulimall.coupon.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-02 10:53:02
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

