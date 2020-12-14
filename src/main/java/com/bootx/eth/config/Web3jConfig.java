package com.bootx.eth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;

@Component
public class Web3jConfig {

    private String web3jUrl="http://localhost:8485";

    @Bean
    public Web3j web3j(){
        Web3j web3j = Web3j.build(new HttpService(web3jUrl));
        return web3j;
    }

    @Bean
    public Admin admin(){
        return Admin.build(new HttpService(web3jUrl));
    }
    @Bean
    public Geth geth(){
        return Geth.build(new HttpService(web3jUrl));
    }
}
