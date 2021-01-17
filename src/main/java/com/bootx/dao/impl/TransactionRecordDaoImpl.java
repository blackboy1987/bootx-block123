
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.TransactionRecordDao;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.entity.TransactionRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class TransactionRecordDaoImpl extends BaseDaoImpl<TransactionRecord, Long> implements TransactionRecordDao {

    @Override
    public Page<TransactionRecord> findPage(Member seller, Member buyer, Integer type, Integer status, Date beginDate, Date endDate, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransactionRecord> criteriaQuery = criteriaBuilder.createQuery(TransactionRecord.class);
        Root<TransactionRecord> root = criteriaQuery.from(TransactionRecord.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (seller != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("seller"), seller));
        }
        if (buyer != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("buyer"), buyer));
        }
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (status != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
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

}