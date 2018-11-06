package com.example.shadhin.helloworldonlyjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterItemsRestFulApi extends AppCompatActivity {
    public String id;
    public String name;
    public String email;
    public String address;
    public String gender;
    public String mobile;
    public String home;
    public String office;

    public AdapterItemsRestFulApi(String id, String name, String email, String address, String gender, String mobile, String home, String office) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.mobile = mobile;
        this.home = home;
        this.office = office;
    }


}

