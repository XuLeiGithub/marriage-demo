package com.jiuyu.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 
 * @ClassName: DateUtils 
 * @Description: 时间工具类
 * @author he_jiebing@jiuyv.com
 * @date 2020年9月23日 下午4:57:59
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static final String YYYY = "yyyy";

	public static final String YYYY_MM = "yyyy-MM";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYYMMDD = "yyyyMMdd";
	
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private static final String[] parsePatterns = { YYYY_MM_DD, YYYY_MM_DD_HH_MM_SS, "yyyy-MM-dd HH:mm", YYYY_MM,
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 获取当前Date型日期
	 * 
	 * @return Date() 当前日期
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, getNowDate());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		if(date==null) {
			return null;
		}
		return new SimpleDateFormat(format).format(date);
	}

	public static final Date dateTime(final String format, final String ts) {
		if(null==format) {
			return null;
		}
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath() {
		Date now = getNowDate();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static final String dateTime() {
		Date now = getNowDate();
		return DateFormatUtils.format(now, YYYYMMDD);
	}

	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 计算两个时间差
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60L;
		long nh = 1000 * 60 * 60L;
		long nm = 1000 * 60L;
		// 获得两个时间的毫秒时间差异 1000
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果  
		return day + "天" + hour + "小时" + min + "分钟";
	}
	
	/**
	 * 
	 * @Description: 获取2个时间的差
	 * @author shu_k
	 * @date 2020年10月22日 下午1:29:20
	 */
	public static Period getDatePoor(String startDate,String endDate) {
		Period prPeriod = Period.between(LocalDate.parse(startDate), LocalDate.parse(endDate));
		return prPeriod;
	}
	
	/**
	 * @Description: 获取2个时间的分账之差 
	 * @author shu_k
	 * @date 2020年10月22日 下午1:48:26
	 */
	public static Long getDtePoorMinus(Date startDate,Date endDate) {
		long nm = 1000 * 60L;
		long diff = endDate.getTime() - startDate.getTime();
		// 计算差多少分钟
		long min = diff / nm;
		return min;
	}
	
	/**
	 * @Description: 获取2个时间的天数差 
	 * @author shu_k
	 * @date 2020年10月19日 上午11:27:58
	 */
	public static long getDatePoorDays(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60L;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		return diff / nd;
	}
	/**
	 * 计算两个日期的间隔天数
	 * @param endDate
	 * @param startDate
	 * @return
	 */
	public static long calculateIntervalDay(Date endDate,Date startDate){
		LocalDate endDateLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate startDateLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long intervalDays = ChronoUnit.DAYS.between(startDateLocalDate,endDateLocalDate);
		return intervalDays;
	}
	
	
	
	/**
	 * @Description: 获取2个日期月份之差
	 * @author shu_k
	 * @date 2020年10月19日 上午11:43:42
	 */
	public static int getDatePoorMonths(Date startDate, Date endDate) {
		Calendar end = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		end.setTime(endDate);
		now.setTime(startDate);
		int year = (end.get(Calendar.YEAR) - now.get(Calendar.YEAR)) * 12;
		int month = end.get(Calendar.MONTH) - now.get(Calendar.MONTH);
		return year + month;
	}
	
	/**
	 * @Description: 比较2个日期所在月份 DD的前后大小
	 * @author shu_k
	 * @date 2020年10月20日 上午10:42:06
	 */
	public static boolean comparaDayOfMonth(Date startDate, Date endDate) {
		Calendar end = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		end.setTime(endDate);
		now.setTime(startDate);
		
		return end.get(Calendar.DAY_OF_MONTH)>=now.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 * @Description: 组装时间keyMap
	 * @author shu_k
	 * @date 2020年10月22日 下午3:36:13
	 */
	public  static Map<String,Long> parse(Long time,Long keyNum,int addTimeType){
		Map<String,Long> timeMap = new TreeMap<String,Long>();
		//时间增量
		Long addTime = 0L;
		if(Calendar.MINUTE==addTimeType) {
			addTime =1000*60L;
			for (int i = 0; i < keyNum; i++) {
				Date t = new Date(time+i*addTime);//按分钟设置key
				String key =getHm(t);
				timeMap.put (key, 0L);
			}
		}else if(Calendar.SECOND==addTimeType) {
			addTime =1000L;
			for (int i = 0; i < keyNum; i++) {
				Date t = new Date(time+i*addTime);//按分钟设置key
				String key =getHms(t);
				timeMap.put (key, 0L);
			}
		}else {
			// 语法完整性
		}
		return timeMap;
		
	}
	
	/**
	 * 
	 * @Description: 获取时间 HH:mm
	 * @author shu_k
	 * @date 2020年10月22日 下午3:36:34
	 */
	public static  String getHm(Date date) {
		String dstr = DateUtils.parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
		if(StringUtils.isNotBlank(dstr)){
			return dstr.substring(11, 16);
		}
		return null;
		
	}
	
	/**
	 * 
	 * @Description: 获取时间 HH:mm:ss
	 * @author shu_k
	 * @date 2020年10月22日 下午3:36:34
	 */
	public static  String getHms(Date date) {
		String dstr = DateUtils.parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
		if(dstr!=null) {
			return dstr.substring(11, 19);
		}
		return null;
		
	}
	
}