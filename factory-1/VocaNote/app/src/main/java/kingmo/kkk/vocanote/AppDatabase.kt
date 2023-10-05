package kingmo.kkk.vocanote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * AppDatabase 클래스는 앱의 로컬 데이터베이스를 나타냅니다.
 * 이 클래스는 Room 라이브러리를 사용하여 SQLite 데이터베이스를 추상화하며,
 * Word 엔터티에 대한 CRUD 작업을 수행하기 위한 DAO를 제공합니다.
 */
@Database(entities = [Word::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /**
     * WordDao에 대한 추상 메서드입니다.
     * 이 메서드를 통해 Word 엔터티에 대한 데이터베이스 작업을 수행할 수 있습니다.
     */
    abstract fun wordDao(): WordDao

    /**
     * AppDatabase의 싱글턴 인스턴스를 반환합니다.
     * 인스턴스가 없는 경우, 새로운 데이터베이스 인스턴스를 생성합니다.
     * @param context 애플리케이션 컨텍스트.
     * @return AppDatabase의 싱글턴 인스턴스.
     */
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app-database.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}