package com.strayalphaca.travel_diary.core.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.strayalphaca.travel_diary.core.data.room.entity.FileEntity
import com.strayalphaca.travel_diary.core.data.room.entity.LocationEntity
import com.strayalphaca.travel_diary.core.data.room.entity.RecordEntity
import com.strayalphaca.travel_diary.core.data.room.entity.RecordFileEntity
import com.strayalphaca.travel_diary.core.data.room.model.FileItem
import com.strayalphaca.travel_diary.core.data.room.model.RecordItem

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecordAndGetId(recordEntity: RecordEntity) : Long

    @Query("SELECT * FROM RecordEntity")
    suspend fun getAllRecords() : List<RecordEntity>

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
    // 이게 문제다, join을 해서, 동일한 id를 가진 일지가 여러개 생긴 거다.
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as fileUri, r.locationId as locationId, f.type as fileType FROM RecordEntity r " +
        "LEFT JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "LEFT JOIN FileEntity f on rf.fileId = f.id " +
        "WHERE r.createdAt LIKE :dateQuery || '%' AND ( rf.positionInRecord = 0 OR rf.positionInRecord is null ) "
    )
    suspend fun getRecordInCalendar(dateQuery : String) : List<RecordItem>

    // 지도 일지 조회 - 전국 지도
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as fileUri, r.locationId as locationId, l.provinceId as provinceId, l.cityGroupId as cityGroupId, f.type as fileType FROM RecordEntity r " +
        "LEFT JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "LEFT JOIN FileEntity f on rf.fileId = f.id " +
        "INNER JOIN LocationEntity l on r.locationId = l.id " +
        "WHERE rf.positionInRecord = 0 OR rf.positionInRecord is null"
    )
    suspend fun getRecordInMapNationWide() : List<RecordItem>

    // 지도 일지 조회 - 광역시/도 기준
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as fileUri, r.locationId as locationId, l.provinceId as provinceId, l.cityGroupId as cityGroupId, f.type as fileType FROM RecordEntity r " +
        "LEFT JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "LEFT JOIN FileEntity f on rf.fileId = f.id " +
        "INNER JOIN LocationEntity l on r.locationId = l.id " +
        "WHERE l.provinceId = :provinceId AND ( rf.positionInRecord = 0 OR rf.positionInRecord is null )"
    )
    suspend fun getRecordInMapProvince(provinceId : Int) : List<RecordItem>

    // 일지 리스트 조회 - 단일 도시 기준
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as fileUri, r.locationId as locationId, l.provinceId as provinceId, l.cityGroupId as cityGroupId, f.type as fileType FROM RecordEntity r " +
        "LEFT JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "LEFT JOIN FileEntity f on rf.fileId = f.id " +
        "INNER JOIN LocationEntity l on r.locationId = l.id " +
        "WHERE l.id = :cityId AND ( rf.positionInRecord = 0 OR rf.positionInRecord is null )" +
        "LIMIT :perPage OFFSET (:pageIdx - 1) * :perPage"
    )
    suspend fun getRecordListInCity(cityId : Int, pageIdx : Int, perPage : Int): List<RecordItem>

    // 일지 리스트 조회 - 도시 그룹 기준
    @Query(
        "SELECT r.id, r.createdAt as date, f.filePath as fileUri, r.locationId as locationId, l.provinceId as provinceId, l.cityGroupId as cityGroupId, f.type as fileType FROM RecordEntity r " +
        "LEFT JOIN RecordFileEntity rf ON r.id = rf.recordId " +
        "LEFT JOIN FileEntity f on rf.fileId = f.id " +
        "INNER JOIN LocationEntity l on r.locationId = l.id " +
        "WHERE l.cityGroupId = :cityGroupId AND ( rf.positionInRecord = 0 OR rf.positionInRecord is null )" +
        "LIMIT :perPage OFFSET (:pageIdx - 1) * :perPage"
    )
    suspend fun getRecordListInCityGroup(cityGroupId : Int, pageIdx : Int, perPage : Int) : List<RecordItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFile(fileEntity: FileEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecordFile(recordFileEntity: RecordFileEntity) : Long

    @Query("DELETE FROM FileEntity WHERE id = :id")
    suspend fun deleteFile(id : Int)

    @Query("DELETE FROM RecordFileEntity WHERE recordId = :recordId")
    suspend fun deleteRecordFileOfRecord(recordId: Int)

    @Query(
        "SELECT f.filePath as filePath, f.id as id, f.type as type, rf.positionInRecord as positionInRecord From FileEntity f " +
        "INNER JOIN RecordFileEntity rf ON f.id == rf.fileId " +
        "WHERE rf.recordId = :recordId " +
        "ORDER BY rf.positionInRecord"
    )
    suspend fun getFiles(recordId : Int) : List<FileItem>

    @Query(
        "SELECT * FROM RecordEntity WHERE createdAt LIKE :dateQuery LIMIT 1"
    )
    suspend fun getRecordByDate(dateQuery : String) : RecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM LocationEntity")
    suspend fun getLocations() : List<LocationEntity>

    @Query("DELETE FROM RecordEntity")
    suspend fun clearRecord()

    @Query("DELETE FROM RecordFileEntity")
    suspend fun clearRecordFile()

    @Transaction
    suspend fun clearData() {
        clearRecord()
        clearRecordFile()
    }
}