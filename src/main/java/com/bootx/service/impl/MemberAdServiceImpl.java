package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.MemberAdDao;
import com.bootx.entity.MemberAd;
import com.bootx.entity.MemberAdStatistics;
import com.bootx.service.MemberAdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MemberAdServiceImpl extends BaseServiceImpl<MemberAd,Long> implements MemberAdService {

    @Resource
    private MemberAdDao memberAdDao;

    @Override
    public MemberAd save(MemberAd memberAd) {
        memberAd.setRemainTimes(memberAd.getTimes());
        MemberAdStatistics memberAdStatistics = new MemberAdStatistics();
        memberAdStatistics.setMemberAd(memberAd);
        memberAdStatistics.setCommentsCount(0L);
        memberAdStatistics.setPraiseCount(0L);
        memberAdStatistics.setShareCount(0L);
        memberAdStatistics.setReadCount(0L);
        memberAd.setStatistics(memberAdStatistics);
        memberAd.setIsShow(true);
        return super.save(memberAd);
    }

    @Override
    public List<Map<String,Object>> findList1(boolean isShow, Integer count) {
        return memberAdDao.findList1(isShow,count);
    }

    @Override
    public Page<Map<String,Object>> findPage1(Pageable pageable, Boolean isShow) {
        return memberAdDao.findPage1(pageable,isShow);
    }
}
