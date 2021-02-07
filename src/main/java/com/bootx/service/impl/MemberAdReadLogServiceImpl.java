package com.bootx.service.impl;

import com.bootx.entity.Member;
import com.bootx.entity.MemberAd;
import com.bootx.entity.MemberAdReadLog;
import com.bootx.service.MemberAdReadLogService;
import com.bootx.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MemberAdReadLogServiceImpl extends BaseServiceImpl<MemberAdReadLog,Long> implements MemberAdReadLogService {
    @Override
    public boolean create(Member member, MemberAd memberAd) {
        boolean flag = false;
        Date now = new Date();
        Long count = jdbcTemplate.queryForObject("select count(id) from memberadreadlog where member_id="+member.getId()+" and memberAd_id="+memberAd.getId()+" and createdDate>='"+ DateUtils.formatDateToString(now,"yyyy-MM-dd 00:00:00") +"' and createdDate<='"+DateUtils.formatDateToString(now,"yyyy-MM-dd 23:59:59")+"'",Long.class);
        if(count!=null&&count>0){
            flag = true;
        }
        MemberAdReadLog memberAdReadLog = new MemberAdReadLog();
        memberAdReadLog.setMember(member);
        memberAdReadLog.setMemberAd(memberAd);
        super.save(memberAdReadLog);
        return flag;
    }
}
