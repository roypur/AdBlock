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

            String scriptPath = new File(m.getCacheDir(), "copy.sh").getAbsolutePath();

            String []cmds = {"su", "-c", "sh", scriptPath, m.getUser()[0], m.getUser()[1]};

            Runtime.getRuntime().exec(cmds);

            HostsFileList hfl = new HostsFileList("https://raw.githubusercontent.com/roypur/hosts/master/ipv4/src");

            ArrayList<String> urls = hfl.getUrls();

            File tmpHost = new File(m.getCacheDir(), "hosts");
            m.update("Parsing hosts");
            urls.add(new File(m.getCacheDir(), "old-hosts").getAbsolutePath());

            AdBlock block = new AdBlock(urls);

            m.update("Saving hosts");

            block.store(tmpHost);

            Runtime.getRuntime().exec(cmds);

            m.update("Update finished!");

        }catch(Exception e){
            m.update("Update failed!");
        }
    }
    public BackgroundProcess(MainActivity m){
        this.m = m;
    }
}
