package technology.purser.adblock;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by roypur on 1/3/16.
 */
public class HostsFileList extends FetchFile{
    private ArrayList<String> urls = new ArrayList<>();

    public HostsFileList(String txtURL) throws Exception{

        Scanner sc = newScanner(txtURL);

        while(sc.hasNextLine()){
            String next = sc.nextLine().trim();
            if(next.length() > 3){
                if(!(next.charAt(0) == '#')){
                    if(next.contains("#")){
                        next = next.split("#")[0];
                    }
                    String []arr = next.split("\\s+");
                    if(arr.length == 1){
                        urls.add(arr[0]);
                    }
                }
            }
        }
    }
    public ArrayList<String> getUrls(){
        return urls;
    }
}
