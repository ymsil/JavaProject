package trabelstesh.javaproject.model.backend;

import trabelstesh.javaproject.model.datasource.List_DBManager;

/**
 * Created by ymsil on 12/8/2016.
 */

public class DBManagerFactory
{
    static DB_manager manager = null;

    public static DB_manager getManager() {
        if (manager == null)
            manager = new List_DBManager();
        return manager;
    }
}
