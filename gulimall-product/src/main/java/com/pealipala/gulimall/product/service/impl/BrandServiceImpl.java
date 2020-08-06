package com.pealipala.gulimall.product.service.impl;

import com.pealipala.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.common.utils.Query;

import com.pealipala.gulimall.product.dao.BrandDao;
import com.pealipala.gulimall.product.entity.BrandEntity;
import com.pealipala.gulimall.product.service.BrandService;
import org.springframework.util.StringUtils;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //1.获取一波关键字
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)){
            queryWrapper.eq("brand_id",key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updateDetail(BrandEntity brand) {
        //保证冗余字段的更新
        this.updateById(brand);
        if (!StringUtils.isEmpty(brand.getName())){
            //同步更新其他表的数据
            categoryBrandRelationService.updateDetail(brand.getBrandId(),brand.getName());
            //TODO 更新其他冗余字段
        }
    }

}