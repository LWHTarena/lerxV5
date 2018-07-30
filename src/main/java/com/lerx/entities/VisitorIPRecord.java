package com.lerx.entities;

public class VisitorIPRecord {
	
	private long id;
	private String ip;
	private String ipfrom;
	private String visitUrl;
	private String reffer;
	private long visitDatetime;
	private VisitorsBook vbook;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIpfrom() {
		return ipfrom;
	}
	public void setIpfrom(String ipfrom) {
		this.ipfrom = ipfrom;
	}
	public String getVisitUrl() {
		return visitUrl;
	}
	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}
	public String getReffer() {
		return reffer;
	}
	public void setReffer(String reffer) {
		this.reffer = reffer;
	}
	public long getVisitDatetime() {
		return visitDatetime;
	}
	public void setVisitDatetime(long visitDatetime) {
		this.visitDatetime = visitDatetime;
	}
	public VisitorsBook getVbook() {
		return vbook;
	}
	public void setVbook(VisitorsBook vbook) {
		this.vbook = vbook;
	}

}
