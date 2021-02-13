package com.bootx.controller.api.app.user.v2;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("appUserV2RelationController")
@RequestMapping("/app/user/v2/relation")
public class RelationController extends BaseController {

    @Autowired
    private MemberService memberService;

    public String sql = "[{\"id\":151,\"posterName\":\"ipfd(不含价）\",\"posterUrl\":null,\"backUrl\":\"/1.jpg\",\"logoUrl\":null,\"qrcodeSize\":187,\"qrcodeX\":440,\"qrcodeY\":1216,\"backWidth\":800,\"backHeight\":1422,\"isEnable\":0},{\"id\":150,\"posterName\":\"APP首页\",\"posterUrl\":null,\"backUrl\":\"/2.jpg\",\"logoUrl\":null,\"qrcodeSize\":172,\"qrcodeX\":449,\"qrcodeY\":1241,\"backWidth\":800,\"backHeight\":1422,\"isEnable\":0},{\"id\":144,\"posterName\":\"425.挖\",\"posterUrl\":null,\"backUrl\":\"/3.jpg\",\"logoUrl\":null,\"qrcodeSize\":139,\"qrcodeX\":464,\"qrcodeY\":1236,\"backWidth\":800,\"backHeight\":1422,\"isEnable\":0},{\"id\":145,\"posterName\":\"425 闲\",\"posterUrl\":null,\"backUrl\":\"/4.jpg\",\"logoUrl\":null,\"qrcodeSize\":148,\"qrcodeX\":456,\"qrcodeY\":1231,\"backWidth\":800,\"backHeight\":1422,\"isEnable\":0},{\"id\":146,\"posterName\":\"425 别墅\",\"posterUrl\":null,\"backUrl\":\"/5.jpg\",\"logoUrl\":null,\"qrcodeSize\":136,\"qrcodeX\":464,\"qrcodeY\":1241,\"backWidth\":800,\"backHeight\":1422,\"isEnable\":0}]";


    @PostMapping("/extend_v2")
    public Result info(@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期，请重新登录");
        }
        if(member.getIsAuth()){
            Map<String,Object> data= new HashMap<>();
            data.put("state",2);
            data.put("invite","http://jlbreg.i-gomall.com/wap/invite/"+member.getExtendCode()+"/"+ DigestUtils.md5Hex(member.getExtendCode()+""));
            data.put("posters", JsonUtils.toObject(sql, new TypeReference<List<Poster>>() {
            }));
            return Result.success(data);
        }
        return Result.success(null);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Poster implements Serializable {

        private Long id;

        private String posterName;

        private String posterUrl;

        private String backUrl;

        private String logoUrl;

        private Integer qrcodeSize;

        private Integer qrcodeX;

        private Integer qrcodeY;

        private Integer backWidth;

        private Integer backHeight;

        private Integer isEnable;

        private Date createDate;

        private Date modifyDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPosterName() {
            return posterName;
        }

        public void setPosterName(String posterName) {
            this.posterName = posterName;
        }

        public String getPosterUrl() {
            return posterUrl;
        }

        public void setPosterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
        }

        public String getBackUrl() {
            return backUrl;
        }

        public void setBackUrl(String backUrl) {
            this.backUrl = backUrl;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public Integer getQrcodeSize() {
            return qrcodeSize;
        }

        public void setQrcodeSize(Integer qrcodeSize) {
            this.qrcodeSize = qrcodeSize;
        }

        public Integer getQrcodeX() {
            return qrcodeX;
        }

        public void setQrcodeX(Integer qrcodeX) {
            this.qrcodeX = qrcodeX;
        }

        public Integer getQrcodeY() {
            return qrcodeY;
        }

        public void setQrcodeY(Integer qrcodeY) {
            this.qrcodeY = qrcodeY;
        }

        public Integer getBackWidth() {
            return backWidth;
        }

        public void setBackWidth(Integer backWidth) {
            this.backWidth = backWidth;
        }

        public Integer getBackHeight() {
            return backHeight;
        }

        public void setBackHeight(Integer backHeight) {
            this.backHeight = backHeight;
        }

        public Integer getIsEnable() {
            return isEnable;
        }

        public void setIsEnable(Integer isEnable) {
            this.isEnable = isEnable;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public Date getModifyDate() {
            return modifyDate;
        }

        public void setModifyDate(Date modifyDate) {
            this.modifyDate = modifyDate;
        }
    }
}
