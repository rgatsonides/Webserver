package nl.sogyo.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ConnectionHandler implements Runnable {

    private Socket socket;
    String ParameterValue;
    String ParameterName;
    String HeaderParameterName;
    String HeaderParameterValue;
    RequestMessage request;

    public ConnectionHandler(Socket toHandle) {
        this.socket = toHandle;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = reader.readLine();
            request = new RequestMessage(line);
            System.out.println();

            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
                if (!line.isEmpty()) {
                    request.NewLine(line);
                }
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            ResponseMessage responsemessage = new ResponseMessage(request);
            responsemessage.setStatus(HttpStatusCode.OK);

            String holeResponseMessage = "\n" + "You did an HTTP " + request.getHTTPMethod()
                    + " request and you requested the following resource:"
                    + request.getResourcePath() + "\n"
                    + "The following header parameters where passed: " + "\n"
                    + HeaderParameters() + "\n" + "\n"
                    + "The following parameters where passed: " + "\n"
                    + Parameters();

            responsemessage.setContent(holeResponseMessage);
            String[] words = holeResponseMessage.split("\n");
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            writer.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String HeaderParameters() {
        String Headerparameters = "";
        for (int i = 0; i < request.getHeaderParameterNames().size(); i++) {
            HeaderParameterName = request.getHeaderParameterNames().get(i);
            HeaderParameterValue = request.getHeaderParameterValue(request.getHeaderParameterNames().get(i));
            Headerparameters += HeaderParameterName + ":" + HeaderParameterValue + "\n";
        }
        return Headerparameters;
    }

    public String Parameters() {
        String Parameters = "";
        if (request.getParameterNames() != null) {
            for (int i = 0; i < request.getParameterNames().size(); i++) {
                ParameterName = request.getParameterNames().get(i);
                ParameterValue = request.getParameterValue(request.getParameterNames().get(i));
                Parameters += ParameterName + ":" + ParameterValue + "\n";
            }
        }
        return Parameters;
    }

    public static void main(String... args) {
        try {
            ServerSocket socket = new ServerSocket(9090);
            while (true) {
                Socket newConnection = socket.accept();
                Thread t = new Thread(new ConnectionHandler(newConnection));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
