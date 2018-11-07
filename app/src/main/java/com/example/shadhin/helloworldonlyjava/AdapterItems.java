package com.example.shadhin.helloworldonlyjava;

public class AdapterItems {
    public String id;
    public String nickName;
    public String email;
    public String phone;
    public String birthday;
    public String gender;
    public String country;
    public String agree;

    //for news details
    AdapterItems(String id, String nickName, String email, String phone, String birthday, String gender, String country, String agree) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.gender = gender;
        this.country = country;
        this.agree = agree;
    }
}