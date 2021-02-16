
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.InvestDao;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class InvestDaoImpl extends BaseDaoImpl<Invest, Long> implements InvestDao {

    @Override
    public List<Invest> findListByCoinType(Member member,Integer coinType) {
        if(member==null|| coinType==null){
            return Collections.emptyList();
        }
        String jpql = "select invest from Invest invest where invest.userId =:userId and invest.coinType =:coinType order by invest.createdDate desc";
        TypedQuery<Invest> query = entityManager.createQuery(jpql, Invest.class).setParameter("userId",member.getId()).setParameter("coinType",coinType);

        return query.getResultList();
    }

    @Override
    public Page<Invest> findPage(Pageable pageable, Member member, Integer excision, MineMachine mineMachine, Integer type, Integer coinType, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invest> criteriaQuery = criteriaBuilder.createQuery(Invest.class);
        Root<Invest> root = criteriaQuery.from(Invest.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), member.getId()));
        }
        if (excision != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("excision"), excision));
        }
        if (mineMachine != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productId"), mineMachine.getId()));
        }
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (coinType != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("coinType"), coinType));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    @Override
    public List<Invest> findList1(Member member, Integer excision, MineMachine mineMachine, Integer type, Integer coinType, Date beginDate, Date endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Invest> criteriaQuery = criteriaBuilder.createQuery(Invest.class);
        Root<Invest> root = criteriaQuery.from(Invest.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), member.getId()));
        }
        if (excision != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("excision"), excision));
        }
        if (mineMachine != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("productId"), mineMachine.getId()));
        }
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (coinType != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("coinType"), coinType));
        }
        if (beginDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), beginDate));
        }
        if (endDate != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), endDate));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }

    @Override
    public Map<String, Object> findOne(Integer type1) {
        try{
            Map<String, Object> map = jdbcTemplate.queryForMap("select id,userId,invest from invest where status=0 and type1=" + type1 + " order by createdDate desc limit 1");
            return map;
        }catch (Exception e){
            return null;
        }
    }
}