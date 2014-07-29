package com.backbase.progfun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class H2AccountRepository implements AccountRepository {

    private static final String GET_ONE_ACCOUNT = "select * from accounts where id = ?";
    private static final String DELETE_ONE_ACCOUNT = "delete from accounts where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(DELETE_ONE_ACCOUNT, new Object[] {id});
    }

    @Override
    public Account findOne(Long id) {
        return jdbcTemplate.queryForObject(GET_ONE_ACCOUNT, new Object[] {id}, new AccountRowMapper());
    }

    private class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Account account = new Account();

            account.setId(rs.getLong("id"));
            account.setName(rs.getString("name"));
            account.setAccountNumber(rs.getString("accountNumber"));

            return account;
        }
    }
}
