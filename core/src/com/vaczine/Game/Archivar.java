package com.vaczine.Game;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Archivar {

    private File f;
    private FileWriter fr;

    public Archivar() {

    }

    public void creararchivo(String str) {
        try {
            f = new File(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            fr = new FileWriter(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void escribirArchivo(String linea) {

        try {
            fr.append(linea);
        }

        catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    public void cerrarArchivo() {

        try {
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}