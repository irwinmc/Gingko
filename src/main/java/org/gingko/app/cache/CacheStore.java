package org.gingko.app.cache;

import org.gingko.context.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Kyia
 */
@Service(AppContext.CACHE_STORE)
public class CacheStore {

	private static final Logger LOG = LoggerFactory.getLogger(CacheStore.class);

	public void init() {

	}
}
