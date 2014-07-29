package com.backbase.progfun;

import com.jayway.jsonpath.JsonPath;
import org.apache.camel.Exchange;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class AccountProcessorTest extends CamelSpringTestSupport {

    private static final String ACCOUNTS_ENDPOINT = "seda:bar";

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("app-context.xml");
    }

    @Test
    public void findOne() {

        final Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("id", 1);
        headers.put(Exchange.HTTP_METHOD, "GET");

        final String response = template.requestBodyAndHeaders(ACCOUNTS_ENDPOINT, null, headers, String.class);
        assertEquals("Checking account",JsonPath.read(response, "name"));

    }

    @Test
    public void delete() {

        final Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("id", 1);
        headers.put(Exchange.HTTP_METHOD, "DELETE");

        final String response = template.requestBodyAndHeaders(ACCOUNTS_ENDPOINT, null, headers, String.class);
        assertEquals(200 ,JsonPath.read(response, "code"));

    }

    @Override
    public boolean isCreateCamelContextPerClass() {
        return true;
    }
}
