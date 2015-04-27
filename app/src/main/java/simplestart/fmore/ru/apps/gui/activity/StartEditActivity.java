package simplestart.fmore.ru.apps.gui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import simplestart.fmore.ru.apps.R;
import simplestart.fmore.ru.apps.manager.DbManager;
import simplestart.fmore.ru.apps.model.Start;

/**
 * Created by Elizarov Sergey (sergey.elizarov@altarix.ru)
 * on 01.12.14.
 */
public class StartEditActivity extends AbstractActivity {
    public static final String EXTRA_START = "extra_start";

    public static void startActivity(Context context) {
        Intent i = new Intent(context, StartEditActivity.class);
        context.startActivity(i);
    }

    public static void startActivity(Context context, Start start) {
        Intent i = new Intent(context, StartEditActivity.class);
        i.putExtra(EXTRA_START, start);
        context.startActivity(i);
    }

    private EditText name, start, app;

    private Start newStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        newStart = (Start) getIntent().getSerializableExtra(EXTRA_START);
        if (newStart != null && !newStart.isEmpty()) {
            name.setText(newStart.getName());
            start.setText(newStart.getStart());
            app.setText(newStart.getApp());
        } else {
            newStart = new Start();
        }
    }

    @Override
    protected void findViews() {
        name = (EditText) findViewById(R.id.name);
        start = (EditText) findViewById(R.id.start);
        app = (EditText) findViewById(R.id.app);
        Button go = (Button) findViewById(R.id.execute);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newStart.setName(name.getText().toString());
                newStart.setStart(start.getText().toString());
                newStart.setApp(app.getText().toString());
                if (newStart.isEmpty()) {
                    Toast.makeText(StartEditActivity.this, "Add data to fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                DbManager.add(StartEditActivity.this, newStart);
                finish();
            }
        });
    }
}
