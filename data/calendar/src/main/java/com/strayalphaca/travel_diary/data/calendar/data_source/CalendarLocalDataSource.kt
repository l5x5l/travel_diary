package com.strayalphaca.travel_diary.data.calendar.data_source

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.DiaryDate
import com.strayalphaca.travel_diary.core.data.model.ImageDto
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.core.data.room.model.getSingleItemPerId
import com.strayalphaca.travel_diary.data.calendar.models.CalendarDiaryDto
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarLocalDataSource @Inject constructor(
    private val recordDao: RecordDao
) : CalendarDataSource {
    override suspend fun getDiaryData(year: Int, month: Int): BaseResponse<List<CalendarDiaryDto>> {
        val data = recordDao.getRecordInCalendar("%04d-%02d".format(year, month))
            .getSingleItemPerId()
            .map { recordItem ->
                CalendarDiaryDto(
                    id = recordItem.id.toString(),
                    image = recordItem.fileUri?.let { ImageDto(it, it, it) },
                    recordDate = recordItem.date
                )
            }
        return BaseResponse.Success(data = data)
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        val data = recordDao.getRecordByDate(DiaryDate.getInstanceFromCalendar(Calendar.getInstance()).toString())
        return BaseResponse.Success(data == null)
    }
}