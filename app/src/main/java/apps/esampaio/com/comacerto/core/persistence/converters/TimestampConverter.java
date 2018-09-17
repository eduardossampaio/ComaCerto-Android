package apps.esampaio.com.comacerto.core.persistence.converters;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter {
    public static final String TIME_STAMP_FORMAT = "dd/MM/yyyy HH:mm";
    static DateFormat df = new SimpleDateFormat(TIME_STAMP_FORMAT);

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }
}