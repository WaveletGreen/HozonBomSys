package sql.pojo.cfg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HzDerivativeMaterielBasicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HzDerivativeMaterielBasicExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidIsNull() {
            addCriterion("DMB_MODEL_UID is null");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidIsNotNull() {
            addCriterion("DMB_MODEL_UID is not null");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidEqualTo(String value) {
            addCriterion("DMB_MODEL_UID =", value, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidNotEqualTo(String value) {
            addCriterion("DMB_MODEL_UID <>", value, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidGreaterThan(String value) {
            addCriterion("DMB_MODEL_UID >", value, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidGreaterThanOrEqualTo(String value) {
            addCriterion("DMB_MODEL_UID >=", value, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidLessThan(String value) {
            addCriterion("DMB_MODEL_UID <", value, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidLessThanOrEqualTo(String value) {
            addCriterion("DMB_MODEL_UID <=", value, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidLike(String value) {
            addCriterion("DMB_MODEL_UID like", value, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidNotLike(String value) {
            addCriterion("DMB_MODEL_UID not like", value, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidIn(List<String> values) {
            addCriterion("DMB_MODEL_UID in", values, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidNotIn(List<String> values) {
            addCriterion("DMB_MODEL_UID not in", values, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidBetween(String value1, String value2) {
            addCriterion("DMB_MODEL_UID between", value1, value2, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbModelUidNotBetween(String value1, String value2) {
            addCriterion("DMB_MODEL_UID not between", value1, value2, "dmbModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidIsNull() {
            addCriterion("DMB_COLOR_MODEL_UID is null");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidIsNotNull() {
            addCriterion("DMB_COLOR_MODEL_UID is not null");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidEqualTo(String value) {
            addCriterion("DMB_COLOR_MODEL_UID =", value, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidNotEqualTo(String value) {
            addCriterion("DMB_COLOR_MODEL_UID <>", value, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidGreaterThan(String value) {
            addCriterion("DMB_COLOR_MODEL_UID >", value, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidGreaterThanOrEqualTo(String value) {
            addCriterion("DMB_COLOR_MODEL_UID >=", value, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidLessThan(String value) {
            addCriterion("DMB_COLOR_MODEL_UID <", value, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidLessThanOrEqualTo(String value) {
            addCriterion("DMB_COLOR_MODEL_UID <=", value, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidLike(String value) {
            addCriterion("DMB_COLOR_MODEL_UID like", value, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidNotLike(String value) {
            addCriterion("DMB_COLOR_MODEL_UID not like", value, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidIn(List<String> values) {
            addCriterion("DMB_COLOR_MODEL_UID in", values, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidNotIn(List<String> values) {
            addCriterion("DMB_COLOR_MODEL_UID not in", values, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidBetween(String value1, String value2) {
            addCriterion("DMB_COLOR_MODEL_UID between", value1, value2, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbColorModelUidNotBetween(String value1, String value2) {
            addCriterion("DMB_COLOR_MODEL_UID not between", value1, value2, "dmbColorModelUid");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorIsNull() {
            addCriterion("DMB_CREATOR is null");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorIsNotNull() {
            addCriterion("DMB_CREATOR is not null");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorEqualTo(Object value) {
            addCriterion("DMB_CREATOR =", value, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorNotEqualTo(Object value) {
            addCriterion("DMB_CREATOR <>", value, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorGreaterThan(Object value) {
            addCriterion("DMB_CREATOR >", value, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorGreaterThanOrEqualTo(Object value) {
            addCriterion("DMB_CREATOR >=", value, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorLessThan(Object value) {
            addCriterion("DMB_CREATOR <", value, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorLessThanOrEqualTo(Object value) {
            addCriterion("DMB_CREATOR <=", value, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorIn(List<Object> values) {
            addCriterion("DMB_CREATOR in", values, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorNotIn(List<Object> values) {
            addCriterion("DMB_CREATOR not in", values, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorBetween(Object value1, Object value2) {
            addCriterion("DMB_CREATOR between", value1, value2, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreatorNotBetween(Object value1, Object value2) {
            addCriterion("DMB_CREATOR not between", value1, value2, "dmbCreator");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateIsNull() {
            addCriterion("DMB_CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateIsNotNull() {
            addCriterion("DMB_CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateEqualTo(Date value) {
            addCriterion("DMB_CREATE_DATE =", value, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateNotEqualTo(Date value) {
            addCriterion("DMB_CREATE_DATE <>", value, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateGreaterThan(Date value) {
            addCriterion("DMB_CREATE_DATE >", value, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("DMB_CREATE_DATE >=", value, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateLessThan(Date value) {
            addCriterion("DMB_CREATE_DATE <", value, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("DMB_CREATE_DATE <=", value, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateIn(List<Date> values) {
            addCriterion("DMB_CREATE_DATE in", values, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateNotIn(List<Date> values) {
            addCriterion("DMB_CREATE_DATE not in", values, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateBetween(Date value1, Date value2) {
            addCriterion("DMB_CREATE_DATE between", value1, value2, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("DMB_CREATE_DATE not between", value1, value2, "dmbCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterIsNull() {
            addCriterion("DMB_UPDATER is null");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterIsNotNull() {
            addCriterion("DMB_UPDATER is not null");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterEqualTo(Object value) {
            addCriterion("DMB_UPDATER =", value, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterNotEqualTo(Object value) {
            addCriterion("DMB_UPDATER <>", value, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterGreaterThan(Object value) {
            addCriterion("DMB_UPDATER >", value, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterGreaterThanOrEqualTo(Object value) {
            addCriterion("DMB_UPDATER >=", value, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterLessThan(Object value) {
            addCriterion("DMB_UPDATER <", value, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterLessThanOrEqualTo(Object value) {
            addCriterion("DMB_UPDATER <=", value, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterIn(List<Object> values) {
            addCriterion("DMB_UPDATER in", values, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterNotIn(List<Object> values) {
            addCriterion("DMB_UPDATER not in", values, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterBetween(Object value1, Object value2) {
            addCriterion("DMB_UPDATER between", value1, value2, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdaterNotBetween(Object value1, Object value2) {
            addCriterion("DMB_UPDATER not between", value1, value2, "dmbUpdater");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateIsNull() {
            addCriterion("DMB_UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateIsNotNull() {
            addCriterion("DMB_UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateEqualTo(Date value) {
            addCriterion("DMB_UPDATE_DATE =", value, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateNotEqualTo(Date value) {
            addCriterion("DMB_UPDATE_DATE <>", value, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateGreaterThan(Date value) {
            addCriterion("DMB_UPDATE_DATE >", value, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("DMB_UPDATE_DATE >=", value, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateLessThan(Date value) {
            addCriterion("DMB_UPDATE_DATE <", value, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("DMB_UPDATE_DATE <=", value, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateIn(List<Date> values) {
            addCriterion("DMB_UPDATE_DATE in", values, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateNotIn(List<Date> values) {
            addCriterion("DMB_UPDATE_DATE not in", values, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateBetween(Date value1, Date value2) {
            addCriterion("DMB_UPDATE_DATE between", value1, value2, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("DMB_UPDATE_DATE not between", value1, value2, "dmbUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1IsNull() {
            addCriterion("DMB_RESERVED_1 is null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1IsNotNull() {
            addCriterion("DMB_RESERVED_1 is not null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1EqualTo(String value) {
            addCriterion("DMB_RESERVED_1 =", value, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1NotEqualTo(String value) {
            addCriterion("DMB_RESERVED_1 <>", value, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1GreaterThan(String value) {
            addCriterion("DMB_RESERVED_1 >", value, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1GreaterThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_1 >=", value, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1LessThan(String value) {
            addCriterion("DMB_RESERVED_1 <", value, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1LessThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_1 <=", value, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1Like(String value) {
            addCriterion("DMB_RESERVED_1 like", value, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1NotLike(String value) {
            addCriterion("DMB_RESERVED_1 not like", value, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1In(List<String> values) {
            addCriterion("DMB_RESERVED_1 in", values, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1NotIn(List<String> values) {
            addCriterion("DMB_RESERVED_1 not in", values, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1Between(String value1, String value2) {
            addCriterion("DMB_RESERVED_1 between", value1, value2, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved1NotBetween(String value1, String value2) {
            addCriterion("DMB_RESERVED_1 not between", value1, value2, "dmbReserved1");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2IsNull() {
            addCriterion("DMB_RESERVED_2 is null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2IsNotNull() {
            addCriterion("DMB_RESERVED_2 is not null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2EqualTo(String value) {
            addCriterion("DMB_RESERVED_2 =", value, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2NotEqualTo(String value) {
            addCriterion("DMB_RESERVED_2 <>", value, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2GreaterThan(String value) {
            addCriterion("DMB_RESERVED_2 >", value, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2GreaterThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_2 >=", value, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2LessThan(String value) {
            addCriterion("DMB_RESERVED_2 <", value, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2LessThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_2 <=", value, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2Like(String value) {
            addCriterion("DMB_RESERVED_2 like", value, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2NotLike(String value) {
            addCriterion("DMB_RESERVED_2 not like", value, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2In(List<String> values) {
            addCriterion("DMB_RESERVED_2 in", values, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2NotIn(List<String> values) {
            addCriterion("DMB_RESERVED_2 not in", values, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2Between(String value1, String value2) {
            addCriterion("DMB_RESERVED_2 between", value1, value2, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved2NotBetween(String value1, String value2) {
            addCriterion("DMB_RESERVED_2 not between", value1, value2, "dmbReserved2");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3IsNull() {
            addCriterion("DMB_RESERVED_3 is null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3IsNotNull() {
            addCriterion("DMB_RESERVED_3 is not null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3EqualTo(String value) {
            addCriterion("DMB_RESERVED_3 =", value, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3NotEqualTo(String value) {
            addCriterion("DMB_RESERVED_3 <>", value, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3GreaterThan(String value) {
            addCriterion("DMB_RESERVED_3 >", value, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3GreaterThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_3 >=", value, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3LessThan(String value) {
            addCriterion("DMB_RESERVED_3 <", value, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3LessThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_3 <=", value, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3Like(String value) {
            addCriterion("DMB_RESERVED_3 like", value, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3NotLike(String value) {
            addCriterion("DMB_RESERVED_3 not like", value, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3In(List<String> values) {
            addCriterion("DMB_RESERVED_3 in", values, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3NotIn(List<String> values) {
            addCriterion("DMB_RESERVED_3 not in", values, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3Between(String value1, String value2) {
            addCriterion("DMB_RESERVED_3 between", value1, value2, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved3NotBetween(String value1, String value2) {
            addCriterion("DMB_RESERVED_3 not between", value1, value2, "dmbReserved3");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4IsNull() {
            addCriterion("DMB_RESERVED_4 is null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4IsNotNull() {
            addCriterion("DMB_RESERVED_4 is not null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4EqualTo(String value) {
            addCriterion("DMB_RESERVED_4 =", value, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4NotEqualTo(String value) {
            addCriterion("DMB_RESERVED_4 <>", value, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4GreaterThan(String value) {
            addCriterion("DMB_RESERVED_4 >", value, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4GreaterThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_4 >=", value, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4LessThan(String value) {
            addCriterion("DMB_RESERVED_4 <", value, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4LessThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_4 <=", value, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4Like(String value) {
            addCriterion("DMB_RESERVED_4 like", value, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4NotLike(String value) {
            addCriterion("DMB_RESERVED_4 not like", value, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4In(List<String> values) {
            addCriterion("DMB_RESERVED_4 in", values, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4NotIn(List<String> values) {
            addCriterion("DMB_RESERVED_4 not in", values, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4Between(String value1, String value2) {
            addCriterion("DMB_RESERVED_4 between", value1, value2, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved4NotBetween(String value1, String value2) {
            addCriterion("DMB_RESERVED_4 not between", value1, value2, "dmbReserved4");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5IsNull() {
            addCriterion("DMB_RESERVED_5 is null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5IsNotNull() {
            addCriterion("DMB_RESERVED_5 is not null");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5EqualTo(String value) {
            addCriterion("DMB_RESERVED_5 =", value, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5NotEqualTo(String value) {
            addCriterion("DMB_RESERVED_5 <>", value, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5GreaterThan(String value) {
            addCriterion("DMB_RESERVED_5 >", value, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5GreaterThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_5 >=", value, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5LessThan(String value) {
            addCriterion("DMB_RESERVED_5 <", value, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5LessThanOrEqualTo(String value) {
            addCriterion("DMB_RESERVED_5 <=", value, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5Like(String value) {
            addCriterion("DMB_RESERVED_5 like", value, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5NotLike(String value) {
            addCriterion("DMB_RESERVED_5 not like", value, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5In(List<String> values) {
            addCriterion("DMB_RESERVED_5 in", values, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5NotIn(List<String> values) {
            addCriterion("DMB_RESERVED_5 not in", values, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5Between(String value1, String value2) {
            addCriterion("DMB_RESERVED_5 between", value1, value2, "dmbReserved5");
            return (Criteria) this;
        }

        public Criteria andDmbReserved5NotBetween(String value1, String value2) {
            addCriterion("DMB_RESERVED_5 not between", value1, value2, "dmbReserved5");
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