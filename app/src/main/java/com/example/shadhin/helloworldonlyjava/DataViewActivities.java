package com.example.shadhin.helloworldonlyjava;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.ArrayList;

public class DataViewActivities extends AppCompatActivity {
    TextView nameView;
    TextView phoneView;
    TextView birthdayView;
    TextView emailView;
    TextView passwordView;
    TextView gender;
    DBManager dbManager;
    Button loadData;
    Button regGoBack;


    String sessionId1;
    String sessionId2;
    String sessionId3;
    String sessionId4;
    String sessionId5;
    String sessionId6;
    String sessionId7;

    String name_insert;
    String phone_insert;
    String birthday_insert;
    String email_insert;
    String password_insert;
    ArrayList<AdapterItems> listnewsData;
    MyCustomAdapter myadapter;
    //AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_data_view);


        nameView = (TextView) findViewById(R.id.name_display);
        phoneView = (TextView) findViewById(R.id.phone_display);
        birthdayView = (TextView) findViewById(R.id.birthday_display);
        emailView = (TextView) findViewById(R.id.email);
        passwordView = (TextView) findViewById(R.id.password);
        gender = (TextView) findViewById(R.id.gender);
        loadData = (Button) findViewById(R.id.load_data);
        regGoBack = findViewById(R.id.reg_btn);

        dbManager = new DBManager(this);
        sessionId1 = getIntent().getStringExtra("nick_name1");
        sessionId2 = getIntent().getStringExtra("phone_number1");
        sessionId3 = getIntent().getStringExtra("birthday1");
        sessionId4 = getIntent().getStringExtra("email1");
        sessionId5 = getIntent().getStringExtra("password1");
        sessionId6 = getIntent().getStringExtra("repassword1");
        sessionId7 = getIntent().getStringExtra("gender");


        nameView.setText(sessionId1);
        phoneView.setText(sessionId2);
        birthdayView.setText(sessionId3);
        emailView.setText(sessionId4);
        passwordView.setText(sessionId5);
        gender.setText(sessionId7);

        name_insert = sessionId1;
        phone_insert = sessionId2;
        birthday_insert = sessionId3;
        email_insert = sessionId4;
        password_insert = sessionId5;
        listnewsData = new ArrayList<AdapterItems>();


        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_data();
            }
        });
        regGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataViewActivities.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void load_data() {
        listnewsData.clear();
       /* if (name_insert != null) {
            ContentValues values = new ContentValues();
            values.put(DBManager.COL_USERNAME, name_insert);
            values.put(DBManager.COL_PHONE, phone_insert);
            values.put(DBManager.COL_BIRTHDAY, birthday_insert);
            values.put(DBManager.COL_EMAIL, email_insert);
            values.put(DBManager.COL_PASSWORD, password_insert);
            long id = dbManager.insert(values);
            if (id > 0) {
                // Toast.makeText(getApplicationContext(), "Data is added and user id : " + id, Toast.LENGTH_LONG).show();
                name_insert = null;
                phone_insert = null;
                birthday_insert = null;
                email_insert = null;
                password_insert = null;

            } else {
                Toast.makeText(getApplicationContext(), "Can not inserted : ", Toast.LENGTH_LONG).show();
            }
        }*/

        //adapter class
        String DESC = "ID DESC";
        Cursor cursor = dbManager.query(null, null, null, DESC, null);
        if (cursor.moveToFirst()) {
            String tableData = "";
            do {
                /*tableData+=cursor.getString(cursor.getColumnIndex(DBManager.COL_ID))+","+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_USERNAME))+","+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL))+","+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_BIRTHDAY))+","+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_PHONE))+"::";*/

                listnewsData.add(new AdapterItems(cursor.getString(cursor.getColumnIndex(DBManager.COL_ID)),
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_PHONE)),
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_BIRTHDAY))));
            } while (cursor.moveToNext());
            Toast.makeText(getApplicationContext(), tableData, Toast.LENGTH_LONG).show();
        }
        //add data and view it
        myadapter = new MyCustomAdapter(listnewsData);


        ListView lsNews = (ListView) findViewById(R.id.lv_user);
        lsNews.setAdapter(myadapter);//intisal with data
    }


    //display news list
    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<AdapterItems> listnewsDataAdpater;

        public MyCustomAdapter(ArrayList<AdapterItems> listnewsDataAdpater) {
            this.listnewsDataAdpater = listnewsDataAdpater;
        }

        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_users, null);
            final AdapterItems s = listnewsDataAdpater.get(position);
            TextView id = (TextView) myView.findViewById(R.id.a_id);
            id.setText(s.id);
            TextView name = (TextView) myView.findViewById(R.id.a_name_id);
            name.setText(s.nickName);
            TextView phone = (TextView) myView.findViewById(R.id.a_phone_id);
            phone.setText(s.phone);
            TextView email = (TextView) myView.findViewById(R.id.a_email_id);
            email.setText(s.email);
            TextView birthday = (TextView) myView.findViewById(R.id.a_birthday_id);
            birthday.setText(s.birthday);

            Button btnDelete = (Button) myView.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] SelectionArgs = {s.id};
                    int id_deleted = dbManager.Delete("ID=?", SelectionArgs);
                    if (id_deleted > 0) {
                        load_data();
                    }
                }
            });
            Button updateUserId;
            updateUserId = (Button) myView.findViewById(R.id.update_user_by_id);
            updateUserId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DataViewActivities.this, UserUpdate.class);
                    intent.putExtra("nick_name2", String.valueOf(s.nickName));
                    intent.putExtra("phone_number2", String.valueOf(s.phone));
                    intent.putExtra("birthday2", String.valueOf(s.birthday));
                    intent.putExtra("email2", String.valueOf(s.email));
                    intent.putExtra("id2", String.valueOf(s.id));
                    startActivity(intent);
                }
            });


            return myView;
        }

    }
}
