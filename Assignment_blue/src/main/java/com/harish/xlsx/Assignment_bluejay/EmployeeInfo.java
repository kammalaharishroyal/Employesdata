package com.harish.xlsx.Assignment_bluejay;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;


public class EmployeeInfo {
	 String PositionID;
	 String empName;
	 HashMap<LocalDate,LocalTime> worktimeMapping;
	@Override
	public String toString() {
		return "EmployeeInfo [PositionID=" + PositionID + ", worktimeMapping=" + worktimeMapping + "]";
	}
	public EmployeeInfo() {
		super();
		// TODO Auto-generated constructor stub
		worktimeMapping=new HashMap<>();
	}
	public EmployeeInfo(String positionID, String empName, HashMap<LocalDate, LocalTime> worktimeMapping) {
		super();
		PositionID = positionID;
		this.worktimeMapping = worktimeMapping;
		this.empName=empName;
	}
	
	 

}
