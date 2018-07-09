package com.linux.vo;

import java.io.Serializable;

public class Content implements Serializable{
	
	private String contentId;
	private String contName;
	private String detail;
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContName() {
		return contName;
	}
	public void setContName(String contName) {
		this.contName = contName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contName == null) ? 0 : contName.hashCode());
		result = prime * result + ((contentId == null) ? 0 : contentId.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Content other = (Content) obj;
		if (contName == null) {
			if (other.contName != null)
				return false;
		} else if (!contName.equals(other.contName))
			return false;
		if (contentId == null) {
			if (other.contentId != null)
				return false;
		} else if (!contentId.equals(other.contentId))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Content [contentId=" + contentId + ", contName=" + contName + ", detail=" + detail + "]";
	}
	
	

}
