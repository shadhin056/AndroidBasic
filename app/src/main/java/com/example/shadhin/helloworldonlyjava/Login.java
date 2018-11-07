package com.example.shadhin.helloworldonlyjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.shadhin.helloworldonlyjava.ip.IPConfigure;
import com.example.shadhin.helloworldonlyjava.util.AsteriskPasswordTransformationMethod;
import com.example.shadhin.helloworldonlyjava.util.CheckNetwork;
import com.example.shadhin.helloworldonlyjava.util.CustomStyle;
import com.example.shadhin.helloworldonlyjava.util.GlobalVariable;
import com.example.shadhin.helloworldonlyjava.volley.VoleyErrorHandling;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.util.Log.e;

public class Login extends AppCompatActivity {

    Button btnLogin;
    TextView btnRegistration;

    private static String url_login = IPConfigure.getIP() + "signInRest.do";
    private SweetAlertDialog pDialog;

    EditText txtEmail;
    EditText txtPassword;
    private GlobalVariable globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

      //  globalVariable = ((GlobalVariable) getApplicationContext());

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //View title = getWindow().findViewById(android.R.id.title);
        //View titleBar = (View) title.getParent();
        //titleBar.setBackgroundColor(Color.RED);
       // setTitleColor(Color.BLUE);


        pDialog = CustomStyle.showProgressDialog(Login.this);


        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistration = (TextView) findViewById(R.id.btnRegistration);

        txtEmail = (EditText) findViewById(R.id.txtEmail1);
        txtPassword = (EditText) findViewById(R.id.txtPassword1);

        txtPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());

       //txtEmail.setText("enamul@erainfotechbd.com");
        //txtPassword.setText("Era123456@");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(Login.this, KYC.class);
               // startActivity(intent);

               if(txtEmail.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Login.this,"","Please Enter Your Email");
                }else if(txtPassword.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Login.this,"","Please Enter Your Password");
                }else if(CheckNetwork.isOnline(Login.this) == false){
                    CustomStyle.showInternetConnectionMessage(Login.this,"");
                }else{
                   //globalVariable.setEmail(txtEmail.getText().toString());
                   loginAction();


                   /*
                   Intent intent = new Intent(Login.this, Welcome_Nav.class);
                   intent.putExtra("email",txtEmail.getText().toString());
                    startActivity(intent);
                    */


                   
                }


            }
        });

       btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

    }


    private void loginAction() {
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        Log.e("response======>",response);


                        try {
                         //   JSONObject jsonObject = new JSONObject(response);
                            //JSONArray loginNodes = jsonObject.getJSONArray("payoneerInfoNodes");

                            JSONObject jObj = new JSONObject(response);
                            String Response_Code = jObj.getString("Response_Code");

                            String Response_Status = jObj.getString("Response_Status");

                              if("0".equals(Response_Code)){
                                  /*String User_Name = jObj.getString("User_Name");
                                  String Session = jObj.getString("Session");
                                  String CusCode = jObj.getString("CusCode");
                                  String Wallet_ID = jObj.getString("Wallet_ID");
                                  String current_Bal = jObj.getString("current_Bal");
                                  String MobileNo = jObj.getString("MobileNo");*/


                                 /* globalVariable.setUsername(User_Name);
                                  globalVariable.setSessionid(Session);
                                  globalVariable.setCuscode(CusCode);
                                  globalVariable.setWaletid(Wallet_ID);
                                  globalVariable.setCurrentbalance(current_Bal);
                                  globalVariable.setMobileno(MobileNo);
                                  globalVariable.setEmail(txtEmail.getText().toString());*/
                                  Intent intent = new Intent(Login.this, ApiLoginSUccess.class);
                                  startActivity(intent);

                              }else if ("2".equals(Response_Code)) {
                                  Intent intent = new Intent(Login.this, ApiLoginSUccess.class);
                                  //globalVariable.setEmail(txtEmail.getText().toString());
                                  //intent.putExtra("email",txtEmail.getText().toString());
                                  startActivity(intent);
                              }else{
                                  Log.e("response======>er",response);
                                  CustomStyle.showErrorMessage(Login.this,"",Response_Status);
                              }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        e("Err", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_pass", "usersignin");
                params.put("emailid", txtEmail.getText().toString());
                params.put("password", txtPassword.getText().toString());
                return params;
            }


        };

        //Begin set Time Out
        new VoleyErrorHandling(stringRequest);
        //End set Time out
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
