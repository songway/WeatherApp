package com.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
public class DateUtils {
	 
	   
	/**
	 * 
	 * 通过解析参数 如"4月24日" 返回日期对应的星期几,int类型 ex,星期日返回1,星期一返回2,... 星期六返回7
	 * 
	 * @param date
	 * @return
	 */
	public static int parseWeek(String text) {

		// String s="4月26日 大雨转小雨";

		String[] arr = text.split(" ");
		String date = arr[0];
		int week = 1;

		// 因为本程序是只获取了月日没有获取年份,所以先获取当前年份
		Date d1 = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy");
		int y = Integer.parseInt(fmt1.format(d1));

		// 解析日期
		SimpleDateFormat fmt = new SimpleDateFormat("MM月dd日");
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
	        String  mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份  
	        String   mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份  
	        String  mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码  
	        String  mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));  
	        if("1".equals(mWay)){  
	            mWay ="天";  
	        }else if("2".equals(mWay)){  
	            mWay ="一";  
	        }else if("3".equals(mWay)){  
	            mWay ="二";  
	        }else if("4".equals(mWay)){  
	            mWay ="三";  
	        }else if("5".equals(mWay)){  
	            mWay ="四";  
	        }else if("6".equals(mWay)){  
	            mWay ="五";  
	        }else if("7".equals(mWay)){  
	            mWay ="六";  
	        }  
	        return mYear + "年" + mMonth + "月" + mDay+"日"+"(星期"+mWay+")";  
	    }  
	      
	    public static String getCurrChineseDate(){  
	    	   
	        final Calendar c = Calendar.getInstance();  
	        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
	        String  hour = String.valueOf(c.get(Calendar.HOUR));  
	        String  mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份  
	        String   mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份  
	        String  mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码  
	        String  mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));  
	        
	        CalendarUtil cc = new CalendarUtil();
	        
	        StringBuilder buf = new StringBuilder();
            CalendarUtil cu = new CalendarUtil();
            String chineseMonth = cu.getChineseMonth(Integer.parseInt(mYear),
            		Integer.parseInt(mMonth), Integer.parseInt(mDay));
            String chineseDay = cu.getChineseDay(Integer.parseInt(mYear),
            		Integer.parseInt(mMonth), Integer.parseInt(mDay));
            buf.append("农历").append(chineseMonth).append(chineseDay);
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
	            int  month = c.get(Calendar.MONTH)+1;// 获取当前月份  
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

