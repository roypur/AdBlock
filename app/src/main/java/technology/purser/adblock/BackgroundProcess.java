package technology.purser.adblock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by roypur on 1/2/16.
 */
class BackgroundProcess implements Runnable{
    private File file;
    private MainActivity m;
    public void run(){

        try{
            m.update(false);
            SH shell = new SH();

            shell.getRoot();

            shell.rw();

            HostsFileList hfl = new HostsFileList("https://raw.githubusercontent.com/roypur/hosts/master/src");

            ArrayList<String> urls = hfl.getUrls();

            urls.add(file.getAbsolutePath());

            AdBlock block = new AdBlock(urls);

            block.store(file);

            shell.cp(file.getAbsolutePath(), "/system/etc/hosts");
            m.update(true);

        }catch(Exception e){
            System.err.println(e);
        }
    }
    public BackgroundProcess(File f, MainActivity m){
        file = f;
        this.m = m;
    }
}
