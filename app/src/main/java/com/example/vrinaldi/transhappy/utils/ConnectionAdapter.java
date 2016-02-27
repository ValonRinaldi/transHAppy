package com.example.vrinaldi.transhappy.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vrinaldi.transhappy.R;

import java.util.List;

import ch.schoeb.opendatatransport.model.Connection;

/**
 * Created by martius on 22.02.16.
 */
public class ConnectionAdapter extends BaseAdapter {

    private Context context;
    private List<Connection> connections;

    private TextView from;
    private TextView to;
    private TextView duration;
    private TextView changes;
    private TextView plattform;

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
                convertView = inflater.inflate(R.layout.search_results_overview_listitems, parent, false);
            }

            Connection connection = (Connection) getItem(position);


            from = (TextView) convertView.findViewById(R.id.resDep);
            from.setText(connection.getFrom().getDeparture().substring(11, 16));

            to = (TextView) convertView.findViewById(R.id.resArr);
            to.setText(connection.getTo().getArrival().substring(11,16));

            duration = (TextView) convertView.findViewById(R.id.resDur);
            duration.setText(connection.getDuration().substring(4,8));

            changes = (TextView) convertView.findViewById(R.id.resChg);
            changes.setText(connection.getTransfers().toString());

            plattform = (TextView) convertView.findViewById(R.id.resPlat);
            plattform.setText(connection.getFrom().getPlatform());

            return convertView;
        }
}
