package technology.purser.adblock;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;

import java.io.File;

class AdBlock{
    private ArrayList<Host> hosts = new ArrayList<>();

    public AdBlock(ArrayList<String> url) throws Exception{
        for(String s: url){
            HostsFile hf = new HostsFile(s);
            for(Host newHost: hf.getHosts()){
                if(!hosts.contains(newHost)){
                    if(newHost.isLocal()){
                        Host ipSix = new Host("::1", newHost.getHostName());
                        Host ipFour = new Host("127.0.0.1", newHost.getHostName());
                        hosts.add(ipSix);
                        hosts.add(ipFour);
                    }else{
                        hosts.add(newHost);
                    }
                }
            }
        }
    }
    public void store(File f) {

        try {

            PrintWriter pw = new PrintWriter(f);

            for (Host h : hosts) {
                pw.println(h.toString());
            }

            pw.flush();
            //pw.close();

        } catch (FileNotFoundException e) {
            System.out.println("AdBlock-store");
            System.out.println(e);
        }
    }
}