package technology.purser.adblock;
import java.util.Scanner;
import java.util.ArrayList;

class HostsFile extends FetchFile{
    private ArrayList<Host> hosts = new ArrayList<>();

    public HostsFile(String txtURL) throws Exception{
        
        Scanner sc = newScanner(txtURL);
        
        while(sc.hasNextLine()){
            String next = sc.nextLine().trim();
            if(next.length() > 5){
                if(!(next.charAt(0) == '#')){
                    if(next.contains("#")){
                        next = next.split("#")[0];
                    }
                    String []arr = next.split("\\s+");
                    if(arr.length > 1){
                        hosts.add(new Host(arr[0], arr[1]));
                    }
                }
            }
        }
    }
    public ArrayList<Host> getHosts(){
        return hosts;
    }
}
