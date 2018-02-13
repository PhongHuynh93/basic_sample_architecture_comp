package example.test.phong.testarch.db.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by user on 2/13/2018.
 */
class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}