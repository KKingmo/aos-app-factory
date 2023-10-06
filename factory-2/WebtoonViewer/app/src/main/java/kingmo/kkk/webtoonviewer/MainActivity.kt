package kingmo.kkk.webtoonviewer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kingmo.kkk.webtoonviewer.databinding.ActivityMainBinding

/**
 * 메인 액티비티로, 탭 레이아웃과 뷰페이저를 통해 웹툰 관련 페이지를 표시합니다.
 * OnTabLayoutNameChanged 인터페이스를 구현하여 탭 이름 변경 시 콜백을 받습니다.
 */
class MainActivity : AppCompatActivity(), OnTabLayoutNameChanged {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 공유 환경설정에서 탭 이름을 가져옵니다.
        val sharedPreference =
            getSharedPreferences(WebViewFragment.Companion.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val tab0 = sharedPreference?.getString("tab0_name", "웹툰메인")
        val tab1 = sharedPreference?.getString("tab1_name", "신작웹툰")
        val tab2 = sharedPreference?.getString("tab2_name", "인기웹툰")

        // 뷰페이저 어댑터 설정
        binding.viewPager.adapter = ViewPagerAdapter(this)

        // 탭 레이아웃과 뷰페이저를 연결하고 탭 이름을 설정합니다.
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            run {
                tab.text = when (position) {
                    0 -> tab0
                    1 -> tab1
                    else -> tab2
                }
            }
        }.attach()
    }

    // 뒤로 가기 버튼을 눌렀을 때 현재 프래그먼트의 웹뷰 내에서 뒤로 갈 수 있으면 그렇게 하고, 그렇지 않으면 액티비티를 종료합니다.
    override fun onBackPressed() {
        val currentFragment =
            supportFragmentManager.fragments[binding.viewPager.currentItem]
        if (currentFragment is WebViewFragment) {
            if (currentFragment.canGoBack()) {
                currentFragment.goBack()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    // 탭 이름이 변경될 때 해당 탭의 이름을 업데이트합니다.
    override fun nameChanged(position: Int, name: String) {
        val tab = binding.tabLayout.getTabAt(position)
        tab?.text = name
    }
}