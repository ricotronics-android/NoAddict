package com.ricotronics.noaddict.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class StreakData(
    val startDate: Long,
    @PrimaryKey val id: Int? = null
) {

}
