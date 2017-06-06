package com.example.easynote.utils;

import android.content.Context;

import com.example.easynote.App;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by SEVAK on 09.05.2017.
 */

public class StorageHelper {

    public static <T extends Serializable> void serialize(String fileName, T obj) {
        try {
            FileOutputStream fileOutputStream = App.getInstance().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(obj);
            out.flush();
            out.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Object deserialize(String fileName) {
        Object object = null;

        try {
            FileInputStream fileInputStream = App.getInstance().openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            object = in.readObject();
            in.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }
}