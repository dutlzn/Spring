package com.project.service.impl;

import com.project.entity.vo.Result;
import com.project.entity.bo.User;
import com.project.service.UserService;

import java.util.List;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {
    @Override
    public Result<Boolean> addUser(User user) {
        return null;
    }

    @Override
    public Result<Boolean> deleteUserById(Long id) {
        return null;
    }

    @Override
    public Result<Boolean> updateUser(User user) {
        return null;
    }

    @Override
    public Result<User> queryUserById(Long id) {
        return null;
    }

    @Override
    public Result<List<User>> queryUser(User user, int pageSize, int pageNo) {
        return null;
    }
}
