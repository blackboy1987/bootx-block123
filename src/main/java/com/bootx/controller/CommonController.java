package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.util.DateUtils;
import com.bootx.util.UploadUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

    @Resource
    private MemberService memberService;

    @PostMapping("/upload")
    public Result upload(MultipartFile file, @CurrentUser Member member, HttpServletRequest request) throws IOException {
        if(member==null){
            member = memberService.getCurrent(request);
        }

        String date= DateUtils.formatDateToString(new Date(),"yyyy/MM/dd");
        File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "."+ FilenameUtils.getExtension(file.getOriginalFilename()));
        System.out.println(tempFile.getAbsolutePath());
        file.transferTo(tempFile);
        String destPath = (member!=null?member.getUsername():"") +"/" +date+"/"+ UUID.randomUUID() +"." + FilenameUtils.getExtension(file.getOriginalFilename());
        UploadUtils.upload(destPath, tempFile, file.getContentType());
        return Result.success(UploadUtils.getUrl(destPath));
    }

    @PostMapping("/upload1")
    public Result upload1(MultipartFile[] files, @CurrentUser Member member, HttpServletRequest request) throws IOException {
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files1 = mRequest.getFileNames();
            while (files1.hasNext()) {
                MultipartFile mFile = mRequest.getFile(files1.next());
                if (mFile != null) {
                    String fileName = UUID.randomUUID() + mFile.getOriginalFilename();
                    System.out.println(fileName);
                }
            }
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        for (String key:parameterMap.keySet()) {
            System.out.println(key+":"+parameterMap.get(key));
        }

        List<String> urls = new ArrayList<>();
        if(member==null){
            member = memberService.getCurrent(request);
        }

        for (MultipartFile file:files) {
            String date= DateUtils.formatDateToString(new Date(),"yyyy/MM/dd");
            File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "."+ FilenameUtils.getExtension(file.getOriginalFilename()));
            System.out.println(tempFile.getAbsolutePath());
            file.transferTo(tempFile);
            String destPath = (member!=null?member.getUsername():"") +"/" +date+"/"+ UUID.randomUUID() +"." + FilenameUtils.getExtension(file.getOriginalFilename());
            UploadUtils.upload(destPath, tempFile, file.getContentType());
            String url = UploadUtils.getUrl(destPath);
            urls.add(url);
        }


        return Result.success(urls);
    }
}
