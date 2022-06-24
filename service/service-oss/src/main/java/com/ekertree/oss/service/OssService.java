package com.ekertree.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName: OssService
 * Description:
 * date: 2022/6/24 21:58
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
