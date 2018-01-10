package com.example.photoapp;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;

public class WelcomeActivity extends AppCompatActivity {
private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    //Called when user clicks "Add picture" -button
    public void addPicture (View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    //Sign out user
    private void signOut() {
        AuthUI.getInstance().signOut(this);
    }

    public void onClick (View view){
        switch (view.getId()){
            case R.id.sign_out:
                signOut();
                break;
        }
    }


}