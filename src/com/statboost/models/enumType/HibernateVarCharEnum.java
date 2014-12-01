package com.statboost.models.enumType;

import org.hibernate.type.EnumType;

import java.sql.Types;
import java.util.Properties;

/**
 * Created by Sam Kerr on 10/26/2014.
 */
public class HibernateVarCharEnum extends EnumType {

    public void setParameterValues(Properties parameters) {
        parameters.setProperty(TYPE, "" + Types.VARCHAR);
        super.setParameterValues(parameters);
    }
}
