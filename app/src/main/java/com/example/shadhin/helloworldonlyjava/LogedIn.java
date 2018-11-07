package com.example.shadhin.helloworldonlyjava;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class LogedIn extends AppCompatActivity {
    TextView name;
    TextView after_login_user_display1;
    TextView email;
    TextView phone;
    ImageView profileImageFromDb1;
    String password1;
    DBManager dbManager;
    TextView password;
    TextView birthday;
    TextView after_login_gender;
    TextView after_login_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loged_in);
        dbManager = new DBManager(this);
        name = findViewById(R.id.after_login_name_display1);
        after_login_user_display1 = findViewById(R.id.after_login_user_display1);
        email = findViewById(R.id.after_login_email1);
        phone = findViewById(R.id.after_login_phone_display1);
        profileImageFromDb1 = findViewById(R.id.profileImageFromDb1);
        //birthday = findViewById(R.id.after_login_birthday_display);
        //password = findViewById(R.id.after_login_password);

        name.setText(getIntent().getStringExtra("nick_name1"));
        after_login_user_display1.setText(getIntent().getStringExtra("user_name1"));
        phone.setText(getIntent().getStringExtra("phone_number1"));
        //birthday.setText(getIntent().getStringExtra("nick_name3")getIntent().getStringExtra("nick_name3"));
        email.setText(getIntent().getStringExtra("email1"));
        password1=getIntent().getStringExtra("password1");
        //password.setText(getIntent().getStringExtra("nick_name3"));
        //after_login_gender.setText(getIntent().getStringExtra("nick_name3"));
        //after_login_country.setText(getIntent().getStringExtra("nick_name3"));
        //agree1.setText(getIntent().getStringExtra("nick_name3"));
        //Bitmap testImage = sessionId6.getImageDataInBitmap();

        String[] selectionsArgs = {getIntent().getStringExtra("email1"), password1};
        byte[] b;
        Cursor cursor = dbManager.query(null, "Email like ? and Password like ?", selectionsArgs, null, "1");
        if (cursor.moveToFirst()) {
            String tableData = "";
            do {
                b = cursor.getBlob(cursor.getColumnIndex(DBManager.COL_ProfilePic));
            } while (cursor.moveToNext());

            //byte[] b = sessionId6.getBytes(Charset.forName("UTF-8"));
            if(b!=null){
                Bitmap testImage = BitmapFactory.decodeByteArray(b, 0, b.length);
                profileImageFromDb1.setImageBitmap(testImage);
            }

        }

    }
}
