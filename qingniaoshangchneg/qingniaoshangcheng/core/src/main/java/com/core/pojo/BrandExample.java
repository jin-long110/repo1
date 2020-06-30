package com.core.pojo;

public class BrandExample {
	private Long id;
	private String name;
	private String description;
	private String url;
	private String logo;
	private Integer status;

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
	
	/**
	 * 分页查询
	 * 定义分页所需要的字段
	 * pageNo  当前页	(从页面传递过来的)
	 * pageSize 每页显示多少条记录(直接每页显示10条数据)
	 * startRow 起始页(通过算法算出来的)
	 */

	public Integer pageNo;
	public Integer pageSize=3;
	public Integer startRow;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		startRow = (pageNo-1) * pageSize;
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		startRow = (pageNo-1) * pageSize;
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
}