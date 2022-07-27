package com.ekertree.ucenter.mapper;

import com.ekertree.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author ekertree
 * @since 2022-07-18
 */
public interface MemberMapper extends BaseMapper<Member> {
    Integer countRegisterDay(@Param("day") String day);
}
