package com.example.mitaksh.imageprocessingapp;

/**
 * Created by Mitaksh on 7/4/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class DrawView extends View
{
    private Canvas m_Canvas;

    private Path m_Path;

    private Paint m_Paint,drawPaint;

    ArrayList<Pair<Path, Paint>> arrayListPaths = new ArrayList<Pair<Path, Paint>>();

    ArrayList<Pair<Path, Paint>> undonePaths = new ArrayList<Pair<Path, Paint>>();

    private float mX, mY;

    private Bitmap bitmapToCanvas;

    private static final float TOUCH_TOLERANCE = 4;
    private boolean erase=false;

    public DrawView(Context context)
    {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        onCanvasInitialization();
    }


    public void onCanvasInitialization()
    {
        m_Paint = new Paint();
        m_Paint.setAntiAlias(true);
        m_Paint.setDither(true);
        m_Paint.setColor(Color.parseColor("#37A1D1"));
        m_Paint.setStyle(Paint.Style.STROKE);
        m_Paint.setStrokeJoin(Paint.Join.ROUND);
        m_Paint.setStrokeCap(Paint.Cap.ROUND);
        m_Paint.setStrokeWidth(2);

        m_Path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmapToCanvas = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        m_Canvas = new Canvas(bitmapToCanvas);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(bitmapToCanvas, 0f, 0f, null);
        canvas.drawPath(m_Path, m_Paint);
    }

    public void onDrawCanvas()
    {
        for (Pair<Path, Paint> p : arrayListPaths)
        {
            m_Canvas.drawPath(p.first, p.second);
        }
    }

    private static final int INVALID_POINTER_ID = -1;

    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        super.onTouchEvent(ev);


        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
            {
                final float x = ev.getX();
                final float y = ev.getY();

                touch_start(x, y);

                mActivePointerId = ev.getPointerId(0);

                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
            {
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                // Find the index of the active pointer and fetch its position
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);

                touch_move(x, y);
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                mActivePointerId = INVALID_POINTER_ID;
                touch_up();
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
            {
                // Extract the index of the pointer that left the touch sensor
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId)
                {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mX = ev.getX(newPointerIndex);
                    mY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                touch_up();
            }
            break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
        }

        invalidate();
        return true;
    }
    public void onClickEraser()
    {

    }



    private void touch_start(float x, float y)
    {
        undonePaths.clear();
        m_Path.reset();
        m_Path.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y)
    {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
        {
            m_Path.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    private void touch_up()
    {
        m_Path.lineTo(mX, mY);

        // commit the path to our offscreen
        m_Canvas.drawPath(m_Path, m_Paint);

        // kill this so we don't double draw
        Paint newPaint = new Paint(m_Paint); // Clones the mPaint object
        arrayListPaths.add(new Pair<Path, Paint>(m_Path, newPaint));
        m_Path = new Path();
    }

    //Erasing code
    public void setErase(boolean isErase){
        erase=isErase;


        if(erase)
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
//set erase true or false
    }
}
