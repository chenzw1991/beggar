package com.chenzhiwu.beggar.common.elasticsearch.condition;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndCondition extends Condition {
	private List<Condition> conditionList = new ArrayList<>();
	private Map<String,List<Condition>> conditionMap = new HashMap<>(4);
	private final static String MUST = "must";
	private final static String MUST_NOT = "must_not";
	private final static String SHOULD = "should";
	private final static String FILTER = "filter";
	
	public AndCondition(Condition condition) {
		conditionList.add(condition);
	}
	
	public AndCondition() {
		
	}
	
	public AndCondition add(Condition condition) {
		conditionList.add(condition);
		return this;
	}
	
	@Override
	public QueryBuilder toQueryBuilder() {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		conditionMap.forEach((k,conditionLists) -> {
			conditionLists.forEach(condition -> {
				if(k.equals(MUST)) {
					boolQueryBuilder.must(condition.toQueryBuilder());
				} else if(k.equals(MUST_NOT)) {
					boolQueryBuilder.mustNot(condition.toQueryBuilder());
				} else if(k.equals(FILTER)){
					boolQueryBuilder.filter(condition.toQueryBuilder());
				}else {
					boolQueryBuilder.should(condition.toQueryBuilder());
				}
			});
		});
		
		return boolQueryBuilder;
	}
	
	public AndCondition filter() {
		conditionMap.put(FILTER	, new ArrayList<>(conditionList));
		conditionList.clear();
		return this;
	}
	
	public AndCondition must() {
		conditionMap.put(MUST, new ArrayList<>(conditionList));
		conditionList.clear();
		return this;
	}
	
	public AndCondition should() {
		conditionMap.put(SHOULD, new ArrayList<>(conditionList));
		conditionList.clear();
		
		return this;
	}
	
	public AndCondition mustNot() {
		conditionMap.put(MUST_NOT, new ArrayList<>(conditionList));
		conditionList.clear();
		
		return this;
	}
	
	
	
}
