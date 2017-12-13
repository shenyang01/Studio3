package text.pite.com.studio3.ui;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import text.pite.com.studio3.R;
import text.pite.com.studio3.adapter.ViewpagerAdapter;
import text.pite.com.studio3.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener,ViewpagerAdapter.ViewPagerOnClick {
    private List<Integer> list;
    private ActivityMainBinding binding;
    private static final float coefficient = 0.2f; //缩放系数
    private ViewpagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        binding= (ActivityMainBinding) viewDataBinding;
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0)
                list.add(R.mipmap.timg);
            else
                list.add(R.mipmap.mm);
        }
        adapter =new ViewpagerAdapter(this, list,this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(3); //设置一页显示数目
        binding.viewPager.setPageMargin(30); // 设置页与页的边距
        binding.viewPager.setCurrentItem((Integer.MAX_VALUE-Integer.MAX_VALUE%list.size())/2+2);

        binding.viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public View getInitView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main,null);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e("tag","positionOffset  "+positionOffset+"   position  "+position%list.size());
            binding.viewPager.findViewWithTag(position+1).setScaleY(1f+coefficient*positionOffset);
            binding.viewPager.findViewWithTag(position).setScaleY(1f+coefficient-coefficient*positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
      /* binding.viewPager.findViewWithTag(position).setScaleY(1+coefficient);
       if(position>0)
        binding.viewPager.findViewWithTag(position-1).setScaleY(1f);
       if(position<adapter.getCount()-1)
        binding.viewPager.findViewWithTag(position+1).setScaleY(1f);*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(int position) {
        showToast("点击了  "+position);
    }
}
