package text.pite.com.studio3.ui;

import android.databinding.ViewDataBinding;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import text.pite.com.studio3.R;
import text.pite.com.studio3.databinding.CameraActivityBinding;

/**
 * @ company pite
 * @ name sy
 * @ time 2018/1/9
 */

public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Camera camera = null;
    private CameraActivityBinding binding;

    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        binding = (CameraActivityBinding) viewDataBinding;
        holder = binding.surfaceView.getHolder();
        holder.addCallback(this);

    }

    @Override
    public View getInitView() {
        return View.inflate(this, R.layout.camera_activity, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(holder);
            camera.startPreview(); // 开启预览
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
