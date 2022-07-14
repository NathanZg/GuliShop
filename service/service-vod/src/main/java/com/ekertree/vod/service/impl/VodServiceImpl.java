package com.ekertree.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.ekertree.servicebase.excetionhandler.GuliException;
import com.ekertree.vod.service.VodService;
import com.ekertree.vod.utils.ConstantVodUtils;
import com.ekertree.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * ClassName: VodServiceImpl
 * Description:
 * date: 2022/7/13 18:20
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadAliYunVideo(MultipartFile file) {
        InputStream inputStream = null;
        String videoId = null;
        try {
            inputStream = file.getInputStream();
            String originName = file.getOriginalFilename();
            String title = originName.substring(0,originName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, originName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeMoreAliYunVideo(List videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置视频id
            //把list中的值转换成用逗号分割的形式
            String join = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(join);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败！");
        }
    }

    @Override
    public void removeAliYunVideo(String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置视频id
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败！");
        }
    }
}
