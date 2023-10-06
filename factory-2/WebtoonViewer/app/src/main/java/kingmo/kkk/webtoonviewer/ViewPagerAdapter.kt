package kingmo.kkk.webtoonviewer

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * MainActivity의 뷰페이저에 사용되는 어댑터입니다.
 * 각 페이지는 WebViewFragment로 웹툰 관련 웹 페이지를 표시합니다.
 */
class ViewPagerAdapter(private val mainActivity: MainActivity) :
    FragmentStateAdapter(mainActivity) {

    // 뷰페이저의 총 페이지 수를 반환합니다.
    override fun getItemCount(): Int {
        return 3
    }

    // 주어진 위치에 해당하는 프래그먼트를 생성하고 반환합니다.
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                return WebViewFragment(
                    position,
                    "https://comic.naver.com/webtoon"
                ).apply {
                    listener = mainActivity
                }
            }
            1 -> {
                return WebViewFragment(
                    position,
                    "https://comic.naver.com/webtoon?tab=new"
                ).apply {
                    listener = mainActivity
                }
            }
            else -> {
                return WebViewFragment(
                    position,
                    "https://comic.naver.com/webtoon?tab=finish"
                ).apply {
                    listener = mainActivity
                }
            }
        }
    }
}