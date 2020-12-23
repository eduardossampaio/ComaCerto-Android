package apps.esampaio.com.comacerto.view.custom.pdfviewpager

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.View
import java.io.File

/**
 * Created by eduar on 23/09/2018.
 */

class PDFViewPagerAdapter(fm: FragmentManager, filePath : File) : FragmentPagerAdapter(fm) {
    var pdfRenderer : PdfRenderer
    init {
        var fileDescriptor: ParcelFileDescriptor? = null
        fileDescriptor = ParcelFileDescriptor.open(filePath, ParcelFileDescriptor.MODE_READ_ONLY)

        pdfRenderer = PdfRenderer(fileDescriptor!!)
    }

    override fun getItem(pageNumber: Int): Fragment? {
        val rendererPage = pdfRenderer.openPage(pageNumber)
        val rendererPageWidth = rendererPage.width
        val rendererPageHeight = rendererPage.height
        val bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888)
        rendererPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        rendererPage.close()
        return PDFViewPagerFragment.newInstance(bitmap)
    }

    override fun getCount(): Int {
        return pdfRenderer.pageCount
    }

}
