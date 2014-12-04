package com.qf.example_volley.entity;
/**
 * 继承Story_Parent
 * images解析出来是数组.
 * @author uaige
 *
 */
public class Story extends Story_Parent{
	private String images[];

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
}
