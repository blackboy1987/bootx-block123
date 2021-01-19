
package com.bootx.dao.impl;

import com.bootx.dao.BitCoinAccountWalletDao;
import com.bootx.entity.BitCoinAccountWallet;
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
public class BitCoinAccountWalletDaoImpl extends BaseDaoImpl<BitCoinAccountWallet, Long> implements BitCoinAccountWalletDao {

    @Override
    public BitCoinAccountWallet findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BitCoinAccountWallet> criteriaQuery = criteriaBuilder.createQuery(BitCoinAccountWallet.class);
        Root<BitCoinAccountWallet> root = criteriaQuery.from(BitCoinAccountWallet.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("bitCoinAccountId"), bitCoinAccountId));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        criteriaQuery.where(restrictions);
        try{
            return super.findList(criteriaQuery, null, null, null, null).get(0);
        }catch (Exception e){
            return null;
        }
    }
}