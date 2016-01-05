package technology.purser.adblock;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

class AdBlock{
    private HashSet<Host> hosts = new HashSet<>();

    public AdBlock(ArrayList<String> url) throws Exception{
        for(String s: url){
            HostsFile hf = new HostsFile(s);
            for(Host newHost: hf.getHosts()){

                //System.out.println("127.0.0.1".hashCode());


                if(!hosts.contains(newHost)){
                    if(newHost.isLocal()){
                        Host ipSix = new Host("::1", newHost.getHostName());
                        Host ipFour = new Host("127.0.0.1", newHost.getHostName());
                        hosts.add(ipSix);
                        hosts.add(ipFour);
                    }else{
                        hosts.add(newHost);
                    }
                }else if(!newHost.isLocal()){
                    hosts.add(newHost);
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
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println("AdBlock-store");
            System.out.println(e);
        }
    }
}