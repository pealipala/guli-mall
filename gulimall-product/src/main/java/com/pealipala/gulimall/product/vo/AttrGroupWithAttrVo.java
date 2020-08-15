package com.pealipala.gulimall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pealipala.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/8/15 17:46
 */
@Data
public class AttrGroupWithAttrVo {
    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
