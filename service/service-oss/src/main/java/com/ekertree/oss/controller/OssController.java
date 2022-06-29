package com.ekertree.oss.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.ekertree.commonutils.Result;
import com.ekertree.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: OssController
 * Description:
 * date: 2022/6/24 21:58
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/eduoss/")
@Api(tags = "文件上传")
@CrossOrigin
public class OssController {

    private OssService ossService;

    public OssController(OssService ossService) {
        this.ossService = ossService;
    }

    @PostMapping("file")
    @ApiOperation("头像上传")
    public Result uploadOssFile(MultipartFile file) {
        String url =  ossService.uploadFileAvatar(file);
        return Result.ok().data("url", url);
    }
}
