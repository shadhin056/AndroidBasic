package com.example.shadhin.helloworldonlyjava;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatePickerDialog picker;
    EditText nickName;
    EditText phoneNumber;
    EditText birthDay;
    EditText email;
    EditText password;
    EditText rePassword;
    TextView birthdayTextView;
    Spinner SpPresentCountry;
    Button submitButton;
    Button resetButton;
    Button randomButton;
    Button quickDataLoad;
    Button quickLogin;
    Button restApiBtn;
    String selectedGenderType = "M";
    Button restapiListViewBtn;
    Button restApiMyBtn;
    Button pick_image;
    Button btn_restapi_reg;
    Button btn_restapi_login;
    ImageView profile_image;
    RadioGroup txtGender;
    String selected="NO";
    ImageButton calenderButton;
    AwesomeValidation awesomeValidation;
    DBManager dbManager;
    private static final String IMAGE_DIRECTORY = "/image_store";
    private int GALLERY = 1, CAMERA = 2;


    //for permission access data
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static int SPLASH_TIME_OUT = 2000;
    private String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);
        //for permission to access data
        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    // Intent i = new Intent(MainActivity.this, AfterLogin.class);
                    //startActivity(i);

                    // close this activity
                    //finish();
                }
            }, SPLASH_TIME_OUT);
        }


        //find id
        SpPresentCountry = (Spinner) findViewById(R.id.SpPresentCountry);
        restApiMyBtn = findViewById(R.id.restapi_myjson);
        nickName = findViewById(R.id.nick_name);
        phoneNumber = findViewById(R.id.phone_numer);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.reenter_password);
        birthdayTextView = findViewById(R.id.birthday_date_view);
        birthDay = findViewById(R.id.up_birthday);
        submitButton = findViewById(R.id.submit);
        resetButton = findViewById(R.id.reset);
        randomButton = findViewById(R.id.random_input);
        calenderButton = findViewById(R.id.calender_btn);
        quickDataLoad = findViewById(R.id.quick_load_data);
        quickLogin = findViewById(R.id.login_btn);
        restApiBtn = findViewById(R.id.restapi_btn);
        restapiListViewBtn = findViewById(R.id.restapi_list_view_btn);
        pick_image = findViewById(R.id.pick_image_btn);
        profile_image = findViewById(R.id.profile_image);
        btn_restapi_reg = findViewById(R.id.btn_restapi_reg);
        btn_restapi_login = findViewById(R.id.btn_restapi_login);
        txtGender = findViewById(R.id.txtGender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.country, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        SpPresentCountry.setAdapter(adapter);


        txtGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        btn_restapi_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });btn_restapi_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        
        //validation using awesome AwesomeValidation
        //https://github.com/thyrlian/AwesomeValidation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(MainActivity.this, R.id.nick_name, "[a-zA-Z\\s]+", R.string.error_nickName);
        // awesomeValidation.addValidation(MainActivity.this,R.id.phone_numer, RegexTemplate.TELEPHONE,R.string.error_phoneNumber);
        awesomeValidation.addValidation(MainActivity.this, R.id.phone_numer, "[0-9\\s]+", R.string.error_phoneNumber);
        awesomeValidation.addValidation(MainActivity.this, R.id.email, android.util.Patterns.EMAIL_ADDRESS, R.string.error_email);
        // String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        String regexPassword = "[a-zA-Z0-9\\s]+";
        awesomeValidation.addValidation(MainActivity.this, R.id.password, regexPassword, R.string.error_password);
        awesomeValidation.addValidation(MainActivity.this, R.id.reenter_password, R.id.password, R.string.error_rePassword);
        // to validate with a simple custom validator function
        awesomeValidation.addValidation(MainActivity.this, R.id.up_birthday, new SimpleCustomValidation() {
            @Override
            public boolean compare(String input) {
                // check if the age is >= 18
                try {
                    Calendar calendarBirthday = Calendar.getInstance();
                    Calendar calendarToday = Calendar.getInstance();
                    calendarBirthday.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(input));
                    int yearOfToday = calendarToday.get(Calendar.YEAR);
                    int yearOfBirthday = calendarBirthday.get(Calendar.YEAR);
                    if (yearOfToday - yearOfBirthday > 18) {
                        return true;
                    } else if (yearOfToday - yearOfBirthday == 18) {
                        int monthOfToday = calendarToday.get(Calendar.MONTH);
                        int monthOfBirthday = calendarBirthday.get(Calendar.MONTH);
                        if (monthOfToday > monthOfBirthday) {
                            return true;
                        } else if (monthOfToday == monthOfBirthday) {
                            if (calendarToday.get(Calendar.DAY_OF_MONTH) >= calendarBirthday.get(Calendar.DAY_OF_MONTH)) {
                                return true;
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }, R.string.error_birthDay);

        awesomeValidation.addValidation(MainActivity.this, R.id.email, new SimpleCustomValidation() {
            @Override
            public boolean compare(String input) {

                // check if the age is >= 18

                //String getEmail = email.getText().toString();
               /*     Cursor cursor = dbManager.query(null, null, null, null, null);
                    String email[]={};
                    int i=0;
                    int ee=0;
                    String string="shadhinemail@gmail.com";
                if (cursor.moveToFirst()) {
                    do {
                        email[i]= cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL));
                        i=i++;
                        Toast.makeText(MainActivity.this, email[i], Toast.LENGTH_LONG).show();
                    } while (cursor.moveToNext());
                }
                for (int ii=0;i<email.length;i++){
                    if(string==input){
                        ee++;
                    }
                }*/
                String string = "shadhinemail@gmail.com";
                String[] selectionsArgs = {input};
                Cursor cursor = dbManager.query(null, "Email like ? ", selectionsArgs, null, "1");
                String myStringArray = "";


                // String[] myStringArrays = new String[10];
                int i = 0;
                try {
                    if (cursor.moveToFirst()) {
                        do {
                            // myStringArray= cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL));
                            myStringArray = cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL));

                            Toast.makeText(MainActivity.this, cursor.getString(cursor.getColumnIndex(DBManager.COL_EMAIL)), Toast.LENGTH_LONG).show();
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (myStringArray.equals(input)) {
                    return false;
                } else {
                    return true;
                }


            }
        }, R.string.error_email_alreadyExist);
        restApiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RestfullApi.class);
                startActivity(intent);
            }
        });
        restapiListViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RestFulApiListView.class);
                startActivity(intent);
            }
        });
        // date pick from calender (when click on editText)
        //birthDay.setInputType(InputType.TYPE_NULL);
        restApiMyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyJsonData.class);
                startActivity(intent);
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickName.setText("");
                email.setText("");
                phoneNumber.setText("");
                birthDay.setText("");
                password.setText("");
                rePassword.setText("");
            }
        });
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickName.setText("shadhin");
                email.setText("shadhinemail@gmail.com");
                phoneNumber.setText("01672708329");
                birthDay.setText("31/12/1993");
                password.setText("123456");
                rePassword.setText("123456");
            }
        });
        quickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginWithOutApi.class);
                startActivity(intent);
            }
        });

        calenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                birthDay.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        //after submit the button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    load_data();
                    // Toast.makeText(MainActivity.this, "Date Received Successfully", Toast.LENGTH_SHORT).show();
                    //birthdayTextView.setText("Selected Date: " + birthDay.getText());
                    // setContentView(R.layout.form_data_view);
                    Intent intent = new Intent(MainActivity.this, DataViewActivities.class);

                    intent.putExtra("nick_name1", nickName.getText().toString());
                    intent.putExtra("phone_number1", phoneNumber.getText().toString());
                    intent.putExtra("birthday1", birthDay.getText().toString());
                    intent.putExtra("email1", email.getText().toString());
                    intent.putExtra("password1", password.getText().toString());
                    intent.putExtra("repassword1", rePassword.getText().toString());
                    intent.putExtra("gender", selectedGenderType);
                    intent.putExtra("country", SpPresentCountry.getSelectedItem().toString());
                    intent.putExtra("agree", selected);
                    startActivity(intent);
                } else {
                    // Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
        quickDataLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataViewActivities.class);
                startActivity(intent);
            }
        });
    }
    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox)v;
        if(checkBox.isChecked()){
            selected="YES";
        }
    }
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
                    Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile_image.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
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


    //for access permission

    private boolean checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        // Intent i = new Intent(MainActivity.this, AfterLogin.class);
                        //startActivity(i);
                        // finish();
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                            showDialogOK("Service Permissions are required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg) {
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //  permissionsclass.requestPermission(type,code);
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.example.shadhin.helloworldonlyjava")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }
    public void load_data() {
        ContentValues values = new ContentValues();

        // for image
        profile_image.setDrawingCacheEnabled(true);
        profile_image.buildDrawingCache();
        Bitmap bitmap = profile_image.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //dbManager.addToDbImage(data);
       /* String[] SelectionArgs = {email.getText().toString()};
        int id = dbManager.update(values, "Email=?", SelectionArgs);
        if (id > 0) {
            Toast.makeText(getApplicationContext(), "Data is Updated", Toast.LENGTH_LONG).show();
            //loadPic();
        } else {
            Toast.makeText(getApplicationContext(), "Data is not Updated", Toast.LENGTH_LONG).show();
        }*/
        //end image to db


        values.put(DBManager.COL_ProfilePic, data);
        values.put(DBManager.COL_USERNAME, nickName.getText().toString());
        values.put(DBManager.COL_PHONE, phoneNumber.getText().toString());
        values.put(DBManager.COL_BIRTHDAY, birthDay.getText().toString());
        values.put(DBManager.COL_EMAIL, email.getText().toString());
        values.put(DBManager.COL_PASSWORD,  password.getText().toString());
        values.put(DBManager.COL_GENDER,  selectedGenderType);
        values.put(DBManager.COL_COUNTRY,  SpPresentCountry.getSelectedItem().toString());
        long id = dbManager.insert(values);


        if (id > 0) {
            Toast.makeText(getApplicationContext(), "Data is added and user id : " + id, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Can not inserted : ", Toast.LENGTH_LONG).show();
        }
    }
}
