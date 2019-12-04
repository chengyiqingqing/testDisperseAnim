package com.sww.testdisperseanim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

public class DisperseView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private List<Ball> mBalls = new ArrayList<>();
    private ValueAnimator animator ;

    public DisperseView(Context context) {
        this(context, null);
    }

    public DisperseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
//        将每一个像素转成ball对象；
        for (int index = 0; index < mBitmap.getWidth(); index++) {
            for (int indexHeight = 0; indexHeight < mBitmap.getHeight(); indexHeight++) {
                Ball ball = new Ball();
                // TODO: 获取bitmap的颜色值 2019/12/3
                ball.color = mBitmap.getPixel(index, indexHeight);
                ball.initBall(index, indexHeight);
                //速度(-20,20)
                ball.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                ball.vY = rangInt(-15, 35);
                //加速度
                ball.aX = 0;
                ball.aY = 0.98f;
                mBalls.add(ball);
            }
        }
        animator = ValueAnimator.ofFloat(0,1);
        animator.setRepeatCount(-1);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBall();
                invalidate();
            }
        });
    }

    private int rangInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    private void updateBall() {
        //更新粒子的位置
        for (Ball ball : mBalls) {
            ball.x += ball.vX;
            ball.y += ball.vY;

            ball.vX += ball.aX;
            ball.vY += ball.aY;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        showOriginPicture(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (animator != null && !animator.isRunning()) {
                //执行动画
                animator.start();
            }

        }
        return super.onTouchEvent(event);
    }

    public void showOriginPicture(Canvas canvas) {
        canvas.translate(300, 300);
        for (Ball ball : mBalls) {
            mPaint.setColor(ball.color);
            canvas.drawCircle(ball.x, ball.y, ball.r, mPaint);
        }
    }

}
