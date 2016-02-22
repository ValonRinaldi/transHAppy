package com.example.vrinaldi.transhappy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martius on 22.02.16.
 */
public class ConnectionRepository {

    private static List<Connection> connections = new ArrayList<>();

    static {
        connections.add(new Connection(1, "Basel", "Zürich", "1300", "2"));
        connections.add(new Connection(2, "Basel", "Bern", "1310", "4"));
        connections.add(new Connection(3, "Basel", "Luzer", "1315", "5"));

    }

    public static List<Connection> getConnections() {
        return connections;
    }


}
