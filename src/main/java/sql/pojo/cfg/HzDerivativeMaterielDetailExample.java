package sql.pojo.cfg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HzDerivativeMaterielDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HzDerivativeMaterielDetailExample() {
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

        public Criteria andDmdDmbIdIsNull() {
            addCriterion("DMD_DMB_ID is null");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdIsNotNull() {
            addCriterion("DMD_DMB_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdEqualTo(BigDecimal value) {
            addCriterion("DMD_DMB_ID =", value, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdNotEqualTo(BigDecimal value) {
            addCriterion("DMD_DMB_ID <>", value, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdGreaterThan(BigDecimal value) {
            addCriterion("DMD_DMB_ID >", value, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DMD_DMB_ID >=", value, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdLessThan(BigDecimal value) {
            addCriterion("DMD_DMB_ID <", value, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DMD_DMB_ID <=", value, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdIn(List<BigDecimal> values) {
            addCriterion("DMD_DMB_ID in", values, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdNotIn(List<BigDecimal> values) {
            addCriterion("DMD_DMB_ID not in", values, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DMD_DMB_ID between", value1, value2, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdDmbIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DMD_DMB_ID not between", value1, value2, "dmdDmbId");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidIsNull() {
            addCriterion("DMD_CFG0_UID is null");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidIsNotNull() {
            addCriterion("DMD_CFG0_UID is not null");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidEqualTo(String value) {
            addCriterion("DMD_CFG0_UID =", value, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidNotEqualTo(String value) {
            addCriterion("DMD_CFG0_UID <>", value, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidGreaterThan(String value) {
            addCriterion("DMD_CFG0_UID >", value, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidGreaterThanOrEqualTo(String value) {
            addCriterion("DMD_CFG0_UID >=", value, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidLessThan(String value) {
            addCriterion("DMD_CFG0_UID <", value, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidLessThanOrEqualTo(String value) {
            addCriterion("DMD_CFG0_UID <=", value, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidLike(String value) {
            addCriterion("DMD_CFG0_UID like", value, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidNotLike(String value) {
            addCriterion("DMD_CFG0_UID not like", value, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidIn(List<String> values) {
            addCriterion("DMD_CFG0_UID in", values, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidNotIn(List<String> values) {
            addCriterion("DMD_CFG0_UID not in", values, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidBetween(String value1, String value2) {
            addCriterion("DMD_CFG0_UID between", value1, value2, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0UidNotBetween(String value1, String value2) {
            addCriterion("DMD_CFG0_UID not between", value1, value2, "dmdCfg0Uid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidIsNull() {
            addCriterion("DMD_CFG0_FAMILY_UID is null");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidIsNotNull() {
            addCriterion("DMD_CFG0_FAMILY_UID is not null");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidEqualTo(String value) {
            addCriterion("DMD_CFG0_FAMILY_UID =", value, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidNotEqualTo(String value) {
            addCriterion("DMD_CFG0_FAMILY_UID <>", value, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidGreaterThan(String value) {
            addCriterion("DMD_CFG0_FAMILY_UID >", value, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidGreaterThanOrEqualTo(String value) {
            addCriterion("DMD_CFG0_FAMILY_UID >=", value, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidLessThan(String value) {
            addCriterion("DMD_CFG0_FAMILY_UID <", value, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidLessThanOrEqualTo(String value) {
            addCriterion("DMD_CFG0_FAMILY_UID <=", value, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidLike(String value) {
            addCriterion("DMD_CFG0_FAMILY_UID like", value, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidNotLike(String value) {
            addCriterion("DMD_CFG0_FAMILY_UID not like", value, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidIn(List<String> values) {
            addCriterion("DMD_CFG0_FAMILY_UID in", values, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidNotIn(List<String> values) {
            addCriterion("DMD_CFG0_FAMILY_UID not in", values, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidBetween(String value1, String value2) {
            addCriterion("DMD_CFG0_FAMILY_UID between", value1, value2, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCfg0FamilyUidNotBetween(String value1, String value2) {
            addCriterion("DMD_CFG0_FAMILY_UID not between", value1, value2, "dmdCfg0FamilyUid");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorIsNull() {
            addCriterion("DMD_CREATOR is null");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorIsNotNull() {
            addCriterion("DMD_CREATOR is not null");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorEqualTo(Object value) {
            addCriterion("DMD_CREATOR =", value, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorNotEqualTo(Object value) {
            addCriterion("DMD_CREATOR <>", value, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorGreaterThan(Object value) {
            addCriterion("DMD_CREATOR >", value, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorGreaterThanOrEqualTo(Object value) {
            addCriterion("DMD_CREATOR >=", value, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorLessThan(Object value) {
            addCriterion("DMD_CREATOR <", value, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorLessThanOrEqualTo(Object value) {
            addCriterion("DMD_CREATOR <=", value, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorIn(List<Object> values) {
            addCriterion("DMD_CREATOR in", values, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorNotIn(List<Object> values) {
            addCriterion("DMD_CREATOR not in", values, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorBetween(Object value1, Object value2) {
            addCriterion("DMD_CREATOR between", value1, value2, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreatorNotBetween(Object value1, Object value2) {
            addCriterion("DMD_CREATOR not between", value1, value2, "dmdCreator");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateIsNull() {
            addCriterion("DMD_CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateIsNotNull() {
            addCriterion("DMD_CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateEqualTo(Date value) {
            addCriterion("DMD_CREATE_DATE =", value, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateNotEqualTo(Date value) {
            addCriterion("DMD_CREATE_DATE <>", value, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateGreaterThan(Date value) {
            addCriterion("DMD_CREATE_DATE >", value, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("DMD_CREATE_DATE >=", value, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateLessThan(Date value) {
            addCriterion("DMD_CREATE_DATE <", value, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("DMD_CREATE_DATE <=", value, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateIn(List<Date> values) {
            addCriterion("DMD_CREATE_DATE in", values, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateNotIn(List<Date> values) {
            addCriterion("DMD_CREATE_DATE not in", values, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateBetween(Date value1, Date value2) {
            addCriterion("DMD_CREATE_DATE between", value1, value2, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("DMD_CREATE_DATE not between", value1, value2, "dmdCreateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterIsNull() {
            addCriterion("DMD_UPDATER is null");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterIsNotNull() {
            addCriterion("DMD_UPDATER is not null");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterEqualTo(Object value) {
            addCriterion("DMD_UPDATER =", value, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterNotEqualTo(Object value) {
            addCriterion("DMD_UPDATER <>", value, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterGreaterThan(Object value) {
            addCriterion("DMD_UPDATER >", value, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterGreaterThanOrEqualTo(Object value) {
            addCriterion("DMD_UPDATER >=", value, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterLessThan(Object value) {
            addCriterion("DMD_UPDATER <", value, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterLessThanOrEqualTo(Object value) {
            addCriterion("DMD_UPDATER <=", value, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterIn(List<Object> values) {
            addCriterion("DMD_UPDATER in", values, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterNotIn(List<Object> values) {
            addCriterion("DMD_UPDATER not in", values, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterBetween(Object value1, Object value2) {
            addCriterion("DMD_UPDATER between", value1, value2, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdaterNotBetween(Object value1, Object value2) {
            addCriterion("DMD_UPDATER not between", value1, value2, "dmdUpdater");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateIsNull() {
            addCriterion("DMD_UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateIsNotNull() {
            addCriterion("DMD_UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateEqualTo(Date value) {
            addCriterion("DMD_UPDATE_DATE =", value, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateNotEqualTo(Date value) {
            addCriterion("DMD_UPDATE_DATE <>", value, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateGreaterThan(Date value) {
            addCriterion("DMD_UPDATE_DATE >", value, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("DMD_UPDATE_DATE >=", value, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateLessThan(Date value) {
            addCriterion("DMD_UPDATE_DATE <", value, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("DMD_UPDATE_DATE <=", value, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateIn(List<Date> values) {
            addCriterion("DMD_UPDATE_DATE in", values, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateNotIn(List<Date> values) {
            addCriterion("DMD_UPDATE_DATE not in", values, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateBetween(Date value1, Date value2) {
            addCriterion("DMD_UPDATE_DATE between", value1, value2, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("DMD_UPDATE_DATE not between", value1, value2, "dmdUpdateDate");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1IsNull() {
            addCriterion("DMD_RESERVED_1 is null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1IsNotNull() {
            addCriterion("DMD_RESERVED_1 is not null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1EqualTo(String value) {
            addCriterion("DMD_RESERVED_1 =", value, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1NotEqualTo(String value) {
            addCriterion("DMD_RESERVED_1 <>", value, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1GreaterThan(String value) {
            addCriterion("DMD_RESERVED_1 >", value, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1GreaterThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_1 >=", value, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1LessThan(String value) {
            addCriterion("DMD_RESERVED_1 <", value, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1LessThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_1 <=", value, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1Like(String value) {
            addCriterion("DMD_RESERVED_1 like", value, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1NotLike(String value) {
            addCriterion("DMD_RESERVED_1 not like", value, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1In(List<String> values) {
            addCriterion("DMD_RESERVED_1 in", values, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1NotIn(List<String> values) {
            addCriterion("DMD_RESERVED_1 not in", values, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1Between(String value1, String value2) {
            addCriterion("DMD_RESERVED_1 between", value1, value2, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved1NotBetween(String value1, String value2) {
            addCriterion("DMD_RESERVED_1 not between", value1, value2, "dmdReserved1");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2IsNull() {
            addCriterion("DMD_RESERVED_2 is null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2IsNotNull() {
            addCriterion("DMD_RESERVED_2 is not null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2EqualTo(String value) {
            addCriterion("DMD_RESERVED_2 =", value, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2NotEqualTo(String value) {
            addCriterion("DMD_RESERVED_2 <>", value, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2GreaterThan(String value) {
            addCriterion("DMD_RESERVED_2 >", value, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2GreaterThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_2 >=", value, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2LessThan(String value) {
            addCriterion("DMD_RESERVED_2 <", value, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2LessThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_2 <=", value, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2Like(String value) {
            addCriterion("DMD_RESERVED_2 like", value, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2NotLike(String value) {
            addCriterion("DMD_RESERVED_2 not like", value, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2In(List<String> values) {
            addCriterion("DMD_RESERVED_2 in", values, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2NotIn(List<String> values) {
            addCriterion("DMD_RESERVED_2 not in", values, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2Between(String value1, String value2) {
            addCriterion("DMD_RESERVED_2 between", value1, value2, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved2NotBetween(String value1, String value2) {
            addCriterion("DMD_RESERVED_2 not between", value1, value2, "dmdReserved2");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3IsNull() {
            addCriterion("DMD_RESERVED_3 is null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3IsNotNull() {
            addCriterion("DMD_RESERVED_3 is not null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3EqualTo(String value) {
            addCriterion("DMD_RESERVED_3 =", value, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3NotEqualTo(String value) {
            addCriterion("DMD_RESERVED_3 <>", value, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3GreaterThan(String value) {
            addCriterion("DMD_RESERVED_3 >", value, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3GreaterThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_3 >=", value, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3LessThan(String value) {
            addCriterion("DMD_RESERVED_3 <", value, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3LessThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_3 <=", value, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3Like(String value) {
            addCriterion("DMD_RESERVED_3 like", value, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3NotLike(String value) {
            addCriterion("DMD_RESERVED_3 not like", value, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3In(List<String> values) {
            addCriterion("DMD_RESERVED_3 in", values, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3NotIn(List<String> values) {
            addCriterion("DMD_RESERVED_3 not in", values, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3Between(String value1, String value2) {
            addCriterion("DMD_RESERVED_3 between", value1, value2, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved3NotBetween(String value1, String value2) {
            addCriterion("DMD_RESERVED_3 not between", value1, value2, "dmdReserved3");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4IsNull() {
            addCriterion("DMD_RESERVED_4 is null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4IsNotNull() {
            addCriterion("DMD_RESERVED_4 is not null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4EqualTo(String value) {
            addCriterion("DMD_RESERVED_4 =", value, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4NotEqualTo(String value) {
            addCriterion("DMD_RESERVED_4 <>", value, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4GreaterThan(String value) {
            addCriterion("DMD_RESERVED_4 >", value, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4GreaterThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_4 >=", value, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4LessThan(String value) {
            addCriterion("DMD_RESERVED_4 <", value, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4LessThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_4 <=", value, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4Like(String value) {
            addCriterion("DMD_RESERVED_4 like", value, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4NotLike(String value) {
            addCriterion("DMD_RESERVED_4 not like", value, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4In(List<String> values) {
            addCriterion("DMD_RESERVED_4 in", values, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4NotIn(List<String> values) {
            addCriterion("DMD_RESERVED_4 not in", values, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4Between(String value1, String value2) {
            addCriterion("DMD_RESERVED_4 between", value1, value2, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved4NotBetween(String value1, String value2) {
            addCriterion("DMD_RESERVED_4 not between", value1, value2, "dmdReserved4");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5IsNull() {
            addCriterion("DMD_RESERVED_5 is null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5IsNotNull() {
            addCriterion("DMD_RESERVED_5 is not null");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5EqualTo(String value) {
            addCriterion("DMD_RESERVED_5 =", value, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5NotEqualTo(String value) {
            addCriterion("DMD_RESERVED_5 <>", value, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5GreaterThan(String value) {
            addCriterion("DMD_RESERVED_5 >", value, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5GreaterThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_5 >=", value, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5LessThan(String value) {
            addCriterion("DMD_RESERVED_5 <", value, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5LessThanOrEqualTo(String value) {
            addCriterion("DMD_RESERVED_5 <=", value, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5Like(String value) {
            addCriterion("DMD_RESERVED_5 like", value, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5NotLike(String value) {
            addCriterion("DMD_RESERVED_5 not like", value, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5In(List<String> values) {
            addCriterion("DMD_RESERVED_5 in", values, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5NotIn(List<String> values) {
            addCriterion("DMD_RESERVED_5 not in", values, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5Between(String value1, String value2) {
            addCriterion("DMD_RESERVED_5 between", value1, value2, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdReserved5NotBetween(String value1, String value2) {
            addCriterion("DMD_RESERVED_5 not between", value1, value2, "dmdReserved5");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueIsNull() {
            addCriterion("DMD_FEATURE_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueIsNotNull() {
            addCriterion("DMD_FEATURE_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueEqualTo(String value) {
            addCriterion("DMD_FEATURE_VALUE =", value, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueNotEqualTo(String value) {
            addCriterion("DMD_FEATURE_VALUE <>", value, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueGreaterThan(String value) {
            addCriterion("DMD_FEATURE_VALUE >", value, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueGreaterThanOrEqualTo(String value) {
            addCriterion("DMD_FEATURE_VALUE >=", value, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueLessThan(String value) {
            addCriterion("DMD_FEATURE_VALUE <", value, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueLessThanOrEqualTo(String value) {
            addCriterion("DMD_FEATURE_VALUE <=", value, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueLike(String value) {
            addCriterion("DMD_FEATURE_VALUE like", value, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueNotLike(String value) {
            addCriterion("DMD_FEATURE_VALUE not like", value, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueIn(List<String> values) {
            addCriterion("DMD_FEATURE_VALUE in", values, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueNotIn(List<String> values) {
            addCriterion("DMD_FEATURE_VALUE not in", values, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueBetween(String value1, String value2) {
            addCriterion("DMD_FEATURE_VALUE between", value1, value2, "dmdFeatureValue");
            return (Criteria) this;
        }

        public Criteria andDmdFeatureValueNotBetween(String value1, String value2) {
            addCriterion("DMD_FEATURE_VALUE not between", value1, value2, "dmdFeatureValue");
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