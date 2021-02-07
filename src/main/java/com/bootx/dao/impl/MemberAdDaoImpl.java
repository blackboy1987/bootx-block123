package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.MemberAdDao;
import com.bootx.entity.MemberAd;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MemberAdDaoImpl extends BaseDaoImpl<MemberAd,Long> implements MemberAdDao {

    @Override
    public List<Map<String,Object>> findList1(boolean isShow, Integer count) {
        if(count==null){
            count = 5;
        }
        return jdbcTemplate.queryForList("select memberad.id,content,price,member.username,memberrank.name from memberad,member,memberrank where memberrank.id=member.memberRank_id and member.id=memberad.member_id and isShow=true and remainTimes>0 order by price desc limit 0,"+count);
    }

    @Override
    public Page<Map<String,Object>> findPage1(Pageable pageable, Boolean isShow) {
        String sql1 = "select memberad.id,content,price,member.username,memberrank.name from memberad,member,memberrank where memberrank.id=member.memberRank_id and member.id=memberad.member_id and isShow=true and remainTimes>0 order by price desc limit "+(pageable.getPageNumber()-1)*pageable.getPageSize()+","+pageable.getPageSize();
        String sql2 = "select count(id) from memberad where isShow=true and remainTimes>0";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql1);
        Long count = jdbcTemplate.queryForObject(sql2,Long.class);

        return new Page<Map<String,Object>>(list,count,pageable);
    }
}
