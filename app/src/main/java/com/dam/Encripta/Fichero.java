package com.dam.Encripta;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Fichero {
    private String nombreFichero;
    private Context context;

    public Fichero(String nombreFichero,Context context) {
        this.nombreFichero = nombreFichero;
        this.context= context;
    }

    public void GuardaCreaFicheroClavesAndroid(int aNumEm[][]){
        String patrones="";
        for(int i=0;i<aNumEm.length;i++){
            for(int j=0;j<aNumEm[0].length;j++){
                patrones+=aNumEm[i][j];
                patrones+='-';
            }
            patrones+='%';
        }
        try
        {
            OutputStreamWriter fout=
                    new OutputStreamWriter(this.context.openFileOutput(nombreFichero, Context.MODE_PRIVATE));
            fout.write(patrones);
            fout.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", ex.getMessage());
        }
    }


    public String cargaFicheroClavesAndroid() {
        String texto="";
        try {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput(nombreFichero)));

            texto = fin.readLine();
            fin.close();
        } catch (Exception ex) {
            Log.e("Ficheros", ex.getMessage());
        }
        return texto;
    }

    public void cargaFicheroClavesAndroid(int aNumEm[][]) {
        try {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput(nombreFichero)));

            String texto = fin.readLine();
            fin.close();
            String temp="";
            int j=0;
            int k=0;

            for(int i=0;i<texto.length();i++){
                if(isNumber(texto.charAt(i))){
                    temp += texto.charAt(i);
                }
                else if(texto.charAt(i)=='-'){
                    aNumEm[j][k]=Integer.valueOf(temp);
                    temp="";
                    k++;
                }
                else if(texto.charAt(i)=='%'){
                    temp="";
                    j++;
                    k=0;
                }

            }
        } catch (Exception ex) {
            Log.e("Ficheros", ex.getMessage());
        }
    }
    public boolean isNumber(char input){
        try{
            if (input>47 && input<58)
                return true;
        }
        catch(Exception e){

        }
        return false;
    }

    public void guardaFicheroEncriptado(String nombreFichero,String texto){
        FileOutputStream fos=null;
        DataOutputStream dos=null;
        try{
            fos = new FileOutputStream(nombreFichero);
            dos = new DataOutputStream(fos);
            for (int i = 0; i < texto.length(); i++) {
                dos.writeChar(texto.charAt(i));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if (dos!=null) dos.close();
                if (fos!=null) fos.close();
            }
            catch(IOException e){
                Log.e("Ficheros", e.getMessage());
            }
        }            
    }
    
    public String cargaFicheroEncriptado(String nombreFichero){
        File f=null;
        FileInputStream fis=null;
        DataInputStream dis=null;
        String outPut="";
        try{
            f=new File(nombreFichero);
            if (f.exists()) {
                fis=new FileInputStream(f);
                dis=new DataInputStream(fis);
                while (true) {    
                    outPut+=dis.readChar();
                }
            }
        }
        catch (EOFException eof) { //...
        }
        catch (FileNotFoundException fnf) { //...
            
        }
        catch (IOException e) {
            Log.e("Ficheros", e.getMessage());
        }
        finally {
            try {
            if (dis!=null) dis.close();
            if (fis!=null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outPut;
    }
}
