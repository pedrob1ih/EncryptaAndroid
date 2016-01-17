package com.dam.Encripta;

import android.content.Context;

import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Encryptacion {
    private int aClavePublica[];
    private int[][] aClavePrivada;
    private Fichero f;
    private Context context;
    private String nombreFichero;
    
    public Encryptacion(String nombreFichero,Context context) {
        this.aClavePublica =new int[3];
        this.aClavePrivada =new int[3][16];
        this.context=context;
        this.nombreFichero=nombreFichero;
        this.f= new Fichero(nombreFichero,context);
        f.cargaFicheroClavesAndroid(aClavePrivada);
        this.f.GuardaCreaFicheroClavesAndroid(this.aClavePrivada);

    }
    
    public static List<Integer> factorial(long number) {
        long n = number;
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
          while (n % i == 0) {
            factors.add(i);
            n /= i;
          }
        }
        return factors;
    }

    //generacion de patrones apartir de texto
    public void generaClaves(String texto) throws Exception{
        for (int i = 0; i < 20; i++) {
            texto+=texto;
        }
        CharArrayReader car=null;
        try{
            car= new CharArrayReader(texto.toCharArray());
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 16; j++) {
                    int valor=car.read();
                    while (valor >30)
                        valor-=30;
                    this.aClavePrivada[i][j]=valor;
                }
            }
        }
        catch(IOException e){
            throw new Exception("fallo al generar");
        }
        finally{
            if(car!=null) car.close();
        }
    }
    
    
    //ELECCION DE CLAVE 
    public void generaClavepublica(){
        Random r=new Random();
        for(int i=0;i< aClavePublica.length;i++)
            //genera una clave q hace posible la desencriptacion
            aClavePublica[i]=r.nextInt(10);
    }
    //ENCRIPTACION
    public String encripta(String texto){
        generaClavepublica();

        return enInsertaClave(this.enRestaStrMas0(texto.replace("\n", "%/")));
    }   //Métodos de Encriptacion
    private String enRestaStrMas0(String texto){ //resta valor a la cadena en ascii individualmente
        int x=0;
        String outPut="";
        //dependiendo de la frecuencia de cada una de las claves se usará un array u otro
        int fA=this.aClavePublica[0];
        int fB=this.aClavePublica[1];
        int fC=this.aClavePublica[2];
        while(x<texto.length()) {
            for (int i = 0; i < fA && x<texto.length(); i++) {
                int a=(int)texto.charAt(x);
                a-=this.aClavePrivada[0][i];
                if(a<10)//dos ceros mas 
                    outPut=outPut+"00"+a;    
                else if(a<100) //un cero mas
                    outPut=outPut+"0"+a;    
                else 
                    outPut=outPut+a;    
                x++;
                //System.out.println(outPut);
            }
            for (int i = 0; i < fB && x<texto.length(); i++) {
                int a=(int)texto.charAt(x);
                a-=this.aClavePrivada[1][i];
                if(a<10)//dos ceros mas 
                    outPut=outPut+"00"+a;    
                else if(a<100) //un cero mas
                    outPut=outPut+"0"+a;    
                else 
                    outPut=outPut+a;    
                x++;
                //System.out.println(outPut);
            }
            for (int i = 0; i < fC && x<texto.length(); i++) {
                int a=(int)texto.charAt(x);
                a-=this.aClavePrivada[2][i];
                if(a<10)//dos ceros mas 
                    outPut=outPut+"00"+a;    
                else if(a<100) //un cero mas
                    outPut=outPut+"0"+a;    
                else 
                    outPut=outPut+a;    
                x++;
                //System.out.println(outPut);
            }
        }                
        return outPut;
    }   //resta valor ascii
    private String enInsertaClave(String texto){
        String outPut="";
        //la clave de descifre se coloca en la primera pos , la tercera y la ultima
        //1 23 1 2 3123
        //System.out.println("clave 1 : "+this.aClavePublica[0]);
        //System.out.println("clave 2 : "+this.aClavePublica[1]);
        //System.out.println("clave 3 : "+this.aClavePublica[2]);
        texto=this.aClavePublica[0]+texto; //insercion de la primera clave
        
        for (int i = 0; i < 3; i++) 
        outPut=outPut+texto.toCharArray()[i]; // se añade la primera clave mas dos caracteres del texto
        
        outPut=outPut+this.aClavePublica[1]; // se añade la segunda clave al texto en la tercera posicion
        
        for (int i = 3; i <texto.toCharArray().length ; i++) 
        outPut=outPut+texto.toCharArray()[i];
        
        outPut=outPut+this.aClavePublica[2]; // se pasa la tercera clave al texto
        
        return outPut;
    }   //ADDICION DE LA CLAVE
     
    //DESENCRIPTACION
    public String desencripta(String tEncriptado) throws Exception{
        //quita la clave y la añade a el array de claves
        //separa dos a dos los caracteres del String de entrada
        ArrayList alSS=desConcat3a3(desQuitaClave(tEncriptado)); 
        // suma el valor que previamente ha sido restado
        desSuma(alSS); 
        String salida=DesValToString(alSS);
        return salida.replaceAll("%/", "\n");
    } //desencritar
    private String desQuitaClave(String tEncriptado){
        String tLimpio="";
        int j=0;
        for (int i = 0; i < tEncriptado.length(); i++) {
            if (i!=0 && i!=3 && i!=tEncriptado.length()-1){
                tLimpio=tLimpio+tEncriptado.charAt(i);
            }    
            else{   
                aClavePublica[j]=Integer.parseInt(String.valueOf(tEncriptado.charAt(i)));
                //System.out.println("desQuitaClave = "+aClavePublica[j]);
                j++;
            }
        }
        return tLimpio;
    } //quita la clave de encriptacion
    private ArrayList desConcat3a3(String tLimpio){
        ArrayList arSS=new ArrayList();
        int i=0;
        String temp="";
        while(i<tLimpio.length()-2){
            temp=String.valueOf(tLimpio.charAt(i));
            temp+=String.valueOf(tLimpio.charAt(i+1));
            temp+=String.valueOf(tLimpio.charAt(i+2));
            arSS.add(Integer.valueOf(temp));    
            i+=3;
            //System.out.println("concatena : "+temp);
        }     
        return arSS;
    } //concatena dos a dos el texto Encriptado
    private void desSuma(ArrayList arSS){
        int x=0;
        
        //dependiendo de la frecuencia de cada una de las claves se usará un array u otro
        int pA=this.aClavePublica[0];
        int pB=this.aClavePublica[1];
        int pC=this.aClavePublica[2];
        
        while(x<arSS.size()) {
            for (int i = 0; i < pA && x<arSS.size() ; i++) {
                arSS.set(x, ((int)arSS.get(x)+this.aClavePrivada[0][i]));
                //System.out.println("suma = "+ ((int)arSS.get(i)+this.aClavePrivada[0][i]));
                x++;
            }
            for (int i = 0; i < pB && x<arSS.size() ; i++) {
                arSS.set(x, ((int)arSS.get(x)+this.aClavePrivada[1][i]));
                //System.out.println("suma = "+ ((int)arSS.get(i)+this.aClavePrivada[1][i]));
                x++;
            }
            for (int i = 0; i < pC && x<arSS.size() ; i++) {
                arSS.set(x, ((int)arSS.get(x)+this.aClavePrivada[2][i]));
                //System.out.println("suma = "+ ((int)arSS.get(i)+this.aClavePrivada[2][i]));
                x++;
            }
        }
    } //suma el valor en ascii
    private String DesValToString(ArrayList arSS) throws Exception{
        CharArrayWriter caw=null;
        try{
            caw= new CharArrayWriter();
            for (int i = 0; i < arSS.size(); i++) {
                caw.write((int)arSS.get(i));
            }
        }
        catch(Exception e){
            throw new Exception("fallo en el flujo al escribir");
        }
        finally{
            if(caw!=null) caw.close();
        }
        
        return caw.toString();
    } //cambia del valor en ascii al caracter

    
    //OTROS METODOS
    public boolean isEncripy(String input){
        try{
            if ((int)input.charAt(0)>47 && (int)input.charAt(0)<58
                    && (int)input.charAt(3)>47 && (int)input.charAt(3)<58
                    && (int)input.charAt(input.length()-1)>47 && 
                    (int)input.charAt(input.length()-1)<58)
                return true;
        }
        catch(Exception e){
            
        }
        return false;
    }

    public void guardarClavePrivadaAndroid(){
        f.GuardaCreaFicheroClavesAndroid(aClavePrivada);
    }
    public void cargaClavePrivadasAndroid(){
        f.cargaFicheroClavesAndroid(aClavePrivada);
    }
    
    //GETTERS & SETTERS
    public int[] getaClavePublica() {
        return aClavePublica;
    }

    public int[][] getaClavePrivada() {
        return aClavePrivada;
    }
    
    public void setaClavePublica(int[] aClavePublica) {
        this.aClavePublica = aClavePublica;
    }

    public void setaClavePrivada(int[][] aClavePrivada) {
        this.aClavePrivada = aClavePrivada;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }
}
