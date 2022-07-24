package com.ekertree.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: commentQuery
 * Description:
 * date: 2022/7/24 14:20
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
public class CommentQuery {
    @ApiModelProperty(value = "会员id")
    private String memberId;
    @ApiModelProperty(value = "会员昵称")
    private String nickname;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "起始时间")
    private String begin;
    @ApiModelProperty(value = "结束时间")
    private String end;
}
