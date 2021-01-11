
package com.bootx.dao.impl;

import com.bootx.dao.BitCoinAccountDao;
import com.bootx.entity.BitCoinAccount;
import org.apache.commons.lang3.StringUtils;
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
public class BitCoinAccountDaoImpl extends BaseDaoImpl<BitCoinAccount, Long> implements BitCoinAccountDao {

    @Override
    public List<BitCoinAccount> findByUserId(Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BitCoinAccount> criteriaQuery = criteriaBuilder.createQuery(BitCoinAccount.class);
        Root<BitCoinAccount> root = criteriaQuery.from(BitCoinAccount.class);
        criteriaQuery.select(root);
        if(userId==null){
            return Collections.emptyList();
        }
        criteriaQuery.where(criteriaBuilder.equal(root.get("userId"), userId));
        return super.findList(criteriaQuery, null, null, null, null);
    }

    @Override
    public BitCoinAccount findByUserIdAndName(Long userId, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BitCoinAccount> criteriaQuery = criteriaBuilder.createQuery(BitCoinAccount.class);
        Root<BitCoinAccount> root = criteriaQuery.from(BitCoinAccount.class);
        criteriaQuery.select(root);
        if(userId==null|| StringUtils.isBlank(name)){
            return new BitCoinAccount();
        }
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("name"), name));
        criteriaQuery.where(restrictions);

        try{
            return super.findList(criteriaQuery, null, null, null, null).get(0);
        }catch (Exception e){
            return new BitCoinAccount();
        }
    }

    @Override
    public BitCoinAccount findByUserIdAndAssetType(Long userId, Integer assetType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BitCoinAccount> criteriaQuery = criteriaBuilder.createQuery(BitCoinAccount.class);
        Root<BitCoinAccount> root = criteriaQuery.from(BitCoinAccount.class);
        criteriaQuery.select(root);
        if(userId==null|| assetType==null){
            return new BitCoinAccount();
        }
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), userId));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("assetType"), assetType));
        criteriaQuery.where(restrictions);
        try{
            return super.findList(criteriaQuery, null, null, null, null).get(0);
        }catch (Exception e){
            return new BitCoinAccount();
        }
    }

    @Override
    public BitCoinAccount findByAddressIdAndAssetType(String addressId, Integer assetType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BitCoinAccount> criteriaQuery = criteriaBuilder.createQuery(BitCoinAccount.class);
        Root<BitCoinAccount> root = criteriaQuery.from(BitCoinAccount.class);
        criteriaQuery.select(root);
        if(StringUtils.isBlank(addressId)|| assetType==null){
            return null;
        }
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("addressId"), addressId));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("assetType"), assetType));
        criteriaQuery.where(restrictions);
        try{
            return super.findList(criteriaQuery, null, null, null, null).get(0);
        }catch (Exception e){
            return null;
        }
    }
}