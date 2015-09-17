package hhn.wearable.activitymonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;

/**
 * Created by heroe on 9/15/2015.
 */
public class SelectWeightActivity extends Activity {
    private String[]          mData = new String[] {"60", "65", "70", "75", "80", "85", "90"};
    private WearableListView  mListView;
    private StringListAdapter mAdapter;
    public final String       TAG = "HHN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_weight);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.list);
                mAdapter = new StringListAdapter(SelectWeightActivity.this, mData);
                mListView.setAdapter(mAdapter);
                mListView.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        String label = mData[viewHolder.getAdapterPosition()];
                        Log.w(TAG, label + " has been selected");
                        saveWeight(Integer.parseInt(label));

                        openSelectHeightActivity();
                    }

                    @Override
                    public void onTopEmptyRegionClick() {

                    }
                });
            }
        });
    }
    private void saveWeight(int weight){
        SharedPreferences preferences = getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("W", weight);
        editor.commit();
    }

    private void openSelectHeightActivity()
    {
        Intent selectHeight = new Intent(this, SelectHeightActivity.class);
        startActivity(selectHeight);
        finish();


    }
}
