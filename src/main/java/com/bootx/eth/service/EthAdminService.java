package com.bootx.eth.service;

import com.bootx.entity.Member;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalListAccounts;
import org.web3j.protocol.core.methods.response.EthAccounts;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface EthAdminService {

    PersonalListAccounts personalListAccounts() throws IOException;

    NewAccountIdentifier newAccountIdentifier(String password);

    EthAccounts ethAccounts() throws IOException;

    String ethGetBalance(String address);

    String transferEther(Member from, Member to, BigDecimal amount);


    BigInteger gasLimit() throws IOException;

    BigInteger getNonce(String address) throws IOException;
}
