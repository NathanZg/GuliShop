package com.ekertree.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekertree.eduservice.entity.EduChapter;
import com.ekertree.eduservice.entity.EduVideo;
import com.ekertree.eduservice.entity.chapter.ChapterVo;
import com.ekertree.eduservice.entity.chapter.VideoVo;
import com.ekertree.eduservice.mapper.EduChapterMapper;
import com.ekertree.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekertree.eduservice.service.EduVideoService;
import com.ekertree.servicebase.excetionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-06-26
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    private EduVideoService eduVideoService;

    public EduChapterServiceImpl(EduVideoService eduVideoService) {
        this.eduVideoService = eduVideoService;
    }

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        //根据课程id查出所有的章节
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> chapters = baseMapper.selectList(chapterQueryWrapper);
        //根据课程id查询对应课程的所有小结
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> videos = eduVideoService.list(videoQueryWrapper);
        //遍历章节的list集合进行封装
        List<ChapterVo> finalList = new ArrayList<>();//用于存储最终封装的数据
        for (int i = 0; i < chapters.size(); i++) {
            EduChapter eduChapter = chapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);
            List<VideoVo> videoList = new ArrayList<>();
            //封装小节
            for (int j = 0; j < videos.size(); j++) {
                EduVideo eduVideo = videos.get(j);
                VideoVo videoVo = new VideoVo();
                //不要使用==进行比较 ==比较的是否为同一个对象 应该用equals比较
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }
            }
            //将封装好的小节放入章节对象中
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id查询小节表，如果查询到有数据，则不进行删除
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        //查询有多少条记录
        int count = eduVideoService.count(queryWrapper);
        if (count > 0) {
            throw new GuliException(20001, "章节中还有小节，无法删除！");
        }else{
            int c = baseMapper.deleteById(chapterId);
            return c > 0;
        }
    }
}
