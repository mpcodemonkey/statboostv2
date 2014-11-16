package com.statboost.models.DAO;

import com.statboost.models.session.QueryObject;
import com.statboost.util.HibernateUtil;
import org.hibernate.*;

import java.util.List;

/**
 * Created by Jon on 9/27/2014.
 *
 */
public class GenericDAO{
    private int numberOfResults;

    private int numberPerPage;

    private int currentPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNumberPerPage() {
        return numberPerPage;
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }



    public Object getResultSet(QueryObject q, int pageNo){


        numberPerPage=15;//hardcoded till not lazy
        currentPage = pageNo;
        SessionFactory genericQueryFactory = HibernateUtil.getDatabaseSessionFactory();
        List<Object> resultSet = null;
        Session session = genericQueryFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(q.getHqlQuery());
            for (String s : q.getQueryParameters().keySet()) {
                query.setParameter(s, q.getQueryParameters().get(s));
                System.out.println(q.getQueryParameters().get(s));
            }

            numberOfResults = query.list().size();
            query.setFirstResult( (pageNo*numberPerPage) - numberPerPage ).setMaxResults(numberPerPage);

            resultSet = query.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return resultSet;
    }

    //for queries that don't require pagination
    public Object getResultSet(QueryObject q){

        SessionFactory genericQueryFactory = HibernateUtil.getDatabaseSessionFactory();
        List<Object> resultSet = null;
        Session session = genericQueryFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(q.getHqlQuery());
            for (String s : q.getQueryParameters().keySet()) {
                query.setParameter(s, q.getQueryParameters().get(s));
                System.out.println(q.getQueryParameters().get(s));
            }
            resultSet = query.list();


            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return resultSet;
    }

    public Object getResultSet(String hql){
        SessionFactory genericQueryFactory = HibernateUtil.getDatabaseSessionFactory();
        List<Object> resultSet = null;
        Session session = genericQueryFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            resultSet = (List<Object>)query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return resultSet;
    }

    public Object getResultSetSql(String sql){
        SessionFactory genericQueryFactory = HibernateUtil.getDatabaseSessionFactory();
        List<Object> resultSet = null;
        Session session = genericQueryFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            resultSet = (List<Object>)query.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return resultSet;
    }
}
