
package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.MemberAd;

import java.util.List;
import java.util.Map;

/**
 * Dao - 文章
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MemberAdDao extends BaseDao<MemberAd, Long> {

    List<Map<String,Object>> findList1(boolean isShow, Integer count);

    Page<Map<String,Object>> findPage1(Pageable pageable, Boolean isShow);
}