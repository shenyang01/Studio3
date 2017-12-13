package text.pite.com.studio3.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


/**
 * 概况viwpager   适配器
 */

public class ViewpagerAdapter extends PagerAdapter {
    private List<Integer> images = null;
    private Context context;
    private ImageView[] imageViews;
    ViewPagerOnClick viewPagerOnClick;

    public ViewpagerAdapter(Context context, List<Integer> images,ViewPagerOnClick viewPagerOnClick) {
        this.images = images;
        this.context = context;
        imageViews = new ImageView[images.size()];
        this.viewPagerOnClick = viewPagerOnClick;
        for (int i=0;i<images.size();i++){
            ImageView  imageView = new ImageView(context);
            imageView.setImageResource(images.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews[i]=imageView;
        }
        imageViews[2].setScaleY(1.2f);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(imageViews[position%imageViews.length]);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
       // ViewGroup.LayoutParams LayoutParams = new ViewGroup.LayoutParams(300,500);
        container.addView(imageViews[position%imageViews.length]);
        imageViews[position%imageViews.length].setTag(position);
        imageViews[position%imageViews.length].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerOnClick.onClick(position%imageViews.length);
            }
        });
        return imageViews[position%imageViews.length];
    }

    /**
     *  显示比例
     */
    @Override
    public float getPageWidth(int position) {
        return 1.0f;
    }
    public interface ViewPagerOnClick{
        void onClick(int  position);
    }

}
