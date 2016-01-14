package technology.purser.adblock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by roypur on 1/2/16.
 */
class BackgroundProcess implements Runnable{
    private MainActivity m;

    private void runScript(String file) throws IOException, InterruptedException{
        {
            String scriptPath = new File(m.getCacheDir(), file).getAbsolutePath();
            String []cmd = {"su", "-c", "sh", scriptPath};
            ProcessBuilder pb = new ProcessBuilder(cmd);

            pb.environment().putAll(m.getEnv());
            Process p = pb.start();

            InputStream stdout = p.getInputStream();
            InputStream stderr = p.getErrorStream();
            
            int a = 1;

            while(a>0) {
                a = stderr.read(new byte[stderr.available()]);
            }
            a = 1;
            while(a>0) {
                a = stdout.read(new byte[stdout.available()]);
            }

            stdout.close();
            stderr.close();

            p.waitFor();
            p.destroy();
        }
    }
    public void run(){

        try{
            m.update("Downloading files");

            runScript("read.sh");

            HostsFileList hfl = new HostsFileList("https://raw.githubusercontent.com/roypur/hosts/master/ipv4/src");

            ArrayList<String> urls = hfl.getUrls();

            File tmpHost = new File(m.getCacheDir(), "hosts");
            m.update("Parsing hosts");
            urls.add(new File(m.getCacheDir(), "old-hosts").getAbsolutePath());

            AdBlock block = new AdBlock(urls);

            m.update("Saving hosts");

            block.store(tmpHost);

            runScript("write.sh");

            m.update("Update finished!");

        }catch(Exception e) {
            m.update("Update failed!");
        }
    }
    public BackgroundProcess(MainActivity m){
        this.m = m;
    }
}
