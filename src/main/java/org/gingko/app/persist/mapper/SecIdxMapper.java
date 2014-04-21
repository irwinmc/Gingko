package org.gingko.app.persist.mapper;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.SecIdx;

import java.util.List;

/**
 * @author Kyia
 */
public interface SecIdxMapper {

    List<SecIdx> select();

    List<SecIdx> selectByPage(@Param("formTypes") String[] formTypes,
                              @Param("date") String date,
                              @Param("limit") int limit,
                              @Param("offset") int offset);

    int selectTotalCount(@Param("formTypes") String[] formTypes,
                         @Param("date") String date);

    List<SecIdx> selectByDate(String date);

    SecIdx selectBySiid(String siid);

    void update(SecIdx secIdx);

    void insert(SecIdx secIdx);

    void insertList(List<SecIdx> list);

    void delete();

    void deleteByDate(String date);
}
