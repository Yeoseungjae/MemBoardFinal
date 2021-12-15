package com.icia.memboardfinal.controller;

import com.icia.memboardfinal.dto.BOARD;
import com.icia.memboardfinal.dto.Comment;
import com.icia.memboardfinal.service.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class BController {
    @Autowired
    private BService bsvc;

    private ModelAndView mav = new ModelAndView();

    // writeForm : 글쓰기 페이지로 이동
    @RequestMapping(value = "/writeForm", method = RequestMethod.GET)
    public String writeForm() {
        return "write";
    }

    // bWrite : 글작성
    @RequestMapping(value = "/bWrite", method = RequestMethod.POST)
    public ModelAndView bWrite(@ModelAttribute BOARD board) throws IOException {
        mav = bsvc.bWrite(board);
        return mav;
    }

    // bList : 글 목록보기
    @RequestMapping(value = "/bList", method = RequestMethod.GET)
    public ModelAndView bList() {
        mav = bsvc.bList();
        return mav;
    }

    // bView : 게시글 상세보기
    @RequestMapping(value = "/bView", method = RequestMethod.GET)
    public ModelAndView bView(@RequestParam("bNo") int bNo) {
        mav = bsvc.bView(bNo);
        return mav;
    }

    // bModiForm : 수정 페이지로 이동
    @RequestMapping(value = "/bModiForm", method = RequestMethod.GET)
    public ModelAndView bModiForm(@RequestParam("bNo") int bNo) {
        mav = bsvc.bModiForm(bNo);
        return mav;
    }

    // bModify : 게시글 수정
    @RequestMapping(value = "/bModify", method = RequestMethod.POST)
    public ModelAndView bModify(@ModelAttribute BOARD board) throws IOException {
        mav = bsvc.bModify(board);
        return mav;
    }

    // bDelete : 게시글 삭제
    @RequestMapping(value = "/bDelete", method = RequestMethod.GET)
    public ModelAndView bDelete(@RequestParam("bNo") int bNo) {
        mav = bsvc.bDelete(bNo);
        return mav;
    }
    // B_search : 게시글 검색
    @RequestMapping(value = "B_search", method=RequestMethod.GET)
    public ModelAndView bSearch(@RequestParam("selectVal") String selectVal, @RequestParam("keyword") String keyword) {
        mav = bsvc.bSearch(selectVal, keyword);
        return mav;
    }

    // C_list : 댓글 리스트 불러오기

    @RequestMapping(value = "C_list", method=RequestMethod.POST)
    public @ResponseBody List<Comment> cList(@RequestParam("CBNo") int CBNo){
        List<Comment> commentList = bsvc.cList(CBNo);
        return commentList;
    }

    // C_write : 댓글 작성
    @RequestMapping(value = "C_write", method=RequestMethod.POST)
    public @ResponseBody List<Comment> cWrite(@ModelAttribute Comment comment){
        List<Comment> commentList = bsvc.cWrite(comment);
        return commentList;
    }

    // C_delete : 댓글 삭제
    @RequestMapping(value = "C_delete", method=RequestMethod.GET)
    public @ResponseBody List<Comment> cDelete(@ModelAttribute Comment comment){
        List<Comment> commentList = bsvc.cDelete(comment);
        return commentList;
    }
}
