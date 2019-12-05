package concurrent.dateformat;

import concurrent.annotations.ThreadSafe;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: kkyeer
 * @Description: 线程安全的DateFormat类
 * @Date:Created in 13:18 12-5
 * @Modified By:
 */
@ThreadSafe
public class ConcurrentDateFormat extends DateFormat {
    private ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<>();

    private final String format;

    public ConcurrentDateFormat(String format) {
        this.format = format;
    }

    private DateFormat newInstance(){
        return new SimpleDateFormat(format);
    }
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        DateFormat dateFormat = dateFormatThreadLocal.get();
        if (dateFormat == null) {
            dateFormat = newInstance();
            dateFormatThreadLocal.set(dateFormat);
        }
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }
    @Override
    public Date parse(String source, ParsePosition pos) {
        DateFormat dateFormat = dateFormatThreadLocal.get();
        if (dateFormat == null) {
            dateFormat = newInstance();
            dateFormatThreadLocal.set(dateFormat);
        }
        return dateFormat.parse(source, pos);
    }
}
