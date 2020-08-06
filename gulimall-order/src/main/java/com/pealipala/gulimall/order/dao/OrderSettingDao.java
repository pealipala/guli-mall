package com.pealipala.gulimall.order.dao;

import com.pealipala.gulimall.order.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * 
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-02 10:45:47
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {
	
}
