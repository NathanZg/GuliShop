package com.ekertree.servicebase.excetionhandler;

import com.baomidou.mybatisplus.extension.api.R;
import com.ekertree.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: GlobalExcetionHandler
 * Description:
 * date: 2022/6/20 15:10
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error().setMessage(e.getMessage());
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public Result error(GuliException e){
        e.printStackTrace();
        return Result.error().setMessage(e.getMessage()).setCode(e.getCode());
    }
}
