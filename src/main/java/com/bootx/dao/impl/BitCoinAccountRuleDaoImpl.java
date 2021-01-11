
package com.bootx.dao.impl;

import com.bootx.dao.BitCoinAccountRuleDao;
import com.bootx.entity.BitCoinAccountRule;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class BitCoinAccountRuleDaoImpl extends BaseDaoImpl<BitCoinAccountRule, Long> implements BitCoinAccountRuleDao {

    @Override
    public BitCoinAccountRule findByAssetType(Integer assetType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BitCoinAccountRule> criteriaQuery = criteriaBuilder.createQuery(BitCoinAccountRule.class);
        Root<BitCoinAccountRule> root = criteriaQuery.from(BitCoinAccountRule.class);
        criteriaQuery.select(root);
        if (assetType == null) {
            return new BitCoinAccountRule();
        }
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("assetType"), assetType));
        criteriaQuery.where(restrictions);
        try {
            return super.findList(criteriaQuery, null, null, null, null).get(0);
        } catch (Exception e) {
            return new BitCoinAccountRule();
        }
    }
}