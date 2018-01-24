package text.pite.com.studio3.ui;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;

import text.pite.com.studio3.R;
import text.pite.com.studio3.databinding.WebviewActivityBinding;

/**
 * @ company pite
 * @ name sy
 * @ time 2018/1/4
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private WebviewActivityBinding binding;

    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        binding = (WebviewActivityBinding) viewDataBinding;
         /* binding.webView.loadUrl("https://www.baidu.com/?tn=98012088_5_dg&ch=12");
        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });*/
        binding.start.setOnClickListener(this);
        binding.stop.setOnClickListener(this);
    }

    @Override
    public View getInitView() {
        return View.inflate(this, R.layout.webview_activity, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                binding.surfaceView.start();
                break;
            case R.id.stop:
                binding.surfaceView.stop();
                startActivity(new Intent(this,ViewpagerScaleActivity.class));
               /* overridePendingTransition(android.R.anim.fade_in, android.R.anim
                        .fade_out);*/
                /*overridePendingTransition(android.R.anim.slide_in_left, android.R.anim
                        .slide_out_right);*/
                break;
        }
    }

    @Override
    public void onBackPressed() {
        binding.surfaceView.stop();
        super.onBackPressed();
    }
}
