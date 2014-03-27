package org.gingko.app.persist.mapper;

import org.gingko.app.persist.domain.ReportKeyword;

import java.util.List;

/**
 * @author Kyia
 */
public interface ReportKeywordMapper {

	List<ReportKeyword> select();

	void deleteById(int id);
}
