package com.ekertree.eduservice.service;

import com.ekertree.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ekertree.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author ekertree
 * @since 2022-06-25
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
