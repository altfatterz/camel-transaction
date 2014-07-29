package com.backbase.progfun;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

@Service
public class AccountProcessor implements Processor {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void process(Exchange exchange) throws Exception {
        final String method = getHeaderValue(exchange, Exchange.HTTP_METHOD);

        if ("GET".equals(method)) {
            doGet(exchange);
        } else if ("DELETE".equals(method)) {
            doDelete(exchange);
        } else {
            exchange.getOut().setBody(convertToJson(new Message(405, "Unsupported method")));
        }
    }

    private void doGet(Exchange exchange) throws Exception {
        Long id = getHeaderValueAsLong(exchange, "id");
        Account account = accountRepository.findOne(id);
        exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");
        if (account != null) {
            exchange.getOut().setBody(convertToJson(account));
        } else {
            exchange.getOut().setBody(convertToJson(new Message(404, "Not Found")));
        }
    }

    private void doDelete(Exchange exchange) throws Exception {
        Long id = getHeaderValueAsLong(exchange, "id");
        int result = accountRepository.delete(id);
        if (result == 1) {
            exchange.getOut().setBody(convertToJson(new Message(200, "Success")));
        } else {
            exchange.getOut().setBody(convertToJson(new Message(404, "Not Found")));
        }
    }

    public String getHeaderValue(Exchange exchange, String header) throws URISyntaxException {
        return exchange.getIn().getHeader(header, String.class);
    }

    public Long getHeaderValueAsLong(Exchange exchange, String header) throws URISyntaxException {
        return exchange.getIn().getHeader(header, Long.class);
    }

    public String convertToJson(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
