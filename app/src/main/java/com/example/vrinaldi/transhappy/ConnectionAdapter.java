package com.example.vrinaldi.transhappy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ch.schoeb.opendatatransport.model.Connection;

/**
 * Created by martius on 22.02.16.
 */
public class ConnectionAdapter extends BaseAdapter {

    private Context context;
    private List<Connection> connections;

    public ConnectionAdapter(Context context, List<Connection> connections) {
        this.context = context;
        this.connections = connections;
    }

    @Override
    public int getCount() {
        return connections.size();
    }

    @Override
    public Object getItem(int position) {
        return connections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listitem, parent, false);
            }

            Connection connection = (Connection) getItem(position);
            TextView from = (TextView) convertView.findViewById(R.id.results_Dep);
            from.setText(connection.getFrom().getDeparture().substring(11,16));
           //from.setText("From");

            TextView to = (TextView) convertView.findViewById(R.id.results_Arr);
            to.setText(connection.getTo().getArrival().substring(11,16));
            //to.setText("To");

            TextView duration = (TextView) convertView.findViewById(R.id.results_Dur);
            duration.setText(connection.getDuration().substring(4,8));
            //duration.setText("dur");

            TextView changes = (TextView) convertView.findViewById(R.id.results_Chg);
            changes.setText(connection.getTransfers().toString());
            //changes.setText("ch");

            TextView plattform = (TextView) convertView.findViewById(R.id.results_Plat);
            plattform.setText(connection.getFrom().getPlatform());
            //plattform.setText("Pla");

            return convertView;
        }
}
