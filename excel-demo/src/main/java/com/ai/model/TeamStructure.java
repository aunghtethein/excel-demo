package com.ai.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "team_structure")
public class TeamStructure {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int no;
	private String name;
	@Column(name = "staff_id")
	private String staffId;
	private String team;
	private String project;
	private String position;
	
	public TeamStructure() {}
	
	public TeamStructure(int no, String name, String staffId, String team, String project, String position) {
		super();
		this.no = no;
		this.name = name;
		this.staffId = staffId;
		this.team = team;
		this.project = project;
		this.position = position;
	}
	
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "TeamStructure [no=" + no + ", name=" + name + ", staffId=" + staffId + ", team=" + team + ", project="
				+ project + ", position=" + position + "]";
	}
	
	
	
}
