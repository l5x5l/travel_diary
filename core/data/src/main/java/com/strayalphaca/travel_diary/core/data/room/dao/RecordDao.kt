package com.strayalphaca.travel_diary.core.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.strayalphaca.travel_diary.core.data.room.entity.FileEntity
import com.strayalphaca.travel_diary.core.data.room.entity.RecordEntity
import com.strayalphaca.travel_diary.core.data.room.entity.RecordFileEntity

@Dao
interface RecordDao {
    @Insert
    suspend fun addRecordAndGetId(recordEntity: RecordEntity) : Long

    @Query("SELECT * FROM RecordEntity WHERE id = :id  LIMIT 1")
    suspend fun getRecord(id : Int) : RecordEntity

    @Query("DELETE FROM RecordEntity WHERE id = :id")
    suspend fun deleteRecord(id : Int)

    @Query(
        "UPDATE RecordEntity " +
        "SET content = :content, feeling = :feeling, weather = :weather, locationId = :locationId " +
        "WHERE id = :id"
    )
    suspend fun updateRecord(id : Int, content : String, weather : String, feeling : String, locationId : Int?)

    // 달력 일지 조회
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as imageUri, r.locationId as locationId FROM RecordEntity r " +
        "INNER JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "INNER JOIN FileEntity f on rf.fileId = f.id " +
        "WHERE r.createdAt LIKE :dateQuery || '%'"
    )
    suspend fun getRecordInCalendar(dateQuery : String) : List<RecordItem>

    // 지도 일지 조회 - 전국 지도
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as imageUri, r.locationId as locationId, l.provinceId as provinceId, l.cityGroupId as cityGroupId FROM RecordEntity r " +
        "INNER JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "INNER JOIN FileEntity f on rf.fileId = f.id " +
        "INNER JOIN LocationEntity l on r.locationId = l.id "
    )
    suspend fun getRecordInMapNationWide() : List<RecordItem>

    // 지도 일지 조회 - 광역시/도 기준
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as imageUri, r.locationId as locationId, l.provinceId as provinceId, l.cityGroupId as cityGroupId FROM RecordEntity r " +
        "INNER JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "INNER JOIN FileEntity f on rf.fileId = f.id " +
        "INNER JOIN LocationEntity l on r.locationId = l.id " +
        "WHERE l.provinceId = :provinceId"
    )
    suspend fun getRecordInMapProvince(provinceId : Int) : List<RecordItem>

    // 일지 리스트 조회 - 단일 도시 기준
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as imageUri, r.locationId as locationId, l.provinceId as provinceId, l.cityGroupId as cityGroupId FROM RecordEntity r " +
        "INNER JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "INNER JOIN FileEntity f on rf.fileId = f.id " +
        "INNER JOIN LocationEntity l on r.locationId = l.id " +
        "WHERE l.id = :cityId " +
        "LIMIT :perPage OFFSET (:pageIdx - 1) * :perPage"
    )
    suspend fun getRecordListInCity(cityId : Int, pageIdx : Int, perPage : Int): List<RecordItem>

    // 일지 리스트 조회 - 도시 그룹 기준
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as imageUri, r.locationId as locationId, l.provinceId as provinceId, l.cityGroupId as cityGroupId FROM RecordEntity r " +
        "INNER JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "INNER JOIN FileEntity f on rf.fileId = f.id " +
        "INNER JOIN LocationEntity l on r.locationId = l.id " +
        "WHERE l.cityGroupId = :cityGroupId " +
        "LIMIT :perPage OFFSET (:pageIdx - 1) * :perPage"
    )
    suspend fun getRecordListInCityGroup(cityGroupId : Int, pageIdx : Int, perPage : Int) : List<RecordItem>

    @Insert
    suspend fun addFile(fileEntity: FileEntity) : Long

    @Insert
    suspend fun addRecordFile(recordFileEntity: RecordFileEntity) : Long

    @Query(
        "SELECT f.filePath as filePath, f.id as id From FileEntity f " +
        "INNER JOIN RecordFileEntity rf ON f.id == rf.id " +
        "WHERE rf.recordId = :recordId"
    )
    suspend fun getFiles(recordId : Int) : List<FileItem>

    @Query(
        "SELECT * FROM RecordEntity WHERE createdAt LIKE :dateQuery LIMIT 1"
    )
    suspend fun getRecordByDate(dateQuery : String) : RecordEntity?

    data class RecordItem(
        val id : Int,
        val date : String,
        val imageUri : String?,
        val locationId : Int?,
        val provinceId : Int?,
        val cityGroupId : Int?
    )

    data class FileItem(
        val id : Int,
        val filePath : String
    )
}