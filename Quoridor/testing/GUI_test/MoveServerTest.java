package GUI_test;

import static org.junit.Assert.*;

import java.io.*;
import java.net.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUI.MoveServer;

public class MoveServerTest {

    private Socket server;
    private int port;
    private final String localhost = "127.0.0.1";
    private PrintWriter out;
    private BufferedReader in;
    
    
    
    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createServer() {
        try {
            port = 6666;

            MoveServer.startServer();
            
            server = new Socket("127.0.0.1", port);
            
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            out = new PrintWriter(server.getOutputStream(), true);
            
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("UnknownHostException");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("IOException");
        }
    }

    @Test
    public void testStartGame() {
        try {
            out.write("QUORIDOR 2 0");
            String response = in.readLine();
            assertEquals("READY Player", response);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
