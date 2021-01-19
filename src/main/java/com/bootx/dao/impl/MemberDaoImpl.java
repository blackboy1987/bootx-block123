
package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.MemberDao;
import com.bootx.entity.Member;
import com.bootx.entity.MemberAttribute;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;


/**
 * Dao - 会员
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member, Long> implements MemberDao {

	@Override
	public Page<Member> findPage(Member.RankingType rankingType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		if (rankingType != null) {
			switch (rankingType) {
			case POINT:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("point")));
				break;
			case BALANCE:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("balance")));
				break;
			case AMOUNT:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("amount")));
				break;
			}
		}
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Long count(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public List<Member> search(String keyword, Set<Member> excludes, Integer count) {
		if (StringUtils.isEmpty(keyword)) {
			return Collections.emptyList();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("username"), "%" + keyword + "%")));
		if (CollectionUtils.isNotEmpty(excludes)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(root.in(excludes)));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, null, null);
	}

	@Override
	public void clearAttributeValue(MemberAttribute memberAttribute) {
		if (memberAttribute == null || memberAttribute.getType() == null || memberAttribute.getPropertyIndex() == null) {
			return;
		}

		String propertyName;
		switch (memberAttribute.getType()) {
		case TEXT:
		case SELECT:
		case CHECKBOX:
			propertyName = Member.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
			break;
		default:
			propertyName = String.valueOf(memberAttribute.getType());
			break;
		}
		String jpql = "update Member mem set mem." + propertyName + " = null";
		entityManager.createQuery(jpql).executeUpdate();
	}

	@Override
	public BigDecimal totalBalance() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("balance")));
		BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result : BigDecimal.ZERO;
	}

	@Override
	public BigDecimal frozenTotalAmount() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal>get("frozenAmount")));
		BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result : BigDecimal.ZERO;
	}

	@Override
	public Page<Member> findPage(Pageable pageable, String username, String name, Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(StringUtils.isNotBlank(username)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("username"), "%" + username + "%")));
		}
		if(StringUtils.isNotBlank(name)){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("name"), "%" + name + "%")));
		}
		if(beginDate!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createdDate"), beginDate)));
		}
		if(endDate!=null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.lessThanOrEqualTo(root.<Date>get("createdDate"), endDate)));
		}


		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery,pageable);
	}


	@Override
	public List<Member> findRoots(Integer count) {
		String jpql = "select member from Member member where member.parent is null order by member.createdDate asc";
		TypedQuery<Member> query = entityManager.createQuery(jpql, Member.class);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Member> findParents(Member member, boolean recursive, Integer count) {
		if (member == null || member.getParent() == null) {
			return Collections.emptyList();
		}
		TypedQuery<Member> query;
		if (recursive) {
			String jpql = "select member from Member member where member.id in (:ids) order by member.grade asc";
			query = entityManager.createQuery(jpql, Member.class).setParameter("ids", Arrays.asList(member.getParentIds()));
		} else {
			String jpql = "select member from Member member where member = :member";
			query = entityManager.createQuery(jpql, Member.class).setParameter("member", member.getParent());
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public List<Member> findChildren(Member member, boolean recursive, Integer count) {
		TypedQuery<Member> query;
		if (recursive) {
			if (member != null) {
				String jpql = "select member from Member member where member.treePath like :treePath order by member.grade asc, member.createdDate asc";
				query = entityManager.createQuery(jpql, Member.class).setParameter("treePath", "%" + Member.TREE_PATH_SEPARATOR + member.getId() + Member.TREE_PATH_SEPARATOR + "%");
			} else {
				String jpql = "select member from Member member order by member.grade asc, member.createdDate asc";
				query = entityManager.createQuery(jpql, Member.class);
			}
			if (count != null) {
				query.setMaxResults(count);
			}
			List<Member> result = query.getResultList();
			sort(result);
			return result;
		} else {
			String jpql = "select member from Member member where member.parent = :parent order by member.createdDate asc";
			query = entityManager.createQuery(jpql, Member.class).setParameter("parent", member);
			if (count != null) {
				query.setMaxResults(count);
			}
			return query.getResultList();
		}
	}

	/**
	 * 排序文章分类
	 *
	 * @param members
	 *            文章分类
	 */
	private void sort(List<Member> members) {
		if (CollectionUtils.isEmpty(members)) {
			return;
		}
		final Map<Long, Integer> orderMap = new HashMap<>();
		for (Member member : members) {
			orderMap.put(member.getId(), member.getGrade());
		}
		Collections.sort(members, new Comparator<Member>() {
			@Override
			public int compare(Member member1, Member member2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(member1.getParentIds(), member1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(member2.getParentIds(), member2.getId());
				Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
				Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
				CompareToBuilder compareToBuilder = new CompareToBuilder();
				while (iterator1.hasNext() && iterator2.hasNext()) {
					Long id1 = iterator1.next();
					Long id2 = iterator2.next();
					Integer order1 = orderMap.get(id1);
					Integer order2 = orderMap.get(id2);
					compareToBuilder.append(order1, order2).append(id1, id2);
					if (!iterator1.hasNext() || !iterator2.hasNext()) {
						compareToBuilder.append(member1.getGrade(), member2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}
}