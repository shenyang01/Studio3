package text.pite.com.studio3.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/12/12
 */

public abstract class BaseActivity extends Activity {
    private Toast toast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getInitView();
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(view);
        setContentView(view);
        initData(viewDataBinding);
    }
    public abstract void initData(ViewDataBinding viewDataBinding);

    public abstract View getInitView();

    @SuppressLint("ShowToast")
    public void showToast(String string){
        if(toast==null){
            toast = Toast.makeText(this, string,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
        }else{
            toast.setText(string);
        }
        toast.show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim
                .slide_out_right);
    }
}
