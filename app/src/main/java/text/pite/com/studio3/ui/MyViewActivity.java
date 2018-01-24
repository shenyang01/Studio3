package text.pite.com.studio3.ui;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.TextView;

import text.pite.com.studio3.R;
import text.pite.com.studio3.databinding.MyviewActivityBinding;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/12/21
 */

public class MyViewActivity extends BaseActivity implements View.OnClickListener {
    private MyviewActivityBinding binding;
    private int index;
    private TextView myViewTv;

    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        binding = (MyviewActivityBinding) viewDataBinding;
        binding.scheduleView.setValue(++index);
        binding.myViewBt.setOnClickListener(this);
        binding.myViewMinus.setOnClickListener(this);
        myViewTv = findViewById(R.id.myView_tv);

    }

    @Override
    public View getInitView() {
        return View.inflate(this, R.layout.myview_activity, null);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myView_minus)
            index--;
        else
            index++;
        showToast("我是旧版apk");
        binding.scheduleView.setValue(index);
        myViewTv.setText("我是二代apk");
    }
}
