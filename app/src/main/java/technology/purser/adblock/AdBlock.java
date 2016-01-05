package technology.purser.adblock;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;

import java.io.File;
import java.util.HashMap;

class AdBlock{
    private HashMap<String, Host> hosts = new HashMap<>();

    public AdBlock(ArrayList<String> url) throws Exception{
        for(String s: url){
            HostsFile hf = new HostsFile(s);
            for(Host newHost: hf.getHosts()){
                if(hosts.get(newHost.getHostName()) == null){
                    if(newHost.isLocal()){
                        Host ipSix = new Host("::1", newHost.getHostName());
                        Host ipFour = new Host("127.0.0.1", newHost.getHostName());
                        hosts.put(newHost.getHostName(), ipSix);
                        hosts.put(newHost.getHostName(), ipFour);
                    }else{
                        hosts.put(newHost.getHostName(), newHost);
                    }
                }else if(!newHost.isLocal()){
                    hosts.put(newHost.getHostName(), newHost);
                }
            }
        }
    }
    public void store(File f) {

        try {

            PrintWriter pw = new PrintWriter(f);

            for (Host h : hosts.values()) {
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