package app.lisboa.lisboapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Event;

/**
 * Created by Rajat Anantharam on 02/11/16.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    private Context mContext;
    private int resourceViewId;

    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resourceViewId = resource;
    }

    private UIContainer holder;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(resourceViewId, parent, false);
            holder = new UIContainer(convertView);
            convertView.setTag(holder);
        } else {
            holder = (UIContainer) convertView.getTag();
        }

        holder.eventName.setText(getItem(position).eventName);
        return convertView;
    }

    private class UIContainer {
        public TextView eventName;
        public UIContainer(View convertView) {
            eventName = (TextView) convertView.findViewById(R.id.eventName);
        }
    }
}