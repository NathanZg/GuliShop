package com.ekertree.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: oneSubject
 * Description:一级分类
 * date: 2022/6/26 17:57
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    //一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
