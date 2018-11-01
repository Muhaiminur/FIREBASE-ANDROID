package com.muhaiminur.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.firbase_Image)
    Button firbaseImage;
    @BindView(R.id.firbaseui_Image_list)
    Button firbaseuiImageList;
    @BindView(R.id.firbase_recyclerview)
    Button firbaseRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.firbase_Image, R.id.firbaseui_Image_list,R.id.firbase_recyclerview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.firbase_Image:
                startActivity(new Intent(MainActivity.this, Firebase_Image.class));
                break;
            case R.id.firbaseui_Image_list:
                startActivity(new Intent(MainActivity.this, FirebaseUI_Recyclerview.class));
                break;
            case R.id.firbase_recyclerview:
                startActivity(new Intent(MainActivity.this, Firebase_Normal_Recyclerview.class));
                break;
        }
    }
}
