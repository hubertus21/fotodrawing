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
    public static final String outputFilenameKey = "DrawingActivityOutputFilename";

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
        String filename = Environment.getExternalStorageDirectory()
                + File.separator + "test.jpg";
        try {
            File f = new File(filename);
            f.createNewFile();

            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            fo.close();
        }catch (IOException a){
            a.printStackTrace();

        }
        Intent a = new Intent();
        a.putExtra(outputFilenameKey,filename);
        setResult(RESULT_OK,a);
        finish();

    }
}
