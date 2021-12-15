package com.icia.memboardfinal.dto;

import lombok.Data;

@Data
public class Comment {
    private int CNo;			// 댓글번호 (PK)
    private int CBNo;			// 게시글번호 (FK)
    private String CWriter;		// 작성자
    private String CContent;	// 댓글 내용
}
