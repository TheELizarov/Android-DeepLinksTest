package simplestart.fmore.ru.apps.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import simplestart.fmore.ru.apps.R;
import simplestart.fmore.ru.apps.utils.IOUtils;


public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "quickstart.sqlite";
    private static final int DB_VERSION = 4;

    private Context context;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = IOUtils.readResourceAsString(context, R.raw.create_db);
        String[] strings = sql.split(";");
        executeStatements(strings, db);

        String insertSql = IOUtils.readResourceAsString(context, R.raw.insert_db);
        String[] insertStrings = insertSql.split(";");
        executeStatements(insertStrings, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            String sql = IOUtils.readResourceAsString(context, R.raw.delete_db);
            String[] strings = sql.split(";");
            executeStatements(strings, db);

            onCreate(db);
        }
    }

    private void executeStatements(String[] strings, SQLiteDatabase db) {
        for (String string : strings) {
            String str = string.replace("\n", "").trim().replace("\t", "");
            if (str.length() > 0) {
                db.execSQL(str);
            }
        }
    }
}
