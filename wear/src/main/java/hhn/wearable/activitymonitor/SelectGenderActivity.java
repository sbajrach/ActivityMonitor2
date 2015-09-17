package hhn.wearable.activitymonitor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.widget.TextView;

public class SelectGenderActivity extends Activity {

    private String[]          mData = new String[] {"\u2640 female", "\u2642 male"};
    private WearableListView  mListView;
    private StringListAdapter mAdapter;
    public final String       TAG = "HHN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.list);
                mAdapter = new StringListAdapter(SelectGenderActivity.this, mData);
                mListView.setAdapter(mAdapter);
                mListView.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        String label = mData[viewHolder.getAdapterPosition()];
                        if(label.contains("female")){
                            saveGender("female");
                        }else {
                            saveGender("ebmale");
                        }
                        Log.w(TAG, label + " has been selected");
                        openSelectWeightActivity();
                    }

                    @Override
                    public void onTopEmptyRegionClick() {

                    }
                });
            }
        });
    }
    private void saveGender(String gender){
        SharedPreferences preferences = getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("gender", gender);
        editor.commit();
    }

    private void openSelectWeightActivity()
    {
        Intent selectWeight = new Intent(this, SelectWeightActivity.class);
        startActivity(selectWeight);
        finish();


    }
}
