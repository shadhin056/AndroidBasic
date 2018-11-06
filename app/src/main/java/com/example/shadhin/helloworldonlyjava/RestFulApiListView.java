package com.example.shadhin.helloworldonlyjava;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestFulApiListView extends Activity {

    ArrayList<AdapterItemsRestFulApi> listnewsDataRest;
    MyCustomAdapterRest myadapterRest;
    // json object response url
    private String urlJsonObj = "https://api.androidhive.info/contacts/";
    private static String TAG = RestFulApiListView.class.getSimpleName();

    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_ful_api_list_view);

        listnewsDataRest = new ArrayList<AdapterItemsRestFulApi>();
        //listnewsDataRest.clear();
        //code here
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        makeJsonObjectRequestRest();


    }

    private void makeJsonObjectRequestRest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Toast.makeText(RestFulApiListView.this, "ok", Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("contacts");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String address = jsonObject.getString("address");
                        String gender = jsonObject.getString("gender");
                        JSONObject phone = jsonObject.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        //Toast.makeText(RestFulApiListView.this, gender, Toast.LENGTH_LONG).show();

                        listnewsDataRest.add(new AdapterItemsRestFulApi(id, name, email, address, gender, mobile, home, office));

                    }
                    myadapterRest = new MyCustomAdapterRest(listnewsDataRest);
                    ListView lsNewsR = findViewById(R.id.lv_rest);
                    lsNewsR.setAdapter(myadapterRest);//intisal with data

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //display news list
    private class MyCustomAdapterRest extends BaseAdapter {
        public ArrayList<AdapterItemsRestFulApi> listnewsDataAdpater;

        public MyCustomAdapterRest(ArrayList<AdapterItemsRestFulApi> listnewsDataAdpater) {
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
            View myView = mInflater.inflate(R.layout.restfulapi_item_list_view, null);

            final AdapterItemsRestFulApi s = listnewsDataAdpater.get(position);
            TextView id = (TextView) myView.findViewById(R.id.r_id);
            id.setText(s.id);

            TextView name = (TextView) myView.findViewById(R.id.r_name);
            name.setText(s.name);

            TextView email = (TextView) myView.findViewById(R.id.r_email);
            email.setText(s.email);

            TextView address = (TextView) myView.findViewById(R.id.r_address);
            address.setText(s.address);

            TextView gender = (TextView) myView.findViewById(R.id.r_gender);
            gender.setText(s.gender);

            TextView mobile = (TextView) myView.findViewById(R.id.r_mobile);
            mobile.setText(s.mobile);

            TextView home = (TextView) myView.findViewById(R.id.r_home);
            home.setText(s.home);

            TextView office = (TextView) myView.findViewById(R.id.r_office);
            office.setText(s.office);

            return myView;
        }

    }
}
