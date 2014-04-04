package org.gingko.app.persist.mapper;

import org.gingko.app.persist.domain.SecIdxForm;

import java.util.List;

/**
 * @author TangYing
 */
public interface SecIdxFormMapper {

    SecIdxForm select(int id);

    List<SecIdxForm> selectBySiid(String siid);

    void update(SecIdxForm secIdxForm);

    void insert(SecIdxForm secIdxForm);
}
