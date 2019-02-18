package com.wangzhen.admin.spliteaudio2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import splite.SpliteVideo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpliteVideo spliteVideo = new SpliteVideo();
        //spliteVideo.checkMediaDecoder();//获取当前手机可以支持得编辑码格式
        spliteVideo.getBuffer();
    }
}
