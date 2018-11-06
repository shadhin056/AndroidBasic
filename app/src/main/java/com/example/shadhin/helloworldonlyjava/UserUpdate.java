package com.example.shadhin.helloworldonlyjava;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    Button backBtn;
    DBManager dbManager;
    String sessionId1;
    String sessionId2;
    String sessionId3;
    String sessionId4;
    String sessionId5;
    String sessionId6;
    String sessionId7;
    String selectedGenderType;
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
        txtGender1 = findViewById(R.id.txtGender1);
        M = findViewById(R.id.M);
        F = findViewById(R.id.F);
        if(sessionId6.equals("M")){
            txtGender1.check(R.id.M);
            selectedGenderType="M";
        }else {
            txtGender1.check(R.id.F);
            selectedGenderType="F";
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
}
