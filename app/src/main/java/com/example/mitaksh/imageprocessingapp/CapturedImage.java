package com.example.mitaksh.imageprocessingapp;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.net.URI;

public class CapturedImage extends AppCompatActivity  {

    Button btn_save, btn_cancel;
    RelativeLayout rl;
    final CharSequence[] options = {"Edit", "Save", "Cancel"};
    private String userChoosenTask;
    private int _xDelta;
    private int _yDelta;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bitmap bitmap = (Bitmap)this.getIntent().getParcelableExtra("image");
        setContentView(R.layout.activity_captured_image);
        rl = (RelativeLayout)findViewById(R.id.parentlayout);

        iv = (ImageView)findViewById(R.id.imageView);
        iv.setImageBitmap(bitmap);
        //iv.setLongClickable(true);
        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CapturedImage.this);
//                builder.setMessage("LongPress happened");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals("Edit")){
                            userChoosenTask = "Edit";
                            Intent ie = new Intent(CapturedImage.this, EditImage.class);
                            ie.putExtra("image",bitmap);
                            startActivity(ie);

                            //editIntent();

                        }

                        else if (options[which].equals("Save")){
                            userChoosenTask = "Save";
                            saveIntent();
                        }

                        else if (options[which].equals("Cancel")){
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }


//    public void editIntent(){
//
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        EditText et = new EditText(this);
//        et.setHint("Add a caption..");
//        et.setFocusable(true);
//        et.setClickable(true);
//        rl.addView(et);
//        et.setOnTouchListener(this);
//    }

    public void saveIntent(){

    }

//    @Override
//    public boolean onTouch(View view, MotionEvent event) {
//
//
//
//        final int X = (int) event.getRawX();
//        final int Y = (int) event.getRawY();
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                _xDelta = X - lParams.leftMargin;
//                _yDelta = Y - lParams.topMargin;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                layoutParams.leftMargin = X - _xDelta;
//                layoutParams.topMargin = Y - _yDelta;
//                layoutParams.rightMargin = 600;
//                layoutParams.bottomMargin = 550;
//                view.setLayoutParams(layoutParams);
//                break;
//        }
//        rl.invalidate();
//        return true;
//    }

}
