package com.deccom.config.dbmigrations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.mongobee.Mongobee;
import com.github.mongobee.exception.MongobeeException;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

/**
 * A simple class to remove all the collections from the MongoDatabase, and the execution of all the migrations.
 * All the config info must be in "src/main/resources/config/application-dev.yml"
 * @author Albert_FX
 *
 */
public class PopulateDatabase {
	private static String fileName = "src/main/resources/config/application-dev.yml";
	public static void main(String[] args) throws ClassNotFoundException {
		printAppHeader();
		String uri = getDatabaseUri();
		String dbname = getDatabaseName();
		MongoTemplate mongoTemplate = new MongoTemplate(
				new MongoClient(
						uri
						.split("mongodb://")[1]
						.split(":")[0])
				, dbname);
		removeCollections(mongoTemplate);
		
		Mongobee runner = new Mongobee(uri);
		runner.setDbName(dbname);
		String packageName = InitialSetupMigration.class.getPackage().getName();
		runner.setChangeLogsScanPackage(packageName); 
		runner.setEnabled(true);
		try {
			runner.execute();
		} catch (MongobeeException e) {
			e.printStackTrace();
		}
	}

	
	private static void removeCollections(MongoTemplate mongoTemplate) {
		for(String collectionName: mongoTemplate.getCollectionNames()) {
			mongoTemplate.getCollection(collectionName).remove(new BasicDBObject());
		}
	}
	
	private static String getDatabaseUri() {
		String uri = null;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			uri = stream.filter(s->s.contains("uri: "))
					.reduce("", (x,y)->x+y)
					.split("uri: ")[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uri;
	}
	private static String getDatabaseName() {
		String dbname = null;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			dbname = stream.filter(s->s.contains("database: "))
					.reduce("", (x,y)->x+y)
					.split("database: ")[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dbname;
	}
	private static void printAppHeader() {
	String msg = 
			"	   _____                  _       _         _____        _        _                    \n" +
			"	  |  __ \\                | |     | |       |  __ \\      | |      | |                   \n" +
			"	  | |__) |__  _ __  _   _| | __ _| |_ ___  | |  | | __ _| |_ __ _| |__   __ _ ___  ___ \n" +
			"	  |  ___/ _ \\| '_ \\| | | | |/ _` | __/ _ \\ | |  | |/ _` | __/ _` | '_ \\ / _` / __|/ _ \\\n"+
			"	  | |  | (_) | |_) | |_| | | (_| | ||  __/ | |__| | (_| | || (_| | |_) | (_| \\__ \\  __/\n" +
			"	  |_|   \\___/| .__/ \\__,_|_|\\__,_|\\__\\___| |_____/ \\__,_|\\__\\__,_|_.__/ \\__,_|___/\\___| \n";
	System.out.println(msg);
	}

}
