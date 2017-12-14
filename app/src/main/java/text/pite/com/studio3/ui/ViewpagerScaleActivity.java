package text.pite.com.studio3.ui;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

import text.pite.com.studio3.R;
import text.pite.com.studio3.adapter.ViewpagerAdapter;
import text.pite.com.studio3.databinding.ActivityMainBinding;

public class ViewpagerScaleActivity extends BaseActivity implements ViewPager.OnPageChangeListener, ViewpagerAdapter.ViewPagerOnClick, View.OnClickListener {
    private List<Integer> list;
    private ActivityMainBinding binding;
    private static final float coefficient = 0.2f; //缩放系数
    private ViewpagerAdapter adapter;
    private String[] string;
    private int i;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        binding = (ActivityMainBinding) viewDataBinding;
        inflater = LayoutInflater.from(this);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0)
                list.add(R.mipmap.timg);
            else
                list.add(R.mipmap.mm);
        }
        string = new String[]{"1111", "2222", "3333", "4444"};
        adapter = new ViewpagerAdapter(this, list, this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(3); //设置一页显示数目
        binding.viewPager.setPageMargin(30); // 设置页与页的边距
        binding.viewPager.setCurrentItem((Integer.MAX_VALUE - Integer.MAX_VALUE % list.size()) / 2 + 2);
        binding.viewPager.addOnPageChangeListener(this);
        //TextView 上下滚动
        binding.textSwitcher.setOnClickListener(this);
        binding.textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                View view = inflater.inflate(R.layout.item_textview, null);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER;
                view.setLayoutParams(lp);
                return view;
            }
        });
        binding.textSwitcher.setText("dzq");
        t.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            binding.textSwitcher.setText(string[msg.what]);
            super.handleMessage(msg);
        }
    };
    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                Message msg = new Message();
                msg.what = i % string.length;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(3000);
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    });


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public View getInitView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main, null);
    }

    //设置缩放大小
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        binding.viewPager.findViewWithTag(position + 1).setScaleY(1f + coefficient * positionOffset);
        binding.viewPager.findViewWithTag(position).setScaleY(1f + coefficient - coefficient * positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(int position) {
        showToast("点击了  " + position);
    }

    @Override
    public void onClick(View v) {
        showToast("点击了");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.stop();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
}
