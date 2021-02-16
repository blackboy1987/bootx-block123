package com.bootx.eth.service.impl;

import com.bootx.entity.Member;
import com.bootx.eth.service.EthAdminService;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.geth.Geth;
import org.web3j.utils.Convert;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

@Service
public class EthAdminServiceImpl implements EthAdminService {

    /**
     * CacheManager
     */
    private static final CacheManager CACHE_MANAGER = CacheManager.create();

    @Autowired
    private Admin admin;

    @Autowired
    private Geth geth;

    @Autowired
    private Web3j web3j;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public PersonalListAccounts personalListAccounts() throws IOException {
        PersonalListAccounts personalListAccounts = admin.personalListAccounts().send();
        return personalListAccounts;
    }

    @Override
    public NewAccountIdentifier newAccountIdentifier(String password) {
        try {
            NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
            return newAccountIdentifier;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EthAccounts ethAccounts() throws IOException {
        EthAccounts ethAccounts = admin.ethAccounts().send();
        return ethAccounts;
    }
    @Override
    public String ethGetBalance(String address) {
        try{
            EthGetBalance ethGetBalance = admin.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            BigDecimal bigDecimal = new BigDecimal(ethGetBalance.getBalance());
            return Convert.fromWei(bigDecimal, Convert.Unit.ETHER).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "0.00";
    }

    /**
     * 0xa4affdf78c8eaf21296568d9ed33b32afb9599e5b5f8e4591bb57856199c958b
     * @param from
     * @param to
     * @param amount
     * @return
     */
    @Override
    public String transferEther(Member from, Member to, BigDecimal amount) {
        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        try{
            geth.personalUnlockAccount(from.getAccountId(),from.getMobile()).send();
            EthGasPrice send = geth.ethGasPrice().send();
            BigInteger gasPrice = send.getGasPrice();
            BigInteger gasLimit = gasLimit();
            BigInteger nonce = getNonce(from.getAccountId());
            Transaction transaction = Transaction.createEtherTransaction(from.getAccountId(),nonce,gasPrice,gasLimit,to.getAccountId(),value);
            EthSendTransaction ethSendTransaction = admin.personalSendTransaction(transaction, from.getMobile()).send();
            System.out.println("nonce:"+nonce+"==="+ethSendTransaction.getTransactionHash());
            setNonce(from.getAccountId(),nonce.add(BigInteger.ONE));
            String transactionHash = ethSendTransaction.getTransactionHash();
            return transactionHash;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    protected EthTransaction getTransactionState(String hashString) {
        try {
            EthGetTransactionReceipt send = web3j.ethGetTransactionReceipt(hashString).send();
            EthTransaction ethTransaction = web3j.ethGetTransactionByHash(hashString).send();
            String jsonString = ethTransaction.getJsonrpc();
            String valueString = Convert.fromWei(String.valueOf(Long.parseLong(ethTransaction.getResult().getValueRaw().replace("0x", ""), 16)), Convert.Unit.ETHER).toString();
            return ethTransaction ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    protected BigInteger validateTransaction(String tradeHash){
       try{
           EthTransaction ethTransaction = admin.ethGetTransactionByHash(tradeHash).send();

           org.web3j.protocol.core.methods.response.Transaction transactionResult = ethTransaction.getResult();
           return transactionResult.getValue();
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }

    @Override
    public BigInteger gasLimit() throws IOException {
        EthBlock send = geth.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
        return send.getBlock().getGasLimit();
    }

    @Override
    public BigInteger getNonce(String address) throws IOException {
        String nonce = stringRedisTemplate.opsForValue().get(address + ":nonce");
        EthGetTransactionCount transactionCount = geth.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
        if(StringUtils.isBlank(nonce)){
            try{
                BigInteger nonce1 = transactionCount.getTransactionCount();
                stringRedisTemplate.opsForValue().set(address + ":nonce",nonce1+"");
            }catch (Exception e){
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return new BigInteger(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(address + ":nonce")));
    }

    private BigInteger setNonce(String address,BigInteger nonce){
        stringRedisTemplate.opsForValue().set(address + ":nonce",nonce+"");
        return nonce;
    }

}
