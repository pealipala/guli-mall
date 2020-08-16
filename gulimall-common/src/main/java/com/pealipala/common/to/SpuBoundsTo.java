package com.pealipala.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/8/16 21:09
 */
@Data
public class SpuBoundsTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
