
package com.onlineportal.http.server.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.onlineportal.http.server.api.HttpHandler;
import com.onlineportal.database.deserialize.DeserializeDatabase;
import com.onlineportal.http.server.HttpRequest;
import com.onlineportal.online.exam.database.IDatabase;
import com.onlineportal.online.exam.database.table.Row;

/**
 * User : Govind
 */
public class LoadLastQuestion implements HttpHandler {

	@Override
	public void doGet(HttpRequest httpRequest, OutputStream outputStream)
			throws IOException {
		// TODO Auto-generated method stub
		DeserializeDatabase contentToBeDeserialized = new DeserializeDatabase();
		IDatabase deserializeTestDatabase = contentToBeDeserialized.deserialize();
		String[] parseWrtToQuestionMark = new String[2];
		parseWrtToQuestionMark = httpRequest.getRequestURI().split("\\?");
		String questionNo = parseWrtToQuestionMark[1].split("\\=")[1];

		int question = Integer.parseInt(questionNo);
		System.out.println(question);
		System.out.println("Loading : URI - "+ httpRequest.getRequestURI());

		deserializeTestDatabase.commit();
		
		
		Row row = deserializeTestDatabase.getAllTables().get(0).getAllRows()
				.get(question-1);
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
					+ "<tr><td><input type = 'button' value = 'Previous' onclick = 'prevHandler()'>"
					+ "<input type = 'button' value = 'End Test' onclick = 'computeResult()'>"+"</td></tr></table>"
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
		// TODO Auto-generated method stub
		
	}

}
