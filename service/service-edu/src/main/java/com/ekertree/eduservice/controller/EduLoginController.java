package com.ekertree.eduservice.controller;

import com.ekertree.commonutils.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: EduLoginController
 * Description:
 * date: 2022/6/22 22:02
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/eduservice/user/")
@CrossOrigin
public class EduLoginController {

    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    @GetMapping("info")
    public Result info() {
        return Result.ok().data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "http://m.imeitou.com/uploads/allimg/2016072417/ppd0uwono3v.gif");
    }
}
