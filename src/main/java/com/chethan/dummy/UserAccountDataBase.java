package com.chethan.dummy;

import com.typesafe.sslconfig.ssl.FakeChainedKeyStore;

import java.util.List;

/**
 * Created by Chethan on Nov 04, 2022.
 */

public class UserAccountDataBase implements Respository<UserAccount>{
    @Override
    public UserAccount getById(int id) {
        return null;
    }

    @Override
    public List<UserAccount> getAll() {
        return null;
    }

    @Override
    public void insert(UserAccount obj) {

    }

    @Override
    public void update(UserAccount obj) {

    }

    @Override
    public void delete(int id) {

    }
}
