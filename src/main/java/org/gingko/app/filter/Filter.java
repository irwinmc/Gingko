package org.gingko.app.filter;

/**
 * @author Kyia
 */
public interface Filter<T> {

	void load();

	boolean doFilter(T t);
}
