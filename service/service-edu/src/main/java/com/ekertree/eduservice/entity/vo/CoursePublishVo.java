package com.ekertree.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: CoursePublishVo
 * Description:
 * date: 2022/7/8 16:23
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
@ApiModel(value = "课程发布信息封装")
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
