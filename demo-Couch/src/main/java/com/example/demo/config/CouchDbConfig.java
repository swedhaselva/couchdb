/*package com.example.demo.config;

import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchDbConfig {

	// connection

	@Bean
	public CouchDbInstance getconnection() throws MalformedURLException {
		HttpClient client = new StdHttpClient.Builder().url("http://localhost:5984").username("chandan")
				.password("root").build();

		CouchDbInstance dbinstance = new StdCouchDbInstance(client);

		return dbinstance;
	}

	@Bean
	public CouchDbConnector couchDbConnector() throws MalformedURLException {
		CouchDbInstance dbInstance = getconnection();
		CouchDbConnector connector = new StdCouchDbConnector("demouser", dbInstance);
		connector.createDatabaseIfNotExists();
		return connector;
	}

}
*/