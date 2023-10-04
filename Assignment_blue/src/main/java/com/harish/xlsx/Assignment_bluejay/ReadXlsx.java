package com.harish.xlsx.Assignment_bluejay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadXlsx {
		
		ReadXlsx(String FilePath_XLSX) {
//		#Reading XLSX File as input and Converting into EmployeeDatalist Object
		List<EmployeeData> employedatalist=readXLSXFile(FilePath_XLSX);
//		#Storing Each Employes data as on Object from EmployeeDataList
		HashMap<String,EmployeeInfo> map=DateStable(employedatalist);
		
		System.out.println("Name and PostionIds with 7 consecitive workdays");
//		#Calling SevenConsecutive Days function to show the Employee name and Id who worked 7 Conecutive days
		sevenConsecutiveDays(map);
		System.out.println("----------");
		System.out.println("Name and PostionIds with less Than 10 hours: and greater than 1hr");
//		#Calling lessThan10Hours  function to show the Employee name and Id who worked less than 10 and greater than 1Hour
		lessThan10Hours(map);
		System.out.println("----------");
		System.out.println("Name and PostionIds with more than 14 hours work");
//		#Calling morethan14hrs  function to show the Employee name and Id who worked more than 7 hours
		morethan14hrs(map);
		System.out.println("----------");

	}

	private static HashMap<String, EmployeeInfo> DateStable(List<EmployeeData> employedatalist) {
		HashMap<String, EmployeeInfo> map=new HashMap<>();
		
		for(EmployeeData empdat:employedatalist)
		{	
			if(map.containsKey(empdat.PositionID))
			{EmployeeInfo etemporary=map.get(empdat.PositionID);
			if(etemporary.worktimeMapping.containsKey(empdat.workdate)) {
				LocalTime t11=empdat.TimecardHours;
				LocalTime t22=etemporary.worktimeMapping.get(empdat.workdate);				
				LocalTime total33=t11.plusHours(t22.getHour()).plusMinutes(t22.getMinute());
				etemporary.worktimeMapping.put(empdat.workdate, total33);
			}
			else
			{
				etemporary.worktimeMapping.put(empdat.workdate,empdat.TimecardHours);
				map.put(empdat.PositionID, etemporary);
			}
			}
		else
		{EmployeeInfo einfo=new EmployeeInfo();
		einfo.PositionID=empdat.PositionID;
		einfo.empName=empdat.EmployerName;
		einfo.worktimeMapping.put(empdat.workdate,empdat.TimecardHours);
		map.put(empdat.PositionID, einfo);
	
		}
		}
		for(EmployeeData empdat:employedatalist)
		{
			if(empdat.workdate.plusDays(1).isEqual(empdat.workout)&&(map.get(empdat.PositionID).worktimeMapping.get(empdat.workdate))!=null&&map.get(empdat.PositionID).worktimeMapping.get(empdat.workout)!=null&&map.get(empdat.PositionID).worktimeMapping.get(empdat.workdate).getHour()<6)
			{	
				LocalTime lt1=map.get(empdat.PositionID).worktimeMapping.get(empdat.workout);
			
				LocalTime lt2=map.get(empdat.PositionID).worktimeMapping.get(empdat.workdate);

				map.get(empdat.PositionID).worktimeMapping.remove(empdat.workout);
				LocalTime lt3=lt1.plusHours(lt2.getHour()).plusMinutes(lt2.getMinute());
				map.get(empdat.PositionID).worktimeMapping.put(empdat.workdate, lt3);
			}
		}
		return map;
	}

	private static void morethan14hrs(HashMap<String, EmployeeInfo> map) {
		
		int i=0;
		for(String key:map.keySet())
		{	
			EmployeeInfo e=map.get(key);
			int t=0;
			for(LocalDate wdate:e.worktimeMapping.keySet())
			{
				if(e.worktimeMapping.get(wdate).getHour()>=14)
				{
					t++;	
				}
				
				
			}
			if(t>=1)
			{	
				System.out.println(i+"). "+e.empName+" , "+e.PositionID);
				i++;
			}
			
		}
		
	}

	private static void sevenConsecutiveDays(HashMap<String, EmployeeInfo> map) {
		for(String poid:map.keySet())
		{int ct=0,ii=0;
			EmployeeInfo etemp=map.get(poid);
			for(LocalDate ldt1:etemp.worktimeMapping.keySet())
			{int xt=0;
				for(int i=1;i<7;i++)
				{
					if((etemp.worktimeMapping).containsKey(ldt1.plusDays(i)))
					{
						xt++;
					}
					else
					{
						break;
					}
					
				}
				if(xt==6)
				{ct=1;
				break;
				}
			
			}
			if(ct==1)
			{
				System.out.println(ii+"). "+etemp.empName+" , "+etemp.PositionID);
				ii++;
			}
			
		}
		
	}

	private static void lessThan10Hours(HashMap<String, EmployeeInfo> map) {
		int i=0;
		for(String key:map.keySet())
		{
			EmployeeInfo e=map.get(key);
			int t=0;
			for(LocalDate wdate:e.worktimeMapping.keySet())
			{	
				if(e.worktimeMapping.get(wdate).getHour()>=10)
				{
					t=1;
					break;
				}
				
			}
			if(t==0)
			{	System.out.println(i+"). "+e.empName+" ,   "+e.PositionID);
			i++;
			}
			
		}
		
	}

	private static List<EmployeeData> readXLSXFile(String string) {
		List<EmployeeData> employedatalist =new ArrayList<>();
		try {
			XSSFWorkbook book=new XSSFWorkbook(new FileInputStream(string));
			XSSFSheet sheet=book.getSheet("Sheet1");
			
			int i=2;
			XSSFRow row=null;
			
			while((row=sheet.getRow(i))!=null)
			{	String PositionID=row.getCell(0).toString();
				String PositionStatus=row.getCell(1).toString();
				LocalDate workdate;
				LocalDate workout;
				 LocalTime TimecardHours; 
				String EmployerName=row.getCell(7).toString();
				LocalDate PayCycleStartDate;
				LocalDate PayCycleEndDate;
				String FileNumber=row.getCell(8).toString();
			    String stringworkdate=row.getCell(2).toString();
			    String stringworkout=row.getCell(3).toString();
			    String stringpcsd=row.getCell(5).toString();
			    String stringpced=row.getCell(6).toString();
			    
			    String time="0"+row.getCell(4).toString();
			    if(time.length()==1)
			    {
			    	
			    	time="00:00";
			    	
			    	
			    }
			    else if(time.charAt(3)==':')
			    {	
			    
			    	time=row.getCell(4).toString();
			    
			    	
			    }
			    if(time.length()==1)
			    {
			    	time="00:00";
			    	
			    	TimecardHours=null;
			    	
			    }else
			    {TimecardHours=LocalTime.parse(time);
			    	
			    }
			    
				workdate=convertStringDateToLocalDate(stringworkdate);
				PayCycleStartDate=convertStringDateToLocalDate(stringpcsd);
				PayCycleEndDate=convertStringDateToLocalDate(stringpced);
				workout=convertStringDateToLocalDate(stringworkout);
				EmployeeData empdata=new EmployeeData(PositionID,PositionStatus,workdate,workout,TimecardHours,PayCycleStartDate,PayCycleEndDate,FileNumber,EmployerName);
				employedatalist.add(empdata);
				i+=1;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employedatalist;
		
	}

	private static LocalDate convertStringDateToLocalDate(String stringworkdate) {
		Date date1=null;
		try {
			if(stringworkdate.length()<2)
			{
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse("01-jan-9090");
			}
			else {
			date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(stringworkdate);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		 
			LocalDate datexx = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return datexx;
	}
	
}


