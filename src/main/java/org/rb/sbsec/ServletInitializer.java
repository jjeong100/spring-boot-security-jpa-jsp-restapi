package org.rb.sbsec;

import org.springframework.boot.builder.SpringApplicationBuilder;
/** 2.0 import org.springframework.boot.web.support.SpringBootServletInitializer; **/
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SbSecJspApplication.class);
	}

}
