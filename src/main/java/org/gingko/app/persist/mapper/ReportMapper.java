package org.gingko.app.persist.mapper;

import org.gingko.app.persist.domain.Report;

import java.util.List;

/**
 * @author Kyia
 */
public interface ReportMapper {

	List<Report> select();
}
