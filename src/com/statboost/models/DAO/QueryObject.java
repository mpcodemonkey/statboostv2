package com.statboost.models.DAO;

import java.util.HashMap;

/**
 * Created by Jon on 9/27/2014.
 */
public class QueryObject {

    public HashMap<String, String> getQueryParameters() {
        return queryParameters;
    }

    public String getHqlQuery() {
        return hqlQuery;
    }

    private HashMap<String, String> queryParameters;
    private String hqlQuery;

    public QueryObject(HashMap<String,String> qp, String hql){
        queryParameters = qp;
        hqlQuery = hql;
    }
}
