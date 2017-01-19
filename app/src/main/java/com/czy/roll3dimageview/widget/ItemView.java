package com.czy.roll3dimageview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenzy.widget.Roll3DView;
import com.czy.roll3dimageview.R;

/**
 * Created by zhangyu on 2017/1/19.
 */

public class ItemView extends LinearLayout implements View.OnClickListener{
    private Context context;

    private Roll3DView roll3DView;
    private Button toLeft;
    private Button toRight;
    private Button toUp;
    private Button toDown;
    private TextView titleTv;
    private BitmapDrawable bgDrawable1, bgDrawable2,bgDrawable3,bgDrawable4,bgDrawable5;
    public ItemView(Context context) {
        super(context);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View.inflate(context, R.layout.demo_item,this);

        toLeft = (Button) findViewById(R.id.left_btn);
        toRight = (Button) findViewById(R.id.right_btn);
        toUp = (Button) findViewById(R.id.roll_up_btn);
        toDown = (Button) findViewById(R.id.roll_down_btn);
        roll3DView = (Roll3DView) findViewById(R.id.three_d_view);
        titleTv = (TextView) findViewById(R.id.title_tv);


        toLeft.setOnClickListener(this);
        toRight.setOnClickListener(this);
        toUp.setOnClickListener(this);
        toDown.setOnClickListener(this);

        bgDrawable1 = (BitmapDrawable) getResources().getDrawable(R.drawable.img1);
        bgDrawable2 = (BitmapDrawable) getResources().getDrawable(R.drawable.img2);
        bgDrawable3 = (BitmapDrawable) getResources().getDrawable(R.drawable.img3);
        bgDrawable4 = (BitmapDrawable) getResources().getDrawable(R.drawable.img4);
        bgDrawable5 = (BitmapDrawable) getResources().getDrawable(R.drawable.img5);


        Bitmap bitmap1 = bgDrawable1.getBitmap();
        Bitmap bitmap2 = bgDrawable2.getBitmap();
        Bitmap bitmap3 = bgDrawable3.getBitmap();
        Bitmap bitmap4 = bgDrawable4.getBitmap();
        Bitmap bitmap5 = bgDrawable5.getBitmap();

        roll3DView.addImageBitmap(bitmap1);
        roll3DView.addImageBitmap(bitmap2);
        roll3DView.addImageBitmap(bitmap3);
        roll3DView.addImageBitmap(bitmap4);
        roll3DView.addImageBitmap(bitmap5);

        roll3DView.setRollMode(Roll3DView.RollMode.Whole3D);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:
                roll3DView.setRollDirection(0);
                roll3DView.toPre();
                break;
            case R.id.right_btn:
                roll3DView.setRollDirection(0);
                roll3DView.toNext();
                break;
            case R.id.roll_up_btn:
                roll3DView.setRollDirection(1);
                roll3DView.toPre();
                break;
            case R.id.roll_down_btn:
                roll3DView.setRollDirection(1);
                roll3DView.toNext();
                break;
        }
    }

    public Roll3DView getRoll3DView() {
        return roll3DView;
    }

    public void setTitleText(String titleText){
        titleTv.setText(titleText);
    }
}
