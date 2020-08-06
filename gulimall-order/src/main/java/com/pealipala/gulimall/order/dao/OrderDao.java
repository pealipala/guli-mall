package com.pealipala.gulimall.order.dao;

import com.pealipala.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-02 10:45:47
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
