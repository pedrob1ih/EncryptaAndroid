package com.dam.Encripta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Fichero {
    private String nombreFichero;

    public Fichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }
    //CREACION O LECTURA DEL FICHERO ALEATORIO E INSERCION DE ARRAYS
    
    public void GuardaCreaFicheroClaves(int aNumEm[][]){
        //generacionn de un fichero con numeros aleatorio entre 0 y 90 separados por '.'
        
        FileOutputStream fos=null;
        DataOutputStream dos=null;
        try{
            fos = new FileOutputStream("Fich");
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
    public void leeFicheroClaves(int aNumEm[][]){
        File f=null;
        FileInputStream fis=null;
        DataInputStream dis=null;
        try{
            f=new File("Fich");
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
    public String cargaFicheroEncriptado(File archivo){
        File f=null;
        FileInputStream fis=null;
        DataInputStream dis=null;
        String outPut="";
        try{
            f=archivo;
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
