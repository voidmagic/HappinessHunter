package com.example.wangqian.happinesshunter.entity;
/**
 * 日记实体类
 */
public class Diary {
	private Integer id;
	private String title;
	private String content;
	private Integer happy;
	private String createtime;
	public Diary(String title, String content, String createtime, Integer happy) {
		super();
		this.title = title;
		this.content = content;
		this.createtime = createtime;
		this.happy = happy;
	}
	public Diary(Integer id, String title, String content, String createtime, Integer happy) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.createtime = createtime;
		this.happy = happy;
	}

	public Integer getHappy() {
		return happy;
	}

	public void setHappy(Integer happy) {
		this.happy = happy;
	}

	public Diary() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "Diary [id=" + id + ", title=" + title + ", content=" + content
				+ ", createtime=" + createtime + "]";
	}
	
	
}
