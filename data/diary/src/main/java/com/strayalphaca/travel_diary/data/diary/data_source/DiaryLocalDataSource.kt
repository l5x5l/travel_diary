package com.strayalphaca.travel_diary.data.diary.data_source

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.core.data.model.CityDto
import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto
import com.strayalphaca.travel_diary.core.data.model.ImageDto
import com.strayalphaca.travel_diary.core.data.model.MediaFileInfoDto
import com.strayalphaca.travel_diary.core.data.model.VoiceFileInDiaryDto
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.core.data.room.entity.RecordEntity
import com.strayalphaca.travel_diary.core.data.room.entity.RecordFileEntity
import com.strayalphaca.travel_diary.core.data.room.model.getSingleItemPerId
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalphaca.travel_diary.map.model.City
import javax.inject.Inject

class DiaryLocalDataSource @Inject constructor(
    private val recordDao: RecordDao
) : DiaryDataSource {
    override suspend fun getDiaryData(id: String): BaseResponse<DiaryDto> {
        val recordEntity = recordDao.getRecord(id.toInt())
        val fileList = recordDao.getFiles(id.toInt()).map {
            MediaFileInfoDto(id = it.id.toString(), originName = it.filePath, uploadedLink = it.filePath, thumbnailLink = it.filePath, type = it.type)
        }
        val diaryDto = DiaryDto(
            id = id,
            date = recordEntity.createdAt,
            feeling = recordEntity.feeling,
            weather = recordEntity.weather,
            content = recordEntity.content,
            medias = fileList.filter { it.type == "Image" || it.type == "Video" },
            voice = fileList.find { it.type == "Voice" }?.let { VoiceFileInDiaryDto(id = it.id, originName = it.originName, uploadedLink = it.uploadedLink, shortLink = it.shortLink) },
            cityId = recordEntity.locationId,
            createdAt = recordEntity.createdAt
        )
        return BaseResponse.Success(data = diaryDto)
    }

    override suspend fun getDiaryList(cityId: Int, perPage: Int, offset: Int): List<DiaryItemDto> {
        return recordDao.getRecordListInCity(cityId, perPage = perPage, pageIdx = offset)
            .getSingleItemPerId()
            .map { recordItem ->
                DiaryItemDto(
                    id = recordItem.id.toString(),
                    image = recordItem.fileUri?.let { ImageDto(originalName = it, uploadedLink = it, shortLink = it) },
                    city = City.findCity(recordItem.locationId!!).run { CityDto(id = id, name = name) },
                    provinceId = recordItem.provinceId!!
                )
            }
    }

    override suspend fun getDiaryListByCityGroup(
        cityGroupId: Int,
        perPage: Int,
        offset: Int
    ): List<DiaryItemDto> {
        return recordDao.getRecordListInCityGroup(cityGroupId, perPage = perPage, pageIdx = offset)
            .getSingleItemPerId()
            .map { recordItem ->
                DiaryItemDto(
                    id = recordItem.id.toString(),
                    image = recordItem.fileUri?.let { ImageDto(originalName = it, uploadedLink = it, shortLink = it) },
                    city = City.findCity(recordItem.locationId!!).run { CityDto(id = id, name = name) },
                    provinceId = recordItem.provinceId!!
                )
            }
    }

    override suspend fun uploadDiaryAndGetId(diaryWriteData: DiaryWriteData): String {
         return recordDao.addRecordAndGetId(
             RecordEntity(
                 content = diaryWriteData.content,
                 weather = diaryWriteData.weather.toString(),
                 feeling = diaryWriteData.feeling.toString(),
                 locationId = diaryWriteData.cityId,
                 createdAt = diaryWriteData.recordDate.toString(),
                 updatedAt = diaryWriteData.recordDate.toString()
             )
         ).also {
             diaryWriteData.medias?.let { fileIdList ->
                 fileIdList.forEachIndexed { index, fileIdString ->
                     recordDao.addRecordFile(RecordFileEntity(recordId = it.toInt(), fileId = fileIdString.toInt(), positionInRecord = index))
                 }
             }
             diaryWriteData.voice?.let { voiceFileId ->
                 recordDao.addRecordFile(RecordFileEntity(recordId = it.toInt(), fileId = voiceFileId.toInt(), positionInRecord = 0))
             }
         }.toString()
    }

    override suspend fun modifyDiary(diaryModifyData: DiaryModifyData) {
        recordDao.deleteRecordFileOfRecord(diaryModifyData.id.toInt())

        recordDao.updateRecord(
            id = diaryModifyData.id.toInt(),
            weather = diaryModifyData.weather.toString(),
            feeling = diaryModifyData.feeling.toString(),
            content = diaryModifyData.content ?: "",
            locationId = diaryModifyData.cityId,
        )

        diaryModifyData.medias?.let { fileIdList ->
            fileIdList.forEachIndexed { index, fileIdString ->
                recordDao.addRecordFile(RecordFileEntity(recordId = diaryModifyData.id.toInt(), fileId = fileIdString.toInt(), positionInRecord = index))
            }
        }
        diaryModifyData.voice?.let { voiceFileId ->
            recordDao.addRecordFile(RecordFileEntity(recordId = diaryModifyData.id.toInt(), fileId = voiceFileId.toInt(), positionInRecord = 0))
        }
    }

    override suspend fun removeDiary(id: String) {
        recordDao.deleteRecord(id.toInt())
    }

    override suspend fun getDiaryCount(): Int {
        return recordDao.getAllRecords().size
    }
}