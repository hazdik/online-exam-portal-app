
package com.onlineportal.database.deserialize;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.onlineportal.online.exam.database.IDatabase;


/**
 * User : Govind
 */
public class DeserializeDatabase {

    public IDatabase deserialize() {

        IDatabase deserializeTestDatabase = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("commitContent.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            deserializeTestDatabase = (IDatabase) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializeTestDatabase;
    }
}
