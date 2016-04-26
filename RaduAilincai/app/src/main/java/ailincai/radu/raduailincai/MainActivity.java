package ailincai.radu.raduailincai;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import ailincai.radu.raduailincai.adapters.ComicsAdapter;
import ailincai.radu.raduailincai.content.Marvel;
import ailincai.radu.raduailincai.model.Comic;

public class MainActivity extends Activity implements Marvel.MarvelListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewReferences();

        Marvel.getInstance().startLoadingData(this);
    }

    private void initViewReferences() {
        listView = (ListView) findViewById(R.id.main_screen_list_view);
    }

    @Override
    public void onSuccess() {
        ArrayList<Comic> comics = Marvel.getInstance().getComics();
        ComicsAdapter comicsAdapter = new ComicsAdapter(comics);
        listView.setAdapter(comicsAdapter);
    }

    @Override
    public void onError() {
    }
}
