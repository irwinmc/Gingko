package org.gingko.app.persist.mapper.sys;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.sys.Menu;

import java.util.List;

/**
 * @author TangYing
 */
public interface MenuMapper {

    List<Menu> selectAll();

	List<Menu> selectByParentId( @Param("parentId") String parentId, @Param("identity") int identity);
}
