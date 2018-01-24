package text.pite.com.studio3.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.databinding.ViewDataBinding;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import text.pite.com.studio3.R;
import text.pite.com.studio3.adapter.MyViewHolder;
import text.pite.com.studio3.adapter.SimpleAdapter;
import text.pite.com.studio3.databinding.CartanimationActivityBinding;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/12/14
 * 购物车 抛物线动画
 */

public class CartAnimationActivity extends BaseActivity implements MyViewHolder.ViewOnclick {
    private CartanimationActivityBinding binding;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];

    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(" 测试数据  " + i);
        }
        binding = (CartanimationActivityBinding) viewDataBinding;
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        binding.cartRecyclerView.setHasFixedSize(true);
        binding.cartRecyclerView.setAdapter(new SimpleAdapter(this, R.layout.recycle_item, list, false, this) {
            @Override
            public void convert(MyViewHolder holder, String data, int position) {
                holder.setText(R.id.recycler_tv, data);
                //holder.setOnclick(R.id.recycler_tv, position);
                holder.setOnclick(R.id.recycler_image, position);
                //holder.setOnclick(R.id.recycler_item, position);
            }
        });
    }

    @Override
    public View getInitView() {
        return LayoutInflater.from(this).inflate(R.layout.cartanimation_activity, null);
    }

    @Override
    public void clickView(View v, int position) {

        switch (v.getId()) {
            case R.id.recycler_tv:
                showToast("recycler_tv  " + position);
                break;
            case R.id.recycler_image:
                addCart((ImageView) v);
                showToast("recycler_image  " + position);
                break;
            case R.id.recycler_item:
                showToast("recycler_item  " + position);
                break;
        }
    }


    private void addCart( ImageView iv) {
        //代码new一个imageview，图片资源是上面的imageview的图片
        final ImageView goods = new ImageView(CartAnimationActivity.this);
        goods.setImageDrawable(iv.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        binding.rl.addView(goods, params);

        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）去掉状态栏高度
        int[] parentLocation = new int[2];
        binding.rl.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        iv.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        binding.cartImage.getLocationInWindow(endLoc);
        Log.e("tag",parentLocation[0]+"  "+parentLocation[1]);

        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + binding.cartImage.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

        //开始绘制贝塞尔曲线
        Path path = new Path();
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
         mPathMeasure = new PathMeasure(path, false);

        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车的数量加1
                // 把移动的图片imageview从父布局里移除
                binding.rl.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
