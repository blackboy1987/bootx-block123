
package com.bootx.dao.impl;

import com.bootx.dao.ReceiptAccountDao;
import com.bootx.entity.ReceiptAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class ReceiptAccountDaoImpl extends BaseDaoImpl<ReceiptAccount, Long> implements ReceiptAccountDao {

    @Override
    public List<ReceiptAccount> findList(Long userId, Integer type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReceiptAccount> criteriaQuery = criteriaBuilder.createQuery(ReceiptAccount.class);
        Root<ReceiptAccount> root = criteriaQuery.from(ReceiptAccount.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (userId == null) {
            return Collections.emptyList();
        }
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }

    @Override
    public List<ReceiptAccount> findListByUserId(Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ReceiptAccount> criteriaQuery = criteriaBuilder.createQuery(ReceiptAccount.class);
        Root<ReceiptAccount> root = criteriaQuery.from(ReceiptAccount.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (userId == null) {
            return Collections.emptyList();
        }
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery);
    }
}