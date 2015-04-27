package simplestart.fmore.ru.apps.gui.activity;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import simplestart.fmore.ru.apps.R;
import simplestart.fmore.ru.apps.gui.fragment.StartListFragment;


public class StartsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starts);
        setStartList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.starts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            StartEditActivity.startActivity(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setStartList() {
        setFragment(StartListFragment.newInstance());
    }


    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }
}
