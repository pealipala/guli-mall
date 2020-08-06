package com.pealipala.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pealipala.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.pealipala.gulimall.product.dao.AttrGroupDao;
import com.pealipala.gulimall.product.dao.CategoryDao;
import com.pealipala.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.pealipala.gulimall.product.entity.AttrGroupEntity;
import com.pealipala.gulimall.product.entity.CategoryEntity;
import com.pealipala.gulimall.product.service.AttrAttrgroupRelationService;
import com.pealipala.gulimall.product.service.CategoryService;
import com.pealipala.gulimall.product.vo.AttrRespVo;
import com.pealipala.gulimall.product.vo.AttrVo;
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

import com.pealipala.gulimall.product.dao.AttrDao;
import com.pealipala.gulimall.product.entity.AttrEntity;
import com.pealipala.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @方法名称 saveAttr
     * @功能描述 <pre>分类规格参数保存</pre>
     * @作者 yechaoze yechaoze@tansun.com.cn
     * @日期 2020/6/12
     * @时间 0:16
     */
    @Transactional
    @Override
    public void saveAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        //1.保存基本数据
        this.save(attrEntity);
        //2.保存关联关系
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
        attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
    }

    /**
     * @方法名称 querybaseAttrPage
     * @功能描述 <pre>分类规格参数的查询</pre>
     * @作者 yechaoze yechaoze@tansun.com.cn
     * @日期 2020/6/12
     * @时间 0:16
     */
    @Override
    public PageUtils querybaseAttrPage(Map<String, Object> params, Long catelogId) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        if (catelogId != 0) {
            queryWrapper.eq("catelog_id", catelogId);
        }
        //获取检索条件
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> attrEntities = page.getRecords();
        List<AttrRespVo> attrRespVoList = attrEntities.stream().map((attrEntity) -> {
            AttrRespVo respVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, respVo);
            //1.设置分类和分组名字
            AttrAttrgroupRelationEntity attrId = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (attrId != null && attrId.getAttrGroupId()!=null) {
                String attrGroupName = attrGroupDao.selectById(attrId.getAttrGroupId()).getAttrGroupName();
                respVo.setGroupName(attrGroupName);
            }
            CategoryEntity catId = categoryDao.selectOne(new QueryWrapper<CategoryEntity>().eq("cat_id", attrEntity.getCatelogId()));
            if (catId != null) {
                String catelogName = categoryDao.selectById(catId.getCatId()).getName();
                respVo.setCatelogName(catelogName);
            }
            return respVo;
        }).collect(Collectors.toList());
        pageUtils.setList(attrRespVoList);
        return pageUtils;
    }

    /**
     * @方法名称 getAttrInfo
     * @功能描述 <pre>修改的时候 获取详情</pre>
     * @作者 yechaoze yechaoze@tansun.com.cn
     * @日期 2020/6/12
     * @时间 1:07
     */
    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity, attrRespVo);

        //1.设置分组信息
        AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
        if (relationEntity != null) {
            attrRespVo.setAttrGroupId(relationEntity.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getId());
            if (attrGroupEntity != null) {
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }

        //2.设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.getCatelogPath(catelogId);
        attrRespVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity!=null){
            attrRespVo.setCatelogName(categoryEntity.getName());
        }
        return attrRespVo;
    }

    /**
     * @方法名称 updateAttr
     * @功能描述 <pre>分类规格参数的修改</pre>
     * @作者 yechaoze yechaoze@tansun.com.cn
     * @日期 2020/6/12
     * @时间 1:24
     */
    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        //1.基本的修改
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);

        //2.修改分组关联
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());

        Integer integer = attrAttrgroupRelationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
        if (integer>0){
            attrAttrgroupRelationDao.update(attrAttrgroupRelationEntity,
                    new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
        }else{
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }
    }


}