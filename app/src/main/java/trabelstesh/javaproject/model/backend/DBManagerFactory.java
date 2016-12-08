package trabelstesh.javaproject.model.backend;

/**
 * Created by ymsil on 12/8/2016.
 */

public class DBManagerFactory
{
    static DB_manager db = null;
    public static DB_manager getDb()
    {
        ///DAL layer implentation using a singelton
        if (db != null) return db;
        db = new DB();
        return db;
    }
}
