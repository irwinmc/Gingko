package org.gingko.app.persist.mapper;

import org.gingko.app.persist.domain.SecHtmlIdx;

import java.util.List;

/**
 * @author Kyia
 */
public interface SecHtmlIdxMapper {

	List<SecHtmlIdx> select();

	void insert(SecHtmlIdx secHtmlIdx);

	void insertList(List<SecHtmlIdx> list);

	void delete();
}
