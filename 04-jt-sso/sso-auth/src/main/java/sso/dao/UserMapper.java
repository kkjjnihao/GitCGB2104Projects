package sso.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    /**
     * 基于用户查询用户信息
     * @param username
     * @return 查询到的用户信息,表中的字段名会作为map中key,字段名对应的值会
     * 作为map中的value进行存储
     */
    @Select("select * from sys_user where username=#{username}")
    Map<String,Object> selectUserByUsername(@Param("username")String username);

    /**
     * 基于用户id查询用户权限信息
     * @param id
     * @return
     */
    @Select(" select distinct m.permission " +
            " from sys_user u left join sys_user_role ur on u.id=ur.user_id " +
            " left join sys_role_menu rm on ur.role_id=rm.role_id " +
            " left join sys_menu m on rm.menu_id=m.id " +
            " where u.id=#{id}")
    List<String> selectUserPermissions(@Param("id") long id);
}
