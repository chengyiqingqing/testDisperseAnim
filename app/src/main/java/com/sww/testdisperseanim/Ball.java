package com.sww.testdisperseanim;

public class Ball {

    public static final float d = 3.0f;

    public int color; // 获取像素点的颜色值
    public float x;// 圆心坐标
    public float y;// 圆心坐标
    public float r;// 圆半径

    public float vX;// x水平方向速度
    public float vY;// y竖直方向速度
    public float aX;// 加速度
    public float aY;

    public void initBall(int indexRow, int indexColumn) {
        r = d / 2;
        x = indexRow * d + r;
        y = indexColumn * d + r;
    }

}
