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

        conList = new ConnectionList();
        repo = OpenTransportRepositoryFactory.CreateOnlineOpenTransportRepository();
        conList = repo.searchConnections(SearchParams.fromParam, SearchParams.toParam);
        conList = repo.searchConnections(
                    SearchParams.fromParam,
                    SearchParams.toParam,
                    SearchParams.via,
                    SearchParams.sdf.format(SearchParams.date),
                    SearchParams.stf.format(SearchParams.time),
                    SearchParams.isArrival);
        return conList;
    }
}
