package technology.purser.adblock;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by roypur on 1/2/16.
 */
class SH {
    private Process shell;
    private OutputStream stdin;

    public void getRoot() throws IOException{
        stdin.write("su".getBytes());
        stdin.flush();
    }
    public SH() throws IOException{
        shell = Runtime.getRuntime().exec("sh");
        stdin = shell.getOutputStream();
    }

    public void cp(String from, String to) throws IOException{
        String command = "cp " + from + " " + to;
        stdin.write(command.getBytes());
        stdin.flush();
    }
    public void rw() throws IOException{
        stdin.write("mount -o remount,rw /system".getBytes());
        stdin.flush();
    }
    public void ro() throws IOException{
        stdin.write("mount -o remount,ro /system".getBytes());
        stdin.flush();
    }
    public void close() throws IOException{
        stdin.close();
    }
}
