package com.DAO;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DocumentDAO
{

	public String addFile(Document document) {
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		int ret = (Integer) session.save(document);
		tx.commit();
		return "success";
	}

	public String displayFile() {
		System.out.println("DAO Class");
		JSONArray json = null;
		try {
			Session session = SessionUtil.getSession();
			Transaction tx = session.beginTransaction();
			json = new JSONArray();
            System.out.println("in");
			Query query = session.createQuery("from Document");
			List<Document> resultList = query.list();
			System.out.println("num of Users:" + resultList.size());
			for (Document next : resultList) {
				System.out.println("next Document: " + next.getFilename());
				/* Stores the values to the json array and sent to controller */
				JSONObject jo = new JSONObject();
				jo.put("id", next.getId());
				jo.put("filename", next.getFilename());
				jo.put("label", next.getLabel());
				jo.put("parentId", next.getParent_id());
				json.put(jo);
			}
			tx.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public String labelCount(){
		System.out.println("DAO Class");
		JSONObject jo = new JSONObject();
		int countResponsive=0;
		int countNonLabelled=0;
		int countAll=0;
		try {
			Session session = SessionUtil.getSession();
			Transaction tx = session.beginTransaction();
            System.out.println("in");
			Query query = session.createQuery("from Document");
			List<Document> resultList = query.list();
			System.out.println("num of Users:" + resultList.size());
			for (Document next : resultList) {
				System.out.println("next Document: " + next.getLabel());
				if(next.getLabel().equalsIgnoreCase("Responsive")){
					countResponsive+=1;
				}
				else {
					countNonLabelled+=1;
				}				
			}
			countAll=countResponsive+countNonLabelled;
			jo.put("all",countAll );
			jo.put("nonlabeled",countNonLabelled);
			jo.put("priviledged",countResponsive);
			tx.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return jo.toString();
	}
}
