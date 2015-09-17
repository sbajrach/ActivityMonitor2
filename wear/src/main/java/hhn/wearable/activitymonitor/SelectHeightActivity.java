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

public class SelectHeightActivity extends Activity {

    private String[] mData= new String[]{"160","165","170","175","180"};
    private WearableListView mListView;
    private StringListAdapter mAdapter;
    public final String       TAG = "HHN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_height);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.list);
                mAdapter = new StringListAdapter(SelectHeightActivity.this, mData);
                mListView.setAdapter(mAdapter);
                mListView.setClickListener(new WearableListView.ClickListener() {
                    @Override
                    public void onClick(WearableListView.ViewHolder viewHolder) {
                        String label = mData[viewHolder.getAdapterPosition()];
                        Log.w(TAG, label + " has been selected");
                        saveHeight(Integer.parseInt(label));
                        openWalkKeeperActivity();
                    }

                    @Override
                    public void onTopEmptyRegionClick() {

                    }
                });

            }
        });
    }
    private void saveHeight(int height){
        SharedPreferences preferences = getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("H", height);
        editor.commit();

    }
    private void openWalkKeeperActivity(){
        Intent walkKeeper = new Intent(this, WalkKeeperActivity.class);
        startActivity(walkKeeper);
        finish();
    }

}
