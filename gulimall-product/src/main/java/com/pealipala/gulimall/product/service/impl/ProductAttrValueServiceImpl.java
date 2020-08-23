package com.pealipala.gulimall.product.service.impl;

import com.pealipala.gulimall.product.entity.AttrEntity;
import com.pealipala.gulimall.product.service.AttrService;
import com.pealipala.gulimall.product.vo.BaseAttrs;
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

import com.pealipala.gulimall.product.dao.ProductAttrValueDao;
import com.pealipala.gulimall.product.entity.ProductAttrValueEntity;
import com.pealipala.gulimall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBaseAttr(Long id, List<BaseAttrs> baseAttrs) {
        if (baseAttrs != null) {
            List<ProductAttrValueEntity> entities = baseAttrs.stream().map(attr -> {
                ProductAttrValueEntity entity = new ProductAttrValueEntity();
                AttrEntity attrEntity = attrService.getById(attr.getAttrId());
                entity.setAttrId(attr.getAttrId());
                entity.setAttrName(attrEntity.getAttrName());
                entity.setAttrValue(attr.getAttrValues());
                entity.setQuickShow(attr.getShowDesc());
                entity.setSpuId(id);
                return entity;
            }).collect(Collectors.toList());
            this.saveBatch(entities);
        }
    }

    @Override
    public List<ProductAttrValueEntity> listForSpu(Long spuId) {
        List<ProductAttrValueEntity> entities = this.baseMapper.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        return entities;
    }

    @Override
    public void updateSpu(Long spuId, List<ProductAttrValueEntity> entities) {
        //1、删除这个spuId之前对应的所有属性
        this.baseMapper.delete(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));


        List<ProductAttrValueEntity> collect = entities.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }

}