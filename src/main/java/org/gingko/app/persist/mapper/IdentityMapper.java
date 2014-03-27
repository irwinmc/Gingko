package org.gingko.app.persist.mapper;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.Identity;

import java.util.List;

/**
 * @author TangYing
 */
public interface IdentityMapper {

    List<Identity> selectByPage(@Param("identity") int identity, @Param("limit") int limit, @Param("offset") int offset);

    int totalCount(@Param("identity") int identity);

	List<String> selectMenusByIdentity(int identity);

    void insert(Identity identity);

    void deleteById(int id);
}
