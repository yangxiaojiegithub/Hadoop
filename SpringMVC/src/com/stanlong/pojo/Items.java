package com.stanlong.pojo;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.stanlong.validate.ValidateGroup1;

public class Items {
	private Integer id;

	// 校验名称在1-10个字符之间
	// message 是提示校验出错显示的信息
	// 在校验规则中添加分组,  groups 可定义多个分组
	@Size(min = 1, max = 10, message = "{items.name.length.error}", groups={ValidateGroup1.class})
	private String name;

	private Float price;

	private String pic;

	// 非空校验
	@NotNull(message = "{items.createtime.isNULL}")
	private Date createtime;

	private String detail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic == null ? null : pic.trim();
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail == null ? null : detail.trim();
	}
}