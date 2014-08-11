package com.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
public class DateUtils {
	 
	   
	/**
	 * 
	 * ͨ���������� ��"4��24��" �������ڶ�Ӧ�����ڼ�,int���� ex,�����շ���1,����һ����2,... ����������7
	 * 
	 * @param date
	 * @return
	 */
	public static int parseWeek(String text) {

		// String s="4��26�� ����תС��";

		String[] arr = text.split(" ");
		String date = arr[0];
		int week = 1;

		// ��Ϊ��������ֻ��ȡ������û�л�ȡ���,�����Ȼ�ȡ��ǰ���
		Date d1 = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy");
		int y = Integer.parseInt(fmt1.format(d1));

		// ��������
		SimpleDateFormat fmt = new SimpleDateFormat("MM��dd��");
		Date d;
		try {
			
				d = fmt.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.set(Calendar.YEAR, y);
			week = c.get(Calendar.DAY_OF_WEEK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return week;
	}

	    public static String getCurrData(){  
	    	   
	        final Calendar c = Calendar.getInstance();  
	        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
	        String  mYear = String.valueOf(c.get(Calendar.YEAR)); // ��ȡ��ǰ���  
	        String   mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// ��ȡ��ǰ�·�  
	        String  mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// ��ȡ��ǰ�·ݵ����ں���  
	        String  mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));  
	        if("1".equals(mWay)){  
	            mWay ="��";  
	        }else if("2".equals(mWay)){  
	            mWay ="һ";  
	        }else if("3".equals(mWay)){  
	            mWay ="��";  
	        }else if("4".equals(mWay)){  
	            mWay ="��";  
	        }else if("5".equals(mWay)){  
	            mWay ="��";  
	        }else if("6".equals(mWay)){  
	            mWay ="��";  
	        }else if("7".equals(mWay)){  
	            mWay ="��";  
	        }  
	        return mYear + "��" + mMonth + "��" + mDay+"��"+"(����"+mWay+")";  
	    }  
	      
	    public static String getCurrChineseDate(){  
	    	   
	        final Calendar c = Calendar.getInstance();  
	        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
	        String  hour = String.valueOf(c.get(Calendar.HOUR));  
	        String  mYear = String.valueOf(c.get(Calendar.YEAR)); // ��ȡ��ǰ���  
	        String   mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// ��ȡ��ǰ�·�  
	        String  mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// ��ȡ��ǰ�·ݵ����ں���  
	        String  mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));  
	        
	        CalendarUtil cc = new CalendarUtil();
	        
	        StringBuilder buf = new StringBuilder();
            CalendarUtil cu = new CalendarUtil();
            String chineseMonth = cu.getChineseMonth(Integer.parseInt(mYear),
            		Integer.parseInt(mMonth), Integer.parseInt(mDay));
            String chineseDay = cu.getChineseDay(Integer.parseInt(mYear),
            		Integer.parseInt(mMonth), Integer.parseInt(mDay));
            buf.append("ũ��").append(chineseMonth).append(chineseDay);
            System.out.println(chineseDay+"=============="+chineseMonth);
            System.out.println(buf.toString());
            return buf.toString();
	    }
	    
	    public static String getCurrTime(){  
	    	   
	        final Calendar c = Calendar.getInstance();  
	        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
	        int inHour=c.get(Calendar.HOUR_OF_DAY);
	        String  hour = inHour<10?"0"+inHour:inHour+""; 
	        int intMin=c.get(Calendar.MINUTE);
	        String  minute = intMin<10?"0"+intMin :intMin+""; 
	        
	        return hour+":"+minute;
	    }
	    public static String getAP(){  
	    	   
	        final Calendar c = Calendar.getInstance();  
	        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
	        int hour =c.get(Calendar.HOUR_OF_DAY); 
	        
	        
	        return hour<=12? "AM" :"PM";
	    }
	    public static String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};
	   
	    public static String  getMonth(){
	    	  final Calendar c = Calendar.getInstance();  
		        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
	            int  month = c.get(Calendar.MONTH)+1;// ��ȡ��ǰ�·�  
	    	return months[month-1];
		}
	    
	    public static String[] weeks={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
			
	    public static String getWeek(){   
	    		final Calendar c = Calendar.getInstance();  
			    c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		        int  mWay =c.get(Calendar.DAY_OF_WEEK);
		        return weeks[mWay-1];
			}
}

