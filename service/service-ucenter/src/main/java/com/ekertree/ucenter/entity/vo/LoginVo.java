package com.ekertree.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: LoginVo
 * Description:
 * date: 2022/7/18 16:53
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
@ApiModel(value="登陆对象", description="登陆对象")
public class LoginVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
}
