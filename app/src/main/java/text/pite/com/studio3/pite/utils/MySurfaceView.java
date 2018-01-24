package text.pite.com.studio3.pite.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * @ company pite
 * @ name sy
 * @ time 2018/1/5
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private int[] colors;
    private MyThread myThread;
    private boolean isstart;


    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化变量
     */
    private void init() {
        Log.e("tag","surface init()");
        isstart = true;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);//注册surfaceHolder
        setFocusable(true);
        setFocusableInTouchMode(true);
        colors = new int[]{Color.DKGRAY, Color.GRAY, Color.RED, Color.GRAY, Color.GREEN};
    }

    /**
     * 停止
     */
    public void stop(){
        Log.e("tag","调用了 stop()");
        isstart = false;
    }

    /**
     * 开启
     */
    public void start(){
        if(isstart)
            return;
        isstart = true;
        new MyThread().start();
    }
    class MyThread extends Thread {
        @Override
        public void run() {
            paint = new Paint();
            paint.setTextSize(200f);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10f);
            paint.setDither(true);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setTextAlign(Paint.Align.CENTER);
            while (isstart) {
                paint.setColor(colors[new Random().nextInt(colors.length)]);
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawText("666666666", getWidth() / 2, getHeight() / 2, paint);
                surfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myThread = new MyThread();
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        paint.reset();
        paint = null;
        Log.e("tag", "surfaceDestroyed");
    }

}
