package com.statboost.models.session;

import java.util.HashMap;

/**
 * Created by Jon on 9/27/2014.
 */
public class QueryObject {

    public HashMap<String, Object> getQueryParameters() {
        return queryParameters;
    }

    public String getHqlQuery() {
        return hqlQuery;
    }

    private HashMap<String, Object> queryParameters;
    private String hqlQuery;

    public QueryObject(HashMap<String,Object> qp, String hql){
        queryParameters = qp;
        hqlQuery = hql;
    }
}
