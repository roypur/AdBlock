package technology.purser.adblock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by roypur on 1/2/16.
 */
public class BackgroundProcess implements Runnable{
    private File file;
    public void run(){

        try{
            SH shell = new SH();

            shell.getRoot();

            shell.rw();

            HostsFileList hfl = new HostsFileList("https://raw.githubusercontent.com/roypur/hosts/master/src");

            ArrayList<String> urls = hfl.getUrls();

            urls.add(file.getAbsolutePath());

            AdBlock block = new AdBlock(urls);

            block.store(file);

            shell.cp(file.getAbsolutePath(), "/system/etc/hosts");

        }catch(Exception e){
            System.err.println(e);
        }
    }
    public void setCache(File f){
        file = f;
    }
}
