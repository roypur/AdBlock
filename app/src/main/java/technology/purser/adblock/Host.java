package technology.purser.adblock;
class Host{
    private final String hostname;
    private final String ip;
    private boolean local;

    private final int hash;

    public Host(String ip, String hostname){
        this.ip = ip;
        this.hostname = hostname;
        hash = (ip+hostname).hashCode();

        if((ip.equals("127.0.0.1")) || (ip.equals("::1")) || (ip.equals("0.0.0.0"))){
            local = true;
        }else{
            local = false;
        }
    }
    public boolean isLocal(){
        return local;
    }
    public String getHostName(){
        return hostname;
    }
    public String getIp(){
        return ip;
    }
    public String toString(){
        return ip + " " + hostname;
    }
    public boolean equals(Object o){
        Host h = (Host)o;

        if((h.getHostName().equals(hostname)) && (h.getIp().equals(ip))){
            //if hostname=hostname and ip=ip
            return true;
        }
        return false;
    }
    public int hashCode(){
        return hash;
    }
}
