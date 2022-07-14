package com.ekertree.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.ekertree.commonutils.Result;
import com.ekertree.servicebase.excetionhandler.GuliException;
import com.ekertree.vod.service.VodService;
import com.ekertree.vod.utils.ConstantVodUtils;
import com.ekertree.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName: VodController
 * Description:
 * date: 2022/7/13 18:18
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/eduvod/video/")
@CrossOrigin
@Api(tags = "视频管理")
public class VodController {

    private VodService vodService;

    public VodController(VodService vodService) {
        this.vodService = vodService;
    }

    @PostMapping("uploadAliYunVideo")
    @ApiOperation("上传视频")
    public Result uploadAliYunVideo(MultipartFile file) {
        String videoId = vodService.uploadAliYunVideo(file);
        return Result.ok().data("videoId", videoId);
    }

    @DeleteMapping("removeAliYunVideo/{id}")
    @ApiOperation("根据id删除视频")
    public Result removeAliYunVideo(@PathVariable String id) {
        vodService.removeAliYunVideo(id);
        return Result.ok();
    }

    @DeleteMapping("deleteBatch")
    @ApiOperation("删除多个视频")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAliYunVideo(videoIdList);
        return Result.ok();
    }
}
