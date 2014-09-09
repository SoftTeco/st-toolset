package com.softteco.toolset.jpa;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author serge
 */
public class QueryBuilder {

    private final StringBuilder queryBuilder = new StringBuilder();
    private final StringBuilder whereBuilder = new StringBuilder();
    private final Map<String, Object> values = new HashMap<String, Object>();

    public QueryBuilder(final String coreQuery) {
        queryBuilder.append(coreQuery);
    }

    public QueryBuilder and(final String constraint, final Object value) {
        if (value == null) {
            return this;
        }

        if (whereBuilder.length() > 0) {
            whereBuilder.append(" and ");
        }
        whereBuilder.append(constraint);
        final int paramNameIndex = constraint.indexOf(":");
        String paramName = constraint.substring(paramNameIndex + 1);
        if (paramName.contains(" ")) {
            paramName = paramName.substring(0, paramName.indexOf(" "));
        }
        values.put(paramName, value);
        return this;
    }

    private String getQuery() {
        if (whereBuilder.length() > 0) {
            queryBuilder.append(" where ").append(whereBuilder.toString());
        }
        return queryBuilder.toString();
    }
    
    public void append(String queryPart) {
        queryBuilder.append(queryPart);
    }

    public Query build(EntityManager entityManager) {
        final Query query = entityManager.createQuery(getQuery());
        for (String each : values.keySet()) {
            query.setParameter(each, values.get(each));
        }
        return query;
    }
}
