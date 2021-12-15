package com.icia.memboardfinal.service;

import com.icia.memboardfinal.dao.BDAO;
import com.icia.memboardfinal.dto.BOARD;
import com.icia.memboardfinal.dto.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BService {
    @Autowired
    private BDAO dao;

    private ModelAndView mav = new ModelAndView();

    // 글 작성 메소드
    public ModelAndView bWrite(BOARD board) throws IOException {
        // 1. 파일 불러오기
        MultipartFile bFile = board.getBFile();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = bFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String bFileName = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/MemBoardFinal/src/main/resources/static/upload/"+bFileName;

        // 6. 파일 선택여부
        if(!bFile.isEmpty()){
            board.setBFileName(bFileName);
            bFile.transferTo(new File(savePath));
        }

        int result = dao.bWrite(board);

        if(result > 0 ){
            mav.setViewName("redirect:/bList");
        } else{
            mav.setViewName("write");
        }
        return mav;
    }

    // 글 목록 조회 메소드
    public ModelAndView bList() {
        List<BOARD> boardList = dao.bList();

        mav.setViewName("blist");
        mav.addObject("boardList", boardList);

        return mav;
    }

    // 게시글 상세보기 메소드
    public ModelAndView bView(int bNo) {

        // (1) 조회수 증가
        dao.bCount(bNo);

        // (2) 게시글 조회
        BOARD board = dao.bView(bNo);
        if(board!=null){
            mav.addObject("board",board);
            mav.setViewName("bview");
        }else{
            mav.setViewName("redirect:/bList");
        }
        return mav;
    }

    // 게시글 수정페이지로 이동
    public ModelAndView bModiForm(int bNo) {
        BOARD board = dao.bView(bNo);

        if(board!=null){
            mav.setViewName("bmodify");
            mav.addObject("board",board);
        }else{
            mav.setViewName("redirect:/bList");
        }

        return mav;
    }

    // 게시글 수정
    public ModelAndView bModify(BOARD board) throws IOException {
        // 1. 파일 불러오기
        MultipartFile bFile = board.getBFile();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = bFile.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 4. 3번(난수)과 2번(원본파일이름) 합치기!
        String bFileName = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "C:/Users/82106/Desktop/승재/공부/오픈소스 강의/Intellij/Project/MemBoardFinal/src/main/resources/static/upload/"+bFileName;

        // 6. 파일 선택여부
        if(!bFile.isEmpty()){
            board.setBFileName(bFileName);
            bFile.transferTo(new File(savePath));
        }

        int result = dao.bModify(board);

        if(result > 0 ){
            mav.setViewName("redirect:/bView?bNo="+board.getBNo());
        } else{
            mav.setViewName("redirect:/bModiForm?bNo="+board.getBNo());
        }
        return mav;
    }

    // 게시글 삭제
    public ModelAndView bDelete(int bNo) {
        int result = dao.bDelete(bNo);

        if(result > 0){
            mav.setViewName("redirect:/bList");
        }else{
            mav.setViewName("redirect:/bView?bNo="+bNo);
        }

        return mav;
    }

    // 게시글 검색
    public ModelAndView bSearch(String selectVal, String keyword) {
        System.out.println("게시글 검색 : " + selectVal + keyword);
        List<BOARD> sList;

        if(selectVal.equals("BWriter")) {
            sList = dao.bsWriter(selectVal, keyword);
        } else if(selectVal.equals("BTitle")) {
            sList = dao.bsTitle(selectVal, keyword);
        } else {
            sList = dao.bsContent(selectVal, keyword);
        }
        System.out.println("게시글 검색 후 : " + sList);
        mav.addObject("sList", sList);
        mav.setViewName("BS_List");
        return mav;
    }

    // 게시글 댓글 조회
    public List<Comment> cList(int CBNo) {
        List<Comment> commentList = dao.cList(CBNo);
        return commentList;
    }

    // 게시판 댓글 작성
    public List<Comment> cWrite(Comment comment) {
        List<Comment> commentList = null;
        int result = dao.cWrite(comment);

        if(result>0) {
            commentList = dao.cList(comment.getCBNo());
        } else {
            commentList = null;
        }
        return commentList;
    }

    // 게시판 댓글 삭제
    public List<Comment> cDelete(Comment comment) {
        List<Comment> commentList = null;
        int result = dao.cDelete(comment);

        if(result>0) {
            commentList = dao.cList(comment.getCBNo());
        } else {
            commentList = null;
        }
        return commentList;
    }
}
