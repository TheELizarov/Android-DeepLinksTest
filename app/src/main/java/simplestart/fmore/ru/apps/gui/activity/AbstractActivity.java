package simplestart.fmore.ru.apps.gui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.WindowManager;


/**
 * Инкапсулирует общие, нужные методы
 */
public abstract class AbstractActivity extends ActionBarActivity {
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * По умолчанию задаем кнопку home
         */
        setHome(true);
    }

    /**
     * Показывать ли в actionBar кнопку home
     * @param enable - true - да, показывать
     */
    protected void setHome(boolean enable) {
        getSupportActionBar().setHomeButtonEnabled(enable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        findViews();
    }

    protected void findViews() {
    }

    protected void startProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
    }

    protected void stopProgress() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (WindowManager.BadTokenException ignored) {

        }
    }
}
