package com.example.shadhin.helloworldonlyjava;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;

public class AfterLogin extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView phone;
    TextView password;
    TextView birthday;
    TextView after_login_gender;
    TextView after_login_country;
    TextView agree1;
    String sessionId1;
    String sessionId2;
    String sessionId3;
    String sessionId4;
    String sessionId5;
    String sessionId6;
    String sessionId7;
    String sessionId8;
    String sessionId9;
    Button pick_image;
    Button upIntoDbBtn;
    Button btn_view_profile;
    ImageView profile_image;
    ImageView profileImageFromDb;
    Button btn_add_note;
    Button btn_load;
    Button btn_reg;
    DBManager dbManager;
    private static final String IMAGE_DIRECTORY = "/image_store";
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login);
        name = findViewById(R.id.after_login_name_display);
        email = findViewById(R.id.after_login_email);
        phone = findViewById(R.id.after_login_phone_display);
        birthday = findViewById(R.id.after_login_birthday_display);
        password = findViewById(R.id.after_login_password);
        pick_image = findViewById(R.id.pick_image_btn);
        after_login_gender = findViewById(R.id.after_login_gender);
        after_login_country = findViewById(R.id.after_login_country);
        profile_image = findViewById(R.id.profile_image);
        upIntoDbBtn = findViewById(R.id.up_into_db_btn);
        profileImageFromDb = findViewById(R.id.profile_image_from_db);
        btn_view_profile = findViewById(R.id.btn_view_profile);
        btn_add_note = findViewById(R.id.btn_add_note);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_load = findViewById(R.id.btn_load);
        agree1 = findViewById(R.id.agree1);
        dbManager = new DBManager(this);
        sessionId1 = getIntent().getStringExtra("nick_name3");
        sessionId2 = getIntent().getStringExtra("phone_number3");
        sessionId3 = getIntent().getStringExtra("birthday3");
        sessionId4 = getIntent().getStringExtra("email3");
        sessionId5 = getIntent().getStringExtra("password3");
        sessionId6 = getIntent().getStringExtra("propic3");
        sessionId7 = getIntent().getStringExtra("gender3");
        sessionId8 = getIntent().getStringExtra("country3");
        sessionId9 = getIntent().getStringExtra("agree3");
        //Bitmap testImage = sessionId6.getImageDataInBitmap();

        String[] selectionsArgs = {sessionId4, sessionId5};
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
                profileImageFromDb.setImageBitmap(testImage);
            }

        }

        name.setText(sessionId1);
        phone.setText(sessionId2);
        birthday.setText(sessionId3);
        email.setText(sessionId4);
        password.setText(sessionId5);
        after_login_gender.setText(sessionId7);
        after_login_country.setText(sessionId8);
        agree1.setText(sessionId9);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLogin.this, Note.class);
                intent.putExtra("email", getIntent().getStringExtra("email3"));
                startActivity(intent);
            }
        });
        btn_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLogin.this, LogedIn.class);
                intent.putExtra("user_name1",getIntent().getStringExtra("user_name3"));
                intent.putExtra("nick_name1", getIntent().getStringExtra("nick_name3"));
                intent.putExtra("phone_number1",getIntent().getStringExtra("phone_number3"));
                intent.putExtra("birthday1", getIntent().getStringExtra("birthday3"));
                intent.putExtra("email1", getIntent().getStringExtra("email3"));
                intent.putExtra("password1", password.getText().toString());
                intent.putExtra("propic1", getIntent().getStringExtra("propic3"));
                startActivity(intent);
            }
        });
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoad();
            }
        });
        upIntoDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_image.setDrawingCacheEnabled(true);
                profile_image.buildDrawingCache();
                Bitmap bitmap = profile_image.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                ContentValues values = new ContentValues();
                values.put(DBManager.COL_ProfilePic, data);

                //dbManager.addToDbImage(data);
                String[] SelectionArgs = {email.getText().toString()};
                int id = dbManager.update(values, "Email=?", SelectionArgs);
                if (id > 0) {
                    Toast.makeText(getApplicationContext(), "Data is Updated", Toast.LENGTH_LONG).show();
                    //loadPic();
                } else {
                    Toast.makeText(getApplicationContext(), "Data is not Updated", Toast.LENGTH_LONG).show();
                }


            }
        });

        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }

    public void onLoad(){
        //adapter class

        ArrayList<NoteItem> listnewsData = new ArrayList<NoteItem>();
        MyCustomAdapter myadapter;
        String DESC = "ID DESC";
        String[] selectionsArgs = {getIntent().getStringExtra("email3")};
        Cursor cursor = dbManager.query1(null, "Email like ?",selectionsArgs , DESC, null);
        if (cursor.moveToFirst()) {
            String tableData = "";
            do {
                /*tableData+=cursor.getString(cursor.getColumnIndex(DBManager.COL_ID))+","+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_USERNAME))+","+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL))+","+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_BIRTHDAY))+","+
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_PHONE))+"::";*/
                listnewsData.add(new NoteItem(cursor.getString(cursor.getColumnIndex(DBManager.COL_ID)),
                        cursor.getString(cursor.getColumnIndex(DBManager.COL_NOTE))));
            } while (cursor.moveToNext());
            Toast.makeText(getApplicationContext(), tableData, Toast.LENGTH_LONG).show();
        }
        //add data and view it
        myadapter = new MyCustomAdapter(listnewsData);
        ListView lsNews = (ListView) findViewById(R.id.lv_note);
        lsNews.setAdapter(myadapter);//intisal with data
    }

    private class MyCustomAdapter extends BaseAdapter {
        public ArrayList<NoteItem> listnewsDataAdpater;

        public MyCustomAdapter(ArrayList<NoteItem> listnewsDataAdpater) {
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
            View myView = mInflater.inflate(R.layout.note, null);
            final NoteItem s = listnewsDataAdpater.get(position);
            TextView id = (TextView) myView.findViewById(R.id.txt_note_id);
            id.setText(s.id);
            TextView name = (TextView) myView.findViewById(R.id.txt_note);
            name.setText(s.note);
            return myView;
        }

    }

    /*  public void loadPic(){
          String[] selectionsArgs = {sessionId4, sessionId5};
          byte[] b;
          Cursor cursor = dbManager.query(null, "Email like ? and Password like ?", selectionsArgs, null, "1");
          if (cursor.moveToFirst()) {
              String tableData = "";
              do {
                  b = cursor.getBlob(cursor.getColumnIndex(DBManager.COL_ProfilePic));
              } while (cursor.moveToNext());

              //byte[] b = sessionId6.getBytes(Charset.forName("UTF-8"));
              Bitmap testImage = BitmapFactory.decodeByteArray(b, 0, b.length);
              profileImageFromDb.setImageBitmap(testImage);
          }

      };*/
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera",
                "Remove image"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:
                                remove_pro_pic();
                        }
                    }
                });
        pictureDialog.show();
    }

    private void remove_pro_pic() {
        profile_image.setImageResource(R.drawable.user);
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(AfterLogin.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile_image.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AfterLogin.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(AfterLogin.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
}
