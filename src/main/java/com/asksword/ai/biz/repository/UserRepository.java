package com.asksword.ai.biz.repository;

import com.asksword.ai.biz.mappers.UserMapper;
import com.asksword.ai.biz.model.entity.UserEntity;
import com.asksword.ai.common.enums.YesOrNoEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserRepository  {
    @Autowired
    private UserMapper userMapper;

    public UserEntity getUserByUserName(String userName ) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, userName)
                .eq(UserEntity::getRecordStatus, YesOrNoEnum.YES.getCode());
        return userMapper.selectOne(wrapper);
    }

    public void register(UserEntity userEntity) {
        userMapper.insert(userEntity);
    }

    public UserEntity selectUserInfoByUserId(Long userId) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUserId, userId)
                .eq(UserEntity::getRecordStatus, YesOrNoEnum.YES.getCode());
        return userMapper.selectOne(wrapper);
    }

    public void update(UserEntity userEntity) {
        userMapper.updateById(userEntity);
    }
}
