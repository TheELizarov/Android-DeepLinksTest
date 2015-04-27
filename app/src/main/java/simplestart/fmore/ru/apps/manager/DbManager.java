package simplestart.fmore.ru.apps.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import simplestart.fmore.ru.apps.model.C;
import simplestart.fmore.ru.apps.model.Start;

/**
 * Created by Elizarov Sergey (sergey.elizarov@altarix.ru)
 * on 01.12.14.
 */
public class DbManager {
    public static Start loadStart(Context context, long id) {
        Start result = null;
        ContentResolver cr = context.getContentResolver();
        String[] selection = null;
        String where = BaseColumns._ID + "=?";
        String[] args = new String[] {String.valueOf(id)};
        String orderBy = null;
        Cursor c = cr.query(C.URI_START, selection, where, args, orderBy);
        if (c != null && c.moveToFirst()) {
            result = new Start(c);
        }
        return result;
    }

    public static int update(Context context, Start start) {
        ContentResolver cr = context.getContentResolver();
        String where = BaseColumns._ID + "=?";
        String[] args = new String[] {String.valueOf(start.getId())};
        return cr.update(C.URI_START, start.asContentValues(), where, args);
    }

    public static void insert(Context context, Start start) {
        ContentResolver cr = context.getContentResolver();
        cr.insert(C.URI_START, start.asContentValues());
    }

    public static void add(Context context, Start start) {
        if (update(context, start) == 0) {
            insert(context, start);
        }
    }

    public static void delete(Context context, Start start) {
        ContentResolver cr = context.getContentResolver();
        String where = BaseColumns._ID + "=?";
        String[] args = new String[] {String.valueOf(start.getId())};
        cr.delete(C.URI_START, where, args);
    }
}
