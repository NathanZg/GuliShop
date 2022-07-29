package com.ekertree.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ekertree.acl.entity.Role;
import com.ekertree.acl.entity.User;
import com.ekertree.acl.service.IndexService;
import com.ekertree.acl.service.PermissionService;
import com.ekertree.acl.service.RoleService;
import com.ekertree.acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    private UserService userService;

    private RoleService roleService;

    private PermissionService permissionService;

    private RedisTemplate redisTemplate;

    public IndexServiceImpl(UserService userService, RoleService roleService, PermissionService permissionService, RedisTemplate redisTemplate) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUsername(username);
        if (null == user) {
            //throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);
        result.put("id", user.getId());
        result.put("name", user.getUsername());
        result.put("avatar", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201910%2F03%2F20191003114823_vkeka.thumb.400_0.gif&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661671751&t=5675c342aca4ad0d61147eaad8d754ce");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        List<JSONObject> permissionList = permissionService.selectPermissionByUserId(user.getId());
        return permissionList;
    }


}
