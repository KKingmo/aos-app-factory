package kingmo.kkk.webtoonviewer

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import kingmo.kkk.webtoonviewer.databinding.FragmentWebviewBinding

/**
 * 웹툰을 보여주기 위한 WebView를 포함하는 Fragment입니다.
 * 사용자는 웹툰을 보면서 탭 이름을 변경하거나 마지막 저장된 페이지로 돌아갈 수 있습니다.
 */
class WebViewFragment(private val position: Int, private val webViewUrl: String) : Fragment() {
    var listener: OnTabLayoutNameChanged? = null

    private lateinit var binding: FragmentWebviewBinding

    companion object {
        // 웹툰의 URL 기록을 저장하기 위한 SharedPreference의 이름
        const val SHARED_PREFERENCE = "WEB_HISTORY"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 뷰 바인딩을 사용하여 레이아웃을 inflate합니다.
        binding = FragmentWebviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // WebView 설정
        binding.webView.webViewClient = WebtoonWebViewClient(binding.progressBar) { url ->
            // 웹툰의 URL을 SharedPreference에 저장합니다.
            activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)?.edit {
                putString("tab$position", url)
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(webViewUrl)

        // 마지막 저장된 페이지로 돌아가기 버튼의 클릭 리스너 설정
        binding.backToLastButton.setOnClickListener {
            val sharedPreference =
                activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)
            val url = sharedPreference?.getString("tab$position", "")
            if (url.isNullOrEmpty()) {
                Toast.makeText(context, "마지막 저장 시점이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                binding.webView.loadUrl(url)
            }
        }

        // 탭 이름 변경 버튼의 클릭 리스너 설정
        binding.changeTabNameButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            val editText = EditText(context)

            dialog.setView(editText)
            dialog.setPositiveButton("저장") { _, _ ->
                // 변경된 탭 이름을 SharedPreference에 저장하고 리스너를 통해 알립니다.
                activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)?.edit {
                    putString("tab${position}_name", editText.text.toString())
                    listener?.nameChanged(position, editText.text.toString())
                }
            }
            dialog.setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }

            dialog.show()
        }
    }

    // WebView가 뒤로 갈 수 있는지 확인하는 메서드
    fun canGoBack(): Boolean {
        return binding.webView.canGoBack()
    }

    // WebView를 뒤로 이동시키는 메서드
    fun goBack() {
        binding.webView.goBack()
    }
}

// 탭 이름이 변경될 때 호출되는 인터페이스
interface OnTabLayoutNameChanged {
    fun nameChanged(position: Int, name: String)
}