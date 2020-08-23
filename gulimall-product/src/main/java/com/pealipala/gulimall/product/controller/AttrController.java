package com.pealipala.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.pealipala.gulimall.product.entity.ProductAttrValueEntity;
import com.pealipala.gulimall.product.service.ProductAttrValueService;
import com.pealipala.gulimall.product.vo.AttrRespVo;
import com.pealipala.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pealipala.gulimall.product.entity.AttrEntity;
import com.pealipala.gulimall.product.service.AttrService;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.common.utils.R;



/**
 * 商品属性
 *
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-01 20:31:27
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    //product/attr/update/{spuId}
    @PostMapping("/update/{spuId}")
    public R updateSpu(@PathVariable("spuId") Long spuId,List<ProductAttrValueEntity> entities){
        productAttrValueService.updateSpu(spuId,entities);

        return R.ok();
    }

    //product/attr/base/listforspu/{spuId}
    @RequestMapping("/base/listforspu/{spuId}")
    public R listForSpu(@PathVariable("spuId") Long spuId){
        List<ProductAttrValueEntity> result = productAttrValueService.listForSpu(spuId);

        return R.ok().put("data", result);
    }

    /**
     * 列表
     */
    @RequestMapping("/{attrType}/list/{catelogId}")
    //@RequiresPermissions("product:attr:list")
    public R baseAttrList(@PathVariable("catelogId") Long catelogId,
                          @RequestParam Map<String, Object> params,
                          @PathVariable("attrType") String type){
        PageUtils page = attrService.querybaseAttrPage(params,catelogId,type);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
//		AttrEntity attr = attrService.getById(attrId);
		AttrRespVo attr = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attr);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attrVo){
		attrService.saveAttr(attrVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
//		attrService.updateById(attr);
		attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
