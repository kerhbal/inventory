package domainapp.modules.simple.util;


import java.util.ArrayList;
import java.util.List;

import domainapp.modules.simple.dom.inventory.ObjectParent;
import domainapp.modules.simple.external.Record;
import domainapp.modules.simple.external.RecordsResponse;
import domainapp.modules.simple.external.RecordsResponseSingle;

public class CommonUtil {
    public static <T extends ObjectParent> List<T> getObjectFromResponse(RecordsResponse<Record<T>> response) {
        List<T> allResults = new ArrayList<>();
        for (Record<T> r : response.getRecords()) {
            T object = r.getFields();
            object.setId(r.getId());
            allResults.add(object);
        }
        return allResults;
    }

    public static <T extends ObjectParent> T getObjectFromSingleResponse(Record<T> record) {
        T object = record.getFields();
        object.setId(record.getId());
        return object;
    }
}
