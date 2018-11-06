package com.example.shadhin.helloworldonlyjava.util;

import android.app.Activity;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by enamul on 30/8/18.
 */

public class CustomStyle {



    public static String fontPath = "fonts/SolaimanLipi_20-04-07.ttf";


    public static SweetAlertDialog showProgressDialog (Activity activity){

        SweetAlertDialog  pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait...");
        pDialog.setCanceledOnTouchOutside(false);
        return pDialog;
    }



    public static void showErrorMessage(Activity activity, String lan, String message){
        if ("BAN".equals(lan)) {
            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ত্রুটি আছে..").setContentText(message).show();
        } else {

            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error..").setContentText(message).show();
        }
    }

    public static void showSuccessMessage(Activity activity, String lan, String message){
        if ("BAN".equals(lan)) {
            new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("অভিনন্দন.").setContentText(message).show();
        } else {

            new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Successful.").setContentText(message).show();
        }
    }


    public static void showInternetConnectionMessage(Activity activity, String lan){
        if ("BAN".equals(lan)) {
            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ইন্টারনেট কানেকশান করার সময় ত্রুটি ঘটেছে ").setContentText("কোনো ইন্টারনেট সংযোগ নেই!").show();
        } else {

            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(" Internet connection Error..").setContentText("No Internet Connection !").show();
        }

    }


}
