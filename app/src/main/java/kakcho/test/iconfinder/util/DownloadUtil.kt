package kakcho.test.iconfinder.util

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import kakcho.test.core.utils.Constants


/**
 * Created by Kamal Nayan on 26-09-2021 at 02:39
 */
class DownloadUtil(private val context: Context) {

    fun download(url: String, fileExtension: String = ".png", downloadId: (Long) -> Unit) {
        val manager: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val filePath =
            Constants.Directories.DOWNLOAD_DIRECTORY + System.currentTimeMillis().toString()+fileExtension
        val request = configureDownload(url,filePath)
        val reference: Long = manager.enqueue(request) ?: -1L
        downloadId(reference)
    }


    private fun configureDownload(
        url: String,
        filePath: String,
    ): DownloadManager.Request {
        val uri = Uri.parse(url)
        val downloadRequest = DownloadManager.Request(uri)
        downloadRequest
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                filePath,
            )
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        or DownloadManager.Request.NETWORK_MOBILE
            )
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setTitle("Downloading icon")
            .setDescription("Your selected icon is being downloaded.")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        return downloadRequest
    }

}