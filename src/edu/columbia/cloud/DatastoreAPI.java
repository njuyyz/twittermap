package edu.columbia.cloud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class DatastoreAPI {

	public static void addRecord(String word, double lat, double lon) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity employee = new Entity("Tweet");

		employee.setProperty("word", word);
		employee.setProperty("Latitude", lat);
		employee.setProperty("Longitude", lon);

		Date tweetDate = new Date();
		employee.setProperty("tweetDate", tweetDate);

		Key k = datastore.put(employee);

	}

	public static ArrayList<Coordinate> getLocationList(String word) {
		ArrayList<Coordinate> result = new ArrayList<Coordinate>();

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		// filter first..
		Filter wordFilter = new FilterPredicate("word", FilterOperator.EQUAL,
				word);

		Query query = new Query("Tweet").setFilter(wordFilter).addSort(
				"tweetDate", SortDirection.DESCENDING);

		List<Entity> tweets = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(100000));
		
		// get the location out of all the tweets
		for(int i = 0; i < tweets.size(); i++)	{
			double lat = (double) tweets.get(i).getProperty("Latitude");
			double lon = (double) tweets.get(i).getProperty("Longitude");
			Coordinate c = new Coordinate(lat, lon);
			result.add(c);
		}
		

		return result;
	}
}
