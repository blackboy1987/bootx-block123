package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.MemberAd;

import java.util.List;
import java.util.Map;

public interface MemberAdService extends BaseService<MemberAd,Long> {

    List<Map<String,Object>> findList1(boolean b, Integer count);

    Page<Map<String,Object>> findPage1(Pageable pageable, Boolean isShow);
}
