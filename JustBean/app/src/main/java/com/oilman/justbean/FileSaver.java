package com.oilman.justbean;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaver {
    Gson gson = new Gson();
    public static void saveFile(){
        File file = new File("temp.txt");
        if (file.canWrite()){
            try {
                FileOutputStream     fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write("草死我了".getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
