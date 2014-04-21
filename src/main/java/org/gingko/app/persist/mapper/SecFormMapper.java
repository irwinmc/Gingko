package org.gingko.app.persist.mapper;

import org.gingko.app.persist.domain.SecForm;

import java.util.List;

/**
 * @author TangYing
 */
public interface SecFormMapper {

    SecForm select(int id);

    SecForm selectByLocalFile(String localFile);

    List<SecForm> selectBySiid(String siid);

    void update(SecForm secForm);

    void insert(SecForm secForm);
}
