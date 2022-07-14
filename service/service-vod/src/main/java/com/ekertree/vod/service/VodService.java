package com.ekertree.vod.service;

import org.springframework.web.multipart.MultipartFile;

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
}
