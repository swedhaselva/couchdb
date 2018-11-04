package com.example.demo.repository;

import java.net.MalformedURLException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;

import org.ektorp.ViewQuery;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserRepositoryImpl implements UserRepository {

	@Override
	public User saveUser(User user) throws URISyntaxException {

		try {
			CouchDbInstance dbInstance = getconnection();

			// creating database

			CouchDbConnector connector = new StdCouchDbConnector("demouser", dbInstance);
			CouchDbImpl impl = new CouchDbImpl(connector);
			connector.createDatabaseIfNotExists();

			String database = connector.getDatabaseName();
			System.out.println(database);

			// creating a documents
			// DesignDocument document = new DesignDocument(user.getId().toString());
			// connector.create(user);
			impl.add(user);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		return null;
	}

	// connection

	public CouchDbInstance getconnection() throws MalformedURLException {
		HttpClient client = new StdHttpClient.Builder().url("http://localhost:5984").username("chandan")
				.password("root").build();

		CouchDbInstance dbinstance = new StdCouchDbInstance(client);

		return dbinstance;
	}

	@Override
	public User getuser(String id) {
		CouchDbInstance dbInstance;
		User user = new User();
		try {
			dbInstance = getconnection();
			CouchDbConnector connector = new StdCouchDbConnector("demouser", dbInstance);

			// Options options = new Options().includeRevisions();
			user = connector.get(User.class, id);

			// System.out.println("revisionid::" + connector.getCurrentRevision(id));

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
			CouchDbConnector connector = new StdCouchDbConnector("demouser", dbInstance);

			connector.delete(id, revision);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public User updateUser(User user) {

		try {
			CouchDbInstance dbInstance = getconnection();
			CouchDbConnector connector = new StdCouchDbConnector("demouser", dbInstance);

			connector.update(user);
			user.getRevision();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	@Override
	public List<User> findByName(String name) throws MalformedURLException {

		CouchDbInstance dbInstance = getconnection();
		CouchDbConnector connector = new StdCouchDbConnector("demouser", dbInstance);
		CouchDbImpl impl = new CouchDbImpl(connector);
		List<User> allUsers = new ArrayList<>();

		List<User> users = impl.getAll();

		for (User user : users) {
			if (user.getName().equalsIgnoreCase(name)) {
				allUsers.add(user);
			}
		}

		return allUsers;
	}

}
