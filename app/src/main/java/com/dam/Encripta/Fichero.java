package com.dam.Encripta;

import android.content.Context;

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
    //CREACION O LECTURA DEL FICHERO ALEATORIO E INSERCION DE ARRAYS

    public void GuardaCreaFicheroClavesAndroid(int aNumEm[][]){
        String patrones="";
        for(int i=0;i<aNumEm.length;i++){
            for(int j=0;j<aNumEm[0].length;j++){
                patrones+=aNumEm[i][j];
                patrones+='-';
            }
            patrones+='\n';
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
//            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
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
//            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
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
                if (isNumber(texto.charAt(i)) && temp.length()==1){
                    aNumEm[j][k]=Integer.valueOf(temp);
                }
                else if(isNumber(texto.charAt(i))){
                    temp += texto.charAt(i);
                }
                else if(texto.charAt(i)=='-'){
                    temp="";
                    k++;
                }
                else if(texto.charAt(i)=='\n'){
                    temp="";
                    j++;
                    k=0;
                }

            }
        } catch (Exception ex) {
//            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
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

    public void GuardaCreaFicheroClaves(int aNumEm[][]){
        //generacionn de un fichero con numeros aleatorio entre 0 y 90 separados por '.'
        
        FileOutputStream fos=null;
        DataOutputStream dos=null;
        try{
            fos = new FileOutputStream(this.nombreFichero);
            dos = new DataOutputStream(fos);
            for (int i = 0; i < aNumEm.length; i++) {
                dos.writeChar('.');
                for (int j = 0; j < 16; j++) {
                    dos.writeInt(aNumEm[i][j]);
                }
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
                e.printStackTrace();
            }
        }            
    }
    public void cargaFicheroClaves(int aNumEm[][]){
        File f=null;
        FileInputStream fis=null;
        DataInputStream dis=null;
        try{
            f=new File(this.nombreFichero);
            if (f.exists()) {
                fis=new FileInputStream(f);
                dis=new DataInputStream(fis);
                int i=0;
                while (true) {    
                    dis.readChar();
                    for (int j = 0; j < 16; j++) {
                        aNumEm[i][j]=dis.readInt();
                    }
                    i++;
                }
            }
        }
        catch (EOFException eof) { //...
        }
        catch (FileNotFoundException fnf) { //...
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
            if (dis!=null) dis.close();
            if (fis!=null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                e.printStackTrace();
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
            e.printStackTrace();
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
