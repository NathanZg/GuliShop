package com.ekertree.servicebase.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * ClassName: CommentUserVo
 * Description:
 * date: 2022/7/23 16:49
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
@ApiModel("评论用户的封装")
public class CommentUserVo {
    private String nickname;
    private String avatar;
}
