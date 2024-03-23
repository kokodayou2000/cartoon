package org.example.mapper;

import org.apache.ibatis.annotations.Select;
import org.example.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface UserMapper extends BaseMapper<UserDO> {

    @Select("select * from cartoon.t_user where t_user.name like CONCAT('%', #{username}, '%')")
    List<UserDO> findByUsername(String username);
}
