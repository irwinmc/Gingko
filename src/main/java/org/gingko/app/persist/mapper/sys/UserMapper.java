package org.gingko.app.persist.mapper.sys;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.sys.User;

import java.util.List;

/**
 * @author Kyia
 */
public interface UserMapper {

	List<User> selectByPage(@Param("limit") int limit, @Param("offset") int offset);

    int selectTotalCount();

    User selectByAccount(@Param("account") String account);

	User login(@Param("account") String account, @Param("password") String password);

	void insert(User user);

    void update(User user);

	void deleteByAccount(String account);
}
