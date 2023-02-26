package com;


import java.io.*;

public class Utils{

    public static String path;
    public static void serializable(Object exp){
        path = "./exp";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectInputStream = new ObjectOutputStream(fileOutputStream);
            objectInputStream.writeObject(exp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unSerializable(){
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
