package com.ekertree.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * ClassName: SubjectData
 * Description:
 * date: 2022/6/25 21:41
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
