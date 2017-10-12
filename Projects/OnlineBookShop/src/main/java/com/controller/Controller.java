package com.controller;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.DAO.Book;
import com.DAO.BookDAO;
import com.DAO.Document;
import com.DAO.DocumentDAO;
import com.DAO.RequestBook;
import com.DAO.UserDetails;

/*import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

/**
 * 
 *
 * @author Bharathakavi
 * @since 5.0
 */
@Path("/book")
public class Controller
{
	final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Controller.class);

	/* Method to register the values from the user interface to database. */
	/**
	 * 
	 * @param email
	 * @param psw
	 * @param cpass
	 * @param usertype
	 * @return Response as where page will redirect.
	 * @throws URISyntaxException
	 * @author Bharathakavi
	 * @since 5.0
	 */
	@POST
	@Path("/register")
	@Produces("text/html")
	public Response register(@FormParam("email") String email, @FormParam("psw") String psw, @FormParam("cpsw") String cpass, @FormParam("usertype") String usertype) throws URISyntaxException {

		logger.info("Started Register Process");
		if (psw.equals(cpass)) {
			UserDetails u = new UserDetails(email, psw, usertype);
			BookDAO b = new BookDAO();
			String result = b.addUser(u);
			if (result.equals("success")) {
				java.net.URI location = new java.net.URI("../Homepage.html");
				return Response.temporaryRedirect(location).build();
				// return Response.status(200).entity("Successfull").build();
			} else {
				logger.warn(" User Not Registered");
				java.net.URI location = new java.net.URI("../register.html");
				return Response.temporaryRedirect(location).build();
			}
		} else {
			logger.warn("Passwords Mismatch");
			return Response.status(200).entity("Both Passwords Should be Same").build();
		}
	}

	/* Method for the login process */
	/**
	 * 
	 * @param email
	 * @param psw
	 * @param usertype
	 * @return
	 * @throws URISyntaxException
	 * @author Bharathakavi
	 * @since 5.0
	 */
	@POST
	@Path("/login")
	@Produces("text/html")
	public Response login(@FormParam("email") String email, @FormParam("psw") String psw, @FormParam("usertype") String usertype) throws URISyntaxException {
		logger.info("Login Process");
		UserDetails u = new UserDetails(email, psw, usertype);
		BookDAO b = new BookDAO();
		// id = ud.login(u);
		String result = b.login(u);
		if (result.equals("success")) {
			if (usertype.equals("Admin")) {
				java.net.URI location = new java.net.URI("../book.html");
				return Response.temporaryRedirect(location).build();
			} else {
				java.net.URI location = new java.net.URI("../main.html");
				return Response.temporaryRedirect(location).build();
			}
		} else {
			logger.warn("Login Failed. Invalid Credentials");
			java.net.URI location = new java.net.URI("../login.html");
			return Response.temporaryRedirect(location).build();
		}
	}

	/* Method adds the new book to the user logged in. */
	/**
	 * 
	 * @param name
	 * @param aname
	 * @param price
	 * @return
	 * @throws URISyntaxException
	 * @throws JSONException
	 * @author Bharathakavi
	 * @since 5.0
	 */
	@POST
	@Path("/addbook")
	@Produces("text/html")
	public Response addBook(@FormParam("name") String name, @FormParam("aname") String aname, @FormParam("price") double price) throws URISyntaxException, JSONException {

		logger.info("Add Book Process");
		Book r = new Book(name, aname, price);
		BookDAO b = new BookDAO();
		int result = b.addBook(r);
		if (result != 0) {
			System.out.println("Successfully Added");
			RequestBook rb = new RequestBook(name);
			System.out.println("entering check method");
			String details = b.check(rb);
			if (details != null) {
				JSONArray jsonArray = new JSONArray(details);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					String name1 = json.getString("name");
					String author = json.getString("author");
					String email = json.getString("email");
					System.out.println(name1 + " " + author + " " + email);
					System.out.println("You have to send mail");
					// from,password,to,subject,message

					logger.info("Mail Creation");
					String msg = "Hi...\nYour Requested Book " + name1 + " \nAuthor " + author + " \nPrice " + price + " \n is available Now..\nGo and Buy...";
					String sub = "Book Availability Notification- MegaWorld BookShop";
					send("mailbookmybooks@gmail.com", "admin-123", email, "Book Availability Notification-BookShow", msg);
				}
			} else {
				System.out.println("No need for mail sending");
			}
			java.net.URI location = new java.net.URI("../book.html");
			return Response.temporaryRedirect(location).build();
		} else

		{
			logger.warn("Problem in book adding");
			System.out.println("Cannot Add the Book");
			java.net.URI location = new java.net.URI("../addbook.html");
			return Response.temporaryRedirect(location).build();
		}
	}

	/* Method adds the new book to the user logged in. */
	/**
	 * 
	 * @param name
	 * @param aname
	 * @return
	 * @throws URISyntaxException
	 * @author Bharathakavi
	 * @since 5.0
	 */
	@POST
	@Path("/requestBook")
	@Produces("text/html")
	public Response requestBook(@FormParam("name") String name, @FormParam("aname") String aname) throws URISyntaxException {

		logger.info("Request Book Process");
		RequestBook r = new RequestBook(name, aname);
		BookDAO b = new BookDAO();
		int result = b.requestBook(r);
		if (result != 0) {
			System.out.println("Successfully Added");
			java.net.URI location = new java.net.URI("../main.html");
			return Response.temporaryRedirect(location).build();
		} else {
			logger.warn("Book Adding Problem");
			System.out.println("Cannot Add the Book");
			java.net.URI location = new java.net.URI("../addbook.html");
			return Response.temporaryRedirect(location).build();
		}
	}

	/* Method for logout invoking which releases the session. */
	/**
	 * 
	 * @return
	 * @author Bharathakavi
	 * @since 5.0
	 */
	@POST
	@Path("/logout")
	public Response logout() {
		logger.info("Logout Process");
		String output = "Successfully logged Out";
		System.out.println("Controller");
		BookDAO b = new BookDAO();
		b.logout();
		System.out.println("Return from controller");
		return Response.ok(output).build();
	}

	public static void send(final String from, final String password, String to, String sub, String msg) {

		/*
		 * logger.info("Mail Sending Process"); // Get properties object Properties props = new Properties(); props.put("mail.smtp.host", "smtp.gmail.com"); props.put("mail.smtp.socketFactory.port", "465"); props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); props.put("mail.smtp.auth", "true"); props.put("mail.smtp.port", "465"); // get Session Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() { protected PasswordAuthentication getPasswordAuthentication() { return new PasswordAuthentication(from, password); } }); // compose message try { MimeMessage message = new MimeMessage(session); message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); message.setSubject(sub); message.setText(msg); // send message
		 * Transport.send(message); System.out.println("message sent successfully"); } catch (MessagingException e) {logger.error("Mail Sending error"); throw new RuntimeException(e); }
		 */
	}

	/* Controller method which displays the Available Book details */
	/**
	 * 
	 * @param value
	 * @return
	 * @throws URISyntaxException
	 * @throws JSONException
	 * @author Bharathakavi
	 * @since 5.0
	 */
	@POST
	@Path("/display")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response display(String value) throws URISyntaxException, JSONException {
		logger.info("Display Book Process");
		System.out.println("Controller");
		JSONObject json = new JSONObject(value);
		String name = json.getString("operation");
		System.out.println(name);
		BookDAO b = new BookDAO();
		String output = b.display();
		System.out.println(output);
		return Response.ok(output).build();
	}

	@POST
	@Path("/addFile")
	public Response addFiles() throws URISyntaxException {
		// logger.info("Started Register Process");
		Document document = new Document("Responsive","ID_001","Email details");
		DocumentDAO b = new DocumentDAO();
		String result = b.addFile(document);
		java.net.URI location = new java.net.URI("../Homepage.html");
		return Response.temporaryRedirect(location).build();
		//return Response.ok(result).build();
	}

	@POST
	@Path("/displayFile")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response displayFile(String value) throws URISyntaxException, JSONException {
		//logger.info("Display File Process");
		System.out.println("Controller");
		JSONObject json = new JSONObject(value);
		String name = json.getString("operation");
		System.out.println(name);
		DocumentDAO b = new DocumentDAO();
		String output = b.displayFile();
		System.out.println(output);
		return Response.ok(output).build();
	}
	
	@POST
	@Path("/labels")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response labelCount(String value) throws URISyntaxException, JSONException {
		//logger.info("Display File Process");
		System.out.println("Controller");
		JSONObject json = new JSONObject(value);
		String name = json.getString("operation");
		System.out.println(name);
		DocumentDAO b = new DocumentDAO();
		String output = b.labelCount();
		System.out.println(output);
		return Response.ok(output).build();
	}

	
}
