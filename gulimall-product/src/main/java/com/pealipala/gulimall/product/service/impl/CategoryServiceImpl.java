package com.pealipala.gulimall.product.service.impl;

import com.pealipala.gulimall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pealipala.common.utils.PageUtils;
import com.pealipala.common.utils.Query;

import com.pealipala.gulimall.product.dao.CategoryDao;
import com.pealipala.gulimall.product.entity.CategoryEntity;
import com.pealipala.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1.查询所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //2.找到所有的1级分类 1个参数 1个返回 小括号 大括号 return ;全部可以省略
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity ->
                //2.1过滤条件
                categoryEntity.getParentCid() == 0
        ).map((menu) -> {
            //2.2获取子菜单 但是子菜单还有子菜单 需要递归
            menu.setChildren(getChildren(menu, entities));
            return menu;
        }).sorted((menu1, menu2) -> {
            //2.3排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1.产品分类下被使用则无法删除
        baseMapper.deleteBatchIds(asList);
    }

   	/**
   	 * @方法名称 getCatelogPath
   	 * @功能描述 <pre>获取节点路径[父/子/孙子]</pre>
   	 * @作者 yechaoze yechaoze@tansun.com.cn
   	 * @日期 2020/6/10
   	 * @时间 0:48
   	 */
    @Override
    public Long[] getCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> resultPath = this.findParentPath(catelogId, paths);
        Collections.reverse(resultPath);
        return resultPath.toArray(new Long[resultPath.size()]);
    }

    @Override
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName())){
            categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
            //TODO 同步修改冗余字段
        }
    }

    /**
     * @方法名称
     * @功能描述 <pre>获取父路径</pre>
     * @作者 yechaoze yechaoze@tansun.com.cn
     * @日期 2020/6/11
     * @时间 0:33
     */
    private List<Long> findParentPath(Long catelogId,List<Long> paths){
        paths.add(catelogId);
        CategoryEntity categoryEntity = this.getById(catelogId);
        if (categoryEntity.getParentCid()!=0){
            this.findParentPath(categoryEntity.getParentCid(),paths);
        }
        return paths;

    }

    //获取子产品
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            //1.过滤条件 取到需要的菜单
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            //2.递归 不断的获取子菜单
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
            //3.排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }

}