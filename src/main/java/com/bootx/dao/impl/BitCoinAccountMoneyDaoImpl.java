
package com.bootx.dao.impl;

import com.bootx.dao.BitCoinAccountMoneyDao;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountMoney;
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
public class BitCoinAccountMoneyDaoImpl extends BaseDaoImpl<BitCoinAccountMoney, Long> implements BitCoinAccountMoneyDao {

    @Override
    public BitCoinAccountMoney findByBitCoinAccountIdAndUserId(BitCoinAccount bitCoinAccount, Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BitCoinAccountMoney> criteriaQuery = criteriaBuilder.createQuery(BitCoinAccountMoney.class);
        Root<BitCoinAccountMoney> root = criteriaQuery.from(BitCoinAccountMoney.class);
        criteriaQuery.select(root);
        if(bitCoinAccount==null|| userId==null){
            return new BitCoinAccountMoney();
        }
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("bitCoinAccountId"), bitCoinAccount.getId()));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        criteriaQuery.where(restrictions);
        try{
            return super.findList(criteriaQuery, null, null, null, null).get(0);
        }catch (Exception e){
            return new BitCoinAccountMoney(userId,bitCoinAccount);
        }
    }
}