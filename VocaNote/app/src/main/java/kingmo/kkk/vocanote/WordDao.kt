package kingmo.kkk.vocanote

import androidx.room.*

/**
 * 이 인터페이스는 Room 라이브러리를 사용하여 데이터베이스에 접근하는 메서드들을 정의합니다.
 */
@Dao
interface WordDao {
    /**
     * 모든 단어를 ID의 내림차순으로 조회합니다.
     * @return 단어 목록.
     */
    @Query("SELECT * from word ORDER BY id DESC")
    fun getAll(): List<Word>

    /**
     * 가장 최근에 추가된 단어를 조회합니다.
     * @return 최근에 추가된 단어.
     */
    @Query("SELECT * from word ORDER BY id DESC LIMIT 1")
    fun getLatestWord() : Word

    /**
     * 새로운 단어를 데이터베이스에 추가합니다.
     * @param word 추가할 단어 객체.
     */
    @Insert
    fun insert(word: Word)

    /**
     * 데이터베이스에서 특정 단어를 삭제합니다.
     * @param word 삭제할 단어 객체.
     */
    @Delete
    fun delete(word: Word)

    /**
     * 데이터베이스의 특정 단어 정보를 업데이트합니다.
     * @param word 업데이트할 단어 객체.
     */
    @Update
    fun update(word: Word)
}