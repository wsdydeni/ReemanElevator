package reeman.elevator.demo;

import android.app.Application;

import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
        ToastUtils.init(this);
    }
}
