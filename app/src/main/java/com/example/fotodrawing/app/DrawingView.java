package com.example.fotodrawing.app;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Hubert on 2015-01-07.
 */
public class DrawingView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
    private Bitmap bitmapImage;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);


    }

    public void setImage(Bitmap bmp){
        bitmapImage = bmp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.draw(new Canvas(bitmapImage));
        canvas.drawPath(path,paint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX,eventY);

                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX,eventY);
                break;
            default:
                return super.onTouchEvent(event);
        }

        invalidate();
        return true;

    }
}
