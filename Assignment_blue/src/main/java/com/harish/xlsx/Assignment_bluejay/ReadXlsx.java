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
	
	public static void main(String[] args) {
		
		List<EmployeeData> employedatalist=readXLSXFile("C:\\Users\\haris\\OneDrive\\Desktop\\A11.xlsx");
//		System.out.println(employedatalist);
//		EmployeeData e1=employedatalist.get(0);
//		EmployeeData e2=employedatalist.get(1);
//		System.out.println(e1);
//		System.out.println(e2);
//		LocalTime t1=e1.TimecardHours;
//		LocalTime t2=e2.TimecardHours;
//		LocalTime total=t1.plusHours(t2.getHour()).plusMinutes(t2.getMinute());
	//	System.out.println(total);
		HashMap<String,EmployeeInfo> map=new HashMap<String,EmployeeInfo>();
		
		
		for(EmployeeData empdat:employedatalist)
		{	
			
			
			if(map.containsKey(empdat.PositionID))
			{EmployeeInfo etemporary=map.get(empdat.PositionID);
//			System.out.println("empinfo"+etemporary);
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
		einfo.worktimeMapping.put(empdat.workdate,empdat.TimecardHours);
		map.put(empdat.PositionID, einfo);
	//	System.out.println("empdat:"+empdat);
		
		
			
		}
		
		
		}
		for(EmployeeData empdat:employedatalist)
		{
			if(empdat.workdate.plusDays(1).isEqual(empdat.workout)&&(map.get(empdat.PositionID).worktimeMapping.get(empdat.workdate))!=null&&map.get(empdat.PositionID).worktimeMapping.get(empdat.workout)!=null)
			{	//System.out.println("1"+empdat.workdate+"2."+empdat.workout);
				LocalTime lt1=map.get(empdat.PositionID).worktimeMapping.get(empdat.workout);
			
				LocalTime lt2=map.get(empdat.PositionID).worktimeMapping.get(empdat.workdate);
//				System.out.println("lt2:"+lt2);
				map.get(empdat.PositionID).worktimeMapping.remove(empdat.workout);
				LocalTime lt3=lt1.plusHours(lt2.getHour()).plusMinutes(lt2.getMinute());
				map.get(empdat.PositionID).worktimeMapping.put(empdat.workdate, lt3);
			}
		}
	
//		System.out.println("WFS000336:---out"+map.get("WFS000336"));
		System.out.println("PostionIds with less Than 7 hours:");
		lessThan10Hours(map);
//		System.out.println("PostionIds with 7 consecitive workdays");
//		sevenConsecutiveDays(map);
//		morethan14hrs(map);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	private static void morethan14hrs(HashMap<String, EmployeeInfo> map) {
		int c=0;
		for(String key:map.keySet())
		{
			EmployeeInfo e=map.get(key);
			int t=0,cx=0;
			for(LocalDate wdate:e.worktimeMapping.keySet())
			{	//System.out.println(e.worktimeMapping.get(wdate));
				if(e.worktimeMapping.get(wdate).getHour()>=14)
				{
					t++;
//					System.out.println("in"+e.worktimeMapping.get(wdate)+"t:"+t);
					
				}
				
				
			}
			if(t>=1)
			{	
				System.out.println("MoreThan14hrs: "+e.PositionID);
			}
			
		}
		
	}

	private static void sevenConsecutiveDays(HashMap<String, EmployeeInfo> map) {
		for(String poid:map.keySet())
		{int ct=0;
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
//					System.out.println("pid:"+poid+"7 dates"+ldt1);
				}
			
			}
			if(ct==1)
			{
				
			
			System.out.println(etemp.PositionID);
			}
			
		}
		
	}

	private static void lessThan10Hours(HashMap<String, EmployeeInfo> map) {
		int c=0;
		for(String key:map.keySet())
		{
			EmployeeInfo e=map.get(key);
			int t=0,cx=0;
			for(LocalDate wdate:e.worktimeMapping.keySet())
			{	
				if(e.worktimeMapping.get(wdate).getHour()>=10)
				{
					t=1;
					break;
				}
				
			}
			if(t==0)
			{	c++;
				System.out.println(e.PositionID);
			}
			
		}
//		System.out.println(map.size()+"c"+c);
		
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
			   // System.out.println("after o"+time);
			    if(time.length()==1)
			    {
			    	time="00:00";
			    	//System.out.println("lenght is 1");
			    	//TimecardHours= LocalTime.parse("00:00");
			    	TimecardHours=null;
			    	
			    }else
			    {TimecardHours=LocalTime.parse(time);
			    	
			    }
			    
//			    System.out.println("time:"+time);
//			    System.out.println(TimecardHours);
				workdate=convertStringDateToLocalDate(stringworkdate);
				PayCycleStartDate=convertStringDateToLocalDate(stringpcsd);
				PayCycleEndDate=convertStringDateToLocalDate(stringpced);
				workout=convertStringDateToLocalDate(stringworkout);
//				System.out.println(PositionID+PositionStatus+workdate+PayCycleStartDate+PayCycleEndDate+FileNumber);
		
				EmployeeData empdata=new EmployeeData(PositionID,PositionStatus,workdate,workout,TimecardHours,PayCycleStartDate,PayCycleEndDate,FileNumber,EmployerName);
				
				employedatalist.add(empdata);
			//	System.out.println(empdata);
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
		
		//System.out.println("---"+stringworkdate);
		Date date1=null;
		try {
//			if(date1==null)
//			{
//				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse("01-jan-9090");
//			}
//			else {
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





//String sDate1="31-sep-1998";  
//Date date1=new SimpleDateFormat("dd-MMM-yyyy").parse(sDate1);  
//System.out.println(sDate1+"\t"+date1);  