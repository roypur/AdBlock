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
            m.update(false);

            String scriptPath = new File(m.getCacheDir(), "copy.sh").getAbsolutePath();
            String []cmds = {"su", "-c", "sh", scriptPath};

            Runtime.getRuntime().exec(cmds);

            HostsFileList hfl = new HostsFileList("https://raw.githubusercontent.com/roypur/hosts/master/src");

            ArrayList<String> urls = hfl.getUrls();

            File tmpHost = new File(m.getCacheDir(), "hosts");

            urls.add(new File(m.getCacheDir(), "old-hosts").getAbsolutePath());

            AdBlock block = new AdBlock(urls);

            block.store(tmpHost);

            Runtime.getRuntime().exec(cmds);

            m.update(true);

        }catch(Exception e){
            System.err.println("BackgroundProcess-thread-fail");
            System.err.println(e);
        }
    }
    public BackgroundProcess(MainActivity m){
        this.m = m;
    }
}
