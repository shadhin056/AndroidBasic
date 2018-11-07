package com.example.shadhin.helloworldonlyjava;

public class NoteItem {
    public String id;
    public String noteTitel;
    public String note;

    //for news details
    NoteItem(String id, String note, String noteTitel) {
        this.id = id;
        this.note = note;
        this.noteTitel = noteTitel;
    }
}