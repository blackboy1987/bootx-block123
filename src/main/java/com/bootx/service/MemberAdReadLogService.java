package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.MemberAd;
import com.bootx.entity.MemberAdReadLog;

public interface MemberAdReadLogService extends BaseService<MemberAdReadLog,Long> {
    boolean create(Member member, MemberAd memberAd);
}
