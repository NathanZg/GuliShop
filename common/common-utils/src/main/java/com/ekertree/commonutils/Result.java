package com.ekertree.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: Result
 * Description:
 * date: 2022/6/20 10:26
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
public class Result {

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty("返回的数据")
    private Map<String,Object> data = new HashMap<>();

    private Result(){}

    public static Result ok(){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功");
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("失败");
        return result;
    }

    public Result data(String key,Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String,Object> map) {
        this.setData(map);
        return this;
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
}
