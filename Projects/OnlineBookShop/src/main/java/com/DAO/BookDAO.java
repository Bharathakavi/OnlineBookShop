package com.DAO;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * 
 *
 * @author Bharathakavi
 * @since 5.0
 */
public class BookDAO
{
	
	private static UserDetails user;

	private static Session session = null;

	/* Method which add the user details to the database. */
	/**
	 * 
	 * @param u
	 * @return
	 * @author Bharathakavi
	 * @since 5.0
	 */
	public String addUser(UserDetails u) {
		Session session_new = SessionUtil.getSession();
		Transaction tx = session_new.beginTransaction();
		String ret = null;
		Criteria criteria = session_new.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", u.getEmail()));
		UserDetails userDetails = (UserDetails) criteria.uniqueResult();
		/* Check for the user Details which already exists or not. */
		if (userDetails == null) {

			System.out.println("UserName OK");
			ret = "success";

		} else {
			System.out.println("UserName Already exixts");
			ret = "fail";
		}
		session_new.save(u);
		tx.commit();
		session_new.close();
		return ret;
	}

	/* Method reads the name and perform the login process */
	/**
	 * 
	 * @param u
	 * @return
	 * @throws NullPointerException
	 * @author Bharathakavi
	 * @since 5.0
	 */
	public String login(UserDetails u) throws NullPointerException {

		session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		// int ret = 0;
		String ret = null;
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", u.getEmail()));
		UserDetails userDetails = (UserDetails) criteria.uniqueResult();
		if (userDetails != null) {
			System.out.println(userDetails.getEmail());
			System.out.println(userDetails.getPassword());
			System.out.println(u.getEmail());
			System.out.println(u.getPassword());
			if (u.getEmail().equals(userDetails.getEmail())) {
				if (u.getPassword().equals(userDetails.getPassword()) && u.getType().equals(userDetails.getType())) {
					System.out.println("UserName and Password Matches");
					user = userDetails;
					ret = "success";
				}
			} else {
				System.out.println("Password Mismatch");
				ret = "fail";
			}
		} else {
			System.out.println("No such User Exists");
			ret = "fail";
		}
		tx.commit();
		// session.close();
		return ret;
	}

	/* Method reads the book details and stores in the database */
	/**
	 * 
	 * @param r
	 * @return
	 * @author Bharathakavi
	 * @since 5.0
	 */
	public int addBook(Book r) {
		Transaction tx = session.beginTransaction();
		int ret = (Integer) session.save(r);
		tx.commit();
		return ret;
	}

	/* Method reads the book details and stores in the database */
	/**
	 * 
	 * @param r
	 * @return
	 * @author Bharathakavi
	 * @since 5.0
	 */
	public int requestBook(RequestBook r) {
		Transaction tx = session.beginTransaction();
		r.setEmail(user.getEmail());
		int ret = (Integer) session.save(r);
		tx.commit();
		return ret;
	}

	/* Method for logout which deallocates the current user in this session */
	/**
	 * 
	 * @return
	 * @author Bharathakavi
	 * @since 5.0
	 */
	public String logout() {
		System.out.println("DAO function" + user);
		user = null;
		System.out.println(user);
		session.close();
		return "success";
	}

	/* Method checks for the availability of the book */
	/**
	 * 
	 * @param rb
	 * @return
	 * @author Bharathakavi
	 * @since 5.0
	 */
	public String check(RequestBook rb) {
		System.out.println("Entered");
		JSONArray json = null;
		String re = null;
		try {
			Transaction tx = session.beginTransaction();
			json = new JSONArray();
			System.out.println("Going to fetch");
			Criteria sc = session.createCriteria(RequestBook.class);
			sc.add(Restrictions.eq("name", rb.getName()));
			List<RequestBook> resultList = sc.list();
			System.out.println("num of Users:" + resultList.size());
			if (resultList.size() != 0) {
				for (RequestBook next : resultList) {
					System.out.println("next Book: " + next.toString());
					/*
					 * Stores the values to the json array and sent to controller
					 */
					JSONObject jo = new JSONObject();
					jo.put("name", next.getName());
					jo.put("email", next.getEmail());
					jo.put("author", next.getAuthor());
					json.put(jo);
				}
				re = json.toString();
				System.out.println(re);
			} else {
				re = null;
			}
			tx.commit();
		} catch (Exception e) {
			e.getMessage();
		}
		return re;
	}

	/* Method displays the eligible students based on company */
	/**
	 * 
	 * @return
	 * @author Bharathakavi
	 * @since 5.0
	 */
	public String display() {
		JSONArray json = null;
		try {
			Transaction tx = session.beginTransaction();
			json = new JSONArray();
			System.out.println("DAO Class");

			Query query = session.createQuery("from Book");
			List<Book> resultList = query.list();
			System.out.println("num of Users:" + resultList.size());
			for (Book next : resultList) {
				System.out.println("next Student: " + next);
				/* Stores the values to the json array and sent to controller */
				JSONObject jo = new JSONObject();
				jo.put("name", next.getName());
				jo.put("author", next.getAuthor());
				jo.put("price", next.getPrice());
				json.put(jo);
			}
			tx.commit();
		} catch (Exception e) {
			e.getMessage();
		}
		return json.toString();
	}

}
