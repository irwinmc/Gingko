package org.gingko.app.persist.mapper;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.User;

import java.util.List;

/**
 * @author Kyia
 */
public interface UserMapper {

	List<User> selectByPage(@Param("limit") int limit, @Param("offset") int offset);

    User selectByAccount(@Param("account") String account);

	User login(@Param("account") String account, @Param("password") String password);

	int totalCount();

	void insert(User user);

    void update(User user);

	void deleteByAccount(String account);
}
