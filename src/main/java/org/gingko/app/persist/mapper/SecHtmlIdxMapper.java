package org.gingko.app.persist.mapper;

import org.gingko.app.persist.domain.SecHtmlIdx;

import java.util.List;

/**
 * @author Kyia
 */
public interface SecHtmlIdxMapper {

	List<SecHtmlIdx> select();

	List<SecHtmlIdx> selectBySiid(String siid);

    List<SecHtmlIdx> selectByDate(String date);

	void insert(SecHtmlIdx secHtmlIdx);

	void insertList(List<SecHtmlIdx> list);

	void delete();

	void deleteBySiid(String siid);

	void deleteByDate(String date);
}
