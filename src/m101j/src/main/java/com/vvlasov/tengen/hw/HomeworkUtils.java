package com.vvlasov.tengen.hw;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * User: Vasily Vlasov
 * Date: 22.05.13
 */
public class HomeworkUtils {
    public static DBCollection createCollection(String dbName, String colName) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB(dbName);
        return db.getCollection(colName);
    }
}
