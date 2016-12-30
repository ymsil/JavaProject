package trabelstesh.javaproject.model.backend;

import trabelstesh.javaproject.model.datasource.List_DBManager;

/**
 * Created by ymsil on 12/8/2016.
 */

public class DBManagerFactory
{
    static IDB_manager manager = null;

    public static IDB_manager getManager() {
        if (manager == null)
            manager = new List_DBManager();
        return manager;
    }
}
