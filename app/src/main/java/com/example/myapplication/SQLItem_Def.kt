package com.example.myapplication

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.Date

@Entity(tableName = "mr_table", foreignKeys = [ForeignKey(entity = LevelSQL::class,
    parentColumns = ["id"],
    childColumns = ["parent"],
    onDelete = CASCADE)])
data class MaintenanceRecordSQL(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "device_name") val deviceName: String,
    @ColumnInfo(name = "work_order_num") val workOrderNum: String,
    @ColumnInfo(name = "service_provider") val serviceProvider: String?,
    @ColumnInfo(name = "service_engineering_code") val serviceEngineeringCode: String?,
    @ColumnInfo(name = "fault_code") val faultCode: String?,
    @ColumnInfo(name = "ipm_procedure") val ipmProcedure: String?,
    @ColumnInfo(name = "status") val status: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Int,
    //initial start date
    @ColumnInfo(name = "parent") val date: Date?,
    //cycle length
    @ColumnInfo(name = "parent") val numdays: Int?,
    //[<stat, date, task>]
    @ColumnInfo(name = "parent") val tasks: Array<Triple<String, Date, Int>>?,
)

@Entity(tableName = "levels_table", foreignKeys = [ForeignKey(entity = LevelSQL::class,
    parentColumns = ["id"],
    childColumns = ["parent"],
    onDelete = CASCADE)])

data class LevelSQL(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "level_name") val levelName: String,
    @ColumnInfo(name = "parent") val parent: Int?,
)