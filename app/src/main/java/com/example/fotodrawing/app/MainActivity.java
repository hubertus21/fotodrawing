package com.example.fotodrawing.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {
    static int imageCaptureRequest = 1;
    static int imageDrawRequest = 2;
    private Bitmap imageBitmap;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void photoButtonClicked(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,imageCaptureRequest);

    }

    public void drawButtonClicked(View v){
        if (imageBitmap == null) {
            Toast.makeText(getApplicationContext(),"No Image",Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(getApplicationContext(),DrawingActivity.class);
        i.putExtra(DrawingActivity.inputBitmapKey,imageBitmap);
        startActivityForResult(i, imageDrawRequest);

    }

    public void sendButtonClicked(View v){

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        //String to[] = {""};
        //emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filename));
// the mail subject
        //emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
        startActivity(Intent.createChooser(emailIntent , "Send email..."));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == imageCaptureRequest){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");

            }else{
                Toast.makeText(this, "Nie udało się", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == imageDrawRequest){
            if(resultCode == RESULT_OK){

                filename = data.getStringExtra(DrawingActivity.outputBitmapKey);

            }else{
                Toast.makeText(this, "Nie udało się", Toast.LENGTH_LONG).show();
            }
        }

    }
}
