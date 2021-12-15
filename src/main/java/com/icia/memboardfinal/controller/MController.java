package com.icia.memboardfinal.controller;

import com.icia.memboardfinal.dto.MEMBER;
import com.icia.memboardfinal.service.MService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MController {

    @Autowired
    private MService msvc;

    @Autowired
    private HttpSession session;

    @Autowired
    private JavaMailSender mailsender;

    ModelAndView mav = new ModelAndView();


    // 프로젝트 시작 시 실행되는 메인페이지
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index1() {
        return "index";
    }

    // joinForm : 회원 가입 페이지로 이동
    @RequestMapping(value = "/joinForm", method = RequestMethod.GET)
    public String joinForm() {
        return "joinForm";
    }

    // mJoin : 회원가입
    @RequestMapping(value = "/mJoin", method = RequestMethod.POST)
    public ModelAndView mJoin(@ModelAttribute MEMBER member) throws IOException {
        mav = msvc.mJoin(member);
        return mav;
    }

    // loginForm : 로그인 페이지로 이동
    @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    public String loginForm() {
        return "loginForm";
    }

    // mLogin : 로그인
    @RequestMapping(value = "/mLogin", method = RequestMethod.POST)
    public ModelAndView mLogin(@ModelAttribute MEMBER member){
        mav = msvc.mLogin(member);
        return mav;
    }
    // logout : 로그아웃
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        session.invalidate();
        return "index";
    }

    // mList : 회원목록
    @RequestMapping(value = "/mList", method = RequestMethod.GET)
    public ModelAndView mList(){
        mav = msvc.mList();
        return mav;
    }

    // mView : 회원상세보기
    @RequestMapping(value = "/mView", method = RequestMethod.GET)
    public ModelAndView mView(@RequestParam("mId") String mId){
        mav = msvc.mView(mId);
        return mav;
    }

    // mModiForm : 회원수정 페이지로 이동
    @RequestMapping(value = "/mModiForm", method = RequestMethod.GET)
    public ModelAndView mModiForm(@RequestParam("mId") String mId){
        mav = msvc.mModiForm(mId);
        return mav;
    }

    // mModify : 회원 수정
    @RequestMapping(value = "/mModify", method = RequestMethod.POST)
    public ModelAndView mModify(@ModelAttribute MEMBER member) throws IOException {
        mav = msvc.mModify(member);
        return mav;
    }

    // mDelete : 회원 삭제
    @RequestMapping(value = "/mDelete", method = RequestMethod.GET)
    public ModelAndView mDelete(@RequestParam("mId") String mId){
        mav = msvc.mDelete(mId);
        return mav;
    }

    // A_idoverlap : 아이디 중복 검사
    @RequestMapping(value = "/A_idoverlap", method=RequestMethod.POST)
    public @ResponseBody String idOverlap(@RequestParam("mId") String mId) {
        String result = msvc.idOverlap(mId);
        // 확인코드좀 작성해서 한번 볼게요
        System.out.println("ID값 " + mId);
        System.out.println("result :  " + result);
        return result;
    }

    // A_emailConfirm : 이메일 인증번호 발송
    @RequestMapping(value = "A_emailConfirm", method=RequestMethod.GET)
    public @ResponseBody String emailConfirm(@RequestParam("mEmail") String mEmail) {
        String uuid = UUID.randomUUID().toString().substring(1,7);

        String message = "<h2>안녕하세요. 인천일보 아카데미 입니다.</h2>"
                + "<br/><p>인증번호는 " + uuid + "입니다.</p>"
                + "<p>인증번호를 인증번호 입력란에 입력해주세요</p>"
                + "(혹시 잘못 전달된 메일이라면 이 이메일을 무시하셔도 됩니다.)";

        MimeMessage mail = mailsender.createMimeMessage();

        try {
            mail.setSubject("[본인인증] 인천일보 아카데미 인증메일입니다.✈✈✈✈✈✈✈✈✈✈✈✈✈✈✈");
            mail.setText(message, "UTF-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mailsender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return uuid;
    }

}
