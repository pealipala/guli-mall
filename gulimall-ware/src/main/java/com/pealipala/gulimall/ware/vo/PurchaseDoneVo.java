package com.pealipala.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/8/23 14:22
 */
@Data
public class PurchaseDoneVo {
    //采购单id
    private Long id;

    private List<PurchaseItemDoneVo> items;
}
