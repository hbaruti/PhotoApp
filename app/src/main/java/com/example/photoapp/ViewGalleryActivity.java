package com.example.photoapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class ViewGalleryActivity extends AppCompatActivity {

private static final int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gallery);

        Button addPictureButton = (Button)findViewById(R.id.addPictureButton);
        addPictureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });


        //TODO set imgView to be visible only after choosing a picture
        if(RESULT_LOAD_IMAGE != 1){
            findViewById(R.id.imgView).setVisibility(View.GONE);
        }else{
            findViewById(R.id.imgView).setVisibility(View.VISIBLE);
        }

        System.out.println("1111111111111111111111111111111111111111111111111111111111111111111111111111");


        //Requesting permissions at runtime (Beginning in Android6.0 (API level 23)
        //Users grant permissions while the app is running instead of when they install the app
        int permissionCheck = ContextCompat.checkSelfPermission(ViewGalleryActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PERMISSION_GRANTED){
            System.out.println("22222222222222222222222222222222222222222222222222222222222222222222222222222");
            //Should the app show and explanation to why it is asking for permissions
            if(ActivityCompat.shouldShowRequestPermissionRationale(ViewGalleryActivity.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                //Show explanation to user. After, ask user for the permissions again.
            }else{
                //No explanation needed, app can request permission.
                ActivityCompat.requestPermissions(ViewGalleryActivity.this, new String[]
                        {android.Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
            }
        }

    }

    //Handling the permissions request response
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int [] grantResults){
        System.out.println("33333333333333333333333333333333333333333333333333333333333333333333333333333333");
        switch(requestCode){
            case RESULT_LOAD_IMAGE: {
                //If request is cancelled, the result arrays are empty
                if(grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED){
                    //Permission was granted, do task
                }else{
                    //Permission was denied, disable functionality
                    Intent intent = new Intent(this, WelcomeActivity.class);
                    startActivity(intent);
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("4444444444444444444444444444444444444444444444444444444444444444444444444444444444");

        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && null != data) {
           Uri selectedImage = data.getData();
           String[] filePathColumn = {MediaStore.Images.Media.DATA};
           Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
           cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           String picturePath = cursor.getString(columnIndex);
           cursor.close();

            ImageView imageView = (ImageView)findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

       }
    }

}