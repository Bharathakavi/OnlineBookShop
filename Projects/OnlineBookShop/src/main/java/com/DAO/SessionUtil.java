package com.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionUtil {
	/*Session details variables.*/
	private static SessionUtil instance= new SessionUtil();
	private SessionFactory sessionFactory;
	
	/*Method for getting the sesssion.*/
	public static SessionUtil getInstance() {
		return instance;
	}
	
	private SessionUtil(){
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		sessionFactory=configuration.buildSessionFactory();
	}
	
	/*Method for creating the session.*/
	public static Session getSession(){
		Session session=getInstance().sessionFactory.openSession();
		return session;
	}
}
