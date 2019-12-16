package com.chenzhiwu.beggar.common.elasticsearch.condition;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import com.chenzhiwu.beggar.common.elasticsearch.exception.ConditionException;

import java.util.HashMap;
import java.util.Map;


public class RangeCondition extends Condition {
    private String field;
    private Map<String, Object> rangeOperator = new HashMap<>(2);
    private final static String GT = ">";
    private final static String GTE = ">=";
    private final static String LT = "<";
    private final static String LTE = "<=";

    public RangeCondition(String field) {
        this.field = field;
    }

    public RangeCondition gt(Object value) {
        rangeOperator.put(GT, value);
        return this;
    }

    public RangeCondition lt(Object value) {
        rangeOperator.put(LT, value);
        return this;
    }

    public RangeCondition gte(Object value) {
        rangeOperator.put(GTE, value);
        return this;
    }

    public RangeCondition lte(Object value) {
        rangeOperator.put(LTE, value);
        return this;
    }

    @Override
    public QueryBuilder toQueryBuilder() {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field);
        if (rangeOperator.size() <= 0) {
            throw new ConditionException("范围查询没有指定范围");
        }
        rangeOperator.forEach((operate, value) -> {
            if (operate.equals(GT)) {
                rangeQueryBuilder.gt(value);
            } else if (operate.equals(LT)) {
                rangeQueryBuilder.lt(value);
            } else if (operate.equals(GTE)) {
                rangeQueryBuilder.gte(value);
            } else {
                rangeQueryBuilder.lte(value);
            }
        });
        return rangeQueryBuilder;
    }

}
