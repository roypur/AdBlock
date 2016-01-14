package technology.purser.adblock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends Activity {

    private Map<String, String> env;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set env vars
        try {

            ProcessBuilder pb = new ProcessBuilder("id");

            env = pb.environment();

            Process p = pb.start();

            InputStream stdout = p.getInputStream();
            InputStream stderr = p.getErrorStream();

            Scanner sc = new Scanner(stdout);

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
                    hasUid = true;
                }
                if(spacedString[i].startsWith("uid=")){
                    //parses gid=groupid(groupname) to groupname
                    gid=spacedString[i].split("\\(")[1].split("\\)")[0];
                    hasGid = true;
                }
                if(hasGid && hasUid){
                    int a = 1;
                    while(a>0) {
                        a = stdout.read(new byte[stdout.available()]);
                    }
                    a = 1;
                    while(a>0) {
                        a = stderr.read(new byte[stderr.available()]);
                    }

                    sc.close();
                    stderr.close();

                    p.destroy();
                    env.put("APP_USER", uid);
                    env.put("APP_GROUP", gid);
                    env.put("APP_CACHE", getCacheDir().getAbsolutePath());
                    break;
                }
            }
        }catch(Exception e){
            update("Failed to identify user");
        }
    }
    public Map<String,String> getEnv(){
        return env;
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
