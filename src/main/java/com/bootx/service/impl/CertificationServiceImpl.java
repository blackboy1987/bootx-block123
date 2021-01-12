
package com.bootx.service.impl;

import com.bootx.dao.CertificationDao;
import com.bootx.entity.Certification;
import com.bootx.entity.Member;
import com.bootx.service.CertificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class CertificationServiceImpl extends BaseServiceImpl<Certification, Long> implements CertificationService {

    @Resource
    public CertificationDao certificationDao;

    @Override
    public Certification findByUserId(Long userId) {
        return certificationDao.find("userId",userId);
    }

    @Override
    public void create(Member member, String zheng, String hand, String name, String card) {
        Certification certification = findByUserId(member.getId());
        if(certification==null){
            certification = new Certification();
        }
        certification.setCertPhoto(new ArrayList<>());
        certification.getCertPhoto().add(zheng);
        certification.getCertPhoto().add(hand);
        certification.setCertNo(card);
        certification.setName(name);
        certification.setState(0);
        certification.setUserId(member.getId());
        if(certification.isNew()){
            super.save(certification);
        }else {
            super.update(certification);
        }
    }
}