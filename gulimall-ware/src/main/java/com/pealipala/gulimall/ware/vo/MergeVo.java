package com.pealipala.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/8/22 23:17
 */
@Data
public class MergeVo {
    private Long purchaseId;//整单id
    private List<Long> items;
}
