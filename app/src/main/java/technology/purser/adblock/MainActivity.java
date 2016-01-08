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

    private String gid;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get linux user
        try {
            InputStream os = Runtime.getRuntime().exec("id").getInputStream();

            Scanner sc = new Scanner(os);

            String txt = sc.nextLine();

            String []spacedString = txt.split("\\s+");

            boolean hasGid = false;
            boolean hasUid = false;

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
                    break;
                }
            }
        }catch(Exception e){
            update("Failed to identify user");
        }
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
            update("Failed to copy script");
        }

        BackgroundProcess bp = new BackgroundProcess(this);
        Thread t = new Thread(bp);
        t.start();
    }
    public String[] getUser(){
        String []ret = {uid,gid};
        return ret;
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
