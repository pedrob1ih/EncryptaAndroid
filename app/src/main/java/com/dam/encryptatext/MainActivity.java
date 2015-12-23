package com.dam.encryptatext;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import encrypta3.Encryptacion;


public class MainActivity extends AppCompatActivity {
    private TextView tvSalida;
    private Button bConvertir,bCopiar;
    private EditText eTEntradaUsuario;
    private Encryptacion e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        e= new Encryptacion("FicheroClaves");

        tvSalida= (TextView) findViewById(R.id.tVSalida);
        bConvertir= (Button) findViewById(R.id.bConvertir);
        bCopiar= (Button) findViewById(R.id.bCopiar);
        eTEntradaUsuario= (EditText) findViewById(R.id.eTEntradaUsuario);

        bConvertir.setOnClickListener( listenerConvertir);
        bCopiar.setOnClickListener(listenerCopiar);

        Bundle b= this.getIntent().getExtras();
        if(b!=null){
            e= new Encryptacion(b.getString("nombreDelFichero"));
        }
        else
            e= new Encryptacion("FicheroClaves");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("nombreDelFichero");
                e= new Encryptacion(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                ;
            }
        }
    }

    View.OnClickListener listenerConvertir=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
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
            i.putExtra("nombreDelFichero",e.getNombreFichero());
            startActivityForResult(i, 1);
        }

        return super.onOptionsItemSelected(item);
    }
}
