package com.example.shadhin.helloworldonlyjava;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Note extends AppCompatActivity {
    EditText txtnote;
    EditText txtemail;
    Button btn_add_note;
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        dbManager = new DBManager(this);
        txtnote=findViewById(R.id.txtnote);
        txtemail=findViewById(R.id.txtemail);
        txtemail.setText(getIntent().getStringExtra("email"));
        btn_add_note=findViewById(R.id.btn_add_note);
        btn_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_data1();
            }
        });
    }

    private void load_data1() {
        ContentValues values = new ContentValues();
        values.put(DBManager.COL_EMAIL, txtemail.getText().toString());
        values.put(DBManager.COL_NOTE,  txtnote.getText().toString());
        long id = dbManager.insert1(values);

        if (id > 0) {
            Toast.makeText(getApplicationContext(), "Data is added and id : " + id, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Can not inserted : ", Toast.LENGTH_LONG).show();
        }
    }
}
