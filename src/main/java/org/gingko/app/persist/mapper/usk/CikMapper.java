package org.gingko.app.persist.mapper.usk;

import org.gingko.app.persist.domain.usk.Cik;

import java.util.List;

/**
 * @author Kyia
 */
public interface CikMapper {

	List<Cik> selectAll();

    List<String> selectCodeByCik(String cik);

	void insertList(List<Cik> list);

	void delete(Cik cik);
}
