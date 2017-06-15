package io.canner.androidstepsview;

import io.canner.stepsview.StepsView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StepsView mStepsView = (StepsView) findViewById(R.id.stepsView);

        mStepsView
            .setBarColorIndicator(
                    getApplicationContext().getResources().getColor(R.color.material_blue_grey_800))
            .setProgressColorIndicator(getApplicationContext().getResources().getColor(R.color.orange))
            .setLabelColorIndicator(getApplicationContext().getResources().getColor(R.color.black))
            .setCircleRadius(60)
            .drawView();
    }

}