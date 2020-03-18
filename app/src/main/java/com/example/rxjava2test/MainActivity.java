package com.example.rxjava2test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private TextView TV;
    private boolean edit=false;
    private static String hint="请输入文字";
    private static String Tag="MainActivity";
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


    }

    private void init(){
        layout=(LinearLayout) findViewById(R.id.layout);
        input=findViewById(R.id.input);

        TV=findViewById(R.id.TV);
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                    input.setText("");
                    edit=true;
                    TV.setText(String.valueOf(edit));
                    }
                }
            }
        );
        input.setOnClickListener(listener);
        input.setText(hint);
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {//emitter预设变数名称为e

                emitter.onNext("next");
                emitter.onComplete();

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.wtf(Tag,"onSubscribe");

            }

            @Override
            public void onNext(String s) {
                Log.wtf(Tag,"onNext");

            }

            @Override
            public void onError(Throwable e) {
                Log.wtf(Tag,"onError");

            }

            @Override
            public void onComplete() {
                Log.wtf(Tag,"OnComplete");

            }
        });


    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.input:

                        edit=false;
                        TV.setText(input.getText());
                    hideKeyboard(MainActivity .this);
                    input.clearFocus();

                    break;
            }
        }
    };
    //Create被观察者
    private void createObservable(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello");
                emitter.onNext("Hi");
                emitter.onNext("Aloha");
                emitter.onComplete();
            }
        });

    }
    //Create订阅者
    private void createSubscriber(){
        /**
         * 创建一个订阅者
         */
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };


    }

    /**
     * 自动关闭软键盘
     * @param activity
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm =  (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 打开关闭相互切换
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }



}
