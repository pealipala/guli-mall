package com.pealipala.gulimall.product.vo;

import lombok.Data;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/6/12 0:27
 */
@Data
public class AttrRespVo extends AttrVo {
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
}
