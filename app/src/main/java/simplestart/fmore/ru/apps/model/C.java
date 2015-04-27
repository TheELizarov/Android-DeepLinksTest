package simplestart.fmore.ru.apps.model;

import android.net.Uri;

/**
 * Created by Elizarov Sergey (sergey.elizarov@altarix.ru)
 * on 01.12.14.
 */
public interface C {
    String AUTHORITY = "simplestart.fmore.ru.apps.dbprovider";
    String TABLE_START = "start";
    Uri URI_START =  Uri.parse("content://" + AUTHORITY + "/" + TABLE_START);

    public interface Start {
        String START = "start";
        String NAME = "name";
        String APP = "app";
    }
}
