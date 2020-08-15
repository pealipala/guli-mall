package com.pealipala.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pealipala.gulimall.product.dao.BrandDao;
import com.pealipala.gulimall.product.dao.CategoryDao;
import com.pealipala.gulimall.product.entity.BrandEntity;
import com.pealipala.gulimall.product.entity.CategoryEntity;
import com.pealipala.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.common.utils.Query;

import com.pealipala.gulimall.product.dao.CategoryBrandRelationDao;
import com.pealipala.gulimall.product.entity.CategoryBrandRelationEntity;
import com.pealipala.gulimall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryBrandRelationDao relationDao;

    @Autowired
    private BrandService brandService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        //查询信息
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateDetail(Long brandId, String brandName) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationEntity.setBrandName(brandName);
        this.update(categoryBrandRelationEntity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    /**
     * @方法名称 updateCategory
     * @功能描述 <pre></pre>
     * @作者 yechaoze yechaoze@tansun.com.cn
     * @日期 2020/6/11
     * @时间 0:42
     */
    @Override
    public void updateCategory(Long catId, String name) {
        //自定义mapper
        this.baseMapper.updateCategory(catId, name);
        //直接修改
//        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
//        categoryBrandRelationEntity.setCatelogId(catId);
//        categoryBrandRelationEntity.setCatelogName(name);
//        this.update(categoryBrandRelationEntity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id",catId));
    }

    @Override
    public List<BrandEntity> getRelationBrandList(Long catId) {
        List<CategoryBrandRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        if (relationEntities != null){
            List<BrandEntity> brandEntities = relationEntities.stream().map((item) -> {
                BrandEntity brandEntity = brandService.getById(item.getBrandId());
                return brandEntity;
            }).collect(Collectors.toList());
            return brandEntities;
        }
        return null;
    }

}