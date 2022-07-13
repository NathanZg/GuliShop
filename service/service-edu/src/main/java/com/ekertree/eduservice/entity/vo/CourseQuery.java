package com.ekertree.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName: CourseQuery
 * Description:
 * date: 2022/7/8 21:59
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "最低价格")
    private String minPrice;

    @ApiModelProperty(value = "最高价格")
    private String maxPrice;

    @ApiModelProperty(value = "发布状态")
    private String status;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
