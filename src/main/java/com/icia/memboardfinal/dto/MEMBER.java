package com.icia.memboardfinal.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("member")
public class MEMBER {
    private String mId;
    private String mPw;
    private String mName;
    private String mPhone;
    private String mAge;
    private String mEmail;
    private String mAddr;
    private MultipartFile mProfile;
    private String mProfileName;
    private String addr1;
    private String addr2;
    private String addr3;
}
