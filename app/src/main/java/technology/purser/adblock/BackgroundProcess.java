package technology.purser.adblock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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

            String []readCmd = {"su", "-c", "sh", readScriptPath};
            String []writeCmd = {"su", "-c", "sh", writeScriptPath};

            Runtime.getRuntime().exec(readCmd);

            HostsFileList hfl = new HostsFileList("https://raw.githubusercontent.com/roypur/hosts/master/ipv4/src");

            ArrayList<String> urls = hfl.getUrls();

            File tmpHost = new File(m.getCacheDir(), "hosts");
            m.update("Parsing hosts");
            urls.add(new File(m.getCacheDir(), "old-hosts").getAbsolutePath());

            AdBlock block = new AdBlock(urls);

            m.update("Saving hosts");

            block.store(tmpHost);

            Runtime.getRuntime().exec(writeCmd);

            m.update("Update finished!");

        }catch(Exception e) {
            m.update("Update failed!");
        }
    }
    public BackgroundProcess(MainActivity m){
        this.m = m;
    }
}
