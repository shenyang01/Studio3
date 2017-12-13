package text.pite.com.studio3.pite.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/11/11
 */

public class TestText extends android.support.v7.widget.AppCompatTextView {
    Rect rect;
    public TestText(Context context) {
        super(context);
        rect = new Rect();
    }

    public TestText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
         rect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e("tag","onSizeChanged "+w+ " "+h+"  "+oldw+ "  "+oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables =getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        if(drawableLeft!= null){ // 测量字体和图标的高度
            int leftWidth =  drawableLeft.getIntrinsicWidth();
           int TextWidth = (int) getPaint().measureText(getText().toString());
           int width = (int) (leftWidth+TextWidth+getCompoundDrawablePadding()); //实际总宽度

            //测量text宽高
            getPaint().getTextBounds((String) getText(),0,getText().length(),rect);
            int width2 = rect.width();


            Log.e("tag","文字实际宽度 "+TextWidth+"  控件宽度  "+getWidth()+ "  "+ width2 );
           canvas.translate((getWidth()-width)/2,0);
        }
        super.onDraw(canvas);
    }
}
