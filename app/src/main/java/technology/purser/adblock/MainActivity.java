package technology.purser.adblock;

import android.app.Activity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File tmpFile = new File(getCacheDir(), "hosts");

        BackgroundProcess bp = new BackgroundProcess();
        bp.setCache(tmpFile);

        Thread trd = new Thread(bp);
        trd.start();
    }
    protected void update(){
        runOnUiThread(new Runnable(){
            public void run(){

            }
        });
    }
}
