package com.ekertree.acl.service.impl;

import com.ekertree.acl.entity.User;
import com.ekertree.acl.entity.UserRole;
import com.ekertree.acl.mapper.UserMapper;
import com.ekertree.acl.service.UserRoleService;
import com.ekertree.acl.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ekertree.commonutils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserRoleService userRoleService;

    public UserServiceImpl(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public void updateUserById(User user) {
        if (!StringUtils.isEmpty(user.getPassword())){
            user.setPassword(MD5.encrypt(user.getPassword()));
        }
        baseMapper.updateById(user);
    }

    @Override
    public void removeUserById(String id) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        userRoleService.remove(wrapper);
        baseMapper.deleteById(id);
    }
}
