package simplestart.fmore.ru.apps.model;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.io.Serializable;

import simplestart.fmore.ru.apps.R;

/**
 * Created by Elizarov Sergey (sergey.elizarov@altarix.ru)
 * on 01.12.14.
 */
public class Start implements Serializable {
    private long id;
    private String name;
    private String start;
    private String app;

    public Start(Cursor c) {
        if (c != null) {
            id = c.getLong(c.getColumnIndex(BaseColumns._ID));
            name = c.getString(c.getColumnIndex(C.Start.NAME));
            start = c.getString(c.getColumnIndex(C.Start.START));
            app = c.getString(c.getColumnIndex(C.Start.APP));
        }
    }

    public Start(String name, String start, String app) {
        this.name = name;
        this.start = start;
        this.app = app;
    }

    public Start() {
        id = -1;
    }

    public boolean isEmpty() {
        return name == null || TextUtils.isEmpty(name) || "null".equalsIgnoreCase(name)
                || start == null || TextUtils.isEmpty(start) || "null".equalsIgnoreCase(start)
                || app == null || TextUtils.isEmpty(app) || "null".equalsIgnoreCase(app);
    }

    public ContentValues asContentValues() {
        ContentValues result = new ContentValues(3);
        result.put(C.Start.NAME, name);
        result.put(C.Start.START, start);
        result.put(C.Start.APP, app);
        return result;
    }

    public boolean compareApp(Start other) {
        boolean result = false;
        if (other != null) {
            result = app.equalsIgnoreCase(other.getApp());
        }
        return result;
    }

    public long getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getName() {
        return name;
    }

    public String getApp() {
        return app;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void start(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(start));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception ignored) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(context.getString(R.string.error_message_not_found_app));
            builder.setPositiveButton(context.getString(R.string.btn_continue), null);
            builder.show();
        }
    }

    public boolean compareByApp(Start other) {
        return other != null && app.equalsIgnoreCase(other.app);
    }
}
