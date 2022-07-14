package com.ekertree.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekertree.eduservice.client.VodClient;
import com.ekertree.eduservice.entity.EduVideo;
import com.ekertree.eduservice.mapper.EduVideoMapper;
import com.ekertree.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-06-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    private VodClient vodClient;

    public EduVideoServiceImpl(VodClient vodClient) {
        this.vodClient = vodClient;
    }

    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查出所有视频id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            String videoSourceId = eduVideoList.get(i).getVideoSourceId();
            if (videoSourceId != null) {
                videoIds.add(videoSourceId);
            }
        }
        if (videoIds.size() > 0) {
            vodClient.deleteBatch(videoIds);
        }
    }
}
