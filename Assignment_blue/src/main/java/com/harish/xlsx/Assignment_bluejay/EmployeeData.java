package com.harish.xlsx.Assignment_bluejay;

import java.time.LocalDate;
import java.time.LocalTime;

public class EmployeeData {
	 String PositionID;
	 String PositionStatus;
	 LocalDate workdate;
	 LocalDate workout;
	 LocalTime TimecardHours; 
	 LocalDate PayCycleStartDate;
	 LocalDate PayCycleEndDate;
	 String FileNumber;
	 String EmployerName;
//	 Date
	public EmployeeData() {
		super();
		// TODO Auto-generated constructor stub
	}
public EmployeeData(String positionID, String positionStatus, LocalDate workdate, LocalDate workout,LocalTime timecardHours,
		LocalDate payCycleStartDate, LocalDate payCycleEndDate, String fileNumber,String EmployerName) {
	super();
	PositionID = positionID;
	PositionStatus = positionStatus;
	this.workdate = workdate;
	TimecardHours = timecardHours;
	PayCycleStartDate = payCycleStartDate;
	PayCycleEndDate = payCycleEndDate;
	FileNumber = fileNumber;
	this.EmployerName=EmployerName;
	this.workout=workout;
}
@Override
public String toString() {
	return "EmployeeData [PositionID=" + PositionID + ", PositionStatus=" + PositionStatus + ", workdate=" + workdate
			+ ", TimecardHours=" + TimecardHours + ", PayCycleStartDate=" + PayCycleStartDate + ", PayCycleEndDate="
			+ PayCycleEndDate + ", FileNumber=" + FileNumber + "]";
}
	

}
