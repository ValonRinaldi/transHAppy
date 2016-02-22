package com.example.vrinaldi.transhappy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by martius on 22.02.16.
 */
public class ConnectionAdapter extends BaseAdapter {

    private Context context;
    private List<Connection> connections;

    public ConnectionAdapter(Context context, List<Connection> persons) {
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
        Connection connection = (Connection) getItem(position);
        return connection.getId();
    }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(getLayoutIdForViewType(position), parent, false);
            }


            Connection connection = (Connection) getItem(position);
/*Hier fehlt noch die entsprechende Implementierung in layout files
            TextView idTextView = (TextView) convertView.findViewById(R.id.id);
            idTextView.setText(connection.getId());

            TextView nameTextView = (TextView) convertView.findViewById(R.id.From);
            nameTextView.setText(connection.getFrom());

            TextView addressTextView = (TextView) convertView.findViewById(R.id.To);
            addressTextView.setText(connection.getTo());
*/
            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }



        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }

        private int getLayoutIdForViewType(int position) {

            int viewType = getItemViewType(position);
            switch (viewType) {
                case 0:
                    return R.layout.listitem;
            }

            return R.layout.listitem;


        }



}
