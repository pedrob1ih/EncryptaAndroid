package com.dam.encryptatext;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import encrypta3.Encryptacion;


public class MainActivity extends AppCompatActivity {
    TextView tvSalida;
    Button bConvertir,bCopiar;
    EditText eTEntradaUsuario;
    Encryptacion e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Encryptacion e= new Encryptacion("FicheroClaves");

        tvSalida= (TextView) findViewById(R.id.tVSalida);
        bConvertir= (Button) findViewById(R.id.bConvertir);
        bCopiar= (Button) findViewById(R.id.bCopiar);
        eTEntradaUsuario= (EditText) findViewById(R.id.eTEntradaUsuario);

        bConvertir.setOnClickListener( listenerConvertir);
        bCopiar.setOnClickListener(listenerCopiar);

    }

    View.OnClickListener listenerConvertir=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            e=new Encryptacion("FicheroClaves");
            if(eTEntradaUsuario.getText().toString()!=null){
                tvSalida.setText(e.encripta(eTEntradaUsuario.getText().toString()));

            }

        }
    };
    View.OnClickListener listenerCopiar=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("textoCopiado", tvSalida.getText());
            clipboard.setPrimaryClip(clip);
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cambioClaves) {
            Intent i = new Intent(MainActivity.this,GestionContraseniasActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
