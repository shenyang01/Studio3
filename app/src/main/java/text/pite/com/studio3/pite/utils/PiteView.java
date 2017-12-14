package text.pite.com.studio3.pite.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/12/13
 */

public class PiteView extends android.support.v7.widget.AppCompatTextView {
    public PiteView(Context context) {
        super(context);
    }

    public PiteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthModule = MeasureSpec.getMode(widthMeasureSpec);
        int heightModule = MeasureSpec.getMode(heightMeasureSpec);
        switch (widthModule) {
            case MeasureSpec.AT_MOST:
                Log.e("tag", widthModule + "  AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("tag", widthModule + "  EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e("tag", widthModule + "  UNSPECIFIED");
                break;
        }
        switch (heightModule) {
            case MeasureSpec.AT_MOST:
                Log.e("tag", heightModule + "  AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("tag", heightModule + "  EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e("tag", heightModule + "  UNSPECIFIED");
                break;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
