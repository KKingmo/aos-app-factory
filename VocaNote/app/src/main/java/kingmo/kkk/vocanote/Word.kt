package kingmo.kkk.vocanote

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Word 클래스는 단어의 정보를 나타내는 데이터 클래스입니다.
 * 이 클래스는 Room 라이브러리를 사용하여 데이터베이스에 저장되는 엔터티로도 정의되어 있습니다.
 * 또한, Parcelable 인터페이스를 구현하여 객체를 Intent에 첨부하거나 다른 컴포넌트와 통신할 때 사용할 수 있습니다.
 *
 * @property text 단어의 텍스트.
 * @property mean 단어의 뜻.
 * @property type 단어의 유형 (예: 명사, 동사 등).
 * @property id 단어의 고유 식별자. 자동생성.
 */
@Parcelize
@Entity(tableName = "word")
data class Word(
    val text: String,
    val mean: String,
    val type: String,
    @PrimaryKey(autoGenerate = true)val id: Int = 0
) : Parcelable
