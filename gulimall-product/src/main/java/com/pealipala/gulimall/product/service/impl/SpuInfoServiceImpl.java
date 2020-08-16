package com.pealipala.gulimall.product.service.impl;

import com.pealipala.common.to.SkuReductionTo;
import com.pealipala.common.to.SpuBoundsTo;
import com.pealipala.common.utils.R;
import com.pealipala.gulimall.product.entity.*;
import com.pealipala.gulimall.product.feign.CouponFeignService;
import com.pealipala.gulimall.product.service.*;
import com.pealipala.gulimall.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.common.utils.Query;

import com.pealipala.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService descService;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private ProductAttrValueService attrValueService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        //1.保存spu基本信息 pms_spu_info
        SpuInfoEntity infoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo,infoEntity);
        infoEntity.setCreateTime(new Date());
        infoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(infoEntity);
        //2.保存spu描述图片 pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity infoDescEntity = new SpuInfoDescEntity();
        infoDescEntity.setSpuId(infoEntity.getId());
        infoDescEntity.setDecript(String.join(",",decript));
        descService.saveSpuInfoDesc(infoDescEntity);
        //3.保存spu图片集  pms_spu_images
        List<String> images = vo.getImages();
        spuImagesService.saveImages(infoEntity.getId(),images);
        //4.保存spu规格参数 pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        attrValueService.saveBaseAttr(infoEntity.getId(),baseAttrs);
        //5.保存当前spu对应积分信息 sms_spu_bounds
        Bounds bounds = vo.getBounds();
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds,spuBoundsTo);
        spuBoundsTo.setSpuId(infoEntity.getId());
        R r1 = couponFeignService.saveSpuBounds(spuBoundsTo);
        if (r1.getCode()!=0){
            log.error("远程保存优惠信息 失败");
        }
        //6.保存当前spu对应的sku信息
        //6.1保存sku基本信息 pms_sku_info
        List<Skus> skus = vo.getSkus();
        if (skus!=null){
            skus.forEach(item->{
                String defaultImg = "";
                for (Images image : item.getImages()){
                    if (image.getDefaultImg()==1){
                        defaultImg = image.getImgUrl();
                    }
                }
                SkuInfoEntity entity = new SkuInfoEntity();
                BeanUtils.copyProperties(item,entity);
                entity.setBrandId(infoEntity.getBrandId());
                entity.setCatalogId(infoEntity.getCatalogId());
                entity.setSaleCount(0L);
                entity.setSpuId(infoEntity.getId());
                entity.setSkuDefaultImg(defaultImg);
                skuInfoService.saveSkuInfo(entity);
                Long skuId = entity.getSkuId();
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity imagesEntity = new SkuImagesEntity();
                    imagesEntity.setSkuId(skuId);
                    imagesEntity.setImgUrl(img.getImgUrl());
                    imagesEntity.setDefaultImg(img.getDefaultImg());
                    return imagesEntity;
                }).collect(Collectors.toList());
                //6.2保存sku图片信息 pms_sku_images
                skuImagesService.saveBatch(imagesEntities);
                //6.3保存sku销售属性信息 pms_sku_sale_attr_value
                List<Attr> attrList = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attrList.stream().map(attr -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);
                //6.4保存sku销售优惠、满减等信息 sms_sku_ladder\sms_sku_full_reduction\sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item,skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                R r2 = couponFeignService.saveSkuReduction(skuReductionTo);
                if (r2.getCode()!=0){
                    log.error("远程保存优惠信息 失败");
                }
            });
        }
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity entity) {
        this.baseMapper.insert(entity);
    }

}