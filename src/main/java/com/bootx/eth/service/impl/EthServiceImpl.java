package com.bootx.eth.service.impl;

import com.bootx.eth.common.Result;
import com.bootx.eth.entity.CurrencyInfo;
import com.bootx.eth.entity.UserCurrencyInfoBean;
import com.bootx.eth.service.EthService;
import com.bootx.eth.util.ETHWallet;
import com.bootx.eth.util.ETHWalletUtils;
import com.bootx.eth.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.geth.Geth;
import org.web3j.utils.Convert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


@Service
public class EthServiceImpl implements EthService {

	@Value(value ="${md5Key}")
	protected String md5Key;

	@Autowired
	private Web3j web3j;

	@Resource
	private Geth geth;

	
	@Override
	public Result createWallet(String wPwd, String sign)  {

		//判断签名是否正确
		Map<String, Object> param = new TreeMap<>();
		param.put("wPwd", wPwd);
		//获取签名
		if( !sign.equals(SignUtil.createSignInfo(param,md5Key))) {
			// throw new BusinessException("签名错误");
		}
		
		ETHWallet wallet = ETHWalletUtils.generateMnemonic(wPwd);
		if( wallet == null ) {
			return Result.error("钱包创建失败");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("eAddress", wallet.getAddress());
		result.put("publicKey", wallet.getPublicKey());
		result.put("privateKey", wallet.getPrivateKey());
		result.put("keyStore", wallet.getKeyStore());
		result.put("words", wallet.getMnemonic());
		return Result.success(result);
	}



	@Override
	public Result loadWallet(String wWord, String wPwd, String sign)  {
		//判断签名是否正确
		Map<String, Object> param = new TreeMap<>();
		param.put("wPwd", wPwd);
		param.put("wWord", wWord);
		//获取签名
		if( !sign.equals(SignUtil.createSignInfo(param,md5Key))) {
			return Result.error("签名错误");
		}

		ETHWallet ethWallet;
		List<String> list = Arrays.asList(wWord.split(" "));
		ethWallet = ETHWalletUtils.importMnemonic(list, wPwd);
		Map<String, Object> result = new HashMap();
		result.put("eAddress", ethWallet.getAddress());
		result.put("publicKey", ethWallet.getPublicKey());
		result.put("privateKey", ethWallet.getPrivateKey());
		result.put("keyStore", ethWallet.getKeyStore());
		return Result.success(result);
	}

	@Override
	public Result getBalance(String eAddress, String sign)  {
		//判断签名是否正确
		Map<String, Object> param = new TreeMap<>();
		param.put("eAddress", eAddress);
		//获取签名
		if( !sign.equals(SignUtil.createSignInfo(param,md5Key))) {
			return Result.error("签名错误");
		}
		List<UserCurrencyInfoBean>  ucibs = new ArrayList<>();
		//eth 币
		BigDecimal bigDecimal = null;
		BigInteger bigInteger = null;
		try {
			bigInteger = web3j.ethGetBalance(eAddress, DefaultBlockParameterName.LATEST).send().getBalance();
		}catch (Exception e){
			e.printStackTrace();
		}
		bigDecimal = new BigDecimal(bigInteger);
		String stringBalance = Convert.fromWei(bigDecimal, Convert.Unit.ETHER).toString();
		UserCurrencyInfoBean eth = new UserCurrencyInfoBean();
		eth.setBanance(new BigDecimal(stringBalance));
		eth.setCurrency("Ethereum".toLowerCase());

		ucibs.add(eth);

		//TODO 其他币种


		return Result.success(ucibs);
	}

	
	
	
	
	@Override
	public Result transfer(Map<String, Object> map, String sign)  {
		return Result.error("error");
	}

	@Override
	public Result getCurrencyInfo(Map<String, Object> map, String sign)  {

		if( !sign.equals(SignUtil.createSignInfo(map,md5Key))) {
			return Result.error("签名错误");
		}
		Map<String, Object> result = new HashMap<>();
		CurrencyInfo currencyInfo = new CurrencyInfo();
		if(  currencyInfo != null ) {
			result.put("name", currencyInfo.getName());
			result.put("symbol",currencyInfo.getSymbol());
			result.put("price_cny", currencyInfo.getPriceCny());
			result.put("logo_png",currencyInfo.getLogoPng());
		}else {
			result.put("name", null);
			result.put("symbol", null);
			result.put("price_cny", null);
			result.put("logo_png", null);
		}


		return Result.success(result);
	}

}
