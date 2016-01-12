package technology.purser.adblock;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by roypur on 1/2/16.
 */
class BackgroundProcess implements Runnable{
    private MainActivity m;
    public void run(){

        try{
            m.update("Downloading files");

            String readScriptPath = new File(m.getCacheDir(), "read.sh").getAbsolutePath();
            String writeScriptPath = new File(m.getCacheDir(), "write.sh").getAbsolutePath();

            String []readCmd = {"su", "-c", "sh", readScriptPath, m.getUser()[0], m.getUser()[1]};
            String []writeCmd = {"su", "-c", "sh", writeScriptPath, m.getUser()[0], m.getUser()[1]};

            String []env = {"APP_USER="+m.getUser()[0], "APP_GROUP="+m.getUser()[1]};

            Runtime.getRuntime().exec(readCmd, env);

            HostsFileList hfl = new HostsFileList("https://raw.githubusercontent.com/roypur/hosts/master/ipv4/src");

            ArrayList<String> urls = hfl.getUrls();

            File tmpHost = new File(m.getCacheDir(), "hosts");
            m.update("Parsing hosts");
            urls.add(new File(m.getCacheDir(), "old-hosts").getAbsolutePath());

            AdBlock block = new AdBlock(urls);

            m.update("Saving hosts");

            block.store(tmpHost);

            Runtime.getRuntime().exec(writeCmd, env);

            m.update("Update finished!");

        }catch(Exception e) {
            m.update("Update failed!");
        }
    }
    public BackgroundProcess(MainActivity m){
        this.m = m;
    }
}
