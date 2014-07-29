package com.backbase.progfun;

public interface AccountRepository {

    int delete(Long id);

    Account findOne(Long id);

}
