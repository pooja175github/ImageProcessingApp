package com.example.mitaksh.imageprocessingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class EditImage extends AppCompatActivity implements View.OnTouchListener {

    ImageView iv;
    Context context;
    String file_path;
    Button btn_caption, btn_draw;
    RelativeLayout rl;
    private int _xDelta;
    private int _yDelta;
    private int lastaction;
    float dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bitmap bitmap = (Bitmap) this.getIntent().getParcelableExtra("image");
        setContentView(R.layout.activity_edit_image);
        rl = (RelativeLayout) findViewById(R.id.iv_image);
        iv = (ImageView) findViewById(R.id.iv_edit);
        iv.setImageBitmap(bitmap);
        btn_draw = (Button) findViewById(R.id.btn_draw);
        btn_caption = (Button) findViewById(R.id.btn_caption);


        btn_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditImage.this, Draw.class);
                startActivity(i);
            }
        });


        // On click Event of the Add Caption Button
        btn_caption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                final View et = new EditText(EditImage.this);
//                et.setHint("Add a caption..");
                et.setFocusable(true);
                et.setClickable(true);
                rl.addView(et);
                et.setOnTouchListener(EditImage.this);
                btn_caption.setText("Save");                  // Text of btn_caption changes to Save once Add Caption Button is clicked
                btn_draw.setText("Cancel");                   // Text of btn_draw changes to Cancel once Add Caption Button is clicked


                //Action on clicking Save Button
                btn_caption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        try {
                            savingImage(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


//                        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
//                        Bitmap bitmap = drawable.getBitmap();
//
//                        File sdCardDirectory = Environment.getExternalStorageDirectory();
//
//                        File image = new File(sdCardDirectory, "test.png");
//
//                        boolean success = false;
//
//                        // Encode the file as a PNG image.
//                        FileOutputStream outStream;
//                        try {
//
//                            outStream = new FileOutputStream(image);
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//                                                                        /* 100 to keep full quality of the image */
//
//                            outStream.flush();
//                            outStream.close();
//                            success = true;
//                        }
//                        catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        if (success) {
//                            Toast.makeText(getApplicationContext(), "Image saved with success",
//                                    Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "Error during image saving", Toast.LENGTH_LONG).show();
//                        }


                    }
                });

                btn_draw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cancelIntent();
                    }
                });



            }
        });
    }

    private void savingImage(Bitmap finalBitmap) throws IOException {


        //File file = new File(context.getFilesDir(), filename);
        File mFolder = new File(Environment.getExternalStorageDirectory()+ File.separator + "/myApp");
        Log.e("Editimage",mFolder.toString());
       // File imgFile = new File(mFolder.getAbsolutePath() +"/"+ finalBitmap);
        //Log.e("Image",imgFile.toString());

//        Log.e("bitmap",finalBitmap.toString());
//        Random generator = new Random();
//        int n = 10000;
//        n = generator.nextInt(n);
//        String fname = "Image-"+ n +".jpg";
//        File file = new File (imgFile, fname);
//        boolean success = false;

       // Log.e("Fname",fname);
        if (!mFolder.exists()) {
            mFolder.mkdir();
            File wallpaperDirectory = new File("/sdcard/myApp/");
            mFolder.mkdirs();
             file_path=mFolder.getPath().toString();
            Log.e("EditImage","Folder created"+file_path);
        }
        //File file = new File(new File("/sdcard/DirName/"), fileName);
        else {
            Log.e("EditImage","folder already there"+file_path);
        }
        File file = new File(new File("/sdcard/DirName/"), my);
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        Log.e("EditImage","outside");
//        FileOutputStream fos = null;
//        FileOutputStream out = new FileOutputStream(file);
//        finalBitmap.compress(Bitmap.CompressFormat.PNG,70, fos);
//        fos.flush();
//        Log.e("EditImage","foldder created ");
//        try {
////            fos = new FileOutputStream(imgFile);
////            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.PNG,70, fos);
//            fos.flush();
//            Log.e("EditImage","foldder created ");
////            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("catch","folder catch");
//        }


        //String root = Environment.getExternalStorageDirectory().toString();

        //Begin
//        File myDir = new File(Environment.getExternalStorageDirectory() + File.separator + "/sdcard/IMages");
//        myDir.mkdirs();
//        Random generator = new Random();
//        int n = 10000;
//        n = generator.nextInt(n);
//        String fname = "Image-"+ n +".jpg";
//        File file = new File (myDir, fname);
//        boolean success = false;


        //End
//        if (file.exists ()) file.delete ();
//        file.createNewFile();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//            success = true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (success) {
//                            Toast.makeText(getApplicationContext(), "Image saved with success",
//                                    Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(),
//                                    "Error during image saving", Toast.LENGTH_LONG).show();
//                        }

    }

    private void cancelIntent() {
        Intent i = new Intent(EditImage.this, MainActivity.class);
        startActivity(i);
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastaction=MotionEvent.ACTION_DOWN;
                Log.e("DragAndDrop","ACTION_DOWN");
                dx=view.getX()-motionEvent.getRawX();
                dy=view.getY()-motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                lastaction=MotionEvent.ACTION_MOVE;
                Log.e("DragAndDrop","ACTION_MOVE");
                view.setX(motionEvent.getRawX()+dx);
                view.setY(motionEvent.getRawY()+dy);
                break;
            case MotionEvent.ACTION_UP:
                if(lastaction == MotionEvent.ACTION_DOWN){

                    Log.e("DragAndDrop","clicked");
                }
                break;
        }
        return false;
    }
}
