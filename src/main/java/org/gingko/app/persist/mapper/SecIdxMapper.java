package org.gingko.app.persist.mapper;

import org.gingko.app.persist.domain.SecIdx;

import java.util.List;

/**
 * @author Kyia
 */
public interface SecIdxMapper {

	List<SecIdx> select();

	void insert(SecIdx secIdx);

	void insertList(List<SecIdx> list);

	void delete();
}
