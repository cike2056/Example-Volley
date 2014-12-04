package com.qf.example_volley.entity;

import java.util.List;
/**
 * 
 * @author uaige
 *
 */
public class News {
	private String date;
	private List<Story> stroies;
	private List<TopStory> topStories;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<Story> getStroies() {
		return stroies;
	}
	public void setStroies(List<Story> stroies) {
		this.stroies = stroies;
	}
	public List<TopStory> getTopStories() {
		return topStories;
	}
	public void setTopStories(List<TopStory> topStories) {
		this.topStories = topStories;
	}
	
}
