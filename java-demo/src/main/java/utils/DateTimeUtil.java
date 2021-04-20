package utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility to date time
 */
public class DateTimeUtil {
	public static final String FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

	private static final String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";

	private static final String FORMAT_HOUR = "yyyy-MM-dd HH";

	public static final String FORMAT_DAY = "yyyy-MM-dd";
	
	public static final String FORMAT_SECOND_NO_SPLIT = "yyyyMMddHHmmss";

	public static final SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_MINUTE);

	public static Date nowDate() {
		return new Date();
	}

	// 获取当前系统日期时间 yyyy-MM-dd HH:mm:ss
	public static String getNowTime() {
		return formatDate(new Date());
	}

	/**
	 * 获取从今天开始的天数的日期
	 * 
	 * @param avail
	 *            int
	 * @return Date
	 */
	public static Date getDateFromToday(int avail) {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		if (avail != 0) {
			cal.add(Calendar.DATE, avail);
		}
		return cal.getTime();
	}

	/**
	 * 获取从今天开始的天数的日期
	 * 
	 * @param avail
	 *            int
	 * @return String
	 */
	public static String getStrDateFromToday(int avail) {
		return formatDateDay(getDateFromToday(avail));
	}

	/**
	 * 获得某一日期的后一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getNextDate(Date date) {
		return getNextNDate(date, 1);
	}

	/**
	 * 获得某一日期的前一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousDate(Date date) {
		return getNextNDate(date, -1);
	}

	/**
	 * 获得某一日期的前N天
	 * 
	 * @param date
	 * @param n
	 * @return Date
	 */
	public static Date getPreviousNDate(Date date, int n) {
		return getNextNDate(date, -n);
	}

	/**
	 * 获得某一日期的后N天
	 * 
	 * @param date
	 * @param n
	 * @return Date
	 */
	public static Date getNextNDate(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, n);
		return calendar.getTime();
	}

	/**
	 * 获取当前周的星期一日期
	 * 
	 * @return
	 */
	public static Date getMondyOnWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 获得某年某月第一天的日期
	 * 
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 获得某年某月第一天的日期
	 * 
     * @return Date
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		cal.set(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * 获得某年某月最后一天的日期
	 * 
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		return getPreviousDate(calendar.getTime());
	}

	/**
	 * 由年月日构建Date类型
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return Date
	 */
	public static Date buildDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return calendar.getTime();
	}
	
	/**
	 * 由年月日构建Date类型  一天的结束时间点
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return Date
	 */
	public static Date buildDateEnd(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, 23, 59, 59);
		return calendar.getTime();
	}
	
	/**
	 * 由年月日构建Date类型  一天的开始时间点
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return Date
	 */
	public static Date buildDateStart(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * 获得某年的第一天的日期
	 * 
	 * @param year
	 * @return Date
	 */
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfMonth(year, 1);
	}

	/**
	 * 获得某年的最后一天的日期
	 * 
	 * @param year
	 * @return Date
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfMonth(year, 12);
	}

	public static String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 转换时间格式 精确到秒
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDate(date, FORMAT_SECOND);
	}

	/**
	 * 转换时间格式 精确到天
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateDay(Date date) {
		return formatDate(date, FORMAT_DAY);
	}

	/**
	 * 转换时间格式 精确到小时
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateHour(Date date) {
		return formatDate(date, FORMAT_HOUR);
	}

	/**
	 * 转换时间格式 精确到分
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateMin(Date date) {
		return formatDate(date, FORMAT_MINUTE);
	}

	/**
	 * 解析字符类型的日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(String date, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new Exception(e);
		}
	}
	/**
	 * 解析字符类型的日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parseHit(String date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 判断两个日期大小，如日期1小于日期2，返回true，否则返回false
	 * 
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean isBefore(Date one, Date two) {
		if (one == null || two == null) {
			return false;
		}
		return one.getTime() < two.getTime();
	}



	/**
	 * 计算两个时间相差月数
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return
	 */
	public static int getMonthSpace(Date date1, Date date2) {
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		int sYear = c2.get(Calendar.YEAR);
		int sMonth = c2.get(Calendar.MONTH);
		int sDay = c2.get(Calendar.DATE);
		int eYear = c1.get(Calendar.YEAR);
		int eMonth = c1.get(Calendar.MONTH);
		int eDay = c1.get(Calendar.DATE);
		result = ((eYear - sYear) * 12 + (eMonth - sMonth));
		if (sDay < eDay) {
			result = result + 1;
		}
		return result == 0 ? 1 : Math.abs(result);
	}
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate)    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat(FORMAT_DAY);  
        try {
			smdate=sdf.parse(sdf.format(smdate));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}  
        try {
			bdate=sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
	/**
	 * 判断是否为闰年
	 * 
	 * @param year
	 *            要判断的年份
	 * @return 如果为闰年返回真，反之为
	 */
	public static boolean isLeapYear(int year) {
		if (year % 4 != 0)
			return false;
		return year % 100 != 0 || year % 400 == 0;
	}

	/**
	 * 得到本季度第一天的日期
	 * 
	 * @Methods Name getLastDayOfQuarter
	 * @return Date
	 */
	public static Date getFirstDayOfQuarter(Date date) {
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(date);
		int curMonth = cDay.get(Calendar.MONTH);
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
			cDay.set(Calendar.MONTH, Calendar.JANUARY);
		}
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
			cDay.set(Calendar.MONTH, Calendar.APRIL);
		}
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
			cDay.set(Calendar.MONTH, Calendar.JULY);
		}
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
			cDay.set(Calendar.MONTH, Calendar.OCTOBER);
		}
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();
	}

	/**
	 * 得到本季度最后一天的日期
	 * 
	 * @Methods Name getLastDayOfQuarter
	 * @return Date
	 */
	public static Date getLastDayOfQuarter(Date date) {
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(date);
		int curMonth = cDay.get(Calendar.MONTH);
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
			cDay.set(Calendar.MONTH, Calendar.MARCH);
		}
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
			cDay.set(Calendar.MONTH, Calendar.JUNE);
		}
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
			cDay.set(Calendar.MONTH, Calendar.AUGUST);
		}
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
			cDay.set(Calendar.MONTH, Calendar.DECEMBER);
		}
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();
	}
	/**
     * 判断sourceTime是否在beginTime和endTime之间
     * @param sourceTime 源时间
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    public static boolean judgeMidTime(String sourceTime, String beginTime, String endTime) {
    	boolean bIf = false;
        try {
        	Date bTime = getDateFromString(sourceTime,
                    "yyyy-MM-dd");
            Calendar cld = Calendar.getInstance();
            cld.setTime(bTime);
            int week = cld.get(Calendar.DAY_OF_WEEK);
            //剔除周六、周日
            if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {
            	return bIf;
            }
            Date sTime = getDateFromString(beginTime,
                    "yyyy-MM-dd");
            Date eTime = getDateFromString(endTime,
                "yyyy-MM-dd");            
            if (sTime.getTime() <= bTime.getTime()  
            		&& bTime.getTime() <= eTime.getTime()) {
            	bIf = true;
            }
        } catch (Exception e) {
        	bIf = false;
        	e.printStackTrace();
         }
        return bIf;
    }
    
    /**
     * 将指定格式的字符串格式化为日期.<br>
     * <br>
     * @param s 字符串内容.
     * @param format 字符串格式.
     * @return 日期
     */
    public static Date getDateFromString(String s, String format) {
        try {        	
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (s.indexOf("/") > 0) {
            	String lineFormat = format.replaceAll("-", "/");
            	SimpleDateFormat tempSDF = new SimpleDateFormat(lineFormat);
            	Date date = tempSDF.parse(s);
            	String dTime = sdf.format(date);
            	return sdf.parse(dTime);
            } else {
                return sdf.parse(s);
            }
        } catch (Exception e) {
            return null;
        }
    }
    /**
	 * 时间加天数
	 * @param date   时间
	 * @param days  间隔天数
	 * @return
	 */
	public static Date addDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	@SuppressWarnings("deprecation")
	public static boolean isSameDay(Date plannedCompletedDate, Date buildDateStart) {
		if(plannedCompletedDate.getYear()==buildDateStart.getYear() && plannedCompletedDate.getMonth()==buildDateStart.getMonth() && plannedCompletedDate.getDate()==buildDateStart.getDate() &&
				plannedCompletedDate.getHours()==buildDateStart.getHours() && plannedCompletedDate.getMinutes()==buildDateStart.getMinutes() && plannedCompletedDate.getSeconds()==buildDateStart.getSeconds()){
			return true;
		}
		return false;
	}



	/**
	 * 将字符串日期换成Date
	 * 
	 * @param sDate
	 *            有效时间字符
	 * @return 返回日期
	 */







	/**
     * 获取相差分钟的时间
     * @param beginTime     开始时间
     * @param periodMinute  相差分钟数
     * @return  
     */
    public static Date addMinuteTime(Date date, int periodMinute) {
        try {
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(date);
        	calendar.add(Calendar.MINUTE, periodMinute);
            return calendar.getTime();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
	/**
	 * 获得某一日期的后一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getNextDayBegin() {
		Date date=new Date();
		String day=DateTimeUtil.formatDateDay(date)+" 00:00:00";
	    Date dateBegain = null;
		try {
			dateBegain = DateTimeUtil.parse(day, DateTimeUtil.FORMAT_SECOND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getNextNDate(dateBegain, 1);
	}
		


	public static Date getDateBySerial(String time){
		StringBuilder sb=new StringBuilder(time.substring(0, 4)).append("-");
		sb.append(time.substring(4,6)).append("-");
		sb.append(time.substring(6,8)).append(" ");
		sb.append(time.substring(8,10)).append(":");
		sb.append(time.substring(10,12)).append(":");
		sb.append(time.substring(12,14));
		return DateTimeUtil.getDateFromString(sb.toString(), DateTimeUtil.FORMAT_SECOND);
	}
	/**
     * 根据日期值得到中文日期字符串.<br>
     * <br>
     * @param date 给定日期.
     */
    public static String getChineseTwoDate(String date) {
        if (date.length() < 10) {
            return "";
        } else {
            String year = date.substring(0, 4); // 年
            String month = date.substring(5, 7); // 月
            String day = date.substring(8, 10); // 日

            return year + "年" + month + "月" + day + "日";
        }
    }
    /**
     * 当得到两个日期相差天数.<br>
     * <br>
     * @param startDate 第一个日期.
     * @param endDate   第二个日期.
     * @return 相差的天数
     */
    public static int selectDateDiff(Date beginDate, Date endDate) {
        int dif = 0;
        try {
            dif = (int) ((endDate.getTime() - beginDate.getTime()) / 86400000);
        } catch (Exception e) {
            dif = 0;
        }
        return dif;
    }

}
