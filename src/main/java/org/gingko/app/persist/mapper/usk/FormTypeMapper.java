package org.gingko.app.persist.mapper.usk;

import org.apache.ibatis.annotations.Param;
import org.gingko.app.persist.domain.usk.FormType;

import java.util.List;

/**
 * @author TangYing
 */
public interface FormTypeMapper {

    List<FormType> selectAll();

    List<String> selectByUsed();

    FormType selectByFormType(String formType);

    void update(@Param("formTypes") String[] formTypes ,@Param("used")  int used);

    void insert(FormType formType);

    void delete(String formType);
}
