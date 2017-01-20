package com.chenzy.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyu on 2017/1/17.
 */

public class Roll3DView extends View {
    private static final String TAG = "TDAct";
    private int viewWidth, viewHeight;
    private Context context;
    private Paint paint;
    private Camera camera;
    private Matrix matrix;

    private float rotateDegree = 0;

    //X方向旋转轴   Y方向旋转轴
    private float axisX = 0, axisY = 0;

    private int partNumber = 1;
    private List<Bitmap> bitmapList;
    private Bitmap[][] bitmaps;
    //滚动方向:1竖直方向 其他为水平方向
    private int direction = 1;
    int averageWidth = 0, averageHeight = 0;

    //滚动模式
    private RollMode rollMode = RollMode.SepartConbine;
    private int preIndex = 0, currIndex = 0, nextIndex = 0;
    private ValueAnimator valueAnimator;
    private int rollDuration = 1 * 1000;
    //正在翻转
    private boolean rolling;

    //滚动模式
    public enum RollMode {
        //3D整体滚动  尾部逐渐分离再合并   各模块依次滚动  百叶窗
        Roll2D, Whole3D, SepartConbine, RollInTurn, Jalousie;
    }


    public Roll3DView(Context context) {
        super(context);
        init(context);

    }

    public Roll3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public Roll3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        bitmapList = new ArrayList<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();
        matrix = new Matrix();
        this.context = context;
    }


    private void initBitmaps() {
        if (viewHeight <= 0 && viewWidth <= 0)
            return;

        if (null == bitmapList || bitmapList.size() <= 0)
            return;

        bitmaps = new Bitmap[bitmapList.size()][partNumber];

        initIndex();

        averageWidth = (int) (viewWidth / partNumber);
        averageHeight = (int) (viewHeight / partNumber);
        Bitmap partBitmap;
        for (int i = 0; i < bitmapList.size(); i++) {
            for (int j = 0; j < partNumber; j++) {
                Rect rect;
                if (rollMode != RollMode.Jalousie) {
                    if (direction == 1) {//纵向分块
                        rect = new Rect(j * averageWidth, 0, (j + 1) * averageWidth, viewHeight);
                        partBitmap = getPartBitmap(bitmapList.get(i), j * averageWidth, 0, rect);
                    } else {//横向分块
                        rect = new Rect(0, j * averageHeight, viewWidth, (j + 1) * averageHeight);
                        partBitmap = getPartBitmap(bitmapList.get(i), 0, j * averageHeight, rect);
                    }
                } else {
                    if (direction == 1) {
                        rect = new Rect(0, j * averageHeight, viewWidth, (j + 1) * averageHeight);
                        partBitmap = getPartBitmap(bitmapList.get(i), 0, j * averageHeight, rect);
                    } else {
                        rect = new Rect(j * averageWidth, 0, (j + 1) * averageWidth, viewHeight);
                        partBitmap = getPartBitmap(bitmapList.get(i), j * averageWidth, 0, rect);
                    }
                }

                bitmaps[i][j] = partBitmap;
            }
        }
    }

    /**
     * 初始化位置
     */
    private void initIndex() {
        int listSize = bitmapList.size();
        nextIndex = currIndex + 1;
        preIndex = currIndex - 1;
        if (nextIndex > listSize - 1)
            nextIndex = 0;
        if (preIndex < 0)
            preIndex = listSize - 1;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == bitmapList || bitmapList.size() <= 0)
            return;
        switch (rollMode) {
            case Roll2D:
                drawRollWhole3D(canvas, true);
                break;
            case Whole3D:
                drawRollWhole3D(canvas, false);
                break;
            case SepartConbine:
                drawSepartConbine(canvas);
                break;
            case RollInTurn:
                drawRollInTurn(canvas);
                break;
            case Jalousie:
                drawJalousie(canvas);
                break;
        }
    }


    /**
     * 整体翻滚
     * degree 0->90 往下翻滚或者往右翻滚
     *
     * @param canvas
     * @param draw2D  是否画2D效果：true  画2D效果； false  画3D效果
     */
    private void drawRollWhole3D(Canvas canvas, boolean draw2D) {

        Bitmap currWholeBitmap = bitmapList.get(currIndex);
        Bitmap nextWholeBitmap = bitmapList.get(nextIndex);
        canvas.save();

        if (direction == 1) {
            camera.save();
            if (draw2D)
                camera.rotateX(0);
            else
                camera.rotateX(-rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-viewWidth / 2, 0);
            matrix.postTranslate(viewWidth / 2, axisY);
            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();
            if (draw2D)
                camera.rotateX(0);
            else
                camera.rotateX((90 - rotateDegree));
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-viewWidth / 2, -viewHeight);
            matrix.postTranslate(viewWidth / 2, axisY);
            canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        } else {
            camera.save();
            if (draw2D)
                camera.rotateY(0);
            else
                camera.rotateY(rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(0, -viewHeight / 2);
            matrix.postTranslate(axisX, viewHeight / 2);

            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();
            if (draw2D)
                camera.rotateY(0);
            else
                camera.rotateY(rotateDegree - 90);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-viewWidth, -viewHeight / 2);
            matrix.postTranslate(axisX, viewHeight / 2);
            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
        }
        canvas.restore();
    }


    /**
     * 纵向  头部接合  尾部分离效果
     * degree 0->90 往下翻滚 或者 往右翻滚   90->0往上翻滚 或者往翻滚
     *
     * @param canvas
     */
    private void drawSepartConbine(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[currIndex][i];
            Bitmap nextBitmap = bitmaps[nextIndex][i];

            canvas.save();
            if (direction == 1) {

                camera.save();
                camera.rotateX(-rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-currBitmap.getWidth() / 2, 0);
                matrix.postTranslate(currBitmap.getWidth() / 2 + i * averageWidth, axisY);
                canvas.drawBitmap(currBitmap, matrix, paint);

                camera.save();
                camera.rotateX((90 - rotateDegree));
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-nextBitmap.getWidth() / 2, -nextBitmap.getHeight());
                matrix.postTranslate(nextBitmap.getWidth() / 2 + i * averageWidth, axisY);
                canvas.drawBitmap(nextBitmap, matrix, paint);

            } else {
                camera.save();
                camera.rotateY(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(0, -currBitmap.getHeight() / 2);
                matrix.postTranslate(axisX, currBitmap.getHeight() / 2 + i * averageHeight);
                canvas.drawBitmap(currBitmap, matrix, paint);

                camera.save();
                camera.rotateY(rotateDegree - 90);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-nextBitmap.getWidth(), -nextBitmap.getHeight() / 2);
                matrix.postTranslate(axisX, nextBitmap.getHeight() / 2 + i * averageHeight);
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }
            canvas.restore();
        }
    }


    /**
     * 依次翻滚
     *
     * @param canvas
     */
    private void drawRollInTurn(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[currIndex][i];
            Bitmap nextBitmap = bitmaps[nextIndex][i];

            float tDegree = rotateDegree - i * 30;
            if (tDegree < 0)
                tDegree = 0;
            if (tDegree > 90)
                tDegree = 90;


            canvas.save();
            if (direction == 1) {
                float tAxisY = tDegree / 90f * viewHeight;
                if (tAxisY > viewHeight)
                    tAxisY = viewHeight;
                if (tAxisY < 0)
                    tAxisY = 0;

                camera.save();
                camera.rotateX(-tDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-currBitmap.getWidth(), 0);
                matrix.postTranslate(currBitmap.getWidth() + i * averageWidth, tAxisY);
                canvas.drawBitmap(currBitmap, matrix, paint);

                camera.save();
                camera.rotateX((90 - tDegree));
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-nextBitmap.getWidth(), -nextBitmap.getHeight());
                matrix.postTranslate(nextBitmap.getWidth() + i * averageWidth, tAxisY);
                canvas.drawBitmap(nextBitmap, matrix, paint);

            } else {
                float tAxisX = tDegree / 90f * viewWidth;
                if (tAxisX > viewWidth)
                    tAxisX = viewWidth;
                if (tAxisX < 0)
                    tAxisX = 0;
                camera.save();
                camera.rotateY(tDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(0, -currBitmap.getHeight() / 2);
                matrix.postTranslate(tAxisX, currBitmap.getHeight() / 2 + i * averageHeight);
                canvas.drawBitmap(currBitmap, matrix, paint);

                //
                camera.save();
                camera.rotateY(tDegree - 90);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-nextBitmap.getWidth(), -nextBitmap.getHeight() / 2);
                matrix.postTranslate(tAxisX, nextBitmap.getHeight() / 2 + i * averageHeight);
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }
            canvas.restore();
        }
    }


    /**
     * 百叶窗翻页
     *
     * @param canvas
     */
    private void drawJalousie(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[currIndex][i];
            Bitmap nextBitmap = bitmaps[nextIndex][i];

            canvas.save();
            //注意 百叶窗的翻转方向和其他模式是相反的  横向的时候纵翻  纵向的时候横翻
            if (direction == 1) {

                if (rotateDegree < 90) {
                    camera.save();
                    camera.rotateX(rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-currBitmap.getWidth() / 2, -currBitmap.getHeight() / 2);
                    matrix.postTranslate(currBitmap.getWidth() / 2, currBitmap.getHeight() / 2 + i * averageHeight);
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    camera.save();
                    camera.rotateX(180 - rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-nextBitmap.getWidth() / 2, -nextBitmap.getHeight() / 2);
                    matrix.postTranslate(nextBitmap.getWidth() / 2, nextBitmap.getHeight() / 2 + i * averageHeight);
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }


            } else {
                if (rotateDegree < 90) {
                    camera.save();
                    camera.rotateY(rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-currBitmap.getWidth() / 2, -currBitmap.getHeight() / 2);
                    matrix.postTranslate(currBitmap.getWidth() / 2 + i * averageWidth, currBitmap.getHeight() / 2);
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    camera.save();
                    camera.rotateY(180 - rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();

                    matrix.preTranslate(-nextBitmap.getWidth() / 2, -nextBitmap.getHeight() / 2);
                    matrix.postTranslate(nextBitmap.getWidth() / 2 + i * averageWidth, nextBitmap.getHeight() / 2);
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }

            }
            canvas.restore();
        }
    }

    /**
     * 获取bitmap的一部分
     *
     * @param bitmap 被裁减的bitmap
     * @param rect   裁剪范围 方块
     * @return 裁剪后的bitmap
     */
    private Bitmap getPartBitmap(Bitmap bitmap, int x, int y, Rect rect) {
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, x, y, rect.width(), rect.height());
        return bitmap1;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initBitmaps();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();

        if (viewWidth != 0 && viewHeight != 0) {
            //缩放处理bitmap
            for (int i = 0; i < bitmapList.size(); i++) {
                bitmapList.set(i, scaleBitmap(bitmapList.get(i)));
            }
            initBitmaps();
            invalidate();
        }
    }


    public void setRotateDegree(float rotateDegree) {
        this.rotateDegree = rotateDegree;
        if (direction == 1) {
            axisY = rotateDegree / (float) (rollMode == RollMode.Jalousie ? 180 : 90) * viewHeight;
        } else {
            axisX =  rotateDegree / (float) (rollMode == RollMode.Jalousie ? 180 : 90) * viewWidth;
        }
        invalidate();
    }


    /**
     * 设置滚动模式
     *
     * @param rollMode
     */
    public void setRollMode(RollMode rollMode) {
        this.rollMode = rollMode;
    }

    /**
     * 设置滚动方向 1竖直方向  其他为水平方向
     *
     * @param direction
     */
    public void setRollDirection(int direction) {
        this.direction = direction;
        initBitmaps();
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
        initBitmaps();
    }

    /**
     * 关于这几句代码的详细解释
     * 参考drawable文件夹中的图片explain_3d_img进行阅读
     * <p>
     * 1.camera.rotateX(rotateDegree);  camera有一个三维坐标系系统 这里绕X轴设置旋转角度
     * 加粗*旋转的中心点是被旋转物的中心*加粗
     * <p>
     * 2.
     * matrix.preTranslate(-viewWidth / 2, -viewHeight);
     * matrix.postTranslate(viewWidth / 2, axisY);
     * <p>
     * matrix原点是(0,0)点  要做绕x轴旋转操作 首先要将物体中心点移到(0,0)点(pre)，再旋转，然后移回原位置(post)  然后显示的就是旋转后的效果了
     * 但是这样操作的话，旋转中心中规中矩在物体中心
     * 如果要控制旋转中心点 则像这里的代码那样  在preTranslate的时候 适当改变参数
     * 这样便将旋转中心点设置到了图片的上边缘(向左移动0.5宽，向上移动0)  和图片的下边缘(向左移动0.5宽，向上移动整个高)
     */

    public void addImageBitmap(Bitmap bitmap) {

        bitmapList.add(bitmap);//缩放处理 使bitmap尺寸铺满view
        initBitmaps();
        invalidate();
    }


    public void removeBitmapAt(int index) {
        bitmapList.remove(index);
    }


    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin 原图
     * @return new Bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) viewWidth) / width;
        float scaleHeight = ((float) viewHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return newBM;
    }


    public void toNext() {
        if (rolling)
            return;

        if (rollMode == RollMode.RollInTurn) {
            valueAnimator = ValueAnimator.ofFloat(0, 90 + (partNumber - 1) * 30);
        } else if (rollMode == RollMode.Jalousie) {
            valueAnimator = ValueAnimator.ofFloat(0, 180);
        } else {
            valueAnimator = ValueAnimator.ofFloat(0, 90);
        }
        rolling = true;
        valueAnimator.setDuration(rollDuration);
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.addListener(toNextAnimListener);
        valueAnimator.start();
    }

    private AnimatorListenerAdapter toNextAnimListener = new AnimatorListenerAdapter() {


        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            currIndex++;
            if (currIndex > bitmapList.size() - 1)
                currIndex = 0;
            initIndex();
            setRotateDegree(0);//更新Index 旋转角度归0
            rolling = false;
        }
    };

    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float value = (float) valueAnimator.getAnimatedValue();
            setRotateDegree(value);
        }
    };


    /**
     * 执行从next  到  curr的翻转过程
     */
    public void toPre() {
        if (rolling)
            return;
        int startRotate = 0;

        if (rollMode == RollMode.RollInTurn) {
            startRotate = 90 + (partNumber - 1) * 30;
        } else if (rollMode == RollMode.Jalousie) {
            startRotate = 180;
        } else {
            startRotate = 90;
        }

        //rotateDegree == 0 说明curr在当前显示
        //设置角度为90或180 nextIndex和currIndex preIndex轮转互换,使next显示到当前的图片，然后完成翻转
        //可以通俗的理解为  先倒过来 再翻过去  只不过倒过来之前把图片也互换了 所以看不出来而已
        rollIndex(true);
        setRotateDegree(startRotate);

        rolling = true;
        valueAnimator = ValueAnimator.ofFloat(startRotate, 0);
        valueAnimator.setDuration(rollDuration);
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.addListener(toPreAnimListener);
        valueAnimator.start();
    }

    private AnimatorListenerAdapter toPreAnimListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            rollIndex(false);//index位置恢复正常
            currIndex--;
            if (currIndex < 0)
                currIndex = bitmapList.size() - 1;
            rolling = false;
            initIndex();
            invalidate();//index位置修正之后刷新一下

        }
    };

    private void rollIndex(boolean toPre) {
        int temp;
        if (toPre) {
            temp = currIndex;
            currIndex = preIndex;
            preIndex = nextIndex;
            nextIndex = temp;
        } else {
            temp = currIndex;
            currIndex = nextIndex;
            nextIndex = preIndex;
            preIndex = temp;
        }

    }

    /**
     * 设置一次的滚动时间
     *
     * @param rollDuration
     */
    public void setRollDuration(int rollDuration) {
        this.rollDuration = rollDuration;
    }
}
