package com.haoniu.zzx.app_ts.utils;

import android.support.annotation.NonNull;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 动态时间格式化
 * Created by SCWANG on 2017/6/17.
 */

public class DynamicTimeFormat extends SimpleDateFormat {

    private static Locale locale = Locale.CHINA;
    private static String weeks[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private static String moments[] = {"中午", "凌晨", "早上", "下午", "晚上"};

    private String mFormat = "%s";

    public DynamicTimeFormat() {
        this("%s", "yyyy年", "M月d日", "HH:mm");
    }

    public DynamicTimeFormat(String format) {
        this();
        this.mFormat = format;
    }

    public DynamicTimeFormat(String yearFormat, String dateFormat, String timeFormat) {
        super(String.format(locale, "%s %s %s", yearFormat, dateFormat, timeFormat), locale);
    }

    public DynamicTimeFormat(String format, String yearFormat, String dateFormat, String timeFormat) {
        this(yearFormat, dateFormat, timeFormat);
        this.mFormat = format;
    }


    @Override
    public StringBuffer format(@NonNull Date date, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {
        toAppendTo = super.format(date, toAppendTo, pos);

        Calendar otherCalendar = calendar;
        Calendar todayCalendar = Calendar.getInstance();

        int hour = otherCalendar.get(Calendar.HOUR_OF_DAY);

        String[] times = toAppendTo.toString().split(" ");
        String moment = hour == 12 ? moments[0] : moments[hour / 6 + 1];
        String timeFormat = moment + " " + times[2];
        String dateFormat = times[1] + " " + timeFormat;
        String yearFormat = times[0] + dateFormat;
        toAppendTo.delete(0, toAppendTo.length());

        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        toAppendTo.append(timeFormat);
                        break;
                    case 1:
                        toAppendTo.append("昨天 ");
                        toAppendTo.append(timeFormat);
                        break;
                    case 2:
                        toAppendTo.append("前天 ");
                        toAppendTo.append(timeFormat);
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if (dayOfWeek != 1) {//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                toAppendTo.append(weeks[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1]);
                                toAppendTo.append(' ');
                                toAppendTo.append(timeFormat);
                            } else {
                                toAppendTo.append(dateFormat);
                            }
                        } else {
                            toAppendTo.append(dateFormat);
                        }
                        break;
                    default:
                        toAppendTo.append(dateFormat);
                        break;
                }
            } else {
                toAppendTo.append(dateFormat);
            }
        } else {
            toAppendTo.append(yearFormat);
        }

        int length = toAppendTo.length();
        toAppendTo.append(String.format(locale, mFormat, toAppendTo.toString()));
        toAppendTo.delete(0, length);
        return toAppendTo;
    }

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文简写 如：12:32:21
     */
    public static String FORMAT_SHORT_TIME = "HH:mm:ss";

    /**
     * 英文简写 如：12:32
     */
    public static String FORMAT_SHORT_TIMES = "HH:mm";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern5() {
        return FORMAT_SHORT_TIME;
    }


    /**
     * 根据预设格式返回当前日期
     *
     * @return
     */
    public static String getNow() {
        return format2(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format2(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format2(Date date) {
        return format2(date, getDatePattern());
    }


    //时分秒
    public static String format5(Date date) {
        return format2(date, getDatePattern5());
    }


    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format2(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse2(String strDate) {
        return parse2(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse2(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format2(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse2(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse2(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 商圈动态的时间格式
     * 消息的时间格式
     *
     * @param date
     * @return
     */
    public static String format6(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        Date oldDate = null;
        long delta = new Date().getTime() - date.getTime();
        try {
            nowDate = simpleDateFormat.parse(simpleDateFormat.format(new Date(System.currentTimeMillis())));
            oldDate = simpleDateFormat.parse(simpleDateFormat.format(date));
            if (nowDate.equals(oldDate)) {
                if (delta / (60 * 1000) < 1) {
                    return "刚刚";
                } else if (delta / (60 * 1000) >= 1 && delta / (60 * 60 * 1000) < 1) {
                    return (delta / (60 * 1000) + 1) == 60 ? "1小时前" : (delta / (60 * 1000) + 1) + "分钟前";
                }

                if (delta / (60 * 60 * 1000) >= 1 && delta / (24 * 60 * 60 * 1000) < 1) {
                    if (delta / (60 * 60 * 1000) == 1 && delta % (60 * 60 * 1000) == 0) {
                        return "1小时前";
                    }
                    return (delta / (60 * 60 * 1000) + 1) == 24 ? "昨天" : (delta / (60 * 60 * 1000) + 1) + "小时前";
                }

                return getTime(date.getTime());
            } else {
                if ((nowDate.getTime() - oldDate.getTime()) / (24 * 60 * 60 * 1000) < 2) {
                    return "昨天";
                } else {
                    return getTime(date.getTime());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getTime(date.getTime());
    }

    /**
     * 商户管理时间
     *
     * @param date
     * @return
     */
//    public static String format7(long date) {
//        if (date / (60 * 1000) < 1) {
//            return "00天00时01分";
//        } else if (date / (60 * 1000) >= 1 && date / (60 * 60 * 1000) < 1) {
//            return "00天00时" + formatTime("mm分").format(new Date(date));
//        } else if (date / (60 * 60 * 1000) >= 1 && date / (24 * 60 * 60 * 1000) < 1) {
//            return "00天" + formatTime("HH时mm分").format(new Date(date));
//        } else if (date / (24 * 60 * 60 * 1000) >= 1 && date / (30 * 24 * 60 * 60 * 1000) < 1) {
//            Long time = date - 24*60*60*1000;//转化成天时，由于时间戳是从1970-01-01算起，天数会多一天
//            return formatTime("dd天HH时mm分").format(new Date(time));
//        }
//        return RegularUtils.getStrTimeDetail2((date- 24*60*60*1000)+ "");
//    }


    private static SimpleDateFormat formatTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return simpleDateFormat;
    }

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
            FORMAT_SHORT);

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }


}
