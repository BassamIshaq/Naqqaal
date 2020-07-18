package Naqqaal;

/**
 * OK
 *
 * @author Lenovo 520
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSendFile {

    public static void main(String[] args) throws Exception {
//        String url = "https://www.google.com/";
        String url = "http://192.168.10.15:5000/path";//192.168.10.15

        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer token");
        connection.setRequestProperty("Content-Type", "audio/mp3");
        connection.setRequestProperty("method", "post");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();//C:\Users\Lenovo 520\Desktop\ppp.mp3
        FileChannel fileChannel = new FileInputStream("E:\\ertugrul-theme-song-mp3-free-download.mp3").getChannel();
        ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(1024);

        while ((fileChannel.read(byteBuffer)) != -1) {
            byteBuffer.flip();
            byte[] b = new byte[byteBuffer.remaining()];
            byteBuffer.get(b);
            outputStream.write(b);
            byteBuffer.clear();
        }

        BufferedReader response = new BufferedReader(
                new InputStreamReader(
                        connection.
                                getInputStream()));
        String line;
        while ((line = response.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static String sendFileToServer(String url) {
//        String url = "http://192.168.10.15:5000/path";//192.168.10.15

//        URLConnection connection = null;
        String returnString = "";
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Authorization", "Bearer token");
            connection.setRequestProperty("Content-Type", "audio/mp3");
            connection.setRequestProperty("method", "post");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();//C:\Users\Lenovo 520\Desktop\ppp.mp3
            FileChannel fileChannel = new FileInputStream("E:\\ertugrul-theme-song-mp3-free-download.mp3").getChannel();
            ByteBuffer byteBuffer = java.nio.ByteBuffer.allocate(1024);

            while ((fileChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                byte[] b = new byte[byteBuffer.remaining()];
                byteBuffer.get(b);
                outputStream.write(b);
                byteBuffer.clear();
            }

            BufferedReader response = new BufferedReader(
                    new InputStreamReader(
                            connection.
                                    getInputStream()));
            String line;
            while ((line = response.readLine()) != null) {
                returnString += line;
//                System.out.println(line);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return returnString;
    }
}
