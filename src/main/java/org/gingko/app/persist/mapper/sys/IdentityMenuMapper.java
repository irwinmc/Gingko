package org.gingko.app.persist.mapper.sys;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.sys.IdentityMenu;

import java.util.List;

/**
 * @author TangYing
 */
public interface IdentityMenuMapper {

    List<IdentityMenu> selectByPage(@Param("identity") int identity, @Param("limit") int limit, @Param("offset") int offset);

    int selectTotalCount(@Param("identity") int identity);

	List<String> selectMenusByIdentity(int identity);

    void insert(IdentityMenu identityMenu);

    void deleteById(int id);
}
