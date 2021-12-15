package com.icia.memboardfinal.service;

import com.icia.memboardfinal.dao.MDAO;
import com.icia.memboardfinal.dto.MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MService {

    @Autowired
    private MDAO dao;

    @Autowired
    private HttpSession session;

    @Autowired
    private PasswordEncoder pwEnc;

    ModelAndView mav = new ModelAndView();


    public ModelAndView mJoin(MEMBER member) throws IOException {


        // 암호화
        member.setMPw(pwEnc.encode(member.getMPw()));

        // 주소 합치기
        member.setMAddr("("+member.getAddr1()+") " + member.getAddr2() + ", " + member.getAddr3());

        // 1. 파일 불러오기
        MultipartFile mProfile = member.getMProfile();

        // 2. 파일 이름 설정
        String originalFilename = mProfile.getOriginalFilename();

        // 3. 난수 설정
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 난수와 파일이름 합치기
        String mProfileName = uuid + "_" + originalFilename;

        // 5. 파일 저장 위치
        String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/MemBoardFinal/src/main/resources/static/profile/"+mProfileName;

        // 6. 파일 선택여부 확인
        if(!mProfile.isEmpty()) {
            member.setMProfileName(mProfileName);
            mProfile.transferTo(new File(savePath));
        }else {
            member.setMProfileName("default.png");
        }

        int result = dao.mJoin(member);

        if(result > 0) {
            mav.setViewName("index");
        }else {
            mav.setViewName("joinForm");
        }

        return mav;
    }

    public ModelAndView mLogin(MEMBER member) {

        MEMBER encPw = dao.mView(member.getMId());

        if(pwEnc.matches(member.getMPw(), encPw.getMPw())){
            session.setAttribute("loginId", encPw.getMId());
            session.setAttribute("loginProfile", encPw.getMProfileName());
            mav.setViewName("index");
        } else{
            mav.setViewName("loginForm");
        }
        return mav;
    }

    public ModelAndView mList() {
        List<MEMBER> mList = dao.mList();

        if(mList!=null){
            mav.addObject("memberList",mList);
            mav.setViewName("mlist");
        }else{
            mav.setViewName("index");
        }
        return mav;
    }

    // 회원 상세보기 메소드
    public ModelAndView mView(String mId) {
        MEMBER member = dao.mView(mId);

        if(member!=null){
            mav.setViewName("mview");
            mav.addObject("member",member);
        } else{
            mav.setViewName("redirect:/mList");
        }
        return mav;
    }

    // 회원 수정 페이지 이동 메소드
    public ModelAndView mModiForm(String mId) {
        MEMBER member = dao.mView(mId);

        if(member!=null){
            mav.setViewName("mmodify");
            mav.addObject("member",member);
        } else{
            mav.setViewName("redirect:/mList");
        }
        return mav;
    }

    // 회원 수정 메소드
    public ModelAndView mModify(MEMBER member) throws IOException {
        // 1. 파일 불러오기
        MultipartFile mProfile = member.getMProfile();

        // 2. 파일 이름 설정
        String originalFilename = mProfile.getOriginalFilename();

        // 3. 난수 설정
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 난수와 파일이름 합치기
        String mProfileName = uuid + "_" + originalFilename;

        // 5. 파일 저장 위치
        String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/MemBoardFinal/src/main/resources/static/profile/"+mProfileName;

        // 6. 파일 선택여부 확인
        if(!mProfile.isEmpty()) {
            member.setMProfileName(mProfileName);
            mProfile.transferTo(new File(savePath));
        }else {
            member.setMProfileName("default.png");
        }
        int result = dao.mModify(member);

        if(result > 0) {
            mav.setViewName("redirect:/mView?mId="+member.getMId());
        } else {
            mav.setViewName("redirect:/mModiForm?mId="+member.getMId());
        }

        return mav;
    }

    // 회원삭제 메소드
    public ModelAndView mDelete(String mId) {
        int result = dao.mDelete(mId);

        if(result > 0 ) {
            mav.setViewName("redirect:/mList");
        } else {
            mav.setViewName("index");
        }

        return mav;
    }

    // 아이디 중복체크
   public String idOverlap(String mId) {
        String idCheck = dao.idOverlap(mId);
        String result = null;

        if(idCheck==null) {
            result="OK";
        } else {
            result = "NO";
        }
        return result;
    }
}
