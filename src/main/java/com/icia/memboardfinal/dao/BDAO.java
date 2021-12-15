package com.icia.memboardfinal.dao;

import com.icia.memboardfinal.dto.BOARD;
import com.icia.memboardfinal.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BDAO {
    int bWrite(BOARD board);

    List<BOARD> bList();

    void bCount(int bNo);

    BOARD bView(int bNo);

    int bModify(BOARD board);

    int bDelete(int bNo);

    List<BOARD> bsWriter(String selectVal, String keyword);

    List<BOARD> bsTitle(String selectVal, String keyword);

    List<BOARD> bsContent(String selectVal, String keyword);

    List<Comment> cList(int cbNo);

    int cWrite(Comment comment);

    int cDelete(Comment comment);
}
