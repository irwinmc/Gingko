package org.gingko.app.persist.mapper;

import org.gingko.app.persist.domain.SecHtmlIdx;

import java.util.List;

/**
 * @author Kyia
 */
public interface SecHtmlIdxMapper {

	List<SecHtmlIdx> select();

	List<SecHtmlIdx> selectBySiid();

	void insert(SecHtmlIdx secHtmlIdx);

	void insertList(List<SecHtmlIdx> list);

	void delete();

	void deleteBySiid();

	void deleteByDate(String date);
}
