package technology.purser.adblock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.app.ActivityManager;
import android.widget.TextView;

import java.io.File;
public class MainActivity extends Activity {

    private File tmpFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tmpFile = new File(getCacheDir(), "hosts");
    }
    public void apply(View v){
        BackgroundProcess bp = new BackgroundProcess(tmpFile, this);
        Thread trd = new Thread(bp);
        trd.start();
    }
    protected void update(){
        runOnUiThread(new Runnable(){
            public void run(){
                TextView tv = (TextView)findViewById(R.id.confirmText);
                tv.setText("successful write!");
            }
        });
    }
}
