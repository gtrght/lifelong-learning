package com.vvlasov.tengen.hw;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.vvlasov.tengen.hw.HomeworkUtils.createCollection;

/**
 * User: Vasily Vlasov
 * Date: 22.05.13
 */
public class Week3Homework1 {
    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createCollection("school", "students");

        DBCursor cursor = collection.find();

        int currentId = -1;
        ArrayList<Object> toRemove = new ArrayList<Object>();

        while (cursor.hasNext()) {
            DBObject student = cursor.next();


            BasicDBList scores = (BasicDBList) student.get("scores");

            BasicDBObject min = null;

            for (Object score : scores) {
                BasicDBObject dbScore = (BasicDBObject) score;

                if("homework".equals(dbScore.get("type")) && (min == null || min.getDouble("score") > dbScore.getDouble("score")))
                    min = dbScore;

            }



            if(min != null)
                scores.remove(min);

            collection.update(new BasicDBObject("_id", student.get("_id")), new BasicDBObject("scores", scores));
        }

    }
}
