package com.czy.roll3dimageview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.chenzy.widget.Roll3DView;
import com.czy.roll3dimageview.annotation.ViewInject;
import com.czy.roll3dimageview.annotation.ViewInjectUtil;


/**
 * Created by zhangyu on 2017/1/17.
 */

public class RollDebugAct extends Activity {
    private static final String TAG = "TDAct";

    @ViewInject(R.id.three_d_view)
    private Roll3DView tdView;
    @ViewInject(R.id.three_d_view1)
    private Roll3DView tdView1;
    @ViewInject(R.id.three_d_view2)
    private Roll3DView tdView2;
    @ViewInject(R.id.three_d_view3)
    private Roll3DView tdView3;
    @ViewInject(R.id.three_d_view4)
    private Roll3DView tdView4;
    @ViewInject(R.id.three_d_view5)
    private Roll3DView tdView5;
    @ViewInject(R.id.three_d_view6)
    private Roll3DView tdView6;
    @ViewInject(R.id.three_d_view8)
    private Roll3DView tdView8;
    @ViewInject(R.id.three_d_view9)
    private Roll3DView tdView9;


    @ViewInject(R.id.atdv_seek_bar)
    private SeekBar seekBar;
    @ViewInject(R.id.atdv_seek_bar1)
    private SeekBar seekBar1;
    @ViewInject(R.id.atdv_seek_bar2)
    private SeekBar seekBar2;

    private int max = 90;
    private int max1 = 210;
    private int max2 = 180;

    private BitmapDrawable bgDrawable1, bgDrawable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_roll_debug);
        ViewInjectUtil.injectView(this);

        initView();
    }

    private void initView() {
        seekBar.setMax(max);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);

        seekBar1.setMax(max1);
        seekBar1.setOnSeekBarChangeListener(onSeekBarChangeListener1);

        seekBar2.setMax(max2);
        seekBar2.setOnSeekBarChangeListener(onSeekBarChangeListener2);

        bgDrawable1 = (BitmapDrawable) getResources().getDrawable(R.drawable.img1);
        bgDrawable2 = (BitmapDrawable) getResources().getDrawable(R.drawable.img2);


        Bitmap bitmap1 = bgDrawable1.getBitmap();
        Bitmap bitmap2 = bgDrawable2.getBitmap();


        tdView.addImageBitmap(bitmap1);
        tdView1.addImageBitmap(bitmap1);
        tdView2.addImageBitmap(bitmap1);
        tdView3.addImageBitmap(bitmap1);
        tdView4.addImageBitmap(bitmap1);
        tdView5.addImageBitmap(bitmap1);
        tdView6.addImageBitmap(bitmap1);
        tdView8.addImageBitmap(bitmap1);
        tdView9.addImageBitmap(bitmap1);

        tdView.addImageBitmap(bitmap2);
        tdView1.addImageBitmap(bitmap2);
        tdView2.addImageBitmap(bitmap2);
        tdView3.addImageBitmap(bitmap2);
        tdView4.addImageBitmap(bitmap2);
        tdView5.addImageBitmap(bitmap2);
        tdView6.addImageBitmap(bitmap2);
        tdView8.addImageBitmap(bitmap2);
        tdView9.addImageBitmap(bitmap2);

    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.d(TAG, "progress = " + progress);


            tdView.setRollDirection(1);
            tdView.setPartNumber(3);
            tdView.setRollMode(Roll3DView.RollMode.SepartConbine);
            tdView.setRotateDegree(progress);

            tdView1.setRollDirection(0);
            tdView1.setRollMode(Roll3DView.RollMode.Whole3D);
            tdView1.setRotateDegree(progress);

            tdView2.setRollDirection(1);
            tdView2.setRollMode(Roll3DView.RollMode.Whole3D);
            tdView2.setRotateDegree(progress);

            tdView3.setRollDirection(0);
            tdView3.setPartNumber(3);
            tdView3.setRollMode(Roll3DView.RollMode.SepartConbine);
            tdView3.setRotateDegree(progress);

            tdView4.setRollDirection(1);
            tdView4.setPartNumber(3);
            tdView4.setRollMode(Roll3DView.RollMode.SepartConbine);
            tdView4.setRotateDegree(progress);


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener1 = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            tdView8.setRollDirection(1);
            tdView8.setPartNumber(5);
            tdView8.setRollMode(Roll3DView.RollMode.RollInTurn);
            tdView8.setRotateDegree(progress);

            tdView9.setRollDirection(0);
            tdView9.setPartNumber(5);
            tdView9.setRollMode(Roll3DView.RollMode.RollInTurn);
            tdView9.setRotateDegree(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            tdView5.setRollDirection(0);
            tdView5.setPartNumber(5);
            tdView5.setRollMode(Roll3DView.RollMode.Jalousie);
            tdView5.setRotateDegree(progress);

            tdView6.setRollDirection(1);
            tdView6.setPartNumber(5);
            tdView6.setRollMode(Roll3DView.RollMode.Jalousie);
            tdView6.setRotateDegree(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
