package org.gingko.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:gingko/beans/server-beans.xml")
public class AppSpringConfig {

}
