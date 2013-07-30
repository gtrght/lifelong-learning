package com.vvlasov.tengen.hw;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.vvlasov.tengen.hw.HomeworkUtils.createCollection;

/**
 * User: Vasily Vlasov
 * Date: 22.05.13
 */
public class Week2Homework2 {
    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createCollection("students", "grades");

        DBCursor cursor = collection.find(new BasicDBObject("type", "homework")).sort(new BasicDBObject("student_id", 1).append("score", 1));

        int currentId = -1;
        ArrayList<Object> toRemove = new ArrayList<Object>();

        while (cursor.hasNext()) {
            DBObject grade = cursor.next();

            int student_id = ((Number) grade.get("student_id")).intValue();
            if(currentId != student_id) {
                toRemove.add(grade.get("_id"));
                currentId = student_id;
            }


        }

        collection.remove(QueryBuilder.start("_id").in(toRemove).get());

        System.out.println(collection.count());


    }
}
