package ailincai.radu.raduailincai;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ailincai.radu.raduailincai.adapters.ComicsAdapter;
import ailincai.radu.raduailincai.content.Marvel;
import ailincai.radu.raduailincai.dialogs.LoadingDialog;
import ailincai.radu.raduailincai.model.Comic;

public class MainActivity extends Activity implements Marvel.MarvelListener {

    private ListView listView;
    private Button previousComicsButton;
    private Button nextComicsButton;
    private LoadingDialog loadingDialog;

    private int currentComicsOffset = 0;
    private int comicsPerPageLimit = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewReferences();
        addListenersForButtons();
        refreshData();
    }

    private void refreshData() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        Marvel.getInstance().fetchComics(currentComicsOffset, comicsPerPageLimit, this);
    }

    private void initViewReferences() {
        listView = (ListView) findViewById(R.id.main_screen_list_view);
        previousComicsButton = (Button) findViewById(R.id.main_screen_previous_comics_button);
        nextComicsButton = (Button) findViewById(R.id.main_screen_next_comics_button);
    }

    private void addListenersForButtons() {
        previousComicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentComicsOffset -= comicsPerPageLimit;
                if (currentComicsOffset < 0) {
                    currentComicsOffset = 0;
                }
                refreshData();
            }
        });
        nextComicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentComicsOffset += comicsPerPageLimit;
                refreshData();
            }
        });
    }

    @Override
    public void onSuccess(ArrayList<Comic> comics) {
        loadingDialog.dismiss();
        ComicsAdapter comicsAdapter = new ComicsAdapter(comics);
        listView.setAdapter(comicsAdapter);
    }

    @Override
    public void onError() {
        loadingDialog.dismiss();
    }
}
