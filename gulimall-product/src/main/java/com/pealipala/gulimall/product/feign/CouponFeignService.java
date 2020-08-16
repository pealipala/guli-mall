package com.pealipala.gulimall.product.feign;

import com.pealipala.common.to.SkuReductionTo;
import com.pealipala.common.to.SpuBoundsTo;
import com.pealipala.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/8/16 21:24
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
