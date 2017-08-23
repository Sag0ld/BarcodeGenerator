package com.sag0ld.barcodegenerator

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private val errorsMessages = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init val
        val contentEditText : EditText = findViewById(R.id.contentEditText)
        val barcodeTypeSpinner : Spinner = findViewById(R.id.barcodeTypeSpinner)
        val barcodeView : ImageView = findViewById(R.id.barcodeView)

        // Set the barcode as imageView
        fun setBarcodeImage (b : Bitmap) {
            barcodeView.setImageBitmap(b)
        }

        // Init the barcode spinner event
        val adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                            R.array.type_of_barcode,android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        barcodeTypeSpinner.adapter = adapter

        barcodeTypeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) { }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val type = barcodeTypeSpinner.selectedItem.toString()
                val content = contentEditText.text.toString()
                if (isValid(type, content)) {
                    try {
                        setBarcodeImage(Controller.instance.generateBarcode(type, content))
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                } else if(errorsMessages.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, errorsMessages.toString(), Toast.LENGTH_SHORT).show()
                    errorsMessages.delete(0,errorsMessages.lastIndex+1)
                }
            }
        }

        // Init the content editText event
        contentEditText.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val type = barcodeTypeSpinner.selectedItem.toString()
                if(isValid(type, p0.toString())) {
                   try {
                       setBarcodeImage(Controller.instance.generateBarcode(type, p0.toString()))
                   } catch (e: Exception) {
                       Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                   }
               } else if(errorsMessages.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, errorsMessages.toString(), Toast.LENGTH_SHORT).show()
                    errorsMessages.delete(0,errorsMessages.lastIndex+1)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun isUPCValid(content : String, limit : Int) : Boolean {
        if (content.length < limit)
            return false
        if (content.length > limit +1) {
            errorsMessages.append("The content must be $limit or ${limit+1} digit long.")
            return false
        }
        if ((content.length == limit || content.length == limit +1)
                && !content.matches(Regex("^\\d+$"))) {
            errorsMessages.append("The content must be only digit.")
            return false
        }

        return true
    }
    private fun isEANValid (content :String, limit : Int) : Boolean {
        return isUPCValid(content, limit)
    }

    private fun isCode128Valid(content : String) :Boolean {
        if(content.length < 1 || content.length > 80) {
            errorsMessages.append("Contents length should be between 1 and 80 characters.")
            return false
        }
        return true
    }

    private fun isValid( type : String, content : String) : Boolean {
        when (type) {
            "UPC-A"     ->  return isUPCValid(content, 11)
            "UPC-E"     ->  return isUPCValid(content, 7)
            "EAN-8"     ->  return isEANValid(content,7)
            "EAN-13"    ->  return isEANValid(content, 12)
            "Code 128"  ->  return isCode128Valid(content)
            "QR Code"   ->  return true

            else  -> return false
        }
    }
}