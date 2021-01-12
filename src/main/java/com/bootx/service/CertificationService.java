
package com.bootx.service;

import com.bootx.entity.Certification;
import com.bootx.entity.Member;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface CertificationService extends BaseService<Certification, Long> {

    Certification findByUserId(Long userId);

    void create(Member member, String zheng, String hand, String name, String card);
}