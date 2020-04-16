package donguyennhathan.com.hocassets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import donguyennhathan.com.hocassets.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayAdapter<String> fontAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
        loadFonts();
        turnOnMusic();
    }

    private void loadFonts() {
        binding.lvFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeFonts(position);
            }
        });
    }

    private void turnOnMusic() {
        try {
            AssetFileDescriptor afd = getAssets().openFd("music/AlanWalker.mp3");
            MediaPlayer player= new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            player.prepare();
            player.start();
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void changeFonts(int position) {
        Typeface typeface= Typeface.createFromAsset(getAssets(),"fonts/"+fontAdapter.getItem(position));
        binding.txtFont.setTypeface(typeface);
    }

    private void addEvents() {
        try {
            AssetManager assetManager= getAssets();
            String[] arrFont= assetManager.list("fonts");
            fontAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
            fontAdapter.addAll(arrFont);
            binding.lvFont.setAdapter(fontAdapter);
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
