package sql.pojo.cfg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HzFcfgBomLvl1ListOperationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HzFcfgBomLvl1ListOperationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andPuidIsNull() {
            addCriterion("PUID is null");
            return (Criteria) this;
        }

        public Criteria andPuidIsNotNull() {
            addCriterion("PUID is not null");
            return (Criteria) this;
        }

        public Criteria andPuidEqualTo(String value) {
            addCriterion("PUID =", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidNotEqualTo(String value) {
            addCriterion("PUID <>", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidGreaterThan(String value) {
            addCriterion("PUID >", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidGreaterThanOrEqualTo(String value) {
            addCriterion("PUID >=", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidLessThan(String value) {
            addCriterion("PUID <", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidLessThanOrEqualTo(String value) {
            addCriterion("PUID <=", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidLike(String value) {
            addCriterion("PUID like", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidNotLike(String value) {
            addCriterion("PUID not like", value, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidIn(List<String> values) {
            addCriterion("PUID in", values, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidNotIn(List<String> values) {
            addCriterion("PUID not in", values, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidBetween(String value1, String value2) {
            addCriterion("PUID between", value1, value2, "puid");
            return (Criteria) this;
        }

        public Criteria andPuidNotBetween(String value1, String value2) {
            addCriterion("PUID not between", value1, value2, "puid");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameIsNull() {
            addCriterion("P_OPRATION_TYPE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameIsNotNull() {
            addCriterion("P_OPRATION_TYPE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameEqualTo(Object value) {
            addCriterion("P_OPRATION_TYPE_NAME =", value, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameNotEqualTo(Object value) {
            addCriterion("P_OPRATION_TYPE_NAME <>", value, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameGreaterThan(Object value) {
            addCriterion("P_OPRATION_TYPE_NAME >", value, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameGreaterThanOrEqualTo(Object value) {
            addCriterion("P_OPRATION_TYPE_NAME >=", value, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameLessThan(Object value) {
            addCriterion("P_OPRATION_TYPE_NAME <", value, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameLessThanOrEqualTo(Object value) {
            addCriterion("P_OPRATION_TYPE_NAME <=", value, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameIn(List<Object> values) {
            addCriterion("P_OPRATION_TYPE_NAME in", values, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameNotIn(List<Object> values) {
            addCriterion("P_OPRATION_TYPE_NAME not in", values, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameBetween(Object value1, Object value2) {
            addCriterion("P_OPRATION_TYPE_NAME between", value1, value2, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOprationTypeNameNotBetween(Object value1, Object value2) {
            addCriterion("P_OPRATION_TYPE_NAME not between", value1, value2, "pOprationTypeName");
            return (Criteria) this;
        }

        public Criteria andPOpratorIsNull() {
            addCriterion("P_OPRATOR is null");
            return (Criteria) this;
        }

        public Criteria andPOpratorIsNotNull() {
            addCriterion("P_OPRATOR is not null");
            return (Criteria) this;
        }

        public Criteria andPOpratorEqualTo(Object value) {
            addCriterion("P_OPRATOR =", value, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorNotEqualTo(Object value) {
            addCriterion("P_OPRATOR <>", value, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorGreaterThan(Object value) {
            addCriterion("P_OPRATOR >", value, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorGreaterThanOrEqualTo(Object value) {
            addCriterion("P_OPRATOR >=", value, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorLessThan(Object value) {
            addCriterion("P_OPRATOR <", value, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorLessThanOrEqualTo(Object value) {
            addCriterion("P_OPRATOR <=", value, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorIn(List<Object> values) {
            addCriterion("P_OPRATOR in", values, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorNotIn(List<Object> values) {
            addCriterion("P_OPRATOR not in", values, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorBetween(Object value1, Object value2) {
            addCriterion("P_OPRATOR between", value1, value2, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOpratorNotBetween(Object value1, Object value2) {
            addCriterion("P_OPRATOR not between", value1, value2, "pOprator");
            return (Criteria) this;
        }

        public Criteria andPOprationDateIsNull() {
            addCriterion("P_OPRATION_DATE is null");
            return (Criteria) this;
        }

        public Criteria andPOprationDateIsNotNull() {
            addCriterion("P_OPRATION_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andPOprationDateEqualTo(Date value) {
            addCriterion("P_OPRATION_DATE =", value, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateNotEqualTo(Date value) {
            addCriterion("P_OPRATION_DATE <>", value, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateGreaterThan(Date value) {
            addCriterion("P_OPRATION_DATE >", value, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("P_OPRATION_DATE >=", value, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateLessThan(Date value) {
            addCriterion("P_OPRATION_DATE <", value, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateLessThanOrEqualTo(Date value) {
            addCriterion("P_OPRATION_DATE <=", value, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateIn(List<Date> values) {
            addCriterion("P_OPRATION_DATE in", values, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateNotIn(List<Date> values) {
            addCriterion("P_OPRATION_DATE not in", values, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateBetween(Date value1, Date value2) {
            addCriterion("P_OPRATION_DATE between", value1, value2, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationDateNotBetween(Date value1, Date value2) {
            addCriterion("P_OPRATION_DATE not between", value1, value2, "pOprationDate");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusIsNull() {
            addCriterion("P_OPRATION_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusIsNotNull() {
            addCriterion("P_OPRATION_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusEqualTo(Short value) {
            addCriterion("P_OPRATION_STATUS =", value, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusNotEqualTo(Short value) {
            addCriterion("P_OPRATION_STATUS <>", value, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusGreaterThan(Short value) {
            addCriterion("P_OPRATION_STATUS >", value, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("P_OPRATION_STATUS >=", value, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusLessThan(Short value) {
            addCriterion("P_OPRATION_STATUS <", value, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusLessThanOrEqualTo(Short value) {
            addCriterion("P_OPRATION_STATUS <=", value, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusIn(List<Short> values) {
            addCriterion("P_OPRATION_STATUS in", values, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusNotIn(List<Short> values) {
            addCriterion("P_OPRATION_STATUS not in", values, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusBetween(Short value1, Short value2) {
            addCriterion("P_OPRATION_STATUS between", value1, value2, "pOprationStatus");
            return (Criteria) this;
        }

        public Criteria andPOprationStatusNotBetween(Short value1, Short value2) {
            addCriterion("P_OPRATION_STATUS not between", value1, value2, "pOprationStatus");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}