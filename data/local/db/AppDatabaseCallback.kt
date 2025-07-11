package com.example.travelapp.data.local.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DestinationDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_DESTINATIONS (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_NAME TEXT,
                $COLUMN_IMAGE_URL TEXT,
                $COLUMN_DESCRIPTION TEXT
            );
        """.trimIndent()

        db.execSQL(createTable)

        db.execSQL("""
        INSERT INTO $TABLE_DESTINATIONS VALUES 
        (1, 'Hà Nội', 'https://images.unsplash.com/photo-1589712851463-9a47cacd310b', 'Thủ đô nghìn năm văn hiến với Hồ Gươm, phố cổ và các di tích lịch sử.'),
        (2, 'TP.HCM', 'https://images.unsplash.com/photo-1591931891377-dc8503cdb582', 'Thành phố sôi động và hiện đại với nhịp sống năng động, nhiều điểm tham quan.'),
        (3, 'Hội An', 'https://images.unsplash.com/photo-1596436889106-be35e843f974', 'Phố cổ được UNESCO công nhận, nổi tiếng với đèn lồng và di sản văn hóa.'),
        (4, 'Huế', 'https://images.unsplash.com/photo-1581843612079-73e38dfd7f9b', 'Cố đô với hoàng thành, lăng tẩm và nhã nhạc cung đình đặc sắc.'),
        (5, 'Mộc Châu', 'https://images.unsplash.com/photo-1601441754989-8d867c4e2b52', 'Cao nguyên xanh với đồi chè, hoa mận và văn hóa dân tộc đặc trưng.'),
        (6, 'Sapa', 'https://images.unsplash.com/photo-1624252480482-5377db93c730', 'Vùng núi nổi tiếng với ruộng bậc thang, bản làng và đỉnh Fansipan.'),
        (7, 'Nha Trang', 'https://images.unsplash.com/photo-1582297427420-f5792c3a67c2', 'Thành phố biển đẹp, có nhiều bãi tắm, đảo và khu nghỉ dưỡng.'),
        (8, 'Phú Quốc', 'https://images.unsplash.com/photo-1571885612322-219a1d3d06ce', 'Đảo ngọc với bãi biển trong xanh, hải sản tươi sống và hoàng hôn tuyệt đẹp.'),
        (9, 'Đà Nẵng', 'https://images.unsplash.com/photo-1587825140708-2455e4a2cbb2', 'Thành phố đáng sống với bãi biển Mỹ Khê, cầu Rồng, và Bà Nà Hills.'),
        (10, 'Vịnh Hạ Long', 'https://images.unsplash.com/photo-1549887534-4d8c20b86aa6', 'Kỳ quan thiên nhiên thế giới với hàng ngàn đảo đá vôi kỳ vĩ.'),
        (11, 'Cần Thơ', 'https://images.unsplash.com/photo-1625126609939-1f46570b8f4b', 'Thành phố miền Tây với chợ nổi Cái Răng và văn hóa sông nước.'),
        (12, 'Tây Ninh', 'https://images.unsplash.com/photo-1601104810152-92dbab2795d7', 'Nổi bật với núi Bà Đen và đạo Cao Đài, điểm đến tâm linh đặc sắc.'),
        (13, 'Bình Thuận', 'https://images.unsplash.com/photo-1622128253073-8c233f2052ef', 'Nơi có Mũi Né, đồi cát bay, biển xanh và nắng gió quanh năm.'),
        (14, 'Ninh Bình', 'https://images.unsplash.com/photo-1585655851325-4b13f09c2a6a', 'Danh thắng Tràng An, Tam Cốc với sông núi hoang sơ tuyệt đẹp.'),
        (15, 'Đà Lạt', 'https://images.unsplash.com/photo-1574672281927-6e989b09e9d6', 'Thành phố ngàn hoa với khí hậu mát mẻ và nhiều điểm du lịch lãng mạn.')
    """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DESTINATIONS")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "destination.db"
        const val DATABASE_VERSION = 1

        const val TABLE_DESTINATIONS = "destinations"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE_URL = "imageUrl"
        const val COLUMN_DESCRIPTION = "description"
    }
}
