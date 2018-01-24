package text.pite.com.studio3.ui;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.AsyncProcessor;
import io.reactivex.subjects.AsyncSubject;
import text.pite.com.studio3.R;
import text.pite.com.studio3.databinding.RxjavaActivityBinding;
import text.pite.com.studio3.pite.utils.RxData;

/**
 * @ company pite
 * @ name sy
 * @ time 2017/12/14
 */

public class RxJavaActivity extends BaseActivity implements View.OnClickListener {
    private RxjavaActivityBinding binding;
    private Observable<String> observable; //被观察者
    private Observer<String> observer; // 观察者
    private Subscription subscription;

    @Override
    public void initData(ViewDataBinding viewDataBinding) {
        binding = (RxjavaActivityBinding) viewDataBinding;
        binding.rxJavaBt.setOnClickListener(this);
       /* Observable.just(new RxData(),new RxData()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new myConsumer());*/
    }

    private void subscribeText() {
        observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("dzq");
                e.onNext("yjs");
                e.onNext("keg");
                e.onComplete(); //调用完成 后面不会调用
                e.onNext("ldd");
            }
        });
        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) { //初始化调用
            }

            @Override
            public void onNext(String s) {
                Log.e("tag", " onNext " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("tag", " Throwable " + e);
            }

            @Override  //手动调用  才会回调此方法
            public void onComplete() {
                Log.e("tag", " onComplete ");
            }
        };

        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                // 上次事件处理完成后，发送下次事件
                        e.onNext("flowable");
                        e.onNext("flowable2");
                        e.onNext("flowable3");
                        e.onNext("flowable4");
                        e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e("tag"," Subscription");
                subscription = s;
                s.request(2);  // 每次处理多少个事件,处理完成后可继续调用
            }

            @Override
            public void onNext(String s) {
                    Log.e("tag",s+ "  onNext");
                try {
                    Thread.sleep(2000);
                    //subscription.request(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        AsyncSubject<String> subject = AsyncSubject.create(); //永远只会输出最后一个
        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e("tag", s);
            }
        });


        Processor processor = AsyncProcessor.create();
        processor.subscribe(new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });


        subject.onNext("-----");
        subject.onComplete();

       // observable.subscribe(observer);  //被观察者有事件主动发送
        flowable.subscribe(subscriber); //等待处理完成，被动发送
    }


    public void onCodeClick() {
        final long count = 60; // 设置60秒
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong; // 由于是倒计时，需要将倒计时的数字反过来
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        binding.rxJavaBt.setEnabled(false);
                        binding.rxJavaBt.setTextColor(Color.GRAY);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Long aLong) {
                        binding.rxJavaBt.setText(aLong + "秒后重发");
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                        binding.rxJavaBt.setEnabled(true);
                        binding.rxJavaBt.setTextColor(Color.RED);
                        binding.rxJavaBt.setText("发送验证码");
                    }
                });
    }
    class myConsumer implements Consumer<RxData>{

        @Override
        public void accept(RxData s) throws Exception {
           showToast(s.toString());
        }
    }
    @Override
    public View getInitView() {
        return View.inflate(this, R.layout.rxjava_activity, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rxJava_bt:
                subscribeText();
                //onCodeClick();
                break;
        }
    }
}
