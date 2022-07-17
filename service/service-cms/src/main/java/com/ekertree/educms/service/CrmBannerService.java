package com.ekertree.educms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ekertree.educms.entity.vo.BannerVo;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-15
 */
public interface CrmBannerService extends IService<CrmBanner> {

    Page<CrmBanner> pageBanner(long current, long limit, BannerVo bannerVo);

    List<CrmBanner> selectAllBanners();
}
