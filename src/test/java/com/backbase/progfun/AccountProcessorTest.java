package com.backbase.progfun;

import com.jayway.jsonpath.JsonPath;
import org.apache.camel.Exchange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:app-context.xml")
public class AccountProcessorTest extends BaseRouteTest {

    private static final String ACCOUNTS_ENDPOINT = "direct:bar";

    @Test
    @Transactional
    public void findOne() {

        final Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("id", 1);
        headers.put(Exchange.HTTP_METHOD, "GET");

        final String response = template.requestBodyAndHeaders(ACCOUNTS_ENDPOINT, null, headers, String.class);
        assertEquals("Checking account",JsonPath.read(response, "name"));

    }

    @Test
    @Transactional
    public void delete() {

        final Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("id", 1);
        headers.put(Exchange.HTTP_METHOD, "DELETE");

        final String response = template.requestBodyAndHeaders(ACCOUNTS_ENDPOINT, null, headers, String.class);
        assertEquals(200, JsonPath.read(response, "code"));

    }

}
