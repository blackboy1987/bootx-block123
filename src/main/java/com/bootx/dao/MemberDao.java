
package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.ArticleCategory;
import com.bootx.entity.Member;
import com.bootx.entity.MemberAttribute;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Dao - 会员
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 查找会员分页
	 * 
	 * @param rankingType
	 *            排名类型
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	Page<Member> findPage(Member.RankingType rankingType, Pageable pageable);

	/**
	 * 查询会员数量
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员数量
	 */
	Long count(Date beginDate, Date endDate);

	/**
	 * 通过名称查找会员
	 * 
	 * @param keyword
	 *            关键词
	 * @param excludes
	 *            排除会员
	 * @param count
	 *            数量
	 * @return 会员
	 */
	List<Member> search(String keyword, Set<Member> excludes, Integer count);

	/**
	 * 清除会员注册项值
	 * 
	 * @param memberAttribute
	 *            会员注册项
	 */
	void clearAttributeValue(MemberAttribute memberAttribute);

	/**
	 * 查询总余额
	 * 
	 * @return 总余额
	 */
	BigDecimal totalBalance();

	/**
	 * 查询冻结总额
	 * 
	 * @return 冻结总额
	 */
	BigDecimal frozenTotalAmount();

	Page<Member> findPage(Pageable pageable, String username, String name, Date beginDate, Date endDate);

	/**
	 * 查找顶级文章分类
	 *
	 * @param count
	 *            数量
	 * @return 顶级文章分类
	 */
	List<Member> findRoots(Integer count);

	/**
	 * 查找上级文章分类
	 *
	 * @param member
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级文章分类
	 */
	List<Member> findParents(Member member, boolean recursive, Integer count);

	/**
	 * 查找下级文章分类
	 *
	 * @param member
	 *            文章分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	List<Member> findChildren(Member member, boolean recursive, Integer count);


}