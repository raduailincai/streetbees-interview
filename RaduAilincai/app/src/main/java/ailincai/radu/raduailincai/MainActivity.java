package ailincai.radu.raduailincai;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ailincai.radu.raduailincai.content.Marvel;

public class MainActivity extends Activity implements Marvel.MarvelListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewReferences();

        Marvel.getInstance().startLoadingData(this);
    }

    private void initViewReferences() {
        textView = (TextView) findViewById(R.id.raw_data_text_view);
    }

    @Override
    public void onSuccess() {
        textView.setText(Marvel.getInstance().getComics());
    }

    @Override
    public void onError() {
        textView.setText(R.string.no_network_connection_message);
    }
}
