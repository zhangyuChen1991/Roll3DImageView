package com.czy.roll3dimageview;

import android.app.Activity;
import android.os.Bundle;

import com.chenzy.widget.Roll3DView;
import com.czy.roll3dimageview.annotation.ViewInject;
import com.czy.roll3dimageview.annotation.ViewInjectUtil;
import com.czy.roll3dimageview.widget.ItemView;

/**
 * Created by zhangyu on 2017/1/18 21:09.
 */

public class DemoAct extends Activity{

    @ViewInject(R.id.item1)
    private ItemView item1;
    @ViewInject(R.id.item2)
    private ItemView item2;
    @ViewInject(R.id.item3)
    private ItemView item3;
    @ViewInject(R.id.item4)
    private ItemView item4;
    @ViewInject(R.id.item5)
    private ItemView item5;

    private Roll3DView roll3DView1,roll3DView2,roll3DView3,roll3DView4,roll3DView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_demo);
        ViewInjectUtil.injectView(this);

        initView();
    }

    private void initView() {
        roll3DView1 = item1.getRoll3DView();
        roll3DView2 = item2.getRoll3DView();
        roll3DView3 = item3.getRoll3DView();
        roll3DView4 = item4.getRoll3DView();
        roll3DView5 = item5.getRoll3DView();

        roll3DView1.setRollMode(Roll3DView.RollMode.Roll2D);
        item1.setTitleText("2D平移");

        roll3DView2.setRollMode(Roll3DView.RollMode.Whole3D);
        item2.setTitleText("3D翻转");

        roll3DView3.setRollMode(Roll3DView.RollMode.SepartConbine);
        roll3DView3.setPartNumber(3);
        item3.setTitleText("开合效果");


        roll3DView4.setRollMode(Roll3DView.RollMode.Jalousie);
        roll3DView4.setPartNumber(5);
        item4.setTitleText("百叶窗");

        roll3DView5.setRollMode(Roll3DView.RollMode.RollInTurn);
        roll3DView5.setPartNumber(9);
        item5.setTitleText("轮转效果");
    }

}
