package br.edu.utfpr.eduardomelentovytch.controledelivroslido.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import br.edu.utfpr.eduardomelentovytch.controledelivroslido.R;

public class TelaSobreActivity extends AppCompatActivity {

    private ConstraintLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_sobre_aplicativo);

        layout = findViewById(R.id.layoutTelaSobre);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }if (id == R.id.menuItemBranco) {
            layout.setBackgroundColor(Color.WHITE);
        } else if (id == R.id.menuItemAzul) {
            layout.setBackgroundColor(Color.BLUE);
        } else if (id == R.id.menuItemCinza) {
            layout.setBackgroundColor(Color.GRAY);
        } else {
            return super.onContextItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sobre_menu_opcoes, menu);
        return true;
    }
}
