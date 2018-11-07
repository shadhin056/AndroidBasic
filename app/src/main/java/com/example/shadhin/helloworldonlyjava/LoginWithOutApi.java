package com.example.shadhin.helloworldonlyjava;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginWithOutApi extends AppCompatActivity {
    Button regBtn;
    Button loginBtn;
    EditText email;
    EditText password;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        regBtn = findViewById(R.id.reg_btn);
        loginBtn = findViewById(R.id.login_btn);
        Button fixedData = findViewById(R.id.fixed_data);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        dbManager = new DBManager(this);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginWithOutApi.this, MainActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DESC = "ID DESC";
                String[] selectionsArgs = {email.getText().toString(), password.getText().toString()};
                //String[] selectionsArgs1 = {email.getText().toString(), password.getText().toString()};
                Cursor cursor = dbManager.query(null, "Email like ? and Password like ?", selectionsArgs, DESC, "1");
                Cursor cursor1 = dbManager.query(null, "Phone like ? and Password like ?", selectionsArgs, DESC, "1");
            /*    if (cursor!=null){
                    Toast.makeText(LoginWithOutApi.this,email.getText().toString()+" and "+password.getText().toString(),Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(LoginWithOutApi.this,"Not found",Toast.LENGTH_LONG).show();
                }*/
                Intent intent = new Intent(LoginWithOutApi.this, AfterLogin.class);
                if (cursor.moveToFirst()) {
                    String tableData = "";
                    do {
                /*tableData+=cursor.getString(cursor.getColumnIndex(DBManager.COL_ID))+","+
                                cursor.getString(cursor.getColumnIndex(DBManager.COL_USERNAME))+","+
                                cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL))+","+
                                cursor.getString(cursor.getColumnIndex(DBManager.COL_BIRTHDAY))+","+
                                cursor.getString(cursor.getColumnIndex(DBManager.COL_PHONE))+"::";*/

                        intent.putExtra("user_name3", cursor.getString(cursor.getColumnIndex(DBManager.COL_USERNAME_MAIN)));
                        intent.putExtra("nick_name3", cursor.getString(cursor.getColumnIndex(DBManager.COL_USERNAME)));
                        intent.putExtra("phone_number3", cursor.getString(cursor.getColumnIndex(DBManager.COL_PHONE)));
                        intent.putExtra("birthday3", cursor.getString(cursor.getColumnIndex(DBManager.COL_BIRTHDAY)));
                        intent.putExtra("email3", cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL)));
                        intent.putExtra("password3", cursor.getString(cursor.getColumnIndex(DBManager.COL_PASSWORD)));
                        intent.putExtra("gender3", cursor.getString(cursor.getColumnIndex(DBManager.COL_GENDER)));
                        intent.putExtra("country3", cursor.getString(cursor.getColumnIndex(DBManager.COL_COUNTRY)));
                        intent.putExtra("agree3", cursor.getString(cursor.getColumnIndex(DBManager.COL_AGREE)));
                        if(cursor.getBlob(cursor.getColumnIndex(DBManager.COL_ProfilePic))!=null){
                            intent.putExtra("propic3", cursor.getBlob(cursor.getColumnIndex(DBManager.COL_ProfilePic)).toString());
                        }

                    } while (cursor.moveToNext());


                   /* intent.putExtra("nick_name3", email.getText().toString());
                    intent.putExtra("password3", password.getText().toString());

*/
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "Loged in" + cursor.getColumnIndex(DBManager.COL_ProfilePic), Toast.LENGTH_LONG).show();
                }else if(cursor1.moveToFirst()){
                    do {
                /*tableData+=cursor.getString(cursor.getColumnIndex(DBManager.COL_ID))+","+
                                cursor.getString(cursor.getColumnIndex(DBManager.COL_USERNAME))+","+
                                cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL))+","+
                                cursor.getString(cursor.getColumnIndex(DBManager.COL_BIRTHDAY))+","+
                                cursor.getString(cursor.getColumnIndex(DBManager.COL_PHONE))+"::";*/

                        intent.putExtra("user_name3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_USERNAME_MAIN)));
                        intent.putExtra("nick_name3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_USERNAME)));
                        intent.putExtra("phone_number3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_PHONE)));
                        intent.putExtra("birthday3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_BIRTHDAY)));
                        intent.putExtra("email3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_EMAIL)));
                        intent.putExtra("password3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_PASSWORD)));
                        intent.putExtra("gender3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_GENDER)));
                        intent.putExtra("country3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_COUNTRY)));
                        intent.putExtra("agree3", cursor1.getString(cursor1.getColumnIndex(DBManager.COL_AGREE)));
                        if(cursor1.getBlob(cursor1.getColumnIndex(DBManager.COL_ProfilePic))!=null){
                            intent.putExtra("propic3", cursor1.getBlob(cursor1.getColumnIndex(DBManager.COL_ProfilePic)).toString());
                        }

                    } while (cursor1.moveToNext());
                    startActivity(intent);
                }else
                 {
                    Toast.makeText(getApplicationContext(), "Please insert currect email and password", Toast.LENGTH_LONG).show();
                }

            }
        });
        fixedData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText("shadhinemail@gmail.com");
                password.setText("1Shadhin%");
            }
        });
    }
}
