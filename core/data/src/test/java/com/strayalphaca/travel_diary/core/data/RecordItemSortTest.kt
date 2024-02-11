package com.strayalphaca.travel_diary.core.data

import com.strayalphaca.travel_diary.core.data.room.model.RecordItem
import com.strayalphaca.travel_diary.core.data.room.model.getSingleItemPerId
import org.junit.Before
import org.junit.Test

class RecordItemSortTest {
    private var recordItemList : List<RecordItem> = listOf()

    private fun getInitData() : List<RecordItem> {
        return listOf(
            RecordItem(id=37, date="2024-02-01", fileUri="/sdcard/.transforms/synthetic/picker/0/com.android.providers.media.photopicker/media/1000000021.jpg", locationId=null, provinceId=null, cityGroupId=null, fileType = "Image"),
            RecordItem(id=38, date="2024-02-02", fileUri=null, locationId=null, provinceId=null, cityGroupId=null, fileType = null),
            RecordItem(id=39, date="2024-02-03", fileUri="/sdcard/.transforms/synthetic/picker/0/com.android.providers.media.photopicker/media/1000000021.jpg", locationId=129, provinceId=null, cityGroupId=null, fileType = "Image"),
            RecordItem(id=40, date="2024-02-04", fileUri="/sdcard/.transforms/synthetic/picker/0/com.android.providers.media.photopicker/media/1000000021.jpg", locationId=69, provinceId=null, cityGroupId=null, fileType = "Image"),
            RecordItem(id=41, date="2024-02-07", fileUri="content:/com.android.providers.downloads.documents/document/msf%3A1000000020", locationId=null, provinceId=null, cityGroupId=null, fileType = "Voice"),
            RecordItem(id=42, date="2024-02-10", fileUri=null, locationId=null, provinceId=null, cityGroupId=null, fileType = null),
            RecordItem(id=43, date="2024-02-08", fileUri="content:/com.android.providers.downloads.documents/document/msf%3A1000000019", locationId=null, provinceId=null, cityGroupId=null, fileType = "Voice"),
            RecordItem(id=43, date="2024-02-08", fileUri="/sdcard/.transforms/synthetic/picker/0/com.android.providers.media.photopicker/media/1000000021.jpg", locationId=null, provinceId=null, cityGroupId=null, fileType = "Image")
        )
    }

    @Before
    fun setUp() {
        recordItemList = getInitData()
    }

    @Test
    fun recordItemList_getOneItemPerId() {
        val handledRecordItemList = recordItemList.getSingleItemPerId()

        assert(!handledRecordItemList.contains(RecordItem(id=43, date="2024-02-08", fileUri="content:/com.android.providers.downloads.documents/document/msf%3A1000000019", locationId=null, provinceId=null, cityGroupId=null, fileType = "Voice"),))
        assert(handledRecordItemList.contains(RecordItem(id=43, date="2024-02-08", fileUri="/sdcard/.transforms/synthetic/picker/0/com.android.providers.media.photopicker/media/1000000021.jpg", locationId=null, provinceId=null, cityGroupId=null, fileType = "Image")))
    }
}