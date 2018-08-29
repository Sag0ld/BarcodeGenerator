package com.sag0ld.barcodegenerator

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.EAN8Writer
import com.sag0ld.barcodegenerator.barcodes.Barcode
import java.util.*

class EAN8 (override var content: String?, override var createAt: Calendar?) : Barcode() {
    override var description: String = App.getContext().getString(R.string.ean8_description)

    override fun generate(): Bitmap {
        val barcode = EAN8Writer().encode(content, BarcodeFormat.EAN_8, width, height)
        return toBitmap(barcode)
    }

    override fun toString(): String {
        return "EAN-8"
    }

}