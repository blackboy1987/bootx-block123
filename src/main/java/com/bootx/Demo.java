package com.bootx;

import com.bootx.util.JsonUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Demo {

    private static String web3jUrl="http://375p7o9204.qicp.vip";
    private static Web3j web3j = Web3j.build(new HttpService(web3jUrl));
    private static Admin admin = Admin.build(new HttpService(web3jUrl));
    private static Geth geth = Geth.build(new HttpService(web3jUrl));

    public static void main(String[] args) throws IOException {
        EthBlock send = geth.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
        System.out.println(send.getBlock().getGasLimit());

        EthGetBalance send1 = admin.ethGetBalance("0xd0aff0d64c2fa3f217671436812537e767fbfcd8",DefaultBlockParameterName.LATEST).send();
        System.out.println("before=================================================================="+JsonUtils.toJson(send1.getBalance()));
        String from ="0xc2c7df99f052d51040d5a86faf580ee715ed3446";
        EthGetTransactionCount transactionCount = geth.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
        BigInteger nonce;
        try{
            nonce = transactionCount.getTransactionCount();
        }catch (Exception e){
            e.printStackTrace();
            nonce = new BigInteger("0x0");
        }

        for (int i = 0; i < 10; i++) {
            a("0xc2c7df99f052d51040d5a86faf580ee715ed3446","0xd0aff0d64c2fa3f217671436812537e767fbfcd8",new BigDecimal(0.001),nonce);
            nonce = nonce.add(BigInteger.ONE);
        }
        EthGetBalance send2 = admin.ethGetBalance("0xd0aff0d64c2fa3f217671436812537e767fbfcd8",DefaultBlockParameterName.LATEST).send();
        System.out.println("after===================================================================="+JsonUtils.toJson(send2.getBalance()));

    }

    public static void a(String from , String to , BigDecimal amount,BigInteger nonce){
        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        try{
            PersonalUnlockAccount send2 = geth.personalUnlockAccount(from, "19971579891").send();
            System.out.println("send2:"+send2.accountUnlocked());
            PersonalUnlockAccount send1 = geth.personalUnlockAccount(to, "13052898812").send();
            System.out.println("send1:"+send1.accountUnlocked());
            EthGasPrice send = geth.ethGasPrice().send();
            BigInteger gasPrice = send.getGasPrice();
            System.out.println("gasPrice:"+gasPrice);
            BigInteger gasLimit = new BigInteger("8000000");

            Transaction transaction = Transaction.createEtherTransaction(from,nonce,gasPrice,gasLimit,to,value);
            EthSendTransaction ethSendTransaction = admin.personalSendTransaction(transaction, "19971579891").send();
            System.out.println("=================================================================================");
            System.out.println(nonce+"::::::"+JsonUtils.toJson(ethSendTransaction));
            System.out.println("=================================================================================");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
