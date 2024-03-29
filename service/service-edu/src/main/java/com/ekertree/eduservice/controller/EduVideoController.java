package com.ekertree.eduservice.controller;


import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.client.VodClient;
import com.ekertree.eduservice.entity.EduVideo;
import com.ekertree.eduservice.service.EduVideoService;
import com.ekertree.servicebase.excetionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author ekertree
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/eduservice/video/")
@Api(tags = "课程小节管理")
public class EduVideoController {

    private EduVideoService videoService;

    private VodClient vodClient;

    public EduVideoController(EduVideoService videoService, VodClient vodClient) {
        this.videoService = videoService;
        this.vodClient = vodClient;
    }

    @PutMapping("addVideo")
    @ApiOperation("添加小节")
    public Result addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return Result.ok();
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除小节")
    public Result deleteVideo(@PathVariable String id) {
        //根据小节id得到所有视频id
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        //如果视频id不为空才删视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            Result result = vodClient.removeAliYunVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "删除视频失败！");
            }
        }
        videoService.removeById(id);
        return Result.ok();
    }

    @GetMapping("getVideoInfo/{id}")
    @ApiOperation("根据小节id获取小节信息")
    public Result getVideoInfo(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return Result.ok().data("video", video);
    }

    @PutMapping("/updateVideo")
    @ApiOperation("修改小节")
    public  Result updateVideo(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return Result.ok();
    }
}

