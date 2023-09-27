package kingmo.kkk.vocanote

/**
 * Word 단어의 정보를 표현하는 데이터 클래스입니다.
 *
 * @property text 단어의 텍스트.
 * @property mean 단어의 의미.
 * @property type 단어의 타입.
 */
data class Word(
    val text: String,
    val mean: String,
    val type: String,
)
