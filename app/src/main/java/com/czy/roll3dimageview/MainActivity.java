package com.czy.roll3dimageview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.czy.roll3dimageview.annotation.ViewInject;
import com.czy.roll3dimageview.annotation.ViewInjectUtil;

public class MainActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.debug_btn)
    private Button debugBtn;
    @ViewInject(R.id.demo_btn)
    private Button demoBtn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInjectUtil.injectView(this);
        initView();
    }

    private void initView() {
        debugBtn.setOnClickListener(this);
        demoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.debug_btn:
                intent = new Intent(this, RollDebugAct.class);
                startActivity(intent);
                break;
            case R.id.demo_btn:
                intent = new Intent(this, DemoAct.class);
                startActivity(intent);
                break;

        }
    }
}
