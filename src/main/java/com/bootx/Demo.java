package com.bootx;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Demo {

    private static String web3jUrl="http://112.30.128.218:8545";
    private static Web3j web3j = Web3j.build(new HttpService(web3jUrl));
    private static Admin admin = Admin.build(new HttpService(web3jUrl));
    private static Geth geth = Geth.build(new HttpService(web3jUrl));

    public static void main(String[] args) throws IOException {

        for (int i=0;i<1000;i++) {
            a("0xf2ec42841c163af914f701f24d5659a922df42eb","0x8a1ab2b1b6b653235e6ca38fc255f25b37b23b3f",new BigDecimal(0.002));
        }

    }

    public static void a(String from , String to , BigDecimal amount){
        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        try{
            geth.personalUnlockAccount(from,"19971579891").send();
            PersonalUnlockAccount send1 = geth.personalUnlockAccount(to, "13052898812").send();
            Boolean aBoolean = send1.accountUnlocked();
            EthGasPrice send = geth.ethGasPrice().send();
            BigInteger gasPrice = send.getGasPrice();
            BigInteger gasLimit = new BigInteger("2000000000");
            EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
            BigInteger nonce;
            try{
                nonce = transactionCount.getTransactionCount();
                System.out.println(nonce);
            }catch (Exception e){
                e.printStackTrace();
                nonce = new BigInteger("0x0");
            }
            Transaction transaction = Transaction.createEtherTransaction(from,nonce,gasPrice,gasLimit,to,value);
            EthSendTransaction ethSendTransaction = admin.personalSendTransaction(transaction, "19971579891").send();
            System.out.println(ethSendTransaction.getTransactionHash());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
