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

    private final String[] views = {"View 1", "View 2", "View 3", "View 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mListView = (ListView) findViewById(R.id.list);

        MyAdapter adapter = new MyAdapter(this, 0);
        adapter.addAll(views);

        mListView.setAdapter(adapter);

    }

    public static class MyAdapter extends ArrayAdapter<String> {

        private final String[] labels = {"This is \n step 1", "I am step 2", "step 3"};

        public MyAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mLabel.setText(getItem(position));
            Log.v("My Adapter", String.valueOf(position));

            holder.mStepsView
                    .setCompletedPosition(position % labels.length)
                    .setLabels(labels)
                    .setProgressStrokeWidth(3)
                    .setBarColorIndicator(
                            getContext().getResources().getColor(R.color.material_blue_grey_800))
                    .setProgressColorIndicator(getContext().getResources().getColor(R.color.orange))
                    .setLabelColorIndicator(getContext().getResources().getColor(R.color.black))
                    .setProgressMargins(130)
                    .setCircleRadius(60)
                    .drawView();

            return convertView;
        }

        static class ViewHolder {

            TextView mLabel;
            StepsView mStepsView;

            public ViewHolder(View view) {
                mLabel = (TextView) view.findViewById(R.id.label);
                mStepsView = (StepsView) view.findViewById(R.id.stepsView);
            }
        }
    }

}