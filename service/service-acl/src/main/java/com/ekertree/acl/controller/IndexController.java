package com.ekertree.acl.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.ekertree.acl.entity.Permission;
import com.ekertree.acl.service.IndexService;
import com.ekertree.acl.service.PermissionService;
import com.ekertree.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
@Api(tags = "后台首页")
//@CrossOrigin
public class IndexController {

    private IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    @ApiOperation("根据token获取用户信息")
    public Result info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.ok().data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    @ApiOperation("获取菜单")
    public Result getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.ok().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    @ApiOperation("退出登陆")
    public Result logout(){
        return Result.ok();
    }

}
