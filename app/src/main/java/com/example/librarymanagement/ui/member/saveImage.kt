package com.example.librarymanagement.ui.member

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun saveImageToInternalStorage(context: Context, sourceUri: Uri, fileName: String): Uri {
    val file = File(context.filesDir, fileName)
    context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    return Uri.fromFile(file) // Trả về URI của tệp đã lưu
}

fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap, fileName: String): Uri {
    val filesDir = context.filesDir
    val imageFile = File(filesDir, fileName)
    FileOutputStream(imageFile).use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    }
    return Uri.fromFile(imageFile)
}
