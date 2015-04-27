package simplestart.fmore.ru.apps.gui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import simplestart.fmore.ru.apps.R;
import simplestart.fmore.ru.apps.gui.activity.StartEditActivity;
import simplestart.fmore.ru.apps.manager.DbManager;
import simplestart.fmore.ru.apps.model.C;
import simplestart.fmore.ru.apps.model.Start;

/**
 * Created by Elizarov Sergey (sergey.elizarov@altarix.ru)
 * on 01.12.14.
 */
public class StartListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 1;

    public static StartListFragment newInstance() {
        return new StartListFragment();
    }

    private CursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StartAdapter(getActivity(), null);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_starts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Start start = DbManager.loadStart(getActivity(), id);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(String.format(getString(R.string.title_choose), start.getName()));
                builder.setNegativeButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbManager.delete(getActivity(), start);
                    }
                });
                builder.setNeutralButton(getString(R.string.change), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StartEditActivity.startActivity(getActivity(), start);
                    }
                });
                builder.setPositiveButton(getString(R.string.start_app), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        start.start(getActivity());
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ActionBarActivity)getActivity()).setTitle(R.string.app_name);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), C.URI_START, null, null, null, C.Start.APP + " desc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        setCursor(null);
    }

    private void setCursor(Cursor cursor) {
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged();
    }

    private class StartAdapter extends CursorAdapter {
        private static final int VIEW_TYPE_COUNT = 2;
        private static final int HEADER = 0;
        private static final int ROW = 1;

        public StartAdapter(Context context, Cursor c) {
            super(context, c, true);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            ViewHolder vh = new ViewHolder();
            View v = null;
            switch (getItemViewType(cursor.getPosition())) {
                case HEADER:
                    v = View.inflate(context, R.layout.item_header, null);
                    vh.header = (TextView) v.findViewById(R.id.header);
                    break;
                case ROW:
                    v = View.inflate(context, R.layout.item, null);
                    break;
            }
            vh.title = (TextView) v.findViewById(R.id.title);
            v.setTag(vh);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder vh = (ViewHolder) view.getTag();
            Start start = new Start(cursor);
            vh.title.setText(start.getName() + "\n" + start.getStart());
            if (getItemViewType(cursor.getPosition()) == HEADER) {
                vh.header.setText(start.getApp());
            }
        }

        private class ViewHolder {
            TextView header, title;
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            int result = HEADER;
            if (position > 0) {
                getCursor().moveToPosition(position);
                Start current = new Start(getCursor());
                getCursor().moveToPrevious();
                Start prev = new Start(getCursor());
                getCursor().moveToPosition(position);
                if (current.compareByApp(prev)) {
                    result = ROW;
                }
            }
            return result;
        }
    }
}
