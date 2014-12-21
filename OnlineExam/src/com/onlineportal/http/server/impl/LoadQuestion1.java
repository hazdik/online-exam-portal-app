package com.onlineportal.http.server.impl;

import com.onlineportal.http.server.api.HttpHandler;
import com.onlineportal.online.exam.database.TableNotFoundException;
import com.onlineportal.database.deserialize.DeserializeDatabase;
import com.onlineportal.http.server.HttpRequest;
import com.onlineportal.online.exam.database.IDatabase;
import com.onlineportal.online.exam.database.table.Field;
import com.onlineportal.online.exam.database.table.Row;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class LoadQuestion1 implements HttpHandler {

	@Override
	public void doGet(HttpRequest httpRequest, OutputStream outputStream)
			throws IOException {
		
		DeserializeDatabase contentToBeDeserialized = new DeserializeDatabase();
		IDatabase deserializeTestDatabase = contentToBeDeserialized
				.deserialize();
		
		if(httpRequest.getRequestURI().contains("?")){
			String[] parseWrtQuestionMark = new String[2]; 
			parseWrtQuestionMark = httpRequest.getRequestURI().split("\\?");
			String userName = parseWrtQuestionMark[1].split("\\=")[1];
			
			try {				
				if(deserializeTestDatabase.getTableByName("UserName").getAllRows().size()==0){
					Field<String> user = new Field<String>("User : "+userName, String.class);
					user.setValue(userName);					
					deserializeTestDatabase.getTableByName("UserName").createNewRow().addField(user);
				}
				else{
					for(int i = 0;i < deserializeTestDatabase.getTableByName("UserName").getAllRows().size();i++){
						try {
							if(deserializeTestDatabase.getTableByName("UserName").getAllRows().get(i).getField("User : "+userName).getValue().equals(userName)){
								String response = "User "+userName+" has already taken the test !!";
								PrintWriter out = new PrintWriter(outputStream);
								out.println("HTTP/1.1 200 OK");
								out.println("Content-Type: text/html");
								out.println("Content-Length: " + response.length());
								out.println();
								out.println(response);
								out.flush();
								out.close();
							}
						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Field<String> user = new Field<String>("User : "+userName, String.class);
					user.setValue(userName);
					deserializeTestDatabase.getTableByName("UserName").createNewRow().addField(user);
				}
			} catch (TableNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Row row = deserializeTestDatabase.getAllTables().get(0).getAllRows()
				.get(0);

		deserializeTestDatabase.commit();
		
		try {
			String response = "<table align='center' cellspacing='10' cellpadding='10'>"+
					"<tr><td>"+row.getField("Field0").getValue()+"&nbsp;&nbsp"
					+row.getField("Field1").getValue()+"</td></tr>"
					+ "<tr><td><input type='radio' name='Choice' value='a'>"+"&nbsp;&nbsp"
					+ row.getField("Field2").getValue()+"</td></tr>"
					+ "<tr><td><input type='radio' name='Choice' value='b'>"+"&nbsp;&nbsp"
					+ row.getField("Field3").getValue()+"</td></tr>"
					+ "<tr><td><input type='radio' name='Choice' value='c'>"+"&nbsp;&nbsp"
					+ row.getField("Field4").getValue()+"</td></tr>"
					+ "<tr><td><input type='radio' name='Choice' value='d'>"+"&nbsp;&nbsp"
					+ row.getField("Field5").getValue() +"</td></tr>"+"<br/>"
					+ "<tr><td><input type = 'button'  value = 'Next' align='center' onclick = 'nextHandler()'>"+"</td></tr></table>"
					;
			
		PrintWriter out = new PrintWriter(outputStream);
		out.println("HTTP/1.1 200 OK");
		out.println("Content-Type: text/html");
		out.println("Content-Length: " + response.length());
		out.println();
		out.println(response);
		out.flush();
		out.close();
		}
		catch(NoSuchFieldException fieldNotPresent){
			System.out.println("There is a mismatch in the fieldName");
		}
		
}

	@Override
	public void doPost(HttpRequest httpRequest, OutputStream outputStream)
			throws IOException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}
}
