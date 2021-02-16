
package com.bootx.controller;

import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.service.MemberService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 首页
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {

	@Resource
	private Admin admin;

	@Resource
	private MemberService memberService;

	/**
	 * 首页
	 */
	@GetMapping
	public String index(ModelMap model) {
		return "shop/index";
	}

	@GetMapping("/balance")
	public @ResponseBody
	Map<String, BigInteger> balance() throws IOException {
		List<Member> members = memberService.findAll();
		Map<String, BigInteger> map = new HashMap<>();
		for (Member member: members) {
			EthGetBalance send2 = admin.ethGetBalance(member.getAccountId(), DefaultBlockParameterName.LATEST).send();
			map.put(member.getUsername(),send2.getBalance());
		}

		return map;
	}


}