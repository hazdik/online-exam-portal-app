package com.onlineportal.http.server.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.onlineportal.http.server.api.HttpHandler;
import com.onlineportal.database.deserialize.DeserializeDatabase;
import com.onlineportal.http.server.HttpRequest;
import com.onlineportal.online.exam.database.IDatabase;
import com.onlineportal.online.exam.database.TableNotFoundException;
import com.onlineportal.online.exam.database.table.Field;
import com.onlineportal.online.exam.database.table.Row;

/**
 * User : Govind
 */
public class ComputeResult implements HttpHandler {

    @Override
    public void doGet(HttpRequest httpRequest, OutputStream outputStream)
            throws IOException {
        // TODO Auto-generated method stub
        int noOfQuestions = 5;
        int marksCount = 0;
        String[] parseWrtToQuestionMark = new String[2];
        parseWrtToQuestionMark = httpRequest.getRequestURI().split("\\?");

        String[] userNameAnswersTimeArray = new String[10];
        userNameAnswersTimeArray = parseWrtToQuestionMark[1]
                .split("\\&");

        String userName = userNameAnswersTimeArray[0].split("\\=")[1];
        String[] answersGivenByUser = new String[5];

        for (int i = 0; i < noOfQuestions; i++) {
            answersGivenByUser[i] = userNameAnswersTimeArray[i + 1].split("\\=")[1];
        }
        String time = userNameAnswersTimeArray[noOfQuestions + 1].split("\\=")[1];

        DeserializeDatabase contentToBeDeserialized = new DeserializeDatabase();
        IDatabase deserializeTestDatabase = contentToBeDeserialized.deserialize();

        for (int i = 0; i < noOfQuestions; i++) {
            try {
                if (deserializeTestDatabase.getTableByName("QuestionNumberQuestionChoicesAnswerTable").getAllRows().
                        get(i).getField("Field6").getValue().equals(answersGivenByUser[i])) {
                    marksCount++;
                }
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TableNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            Row row = new Row();
            row = deserializeTestDatabase.getTableByName("UserNameAnswersTotalTable").createNewRow();
            Field<String> user = new Field<String>(userName, String.class);
            user.setValue(userName);
            Field<String> answer1 = new Field<String>("Answer1", String.class);
            answer1.setValue(answersGivenByUser[0]);
            Field<String> answer2 = new Field<String>("Answer2", String.class);
            answer2.setValue(answersGivenByUser[1]);
            Field<String> answer3 = new Field<String>("Answer3", String.class);
            answer3.setValue(answersGivenByUser[2]);
            Field<String> answer4 = new Field<String>("Answer4", String.class);
            answer4.setValue(answersGivenByUser[3]);
            Field<String> answer5 = new Field<String>("Answer5", String.class);
            answer5.setValue(answersGivenByUser[4]);
            Field<String> total = new Field<String>("Total", String.class);
            total.setValue(String.valueOf(marksCount));
            row.addField(user);
            row.addField(answer1);
            row.addField(answer2);
            row.addField(answer3);
            row.addField(answer4);
            row.addField(answer5);
            row.addField(total);
            deserializeTestDatabase.commit();
        } catch (TableNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        double timeInSec = (Integer.parseInt(time)) / 1000;
        double elapsedTime = timeInSec / 60;

        int integer_Part = (int) elapsedTime;
        double float_Part = elapsedTime - integer_Part;
        int floating_point = (int) (Math.pow(10, 2) * float_Part);
        if (floating_point > 59) {
            floating_point -= 60;
            integer_Part++;
        }
        String final_nbr = String.valueOf(integer_Part) + "." + String.valueOf(floating_point);

        String response = "<h1 style='text-align:center'>Congratulations " + userName
                + "<br />No. of Questions answered correctly : " + marksCount
                + "<br /> Time taken to complete the test : " + final_nbr + " min</h1>";
        PrintWriter out = new PrintWriter(outputStream);
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("Content-Length: " + response.length());
        out.println();
        out.println(response);
        out.flush();
        out.close();
    }

    @Override
    public void doPost(HttpRequest httpRequest, OutputStream outputStream)
            throws IOException {
        // TODO Auto-generated method stub

    }

}
