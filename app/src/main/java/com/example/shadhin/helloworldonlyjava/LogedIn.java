package com.example.shadhin.helloworldonlyjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LogedIn extends AppCompatActivity {
    TextView name;
    TextView after_login_user_display1;
    TextView email;
    TextView phone;
    TextView password;
    TextView birthday;
    TextView after_login_gender;
    TextView after_login_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_in);
        name = findViewById(R.id.after_login_name_display1);
        after_login_user_display1 = findViewById(R.id.after_login_user_display1);
        email = findViewById(R.id.after_login_email1);
        phone = findViewById(R.id.after_login_phone_display1);
        //birthday = findViewById(R.id.after_login_birthday_display);
        //password = findViewById(R.id.after_login_password);

        name.setText(getIntent().getStringExtra("nick_name1"));
        after_login_user_display1.setText(getIntent().getStringExtra("user_name1"));
        phone.setText(getIntent().getStringExtra("phone_number1"));
        //birthday.setText(getIntent().getStringExtra("nick_name3")getIntent().getStringExtra("nick_name3"));
        email.setText(getIntent().getStringExtra("email1"));
        //password.setText(getIntent().getStringExtra("nick_name3"));
        //after_login_gender.setText(getIntent().getStringExtra("nick_name3"));
        //after_login_country.setText(getIntent().getStringExtra("nick_name3"));
        //agree1.setText(getIntent().getStringExtra("nick_name3"));


    }
}
