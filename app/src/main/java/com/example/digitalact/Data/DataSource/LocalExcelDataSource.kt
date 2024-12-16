package com.example.digitalact.Data.DataSource

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.example.digitalact.Data.DataBase.Entity.ActEntity
import com.example.digitalact.Data.Repository.TaskRepositoryImpl
import com.example.digitalact.Dependencies
import com.example.digitalact.Domains.Model.Act
import com.example.digitalact.Domains.Model.Task
import com.example.digitalact.Domains.Repository.TaskRepository
import com.example.digitalact.Presentation.ViewModel.TaskViewModel
import com.example.digitalact.Presentation.ViewModel.TelephoneViewModel
import com.example.digitaltask.Data.DataBase.Dao.TaskDao
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream


class LocalExcelDataSource(private val context: Context) : ExcelDataSource {

    override fun readTaskFromExcel(fileUri: Uri){
        val taskRepository = TaskViewModel(Dependencies.taskRepository)
        try {
            context.contentResolver.openInputStream(fileUri)?.use { inputStream ->
                val workbook = WorkbookFactory.create(inputStream)
                val sheet = workbook.getSheetAt(0)
                val dataFormatter = DataFormatter()

                for (row in sheet) {
                    val accountNumber =  dataFormatter.formatCellValue(row.getCell(0))
                    val address = dataFormatter.formatCellValue(row.getCell(1))
                    val fullName = dataFormatter.formatCellValue(row.getCell(2))
                    val typePU = dataFormatter.formatCellValue(row.getCell(3))
                    val numberPU = dataFormatter.formatCellValue(row.getCell(4))
                    val reasonReplacement = dataFormatter.formatCellValue(row.getCell(5))
                    val numberApplication = dataFormatter.formatCellValue(row.getCell(6))
                    val telephone = dataFormatter.formatCellValue(row.getCell(7))
                    val comment = dataFormatter.formatCellValue(row.getCell(8))
                    taskRepository.insertNewTaskDataInDatabase(
                        accountNumber,
                        address,
                        fullName,
                        typePU,
                        numberPU,
                        reasonReplacement,
                        numberApplication,
                        telephone,
                        comment
                    )
                }
                workbook.close()
            }
            Toast.makeText(context, "База данных успешно загружена", Toast.LENGTH_LONG).show()

        }
        catch (e: Exception) {
            Toast.makeText(context, "Ошибка: $e", Toast.LENGTH_LONG).show()
        }
    }

    override fun readTelephoneFromExcel(fileUri: Uri) {
        val telephoneRepository = TelephoneViewModel(Dependencies.telephoneRepository)
        try {
            context.contentResolver.openInputStream(fileUri)?.use { inputStream ->
                val workbook = WorkbookFactory.create(inputStream)
                val sheet = workbook.getSheetAt(0)
                val dataFormatter = DataFormatter()

                for (row in sheet) {
                    val numberTelephone =  dataFormatter.formatCellValue(row.getCell(0))
                    val iccID = dataFormatter.formatCellValue(row.getCell(1))
                    telephoneRepository.insertTelephoneData(
                        numberTelephone,
                        iccID
                    )
                }
                workbook.close()
            }
            Toast.makeText(context, "База данных успешно загружена", Toast.LENGTH_LONG).show()

        }
        catch (e: Exception) {
            Toast.makeText(context, "Ошибка: $e", Toast.LENGTH_LONG).show()
        }
    }

    override fun writeActFromExcel(listActs: List<Act>, fileName: String) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Act")

        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Лицевой счет")
        headerRow.createCell(1).setCellValue("Адрес")
        headerRow.createCell(2).setCellValue("ФИО")
        headerRow.createCell(3).setCellValue("Тип старого ПУ")
        headerRow.createCell(4).setCellValue("Номер старого ПУ")
        headerRow.createCell(5).setCellValue("Тип старого ТТ")
        headerRow.createCell(6).setCellValue("Номер старого ТТ")
        headerRow.createCell(7).setCellValue("Причина замены")
        headerRow.createCell(8).setCellValue("Номер заказа")
        headerRow.createCell(9).setCellValue("Тип нового ПУ")
        headerRow.createCell(10).setCellValue("Номер нового ПУ")
        headerRow.createCell(11).setCellValue("Тип нового ТТ")
        headerRow.createCell(12).setCellValue("Номер нового ТТ")
        headerRow.createCell(13).setCellValue("Пломба №1")
        headerRow.createCell(14).setCellValue("Пломба №2")
        headerRow.createCell(15).setCellValue("Пломба №3")
        headerRow.createCell(16).setCellValue("Sim-карта")
        headerRow.createCell(17).setCellValue("Дата выполнения")
        headerRow.createCell(18).setCellValue("ФИО исполнителя")
        headerRow.createCell(19).setCellValue("Место установки")
        headerRow.createCell(20).setCellValue("Примечание")

        for ((index, act) in listActs.withIndex()) {
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(act.accountNumber)
            row.createCell(1).setCellValue(act.address)
            row.createCell(2).setCellValue(act.fullName)
            row.createCell(3).setCellValue(act.typeOldPU)
            row.createCell(4).setCellValue(act.numberOldPU)
            row.createCell(5).setCellValue(act.typeOldTT)
            row.createCell(6).setCellValue(act.numberOldTT)
            row.createCell(7).setCellValue(act.reasonReplacement)
            row.createCell(8).setCellValue(act.numberApplication)
            row.createCell(9).setCellValue(act.typeNewPU)
            row.createCell(10).setCellValue(act.numberNewPU)
            row.createCell(11).setCellValue(act.typeNewTT)
            row.createCell(12).setCellValue(act.numberNewTT)
            row.createCell(13).setCellValue(act.sealPUOne)
            row.createCell(14).setCellValue(act.sealPUTwo)
            row.createCell(15).setCellValue(act.sealPUThree)
            row.createCell(16).setCellValue(act.simCard)
            row.createCell(17).setCellValue(act.dateCompletion)
            row.createCell(18).setCellValue(act.fullNameExecutor)
            row.createCell(19).setCellValue(act.installationLocation)
            row.createCell(20).setCellValue(act.comment)
        }

        for (i in 0 until headerRow.lastCellNum) {
            sheet.autoSizeColumnByData(i)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Для Android 10 и выше используем MediaStore
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.xlsx")
                put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
            }

            val uri = context.contentResolver.insert(MediaStore.Files.getContentUri("external"), values)

            uri?.let {
                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    workbook.write(outputStream)
                    workbook.close()
                    outputStream.flush()
                }
            }
        } else {
            // Для Android 9 и ниже сохраняем файл в обычной файловой системе
            val filePath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath + "/$fileName.xlsx"
            val fileOutputStream = FileOutputStream(filePath)
            workbook.write(fileOutputStream)
            workbook.close()
            fileOutputStream.flush()
            fileOutputStream.close()
        }

        Toast.makeText(context, "Данные успешно сохранены", Toast.LENGTH_LONG).show()

    }

    fun org.apache.poi.ss.usermodel.Sheet.autoSizeColumnByData(column: Int) {
        var maxLength = 0

        // Пройтись по всем строкам и найти самую длинную строку
        for (row in 0..lastRowNum) {
            val cell = getRow(row)?.getCell(column)
            if (cell != null) {
                val length = cell.stringCellValue.length
                if (length > maxLength) {
                    maxLength = length
                }
            }
        }

        // Устанавливаем ширину колонки на основе максимальной длины строки
        setColumnWidth(column, (maxLength + 2) * 256) // +2 для отступов
    }


}