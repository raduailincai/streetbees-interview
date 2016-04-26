package ailincai.radu.raduailincai;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ailincai.radu.raduailincai.adapters.ComicsAdapter;
import ailincai.radu.raduailincai.content.Marvel;
import ailincai.radu.raduailincai.dialogs.LoadingDialog;
import ailincai.radu.raduailincai.model.Comic;

public class MainActivity extends Activity implements Marvel.MarvelListener {

    public static final int MINIMUM_ELEMENTS_OFFSET = 0;
    public static final int DEFAULT_COMICS_PER_PAGE_LIMIT = 25;

    private ListView listView;
    private Button previousComicsButton;
    private Button nextComicsButton;
    private TextView statusField;
    private LoadingDialog loadingDialog;

    private int currentComicsOffset = MINIMUM_ELEMENTS_OFFSET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewReferences();
        addViewListeners();
        populateViewsWithData();
    }

    private void initViewReferences() {
        listView = (ListView) findViewById(R.id.main_screen_list_view);
        previousComicsButton = (Button) findViewById(R.id.main_screen_previous_comics_button);
        nextComicsButton = (Button) findViewById(R.id.main_screen_next_comics_button);
        statusField = (TextView) findViewById(R.id.main_screen_status_field);
    }

    private void addViewListeners() {
        previousComicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentComicsOffset >= DEFAULT_COMICS_PER_PAGE_LIMIT) {
                    currentComicsOffset -= DEFAULT_COMICS_PER_PAGE_LIMIT;
                    populateViewsWithData();
                }
            }
        });
        nextComicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentComicsOffset += DEFAULT_COMICS_PER_PAGE_LIMIT;
                populateViewsWithData();
            }
        });
    }

    private void populateViewsWithData() {
        showLoadingDialog();
        int startIndex = currentComicsOffset;
        int endIndex = currentComicsOffset + DEFAULT_COMICS_PER_PAGE_LIMIT;
        statusField.setText(String.format(getString(R.string.main_screen_status_message_template), startIndex, endIndex));
        Marvel.getInstance().fetchComics(this.currentComicsOffset, DEFAULT_COMICS_PER_PAGE_LIMIT, this);
    }

    @Override
    public void onSuccess(ArrayList<Comic> comics) {
        dismissLoadingDialog();
        refreshListView(comics);
    }

    @Override
    public void onError() {
        dismissLoadingDialog();
    }

    private void refreshListView(ArrayList<Comic> comics) {
        ComicsAdapter comicsAdapter = new ComicsAdapter(comics);
        listView.setAdapter(comicsAdapter);
    }

    private void showLoadingDialog() {
        dismissLoadingDialog();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
