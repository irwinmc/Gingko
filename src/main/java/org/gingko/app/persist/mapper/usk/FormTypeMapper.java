package org.gingko.app.persist.mapper.usk;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.usk.FormType;

import java.util.List;

/**
 * @author TangYing
 */
public interface FormTypeMapper {

    List<FormType> selectAll();

    List<FormType> selectByGroupId(int groupId);

    FormType selectByGroupAndType(@Param("formType")  String formType, @Param("groupId")  int groupId);

    void insert(FormType formType);

    void deleteById(int id);
}
