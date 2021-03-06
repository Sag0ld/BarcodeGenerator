package com.sag0ld.barcodegenerator.database

import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "barcodes")
class Barcode : ViewModel() {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "type")
    var type: String? = null

    @ColumnInfo(name = "content")
    var content: String? = null

    //@ColumnInfo(name = "uri")
    //var uri: String? = null

    @ColumnInfo(name = "isFromScanner")
    var isFromScanner: Boolean = false

    @ColumnInfo(name = "create_at")
    var createAt: Long? = null

    @Ignore
    fun isQrCode(): Boolean {
        type?.let {
            return it == "QR Code"
        }

        return false
    }

    @Ignore
    fun createAtDatetoString(): String {
        createAt?.let {
            val format1 = SimpleDateFormat("dd MMMMMM yyyy")
            val cal = Calendar.getInstance()
            cal.timeInMillis = it

            return format1.format(cal.time)
        }
        return ""
    }
    @Ignore
    fun createAtHourtoString() : String {
        createAt?.let {
            val format1 = SimpleDateFormat("HH:mm:ss")
            val cal = Calendar.getInstance()
            cal.timeInMillis = it

            return format1.format(cal.time)
        }
        return ""
    }
}