package com.pealipala.gulimall.product.service.impl;

import com.pealipala.gulimall.product.entity.AttrEntity;
import com.pealipala.gulimall.product.service.AttrService;
import com.pealipala.gulimall.product.vo.AttrGroupWithAttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

import com.pealipala.gulimall.product.dao.AttrGroupDao;
import com.pealipala.gulimall.product.entity.AttrGroupEntity;
import com.pealipala.gulimall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<AttrGroupEntity>();
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and((obj) -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }
        if (categoryId == 0) {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    queryWrapper
            );

            return new PageUtils(page);
        } else {
            queryWrapper.eq("catelog_id", categoryId);
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    queryWrapper
            );
            return new PageUtils(page);
        }
    }

    /**
      * @Param
      * @return 根据分类id查出分组 再查出分组中的属性
      */
    @Override
    public List<AttrGroupWithAttrVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //1.查询分组信息
        List<AttrGroupEntity> entities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        //2.查询所有属性
        if (entities!=null){
            List<AttrGroupWithAttrVo> vos = entities.stream().map(group -> {
                AttrGroupWithAttrVo attrVo = new AttrGroupWithAttrVo();
                BeanUtils.copyProperties(group,attrVo);
                List<AttrEntity> attrs = attrService.getAttrRelation(attrVo.getAttrGroupId());
                attrVo.setAttrs(attrs);
                return attrVo;
            }).collect(Collectors.toList());
            return vos;
        }
        return null;
    }

}