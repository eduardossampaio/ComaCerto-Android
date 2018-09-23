package apps.esampaio.com.comacerto.view.custom.pdfviewpager

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.esampaio.com.comacerto.R
import kotlinx.android.synthetic.main.pdf_view_pager_fragment.*
import java.util.zip.Inflater

/**
 * Created by eduar on 23/09/2018.
 */

class PDFViewPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pdf_view_pager_fragment,null,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmap = arguments?.getParcelable(PARAM_PDF_PAGE) as Bitmap
        if (bitmap != null) {
            pdf_page_bitmap.setImageBitmap(bitmap)
        }
    }

    companion object {
        private val PARAM_PDF_PAGE = "PARAM_PDF_PAGE"

        fun newInstance(pdfPage: Bitmap): PDFViewPagerFragment {

            val args = Bundle()
            args.putParcelable(PARAM_PDF_PAGE, pdfPage)
            val fragment = PDFViewPagerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
