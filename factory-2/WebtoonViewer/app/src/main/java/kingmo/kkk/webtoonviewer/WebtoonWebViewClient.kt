package kingmo.kkk.webtoonviewer

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

/**
 * 웹툰 관련 웹 페이지를 로드할 때의 WebViewClient입니다.
 * 페이지 로딩 상태에 따라 ProgressBar의 가시성을 변경하며,
 * 특정 URL에 대한 처리를 수행합니다.
 */
class WebtoonWebViewClient(
    private val progressBar: ProgressBar, // 웹 페이지 로딩 상태를 표시하는 ProgressBar
    private val saveData: (String) -> Unit, // 특정 URL 정보를 저장하기 위한 콜백 함수
) : WebViewClient() {

    // 웹페이지 로딩 중 특정 URL을 가로채는 메서드
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        // 만약 로딩 중인 URL이 웹툰 상세 페이지라면
        if (request != null && request.url.toString().contains("comic.naver.com/webtoon/detail")) {
            // 해당 URL 정보를 저장
            saveData.invoke(request.url.toString())
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    // 웹페이지 로딩이 완료되었을 때 호출되는 메서드
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        // ProgressBar를 숨김
        progressBar.visibility = View.GONE
    }

    // 웹페이지 로딩이 시작될 때 호출되는 메서드
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        // ProgressBar를 표시
        progressBar.visibility = View.VISIBLE
    }
}