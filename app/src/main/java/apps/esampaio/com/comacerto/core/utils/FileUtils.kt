package apps.esampaio.com.comacerto.core.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v4.content.FileProvider
import android.webkit.MimeTypeMap
import android.widget.Toast
import apps.esampaio.com.comacerto.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileUtils {

    companion object {
        @Throws(IOException::class)
        fun saveDataToFile(fileName:String, data: ByteArray) : File{
            val file = createFile(fileName)
            val out = FileOutputStream(file)
            out.write(data)
            out.close()
            return file
        }

        fun createFile(fileName: String) : File {
            val root = Environment.getExternalStorageDirectory().toString()
            val myDir = File(root)
            myDir.mkdirs()
            return File(myDir, fileName)
        }

        fun shareFile(context:Context, file: File, filetype: String) {
            val myMime = MimeTypeMap.getSingleton()
            val intent = Intent(Intent.ACTION_SEND)
            val mimeType = myMime.getMimeTypeFromExtension(filetype)
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                val fileURI = FileProvider.getUriForFile(context!!,
                        BuildConfig.APPLICATION_ID + ".provider",
                        file)
                intent.setDataAndType(fileURI, mimeType)

            } else {
                intent.setDataAndType(Uri.fromFile(file), mimeType)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "No Application found to open this type of file.", Toast.LENGTH_LONG).show()

            }

        }

    }
}