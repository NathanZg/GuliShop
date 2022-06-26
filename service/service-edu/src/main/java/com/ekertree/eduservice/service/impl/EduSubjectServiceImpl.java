package com.ekertree.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ekertree.commonutils.Result;
import com.ekertree.eduservice.entity.EduSubject;
import com.ekertree.eduservice.entity.excel.SubjectData;
import com.ekertree.eduservice.entity.subject.OneSubject;
import com.ekertree.eduservice.entity.subject.TwoSubject;
import com.ekertree.eduservice.listener.SubjectExcelListener;
import com.ekertree.eduservice.mapper.EduSubjectMapper;
import com.ekertree.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author ekertree
 * @since 2022-06-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类 parentId = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);
        //查询所有二级分类 parentId != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubject = baseMapper.selectList(wrapperTwo);

        //存储最终数据的集合
        List<OneSubject> finalSubject = new ArrayList<>();

        //封装一级分类
        for (int i = 0; i < oneSubjects.size(); i++) {
            EduSubject eduSubject = oneSubjects.get(i);
            //把eduSubject的值取出放入oneSubject
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneSubject);
            List<TwoSubject> finalTwoSubject = new ArrayList<>();
            //遍历一级分类
            for (int j = 0; j < twoSubject.size(); j++) {
                EduSubject loopTwoSubject = twoSubject.get(j);
                //判断二级分类的parentId和一级分类是否一样
                if (loopTwoSubject.getParentId().equals(oneSubject.getId())) {
                    TwoSubject ts = new TwoSubject();
                    BeanUtils.copyProperties(loopTwoSubject, ts);
                    finalTwoSubject.add(ts);
                }
            }
            //将二级分类放入一级分类中
            oneSubject.setChildren(finalTwoSubject);
            finalSubject.add(oneSubject);
        }
        return finalSubject;
    }
}
