package com.example.vrinaldi.transhappy;

import android.os.AsyncTask;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OpenTransportRepositoryFactory;
import ch.schoeb.opendatatransport.model.*;

/**
 * Created by vrinaldi on 23/02/16.
 */
public class ConnectionWorker extends AsyncTask<SearchParams, Integer, ConnectionList> {

    private IOpenTransportRepository repo;
    private ConnectionList conList;

    @Override
    protected ConnectionList doInBackground(SearchParams... params) {
        SearchParams searchParam = params[0];

        conList = new ConnectionList();
        repo = OpenTransportRepositoryFactory.CreateOnlineOpenTransportRepository();

        conList = repo.searchConnections(searchParam.getFromParam(), searchParam.getToParam());

        return conList;
    }

    private Connection createConnection(String from, String to) {
        Connection conn = new Connection();
        conn.setFrom(getConnectionStation(from));
        conn.setTo(getConnectionStation(to));
        conn.setDuration("1:00");
        conn.setTransfers(2);
        return conn;
    }

    private ConnectionStation getConnectionStation(String name) {
        ConnectionStation fromStation = new ConnectionStation();
        Station station = new Station();
        station.setName(name);
        fromStation.setStation(station);
        return fromStation;
    }


}
