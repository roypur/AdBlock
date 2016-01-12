package technology.purser.adblock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set env vars
        try {
            InputStream os = Runtime.getRuntime().exec("id").getInputStream();

            Scanner sc = new Scanner(os);

            String txt = sc.nextLine();

            String []spacedString = txt.split("\\s+");

            boolean hasGid = false;
            boolean hasUid = false;
            String uid = "";
            String gid = "";

            for(int i=0; i<spacedString.length; i++){
                if(spacedString[i].startsWith("uid=")){
                    //parses uid=userid(username) to username
                    uid=spacedString[i].split("\\(")[1].split("\\)")[0];
                }
                if(spacedString[i].startsWith("uid=")){
                    //parses gid=groupid(groupname) to groupname
                    gid=spacedString[i].split("\\(")[1].split("\\)")[0];
                }
                if(hasGid && hasUid){
                    sc.close();
                    System.setProperty("APP_USER", uid);
                    System.setProperty("APP_GROUP", gid);
                    System.setProperty("APP_CACHE", getCacheDir().getAbsolutePath());
                    break;
                }
            }
        }catch(Exception e){
            update("Failed to identify user");
        }
    }
    private void copyAsset(String file){
        try {
            Scanner sc = new Scanner(getAssets().open(file));
            PrintWriter p = new PrintWriter(new File(getCacheDir(), file));
            while(sc.hasNextLine()){
                p.println(sc.nextLine());
            }
            p.flush();
            p.close();

        }catch(Exception e){
            update("Failed to copy script");
        }
    }
    public void apply(View v){
        copyAsset("read.sh");
        copyAsset("write.sh");

        BackgroundProcess bp = new BackgroundProcess(this);
        Thread t = new Thread(bp);
        t.start();
    }

    protected void update(final String txt){
        runOnUiThread(new Runnable(){
            public void run(){
                TextView tv = (TextView)findViewById(R.id.confirmText);
                tv.setText(txt);
            }
        });
    }
}
