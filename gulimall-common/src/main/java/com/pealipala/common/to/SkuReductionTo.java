package com.pealipala.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/8/16 21:39
 */
@Data
public class SkuReductionTo {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
