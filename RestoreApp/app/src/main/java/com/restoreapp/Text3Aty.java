package com.restoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.restoreapp.atyutil.AtyStateUtil;

public class Text3Aty extends BaseActivity {

    private TextView tip_tv;
    private Button jump_btn;
    private int post_id;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        post_id = getIntent().getIntExtra("post_id", -1);
        user_name = getIntent().getStringExtra("user_name");

        tip_tv = (TextView) findViewById(R.id.tip_tv);
        tip_tv.setText(this.getClass().getSimpleName() +
                "\n| post_id: " + post_id +
                "\n| user_name: " + user_name +
                "\n| 点击Home按键 杀掉进程 进入 跳转到本页");

        jump_btn = (Button) findViewById(R.id.jump_btn);
        jump_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip_tv = null;
                Intent intent = new Intent(Text3Aty.this, NNMainAty.class);
                intent.putExtra("savedata", tip_tv.getText());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setTargetParam(
                new String[]{"post_id", "user_name"},
                new Object[]{post_id, user_name}
        );
        AtyStateUtil.getInstance().saveTargetData(this.getClass().getCanonicalName(), mTargetParam);
    }
}
