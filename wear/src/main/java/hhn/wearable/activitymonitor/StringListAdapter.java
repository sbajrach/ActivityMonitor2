package hhn.wearable.activitymonitor;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by heroe on 9/15/2015.
 */
public class StringListAdapter extends WearableListView.Adapter{
    private String[] data;
    private LayoutInflater mLayoutInflater;

    public StringListAdapter(Context context, String[] data){
        mLayoutInflater = LayoutInflater.from(context); // take layoutInflater from context , ie calling activity (WearActivity)
        this.data = data; // Assign array of data from calling activity to data
    }
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        WearableListView.ViewHolder viewHolder = new WearableListView.ViewHolder(mLayoutInflater.inflate(R.layout.stringlist_item, viewGroup, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
        TextView text = (TextView) viewHolder.itemView.findViewById(R.id.stringlist_item_text);
        text.setText(data[i]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}