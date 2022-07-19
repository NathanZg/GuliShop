package com.ekertree.ucenter.service;

import com.ekertree.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ekertree.ucenter.entity.vo.LoginVo;
import com.ekertree.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-18
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    String redirectUrl();

    String callback(String code, String state);

    Member getMemberByOpenId(String openid);
}
