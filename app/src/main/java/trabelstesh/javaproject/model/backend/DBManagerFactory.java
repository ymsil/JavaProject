package trabelstesh.javaproject.model.backend;

import trabelstesh.javaproject.model.datasource.List_DBManager;
import trabelstesh.javaproject.model.datasource.SQL_DBManager;

/**
 * Created by ymsil on 12/8/2016.
 */

public class DBManagerFactory
{
    static IDB_manager manager = null;

    public static IDB_manager getManager() {
        if (manager == null)
            manager = new SQL_DBManager();
//            manager = new List_DBManager();

        return manager;
    }
}
