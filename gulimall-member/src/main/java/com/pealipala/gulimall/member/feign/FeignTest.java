package com.pealipala.gulimall.member.feign;

import com.pealipala.common.utils.PageUtils;
import com.pealipala.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author yechaoze
 * @version 1.0
 * @date 2020/5/4 10:59
 */
@FeignClient("gulimall-coupon")
public interface FeignTest {
    @RequestMapping("/coupon/categorybounds/list")
    public R list(@RequestParam Map<String, Object> params);
}
