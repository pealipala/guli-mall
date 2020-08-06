package com.pealipala.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.gulimall.coupon.entity.CategoryBoundsEntity;

import java.util.Map;

/**
 * 商品分类积分设置
 *
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-02 10:53:02
 */
public interface CategoryBoundsService extends IService<CategoryBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

