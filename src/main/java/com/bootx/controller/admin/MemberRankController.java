
package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.common.Pageable;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.MemberRank;
import com.bootx.service.MemberRankService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Controller - 会员等级
 * 
 * @author 爱购 Team
 * @version 6.1
 */
@RestController("adminMemberRankController")
@RequestMapping("/block/admin/member_rank")
public class MemberRankController extends BaseController {

	@Resource
	private MemberRankService memberRankService;

	/**
	 * 检查消费金额是否唯一
	 */
	@GetMapping("/check_amount")
	public @ResponseBody boolean checkAmount(Long id, BigDecimal amount) {
		return amount != null && memberRankService.amountUnique(id, amount);
	}
	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Message save(MemberRank memberRank) {
		if (!isValid(memberRank)) {
			return Message.error("参数错误");
		}
		if (memberRank.getIsSpecial()) {
			memberRank.setAmount(null);
		} else if (memberRank.getAmount() == null || memberRankService.amountExists(memberRank.getAmount())) {
			return Message.error("参数错误");
		}
		memberRank.setMembers(null);
		memberRankService.save(memberRank);
		return Message.success("操作成功");
	}

	/**
	 * 编辑
	 */
	@GetMapping("/edit")
	@JsonView(BaseEntity.EditView.class)
	public MemberRank edit(Long id, ModelMap model) {
		return memberRankService.find(id);
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Message update(MemberRank memberRank, Long id) {
		if (!isValid(memberRank)) {
			return Message.error("参数错误");
		}
		MemberRank pMemberRank = memberRankService.find(id);
		if (pMemberRank == null) {
			return Message.error("参数错误");
		}
		if (pMemberRank.getIsDefault()) {
			memberRank.setIsDefault(true);
		}
		if (memberRank.getIsSpecial()) {
			memberRank.setAmount(null);
		} else if (memberRank.getAmount() == null || !memberRankService.amountUnique(id, memberRank.getAmount())) {
			return Message.error("参数错误");
		}
		memberRankService.update(memberRank, "members", "promotions");
		return Message.success("操作成功");
	}

	/**
	 * 列表
	 */
	@PostMapping("/page")
	@JsonView(BaseEntity.PageView.class)
	public Message list(Pageable pageable) {
		return Message.success(memberRankService.findPage(pageable));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				MemberRank memberRank = memberRankService.find(id);
				if (memberRank != null && memberRank.getMembers() != null && !memberRank.getMembers().isEmpty()) {
					return Message.error(memberRank.getName()+"下存在会员，禁止删除");
				}
			}
			long totalCount = memberRankService.count();
			if (ids.length >= totalCount) {
				return Message.error("禁止删除");
			}
			memberRankService.delete(ids);
		}
		return Message.success("操作成功");
	}

}