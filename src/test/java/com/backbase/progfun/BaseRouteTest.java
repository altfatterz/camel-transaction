package com.backbase.progfun;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseRouteTest implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    protected ProducerTemplate template;

    @Override
    public void afterPropertiesSet() throws Exception {
        template = getCamelContext().createProducerTemplate();
    }

    private CamelContext getCamelContext() {
        return applicationContext.getBean("foo", CamelContext.class);
    }

}
