package com.ekertree.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekertree.eduservice.entity.EduSubject;
import com.ekertree.eduservice.entity.excel.SubjectData;
import com.ekertree.eduservice.service.EduSubjectService;
import com.ekertree.servicebase.excetionhandler.GuliException;

/**
 * ClassName: SubjectExcelListener
 * Description:
 * date: 2022/6/25 21:49
 *
 * @author Ekertree
 * @since JDK 1.8
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    /*
        SubjectExcelListener无法交给spring管理，得自己new，就无法注入其他对象，无法实现数据库操作
     */

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空！");
        }
        /*
            一行行读，第一个值是一级分类，第二个值是二级分类
         */
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) { //没有相同的一级分类
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }

        String pid = existOneSubject.getId();

        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(),pid);
        if (existTwoSubject == null) { //没有相同的二级分类
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }

    }

    //判断一级分类能不能添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        EduSubject one = eduSubjectService.getOne(wrapper);
        return one;
    }

    //判断二级分类能不能添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject two = eduSubjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
