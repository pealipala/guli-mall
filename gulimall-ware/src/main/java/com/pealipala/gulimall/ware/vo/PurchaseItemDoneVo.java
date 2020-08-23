package com.pealipala.gulimall.ware.vo;

import lombok.Data;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/8/23 14:24
 */
@Data
public class PurchaseItemDoneVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
