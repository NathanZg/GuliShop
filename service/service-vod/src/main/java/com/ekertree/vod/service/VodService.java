package com.ekertree.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName: VodService
 * Description:
 * date: 2022/7/13 18:19
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public interface VodService {
    String uploadAliYunVideo(MultipartFile file);

    void removeMoreAliYunVideo(List videoIdList);

    void removeAliYunVideo(String id);

    String getPlayAuth(String vid);
}
