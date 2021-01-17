package com.project.service;

import com.project.entity.vo.Result;
import com.project.entity.bo.User;

import java.util.List;

/**
 * 用户服务接口类
 */
public interface UserService {
    Result<Boolean> addUser(User user);
    Result<Boolean> deleteUserById(Long id);
    Result<Boolean> updateUser(User user);
    Result<User> queryUserById(Long id);
    // 模拟分页
    Result<List<User>> queryUser(User user, int pageSize, int pageNo);
}
