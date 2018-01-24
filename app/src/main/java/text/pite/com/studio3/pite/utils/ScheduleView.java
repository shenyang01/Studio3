package text.pite.com.studio3.pite.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import text.pite.com.studio3.R;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/12/21
 * 记录进度的控件
 */

public class ScheduleView extends View {
    private int startColor;
    private int stopColor;
    private int stepNum;
    private float radius;
    private float lineSize;
    private Paint paint;
    private int viewWidth;
    private int viewHeight;
    private int value;

    public ScheduleView(Context context) {
        this(context, null);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        paint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScheduleView, defStyleAttr, 0);
        startColor = typedArray.getColor(R.styleable.ScheduleView_startColor, Color.GRAY);
        stopColor = typedArray.getColor(R.styleable.ScheduleView_stopColor, Color.RED);
        stepNum = typedArray.getInteger(R.styleable.ScheduleView_stepNum, 5);
        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, typedArray.getDimension
                (R.styleable.ScheduleView_radius, 20f), context.getResources().getDisplayMetrics());
        lineSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, typedArray.getDimension
                (R.styleable.ScheduleView_lineSize, 1f), context.getResources().getDisplayMetrics());
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setDither(true);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY: //精确值
                viewWidth = width;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                viewWidth = 600;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY: //精确值
                viewHeight = height;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                viewHeight = 600;
                break;
        }
        setMeasuredDimension(viewWidth, viewHeight);
        Log.e("tag", "viewWidth  " + viewWidth + "  viewHeight  " + viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        value= value<0?0:value;
        int height = getHeight();
        float one = radius * 2;
        //单个线条的长度
        float linWidth = (getWidth() - one * stepNum - paddingLeft - paddingRight) / (stepNum - 1);
        Log.e("tag", " linWidth  " + linWidth + "  getWidth()  " + getWidth() + " stepNum " + stepNum + "  paddingLeft " + paddingLeft);
        paint.setColor(startColor);
        paint.setStrokeWidth(lineSize);

        for (int i = 0; i < stepNum; i++) {
            if(i<value){
                paint.setColor(stopColor);
            }else {
            paint.setColor(startColor);
            }
            canvas.drawCircle(paddingLeft + one * (i + 1) + linWidth * i - radius, height / 2, radius, paint);
            if(i<value-1){
                paint.setColor(stopColor);
            }else {
                paint.setColor(startColor);
            }
            canvas.drawLine(one * (i + 1)+linWidth*i-1f, height / 2, (linWidth + one) * (i + 1)+1f, height / 2, paint);

            // 计算文字的高宽
            String text = String.valueOf(i+1);
            paint.setTextSize(60f);
            //绘制数字
            paint.setColor(Color.BLUE);
            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float textHeight=(-fontMetrics.ascent-fontMetrics.descent)/2;  //字体的实际高度
            Log.e("tag",textHeight+" textHeight");
            canvas.drawText(text, one * (i + 1) + linWidth * i - radius , height / 2+textHeight , paint);
        }
    }

    /**
     *
     * @param value 根据value 值绘制进度
     */
    public void setValue(int value){
        if(value<=0)
            value=1;
        else if(value>stepNum){
            value=stepNum;
        }
        this.value = value;
        invalidate();
    }

}
