package com.sag0ld.barcodegenerator

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import com.sag0ld.barcodegenerator.barcodes.Barcode
import java.util.*

class Code128(override var content: String?, override var createAt: Calendar?) : Barcode() {
    override var description: String = App.getContext().getString(R.string.code128_description)

    override fun generate(): Bitmap {
        val barcode = Code128Writer().encode(content, BarcodeFormat.CODE_128, width,height)
        return toBitmap(barcode)
    }

    override fun toString(): String {
        return "Code 128"
    }
}