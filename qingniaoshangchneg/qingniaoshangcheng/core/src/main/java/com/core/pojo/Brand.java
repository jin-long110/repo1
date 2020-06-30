package com.core.pojo;

import com.core.common.SERVER_URL;

/**
 * 品牌pojo
 */
public class Brand {
	private Long id;
	private String name;	//品牌名称
	private String description;	//品牌描述
	private String url;		//品牌的官方url
	private String logo;	//品牌的图标
	private Integer status; // 1在售 2停售 3删除

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getImgUrl() {
		return SERVER_URL.IMG_SERVER+logo;
	}
}