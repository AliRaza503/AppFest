package com.example.app.database

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.app.model.AyatData
import kotlinx.coroutines.awaitCancellation
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.IOException
import java.io.InputStream
import kotlin.jvm.Throws

object ReadDatabase {
    var ayatList: ArrayList<AyatData>? = null
    suspend fun initDatabase(context: Context): ArrayList<AyatData> {
        ayatList = ArrayList()
        var assetInStream: InputStream? = null

        try {
            assetInStream = context.assets.open("tayah.xlsx")
            val myWorkBook = XSSFWorkbook(assetInStream)
            val dataSheet: XSSFSheet = myWorkBook.getSheetAt(0)

            val rowIter: Iterator<Row> = dataSheet.rowIterator()
            var rowno = 2
            while (rowIter.hasNext()) {
                if (rowno != 0) {
                    val cellIter = rowIter.next().cellIterator()
                    var colno = 0
                    var ayaId = ""
                    var suraId = ""
                    var ayaNo = ""
                    var arabicText = ""
                    var fatehMuhammad = ""
                    var mehmodulHassan = ""
                    var drMohsin = ""
                    var muftiTaqi = ""
                    var rukuId = ""
                    var paraRukuId = ""
                    var paraId = ""

                    while (cellIter.hasNext()) {
                        val myCell = cellIter.next() as XSSFCell
                        if (colno == 0) {
                            ayaId = myCell.toString().substringBefore(".")
                        }

                        if (colno == 1) {
                            suraId = myCell.toString().substringBefore(".")
                        }

                        if (colno == 2) {
                            ayaNo = myCell.toString().substringBefore(".")
                        }

                        if (colno == 3) {
                            arabicText = myCell.toString()
                        }

                        if (colno == 4) {
                            fatehMuhammad = myCell.toString()
                        }

                        if (colno == 5) {
                            mehmodulHassan = myCell.toString()
                        }

                        if (colno == 6) {
                            drMohsin = myCell.toString()
                        }
                        if (colno == 7) {
                            muftiTaqi = myCell.toString()
                        }
                        if (colno == 8) {
                            rukuId = myCell.toString().substringBefore(".")
                        }
                        if (colno == 9) {
                            paraRukuId = myCell.toString().substringBefore(".")
                        }
                        if (colno == 10) {
                            paraId = myCell.toString().substringBefore(".")
                        }
                        colno++
                    }
                    val ayah = AyatData(
                        ayaId,
                        suraId,
                        ayaNo,
                        arabicText,
                        fatehMuhammad,
                        mehmodulHassan,
                        drMohsin,
                        muftiTaqi,
                        rukuId,
                        paraRukuId,
                        paraId
                    )
                    ayatList!!.add(ayah)
                }
                rowno++
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("MainActivity", e.printStackTrace().toString())
        } finally {
            assetInStream?.close()
        }
        return ayatList!!
    }
    fun getAyatDataList(): ArrayList<AyatData>{
        return ayatList!!
    }
}