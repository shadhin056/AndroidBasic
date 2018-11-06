package com.example.shadhin.helloworldonlyjava;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.badoualy.stepperindicator.StepperIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.example.shadhin.helloworldonlyjava.ip.IPConfigure;
import com.example.shadhin.helloworldonlyjava.util.AsteriskPasswordTransformationMethod;
import com.example.shadhin.helloworldonlyjava.util.CheckNetwork;
import com.example.shadhin.helloworldonlyjava.util.CustomStyle;
import com.example.shadhin.helloworldonlyjava.util.MyDateFormat;
import com.example.shadhin.helloworldonlyjava.volley.VoleyErrorHandling;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.util.Log.e;


/**
 * Created by enamul on 3/9/18.
 */

public class Registration extends AppCompatActivity {


    LinearLayout layout_step_1;
    LinearLayout layout_step_2;

    Button btnNextStep_1;
    Button btnPreviousStep_1;
    Button btnRegistration;

    ViewPager pager;
    StepperIndicator indicator;

    EditText txtFullName;
    EditText txtEmail;
    EditText txtMobile;
    EditText txtPassword;
    EditText txtConfirmPassword;

    PagerAdapter mPagerAdapter;

    private static String url_login = IPConfigure.getIP() + "signupRestReg.do";
    private SweetAlertDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        pDialog = CustomStyle.showProgressDialog(Registration.this);

        pager = findViewById(R.id.pager);
        indicator = findViewById(R.id.stepper_indicator);

        layout_step_1 = (LinearLayout) findViewById(R.id.layout_step_1);
        layout_step_2 = (LinearLayout) findViewById(R.id.layout_step_2);

        btnNextStep_1 = (Button) findViewById(R.id.btnNextStep_1);
        btnPreviousStep_1 = (Button) findViewById(R.id.btnPreviousStep_1);
        btnRegistration = (Button) findViewById(R.id.btnRegistration);

        txtFullName = (EditText) findViewById(R.id.txtFullName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtMobile = (EditText) findViewById(R.id.txtMobile);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);


        txtPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        txtConfirmPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());


        layout_step_2.setVisibility(View.GONE);


        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtFullName.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Registration.this,"","Please Enter Your User Name");
                }else if(txtEmail.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Registration.this,"","Please Enter Your Email");
                }else if(txtMobile.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Registration.this,"","Please Enter Your Mobile");
                }else if(txtPassword.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Registration.this,"","Please Enter Your Password");
                }else if(txtConfirmPassword.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Registration.this,"","Please Enter Your Confirm Password");
                }else if(!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())){
                    CustomStyle.showErrorMessage(Registration.this,"","Password Not Match With Confirm Password.");
                }else if(CheckNetwork.isOnline(Registration.this) == false){
                    CustomStyle.showInternetConnectionMessage(Registration.this,"");
                }else{
                    doRegistration();
                }
            }
        });






        btnNextStep_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtFullName.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Registration.this,"","Please Enter Your User Name");
                }else if(txtEmail.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Registration.this,"","Please Enter Your Email");
                }else if(txtMobile.getText().toString().isEmpty()){
                    CustomStyle.showErrorMessage(Registration.this,"","Please Enter Your Mobile");
                }else {

                    layout_step_1.setVisibility(View.GONE);
                    layout_step_2.setVisibility(View.VISIBLE);
                    indicator.setCurrentStep(1);

                    if (!txtPassword.getText().toString().isEmpty() && !txtConfirmPassword.getText().toString().isEmpty()) {
                        if (txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                            indicator.setStepCount(2);
                            indicator.setCurrentStep(2);

                        }
                    }

                    Animation RightSwipe = AnimationUtils.loadAnimation(Registration.this, R.anim.left_view_animition);
                    layout_step_2.startAnimation(RightSwipe);
                }


            }
        });

        btnPreviousStep_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                indicator.setStepCount(2);
                indicator.setCurrentStep(1);


                layout_step_1.setVisibility(View.VISIBLE);
                layout_step_2.setVisibility(View.GONE);

                Animation RightSwipe = AnimationUtils.loadAnimation(Registration.this, R.anim.right_view_animition);
                layout_step_1.startAnimation(RightSwipe);


            }
        });


        txtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mobile = txtMobile.getText().toString();

                if(!txtFullName.getText().toString().isEmpty() && !txtEmail.getText().toString().isEmpty()){
                    if(mobile.length()<11){
                        txtMobile.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    }else if(mobile.length()>=11){
                        indicator.setCurrentStep(1);
                        txtMobile.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_ATOP);
                    }
                }else{
                    if(mobile.length()>=11){
                        txtMobile.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                    }else{
                        txtMobile.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_ATOP);
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // Log.e("Comfirm password",);
                String password=txtPassword.getText().toString();
                String confirmpassword=txtConfirmPassword.getText().toString();

                if(!password.isEmpty() && !confirmpassword.isEmpty()) {
                    if (password.equals(confirmpassword)) {
                        txtConfirmPassword.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_ATOP);
                        txtPassword.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_ATOP);
                        indicator.setStepCount(2);
                        indicator.setCurrentStep(2);
                    } else if (!password.equals(confirmpassword)) {
                        //indicator.setStepCount(2);
                        //indicator.setCurrentStep(1);
                        txtPassword.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        txtConfirmPassword.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                        //indicator.setStepCount(2);
                       // indicator.setCurrentStep(2);

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password=txtPassword.getText().toString();
                String confirmpassword=txtConfirmPassword.getText().toString();
                if (!password.equals(confirmpassword)) {
                    indicator.setStepCount(2);
                    indicator.setCurrentStep(1);
                }

            }
        });





        assert pager != null;

        mPagerAdapter=  new PagerAdapter(this.getSupportFragmentManager());

        pager.setAdapter(mPagerAdapter);


        // We keep last page for a "finishing" page
        indicator.setViewPager(pager, true);

        // indicator.setStepCount(2);


        /*indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {

                Log.e("step Position==>",step+"");
                pager.setCurrentItem(step, true);




            }
        });
        */


    }


    private void doRegistration() {
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        Log.e("response======>",response);

                        try {
                            JSONObject jObj = new JSONObject(response);
                            String ErrorCode = jObj.getString("Response_Code");

                            String ErrorMessage = jObj.getString("Response_Status");

                            Log.e("ResponseCode======>",ErrorMessage);
                            Log.e("ResponseMessage======>",ErrorCode);

                            if("0".equals(ErrorCode)){
                                txtFullName.setText("");
                                txtEmail.setText("");
                                txtMobile.setText("");
                                txtPassword.setText("");
                                txtConfirmPassword.setText("");

                                CustomStyle.showSuccessMessage(Registration.this,"",ErrorMessage);
                            }else{
                                CustomStyle.showErrorMessage(Registration.this,"",ErrorMessage);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                       /* try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray loginNodes = jsonObject.getJSONArray("payoneerInfoNodes");


                            for (int i = 0; i < loginNodes.length(); i++) {
                                JSONObject jo = loginNodes.getJSONObject(i);




                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        */

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
                params.put("api_pass", "adduser");
                params.put("emailid", txtEmail.getText().toString());
                params.put("password", txtConfirmPassword.getText().toString());
                params.put("mobileno",txtMobile.getText().toString());
                params.put("signupid", "na");
                params.put("signupDate", MyDateFormat.getApiCurrentDate());//dd-mm-yyyyyy
                params.put("oprstamp",txtEmail.getText().toString());
                params.put("timstamp", MyDateFormat.getApiCurrentDate());
                params.put("terminalid", "");

                return params;
            }


        };

        //Begin set Time Out
        new VoleyErrorHandling(stringRequest);
        //End set Time out
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position + 1, position == getCount() - 1);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.e("position", "" + position);

            return "Page " + position;
        }
    }


    public static class PageFragment extends Fragment {

        private TextView lblPage;

        public static PageFragment newInstance(int page, boolean isLast) {
            Bundle args = new Bundle();
            args.putInt("page", page);
            if (isLast)
                args.putBoolean("isLast", true);

            final PageFragment fragment = new PageFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_page, container, false);
            lblPage = (TextView) view.findViewById(R.id.lbl_page);
            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            final int page = getArguments().getInt("page", 0);

            Log.e(" page==>", page + "");

            if (getArguments().containsKey("isLast"))
                lblPage.setText("You're done!");
            else
                lblPage.setText(Integer.toString(page));
        }
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
