package com.pealipala.gulimall.product.dao;

import com.pealipala.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-01 20:31:28
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
