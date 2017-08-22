package com.sag0ld.barcodegenerator

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.MultiFormatOneDReader
import com.google.zxing.oned.UPCAWriter
import com.google.zxing.oned.UPCEANReader

/**
 * Created by Sagold on 2017-08-18.
 */
class UPCA (override var content: String) : Barcode() {

    init {
        // Validation
        if (content.length > 11) {
            throw Exception ("Must be 11 digit long.")
        }
    }

    override fun generate(): Bitmap {
        var barcode = UPCAWriter().encode(content, BarcodeFormat.UPC_A
                                                     ,160*5,101*3)
        return toBitmap(barcode)
    }

    override fun toString(): String {
        return "UPC-A"
    }

}