package com.example.fotodrawing.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class DrawingActivity extends Activity {

    public static final String inputBitmapKey = "DrawingActivityInputBitmap";
    public static final String outputBitmapKey = "DrawingActivityOutputBitmap";

    private DrawingView drawingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        drawingView = (DrawingView)findViewById(R.id.view);
        if(getIntent().hasExtra(inputBitmapKey))
            drawingView.setImage((Bitmap)getIntent().getExtras().get(inputBitmapKey));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void endButtonClicked(View v){
        Bitmap b = drawingView.getBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

//you can create a new file name "test.jpg" in sdcard folder.
        try {
            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "test.jpg");
            f.createNewFile();
//write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

// remember close de FileOutput
            fo.close();
        }catch (IOException a){
            a.printStackTrace();

        }
        Intent a = new Intent();
        a.putExtra(outputBitmapKey,Environment.getExternalStorageDirectory()
                + File.separator + "test.jpg");
        setResult(RESULT_OK,a);
        finish();

    }
}
