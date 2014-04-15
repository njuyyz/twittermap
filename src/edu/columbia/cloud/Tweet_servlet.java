package edu.columbia.cloud;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Tweet_servlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String tweetbookName = req.getParameter("tweetbookName");
		Key tweetbookKey = KeyFactory.createKey("Tweetbook", tweetbookName);
		
		String content = req.getParameter("content");
		
		Date date = new Date();
		Entity greeting = new Entity("Greeting", tweetbookName);
		
		greeting.setProperty("user", user);
		greeting.setProperty("date", date);
		greeting.setProperty("content", content);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(greeting);

		// sendRediret
		//req.getRequestDispatcher("Index.jsp").forward(req, resp);
		
		
		// resp.sendRedirect("/tweetbook.jsp?guestbookName=" + tweetbookName);
	}
}