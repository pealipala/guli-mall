package com.pealipala.gulimall.member.dao;

import com.pealipala.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author yechaoze
 * @email 1029102110@qq.com
 * @date 2020-05-02 10:56:53
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
