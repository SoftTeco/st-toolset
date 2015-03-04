package com.softteco.toolset.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author serge
 */
public final class QueryBuilder {

    private final StringBuilder queryBuilder = new StringBuilder();
    private final StringBuilder whereBuilder = new StringBuilder();
    private final StringBuilder orderByBuilder = new StringBuilder();
    private final Map<String, Object> values = new HashMap<>();

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

    public QueryBuilder in(final String constraint, final String paramName, final List value) {
        if (value == null) {
            return this;
        }

        if (whereBuilder.length() > 0) {
            whereBuilder.append(" and ");
        }

        whereBuilder.append("(");
        int i = 0;
        for (Object valueObj : value) {
            if (i++ != 0) {
                whereBuilder.append(" OR ");
            }
            whereBuilder.append("(");

            final String newParamName = paramName + i;
            whereBuilder.append(constraint.replaceAll(paramName, newParamName));

            values.put(paramName + i, valueObj);
            whereBuilder.append(")");
        }
        whereBuilder.append(")");

        return this;
    }

    private String getQuery() {
        if (whereBuilder.length() > 0) {
            queryBuilder.append(" where ").append(whereBuilder);
        }
        if (orderByBuilder.length() > 0) {
            queryBuilder.append(" ").append(orderByBuilder);
        }
        return queryBuilder.toString();
    }

    public void append(final String queryPart) {
        queryBuilder.append(queryPart);
    }

    public void orderBy(final String orderBy) {
        orderByBuilder.append(orderBy);
    }

    public Query build(final EntityManager entityManager) {
        final Query query = entityManager.createQuery(getQuery());
        for (String each : values.keySet()) {
            query.setParameter(each, values.get(each));
        }
        return query;
    }
}
