package com.onlineportal.online.exam.database.client;

import java.io.FileNotFoundException;

import com.onlineportal.online.exam.database.DatabaseFactory;
import com.onlineportal.online.exam.database.IDatabase;
import com.onlineportal.online.exam.readingquestions.ReadFromTheFile;
import com.onlineportal.online.exam.database.table.Field;
import com.onlineportal.online.exam.database.table.ITable;
import com.onlineportal.online.exam.database.table.Row;

/**
 * User : Govind
 */
public class ProcessContents {

    /**
     * @throws NoSuchFieldException
     */

    void processData() throws NoSuchFieldException {

        DatabaseFactory databaseFactory = DatabaseFactory.getDatabaseFactory();
        IDatabase testDatabase = databaseFactory.createDatabase("userDetails");

        String fileHavingQuestionsAndAnswers = "../../../../static-files/Questions/Question.txt";

        ReadFromTheFile readFile = new ReadFromTheFile();
        String questionsInTheFile = null;

        try {
            questionsInTheFile = readFile
                    .readQuestionsFromFile(fileHavingQuestionsAndAnswers);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String[] parsedQuestionsAndAnswers = new String[10];
        ITable masterTable = testDatabase.createTable("QuestionNumberQuestionChoicesAnswerTable");
        Row questionAnswerChoiceRow = new Row();

        parsedQuestionsAndAnswers = questionsInTheFile.split("\n");
        String[] questionAndAnswer = new String[20];

        for (int i = 0; i < parsedQuestionsAndAnswers.length; i++) {
            questionAndAnswer = parsedQuestionsAndAnswers[i].split(",");
            questionAnswerChoiceRow = masterTable.createNewRow();
            Field<String> questionAnswerChoice;
            for (int j = 0; j < questionAndAnswer.length; j++) {
                questionAnswerChoice = new Field<String>("Field" + j, String.class);
                masterTable.updateRow(questionAnswerChoiceRow, "Field" + j, questionAndAnswer[j]);
            }
        }

        for (int i = 0; i < parsedQuestionsAndAnswers.length; i++) {
            for (int j = 0; j < questionAndAnswer.length; j++) {
                System.out.println(masterTable.getAllRows().get(i).getField("Field" + j).getValue());
            }
        }

        ITable table2 = testDatabase.createTable("UserNameAnswersTotalTable");
        ITable table3 = testDatabase.createTable("UserName");
        testDatabase.commit();

    }

}
