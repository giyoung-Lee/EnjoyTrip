package com.ssafy.qna.model;

import java.util.List;

public class QnaBoardDto {
	private int article_no;
	private String user_id;
	private String subject;
	private String content;
	private String hit;
	private String register_time;
	private List<QnaMemoDto> memo_list;
	
	
	public int getArticle_no() {
		return article_no;
	}
	public void setArticle_no(int article_no) {
		this.article_no = article_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getRegister_time() {
		return register_time;
	}
	public void setRegister_time(String register_time) {
		this.register_time = register_time;
	}
	
	public List<QnaMemoDto> getMemo_list() {
		return memo_list;
	}
	public void setMemo_list(List<QnaMemoDto> memo_list) {
		this.memo_list = memo_list;
	}
	
	@Override
	public String toString() {
		return "QnaBoardDto [article_no=" + article_no + ", user_id=" + user_id + ", subject=" + subject + ", content="
				+ content + ", hit=" + hit + ", register_time=" + register_time + ", memo_list=" + memo_list + "]";
	}
	
	
	
}
