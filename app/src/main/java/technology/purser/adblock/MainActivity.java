package technology.purser.adblock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.PrintWriter;



import java.io.File;
import java.util.Scanner;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void apply(View v){

        try {
            Scanner sc = new Scanner(getAssets().open("copy.sh"));
            PrintWriter p = new PrintWriter(new File(getCacheDir(), "copy.sh"));
            while(sc.hasNextLine()){
                p.println(sc.nextLine());
            }
            p.flush();
            p.close();

        }catch(Exception e){
        }

        BackgroundProcess bp = new BackgroundProcess(this);
        Thread t = new Thread(bp);
        t.start();
    }

    protected void update(final boolean done){
        runOnUiThread(new Runnable(){
            public void run(){
                TextView tv = (TextView)findViewById(R.id.confirmText);
                if(done) {
                    tv.setText("successful write!");
                }else{
                    tv.setText("Applying hosts-file, please wait");
                }
            }
        });
    }
}
