package com.ekertree.servicebase.excetionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: GuliException
 * Description:
 * date: 2022/6/25 22:04
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{
    private Integer code;
    private String msg;
}
