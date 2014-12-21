
package com.onlineportal.online.exam.readingquestions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User : Govind
 */
public class ReadFromTheFile {
	
	 BufferedReader buffReader;
	 public String readQuestionsFromFile(String fileName) throws FileNotFoundException{
		 buffReader = new BufferedReader(new FileReader(fileName));
		 String contentsInTheFile = "";
		 String content;
		  try {
			while((content = buffReader.readLine())!= null){
				  contentsInTheFile = contentsInTheFile + content +"\n";
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  finally{
//		 System.out.println("Contents : "+contentsInTheFile);
		 try {
			buffReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
		  return contentsInTheFile;
		  
		  
	}
}
