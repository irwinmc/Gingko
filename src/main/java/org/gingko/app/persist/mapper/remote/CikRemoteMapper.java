package org.gingko.app.persist.mapper.remote;

import org.gingko.app.persist.domain.usk.Cik;

import java.util.List;

/**
 * @author TangYing
 */
public interface CikRemoteMapper {

    List<Cik> select();
}
