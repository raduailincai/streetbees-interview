package ailincai.radu.raduailincai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ailincai.radu.raduailincai.adapters.ComicWrapper;
import ailincai.radu.raduailincai.adapters.ComicsAdapter;
import ailincai.radu.raduailincai.content.Marvel;
import ailincai.radu.raduailincai.content.cache.UserData;
import ailincai.radu.raduailincai.dialogs.LoadingDialog;
import ailincai.radu.raduailincai.dialogs.MessageDialog;
import ailincai.radu.raduailincai.model.Comic;

public class MainActivity extends Activity implements Marvel.MarvelListener {

    private static final int MINIMUM_ELEMENTS_OFFSET = 0;
    private static final int DEFAULT_COMICS_PER_PAGE_LIMIT = 25;
    private static final int SELECT_PICTURE_REQUEST_CODE = 1;

    private ListView listView;
    private Button previousComicsButton;
    private Button nextComicsButton;
    private TextView statusField;
    private LoadingDialog loadingDialog;

    private int currentComicsOffset = MINIMUM_ELEMENTS_OFFSET;
    private ArrayList<Comic> onScreenComics;
    private Comic lastSelectedComic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationContext.init(this);

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastSelectedComic = onScreenComics.get(position);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE_REQUEST_CODE);
            }
        });
    }

    private void populateViewsWithData() {
        showLoadingDialog();
        updateStatusField();
        Marvel.getInstance().fetchComics(this.currentComicsOffset, DEFAULT_COMICS_PER_PAGE_LIMIT, this);
    }

    private void updateStatusField() {
        int startIndex = currentComicsOffset;
        int endIndex = currentComicsOffset + DEFAULT_COMICS_PER_PAGE_LIMIT;
        statusField.setText(String.format(getString(R.string.main_screen_status_message_template), startIndex, endIndex));
    }

    @Override
    public void onSuccess(ArrayList<Comic> comics) {
        onScreenComics = comics;
        dismissLoadingDialog();
        refreshListView(comics);
    }

    @Override
    public void onInternalError() {
        dismissLoadingDialog();
        new MessageDialog(R.string.internal_error_message).show(this);
    }

    @Override
    public void onNetworkError() {
        dismissLoadingDialog();
        new MessageDialog(R.string.no_network_connection_message).show(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE_REQUEST_CODE) {
                String selectedPicturePath = data.getData().toString();
                UserData.getInstance().setUserImageForComic(selectedPicturePath, lastSelectedComic);
                refreshListView(onScreenComics);
            }
        }
    }

    private void refreshListView(ArrayList<Comic> comics) {
        ArrayList<ComicWrapper> comicWrappers = ComicWrapper.mapComics(comics);
        ComicsAdapter comicsAdapter = new ComicsAdapter(comicWrappers);
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
