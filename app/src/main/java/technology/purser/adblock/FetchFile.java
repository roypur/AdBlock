package technology.purser.adblock;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by roypur on 1/3/16.
 */
class FetchFile {
    protected Scanner newScanner(String txtURL) throws Exception{
        if(txtURL.contains("http://") || txtURL.contains("https://") || txtURL.contains("ftp://")){
            URL url = new URL(txtURL);
            InputStream is = url.openStream();
            Scanner sc = new Scanner(is);
            return sc;
        }
        File f = new File(txtURL);
        Scanner sc = new Scanner(f);
        return sc;
    }
}
