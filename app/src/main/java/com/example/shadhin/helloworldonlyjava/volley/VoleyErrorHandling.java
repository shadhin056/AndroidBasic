package com.example.shadhin.helloworldonlyjava.volley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by enamul on 5/9/18.
 */

public class VoleyErrorHandling {

    public static int timout = 120000;
    public static int maxAttemt = 5;

    public VoleyErrorHandling() {

    }





    public VoleyErrorHandling(StringRequest stringRequest) {

      /*  stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                1*60*1000,
              //  DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                0,//Stop mutilple request
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        */


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                1 * 60 * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

       /* stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                // Here goes the new timeout
                return 1*60*1000;

            }

            @Override
            public int getCurrentRetryCount() {
                // The max number of attempts
                return 1;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                // Here you could check if the retry count has gotten
                // To the max number, and if so, send a VolleyError msg
                // or something
                //Log.d("err", error.toString());
               // throw(error);
            }
        });
        */
    }
}
