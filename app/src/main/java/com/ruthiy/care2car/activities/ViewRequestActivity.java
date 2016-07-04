package com.ruthiy.care2car.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.Request;

import java.io.Serializable;

public class ViewRequestActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        TextView tvArea = (TextView) findViewById(R.id.tv_area);
        TextView tvCategory = (TextView) findViewById(R.id.tv_category);
        TextView tvEngineValume = (TextView) findViewById(R.id.tv_engineValume);
        TextView tvOpenDate = (TextView) findViewById(R.id.tv_openDate);
        TextView tvType = (TextView) findViewById(R.id.tv_type);
        TextView tvRemarks = (TextView) findViewById(R.id.tv_remarks);
        TextView tvStatus = (TextView) findViewById(R.id.tv_status);


        Bundle  b = this.getIntent().getExtras();
        if (b.containsKey("REQUEST")) {
            Request requestParam = (Request) getIntent().getSerializableExtra("REQUEST");
            tvArea.setText(requestParam.getAreaId());
            tvCategory.setText(requestParam.getCategoryId());
            tvEngineValume.setText(requestParam.getEngineVolumeId());
            tvOpenDate.setText(requestParam.getRequestStDate()!= null ? requestParam.getRequestStDate().toString():"");
            tvType.setText(requestParam.getCarTypeId());
            tvRemarks.setText(requestParam.getRemarks());
            tvStatus.setText(requestParam.getRequestStatusId());
            //location.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/openSans/OpenSans-Light.ttf"));
        }
    }
}
