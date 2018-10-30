package com.example.demo.repository;

import java.net.MalformedURLException;

import java.net.URISyntaxException;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;

import org.ektorp.ViewQuery;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserRepositoryImpl extends CouchDbRepositorySupport<User> implements UserRepository {

	public UserRepositoryImpl(CouchDbConnector db) {
		super(User.class, db);
	}

	@Override
	public User saveUser(User user) throws URISyntaxException {

		try {
			CouchDbInstance dbInstance = getconnection();

			// creating database

			CouchDbConnector connector = new StdCouchDbConnector("demoUser", dbInstance);
			connector.createDatabaseIfNotExists();

			String database = connector.getDatabaseName();
			System.out.println(database);

			// creating a documents
			// DesignDocument document = new DesignDocument(user.getId().toString());
			connector.create(user);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		return null;
	}

	// connection

	public CouchDbInstance getconnection() throws MalformedURLException {
		HttpClient client = new StdHttpClient.Builder().url("http://localhost:5984").username("swe").password("123")
				.build();

		CouchDbInstance dbinstance = new StdCouchDbInstance(client);

		return dbinstance;
	}

	@Override
	public User getuser(String id) {
		CouchDbInstance dbInstance;
		User user = new User();
		try {
			dbInstance = getconnection();
			CouchDbConnector connector = new StdCouchDbConnector("demoUser", dbInstance);

			// Options options = new Options().includeRevisions();
			user = connector.get(User.class, id);

			System.out.println("revisionid::" + connector.getCurrentRevision(id));

			// getting all documents

			List<String> docId = connector.getAllDocIds();

			ViewQuery q = new ViewQuery().allDocs().includeDocs(true).keys(docId);

			List<User> bulkLoaded = connector.queryView(q, User.class);
			System.out.println(bulkLoaded);

			System.out.println("user data:::" + user);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	// Delete Function
	public User deleteUser(String id, String revision) {

		try {
			CouchDbInstance dbInstance = getconnection();
			CouchDbConnector connector = new StdCouchDbConnector("demoUser", dbInstance);

			connector.delete(id, revision);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	// Sofa sofa = ...
	// db.update(sofa)
	// // revision will be updated after update
	// sofa.getRevision();

	public User updateUser(User user) {

		try {
			CouchDbInstance dbInstance = getconnection();
			CouchDbConnector connector = new StdCouchDbConnector("demoUser", dbInstance);

			connector.update(user);
			user.getRevision();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	public List<User> findByName(String name) {

		return queryView(name);
	}

}
