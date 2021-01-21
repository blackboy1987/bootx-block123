package com.bootx.eth.service.impl;

import com.bootx.entity.Member;
import com.bootx.eth.service.EthAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.geth.Geth;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class EthAdminServiceImpl implements EthAdminService {

    @Autowired
    private Admin admin;

    @Autowired
    private Geth geth;

    @Autowired
    private Web3j web3j;


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
            System.out.println(address);
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
    @Async
    public String transferEther(Member from, Member to, BigDecimal amount) {
        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        System.out.println(value);
        System.out.println(ethGetBalance(from.getAccountId()));
        try{
            geth.personalUnlockAccount(from.getAccountId(),from.getUsername()).send();
            PersonalUnlockAccount send1 = geth.personalUnlockAccount(to.getAccountId(), to.getUsername()).send();
            Boolean aBoolean = send1.accountUnlocked();
            EthGasPrice send = geth.ethGasPrice().send();
            BigInteger gasPrice = send.getGasPrice();
            EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(from.getAccountId(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce;
            try{
                nonce = transactionCount.getTransactionCount();
            }catch (Exception e){
                e.printStackTrace();
                nonce = new BigInteger("0x0");
            }
            Transaction transaction = Transaction.createEtherTransaction(from.getAccountId(),nonce,gasPrice,gasPrice,to.getAccountId(),value);
            EthSendTransaction ethSendTransaction = admin.personalSendTransaction(transaction, from.getUsername()).send();
            System.out.println(ethSendTransaction.getTransactionHash());
            return ethSendTransaction.getTransactionHash();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            geth.personalLockAccount(from.getAccountId()).send();
            geth.personalLockAccount(to.getAccountId()).send();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "0.00";
    }

    protected EthTransaction getTransactionState(String hashString) {
        try {
            EthGetTransactionReceipt send = web3j.ethGetTransactionReceipt(hashString).send();
            EthTransaction ethTransaction = web3j.ethGetTransactionByHash(hashString).send();
            String jsonString = ethTransaction.getJsonrpc();
            System.out.println("json: " + jsonString);
            String valueString = Convert.fromWei(String.valueOf(Long.parseLong(ethTransaction.getResult().getValueRaw().replace("0x", ""), 16)), Convert.Unit.ETHER).toString();
            System.out.println("valueString : " + valueString);
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

           System.out.println(transactionResult.getValue());
           return transactionResult.getValue();
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }

}
