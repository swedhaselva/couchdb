package com.example.demo.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

import com.example.demo.model.User;

public class CouchDbImpl extends CouchDbRepositorySupport<User> {

	public CouchDbImpl(CouchDbConnector db) {
        super(User.class, db);
    }

	
}
