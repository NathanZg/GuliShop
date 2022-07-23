package com.ekertree.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ekertree.educms.entity.CrmBanner;
import com.ekertree.educms.entity.vo.BannerVo;
import com.ekertree.educms.mapper.CrmBannerMapper;
import com.ekertree.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-07-15
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public Page<CrmBanner> pageBanner(long current, long limit, BannerVo bannerVo) {
        Page<CrmBanner> bannerPage = new Page<>(current,limit);
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        String id = bannerVo.getId();
        String title = bannerVo.getTitle();
        String imageUrl = bannerVo.getImageUrl();
        String linkUrl = bannerVo.getLinkUrl();
        Date gmtCreate = bannerVo.getGmtCreate();
        Date gmtModified = bannerVo.getGmtModified();
        if (!StringUtils.isEmpty(id)){
            wrapper.like("id", id);
        }
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(imageUrl)){
            wrapper.like("image_url", imageUrl);
        }
        if (!StringUtils.isEmpty(linkUrl)){
            wrapper.like("link_url", linkUrl);
        }
        if (!StringUtils.isEmpty(gmtCreate)){
            wrapper.eq("gmt_Create", gmtCreate);
        }
        if (!StringUtils.isEmpty(gmtModified)){
            wrapper.eq("gmt_Modified", gmtModified);
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(bannerPage, wrapper);
        return bannerPage;
    }

    @Override
    @Cacheable(key = "'selectBannerList'",value = "bannerList")
    public List<CrmBanner> selectAllBanners() {
        //根据加入时间降序排列，显示最新加入的两条
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.last("limit 3");
        List<CrmBanner> crmBanners = baseMapper.selectList(wrapper);
        return crmBanners;
    }
}
