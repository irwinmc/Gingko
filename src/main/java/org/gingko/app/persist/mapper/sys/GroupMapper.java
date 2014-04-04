package org.gingko.app.persist.mapper.sys;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.sys.Group;
import org.gingko.app.persist.domain.sys.IdentityMenu;

import java.util.List;

/**
 * @author TangYing
 */
public interface GroupMapper {

    List<Group> selectAll();

    void insert(Group group);

    void update(Group group);

    void deleteById(int groupId);
}
