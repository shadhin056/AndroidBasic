package com.example.shadhin.helloworldonlyjava;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class UserUpdate extends AppCompatActivity {
    EditText upName;
    EditText upPhone;
    EditText upBDate;
    EditText upEmail;
    EditText upPassword;
    EditText upRePassword;
    Button updateBtn;
    RadioGroup txtGender1;
    RadioButton M;
    RadioButton F;
    Spinner SpPresentCountry1;
    Button backBtn;
    DBManager dbManager;
    String sessionId1;
    String sessionId2;
    String sessionId3;
    String sessionId4;
    String sessionId5;
    String sessionId6;
    String sessionId7;
    String sessionId8;
    String selected1;
    String selectedGenderType;
    CheckBox checkBox1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);
        sessionId1 = getIntent().getStringExtra("nick_name2");
        sessionId2 = getIntent().getStringExtra("phone_number2");
        sessionId3 = getIntent().getStringExtra("birthday2");
        sessionId4 = getIntent().getStringExtra("email2");
        sessionId5 = getIntent().getStringExtra("id2");
        sessionId6 = getIntent().getStringExtra("gender2");
        sessionId7 = getIntent().getStringExtra("country2");
        sessionId8 = getIntent().getStringExtra("agree2");
        SpPresentCountry1 = (Spinner) findViewById(R.id.SpPresentCountry1);
        String compareValue = sessionId7;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpPresentCountry1.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            SpPresentCountry1.setSelection(spinnerPosition);
        }



        txtGender1 = findViewById(R.id.txtGender1);
        checkBox1 = (CheckBox) findViewById(R.id.chk1);
        M = findViewById(R.id.M);
        F = findViewById(R.id.F);
        if(sessionId6.equals("M")){
            txtGender1.check(R.id.M);
            selectedGenderType="M";
        }else {
            txtGender1.check(R.id.F);
            selectedGenderType="F";
        }


        if(sessionId8.equals("YES")){

            checkBox1.setChecked(true);
            selected1="YES";
        }else {
            checkBox1.setChecked(false);
            selected1="NO";
        }
        //txtGender1.check(getIntent().getIntExtra("gender2", R.id.M));
        //txtGender1.check(getIntent().getIntExtra("gender2", R.id.F));
        upName = findViewById(R.id.up_nick_name);
        upPhone = findViewById(R.id.up_phone_numer);
        upBDate = findViewById(R.id.up_birthday);
        upEmail = findViewById(R.id.up_email);
        upPassword = findViewById(R.id.up_password);
        upRePassword = findViewById(R.id.up_reenter_password);
        updateBtn = findViewById(R.id.update_user_btn);
        backBtn = findViewById(R.id.back_update);


        upName.setText(sessionId1);
        upPhone.setText(sessionId2);
        upBDate.setText(sessionId3);
        upEmail.setText(sessionId4);

        txtGender1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);


                if (index == 0) {
                    selectedGenderType = "M";

                } else {
                    selectedGenderType = "F";

                }

               /* Log.e("--Index", index + "");
                Log.e("--selectedGenderType", selectedGenderType);*/
            }
        });
        dbManager = new DBManager(this);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(DBManager.COL_USERNAME, upName.getText().toString());
                values.put(DBManager.COL_PHONE, upPhone.getText().toString());
                values.put(DBManager.COL_BIRTHDAY, upBDate.getText().toString());
                values.put(DBManager.COL_PASSWORD, upPassword.getText().toString());
                values.put(DBManager.COL_EMAIL, upEmail.getText().toString());
                values.put(DBManager.COL_ID, sessionId5);
                values.put(DBManager.COL_GENDER, selectedGenderType);
                values.put(DBManager.COL_COUNTRY,  SpPresentCountry1.getSelectedItem().toString());
                values.put(DBManager.COL_AGREE,  selected1);
                String[] SelectionArgs = {sessionId5};
                int id = dbManager.update(values, "ID=?", SelectionArgs);
                if (id > 0) {
                    Toast.makeText(getApplicationContext(), "Data is Updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data is not Updated", Toast.LENGTH_LONG).show();
                }
            }

            ;


        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserUpdate.this, DataViewActivities.class);
                startActivity(intent);
            }
        });


    }
    public void itemClicked1(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            selected1="YES";
        }
    }
}
