package com.restoreapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.restoreapp.atyutil.AtyStateUtil;

public class MainActivity extends BaseActivity {

    private TextView tip_tv;
    private Button jump_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tip_tv = (TextView) findViewById(R.id.tip_tv);
        tip_tv.setText(this.getClass().getSimpleName());

        jump_btn = (Button) findViewById(R.id.jump_btn);
        jump_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Text1Aty.class));
            }
        });

        AtyStateUtil.getInstance().jumpSavedActivity(this);
    }
}
