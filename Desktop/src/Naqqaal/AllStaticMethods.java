/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Naqqaal;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo 520
 */
public class AllStaticMethods {

    public static boolean createFile(String path, String data) {
        File file = new File(path);

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(data);
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    public static boolean copyFile(String sourcePath, String destinationPath) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourcePath);
            outputStream = new FileOutputStream(destinationPath);
            FileChannel sourceFileChannel = inputStream.getChannel();
            FileChannel destinationFileChannel = outputStream.getChannel();
            long remaining = sourceFileChannel.size();
            System.out.println("Copying...");
            long position = 0;
            while (remaining > 0) {
                // this loop is used because the transferTo() method can, at max, transfer 2GB of data at one call so 
                // this loop will call transferTo() method multiple times to completely transfer file
                System.out.println("Remaining: " + remaining);
                long transferred = sourceFileChannel.transferTo(position, remaining, destinationFileChannel);
                remaining -= transferred;
                position += transferred;
            }
            System.out.println("Copy Successful !");
        } catch (IOException ex) {
//            System.out.println("AllStaticMethods.java:copyFile():main catch block: " + ex);
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                System.out.println("NewProjectScreen.java:copyFile():catch block in finally block: " + e);
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Subtitle> readSrtFileOld(String filePath) {
        ArrayList<Subtitle> subtitlesArrayList = new ArrayList<>(0);
        File file = new File(filePath);    // File object to get the properties of the text file
        Scanner fileInput;

        try {
            fileInput = new Scanner(file);
            ArrayList<String> subtitle = new ArrayList();
            String id;
            String time;
            String text = "";

            while (fileInput.hasNextLine()) {
                String line = fileInput.nextLine();
                if (!line.trim().equals("")) {
                    subtitle.add(line);
                } else {
                    id = subtitle.get(0).trim();
                    time = subtitle.get(1);
                    for (int i = 2; i < subtitle.size(); i++) {
                        text += subtitle.get(i) + "\n";
                    }
                    text = text.substring(0, text.length() - 1); // removing \n from end of the index
//                    Extracting time stamps from string time 
                    String[] startAndEndTimes = time.split("-->"); // 0 index is start time and 1 is end time [hh:mm:ss,ms]
                    startAndEndTimes[0] = startAndEndTimes[0].trim(); //Start Time
                    startAndEndTimes[1] = startAndEndTimes[1].trim(); // End Time
                    // EXTRACTING START TIME
                    String[] startTimeTokens = startAndEndTimes[0].split(":");
                    // 0 index is startHours, 1 index is startMin,2 index is startSec,startMilliSec
                    int startHours_ = Integer.parseInt(startTimeTokens[0]);
                    int startMin_ = Integer.parseInt(startTimeTokens[1]);
                    int startSec_ = Integer.parseInt(startTimeTokens[2].split(",")[0]);
                    int startMilliSec_ = Integer.parseInt(startTimeTokens[2].split(",")[1]);

                    // EXTRACTING STOP TIME
                    String[] stopTimeTokens = startAndEndTimes[1].split(":");
                    // 0 index is startHours, 1 index is startMin,2 index is startSec,startMilliSec
                    int stopHours_ = Integer.parseInt(stopTimeTokens[0]);
                    int stopMin_ = Integer.parseInt(stopTimeTokens[1]);
                    int stopSec_ = Integer.parseInt(stopTimeTokens[2].split(",")[0]);
                    int stopMilliSec_ = Integer.parseInt(stopTimeTokens[2].split(",")[1]);

//                    create subtitle object
                    int intID = 1;
                    try {
                        intID = Integer.parseInt(id.trim());
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println(numberFormatException);
                    }
                    Subtitle subtitleObject = new Subtitle(intID, startHours_, startMin_, startSec_, startMilliSec_,
                            stopHours_, stopMin_, stopSec_, stopMilliSec_, text, "", false, "");
//                    add to subtitlesArrayList 
                    subtitlesArrayList.add(subtitleObject);
                    text = "";
                    subtitle.clear();
                }
            }
            fileInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("Subtitle Class, readSrtFile(): " + e);
        }
        return subtitlesArrayList;

    }

    public static ArrayList<Subtitle> readSrtFile(String filePath) {
        String readTextFile = readTextFile(filePath);
        readTextFile = readTextFile.replace(((char) 13) + "", "");
        ArrayList<Subtitle> subtitlesArrayList = new ArrayList<>();

        String[] split = readTextFile.split("\n\n");// + ((char) 13)
        for (int i = 0; i < split.length; i++) {
            String subtitle = split[i].trim();
            String[] subtitleChunks = subtitle.split("\n");
            String id = subtitleChunks[0];
            String time = subtitleChunks[1];
            ArrayList<String> text = new ArrayList<>();
            for (int j = 2; j < subtitleChunks.length; j++) {
                text.add(subtitleChunks[j] + "\n");
            }
            String text_ = "";
            for (int j = 0; j < text.size(); j++) {
                text_ += text.get(j);
            }
            text_ = text_.substring(0, text_.lastIndexOf("\n"));// removing \n from the end of text
//            System.out.println(text+ " : text");

//            ID...
            int intID = 1;
            try {
                intID = Integer.parseInt(id.trim());
            } catch (NumberFormatException numberFormatException) {
                System.out.println(numberFormatException);
            }

//-------------------------- TIME EXTRACTION FROM TIME STRING ---------------------------------
//                    Extracting time stamps from string time 
            String[] startAndEndTimes = time.split("-->"); // 0 index is start time and 1 is end time [hh:mm:ss,ms]
            startAndEndTimes[0] = startAndEndTimes[0].trim(); //Start Time
            startAndEndTimes[1] = startAndEndTimes[1].trim(); // End Time
            // EXTRACTING START TIME
            String[] startTimeTokens = startAndEndTimes[0].split(":");
            // 0 index is startHours, 1 index is startMin,2 index is startSec,startMilliSec
            int startHours_ = Integer.parseInt(startTimeTokens[0]);
            int startMin_ = Integer.parseInt(startTimeTokens[1]);
            int startSec_ = Integer.parseInt(startTimeTokens[2].split(",")[0]);
            int startMilliSec_ = Integer.parseInt(startTimeTokens[2].split(",")[1]);

            // EXTRACTING STOP TIME
            String[] stopTimeTokens = startAndEndTimes[1].split(":");
            // 0 index is startHours, 1 index is startMin,2 index is startSec,startMilliSec
            int stopHours_ = Integer.parseInt(stopTimeTokens[0]);
            int stopMin_ = Integer.parseInt(stopTimeTokens[1]);
            int stopSec_ = Integer.parseInt(stopTimeTokens[2].split(",")[0]);
            int stopMilliSec_ = Integer.parseInt(stopTimeTokens[2].split(",")[1]);

//------------------------------------ TIME EXTRACTION ENDS ----------------------------------------------
            Subtitle subtitleObject = new Subtitle(intID, startHours_, startMin_, startSec_, startMilliSec_,
                    stopHours_, stopMin_, stopSec_, stopMilliSec_, text_, "", false, "");
//                    add to subtitlesArrayList 
            subtitlesArrayList.add(subtitleObject);
        }
        return subtitlesArrayList;
    }

    public static String readTextFile(String filePath) {
        File file = new File(filePath);    // File object to get the properties of the text file
        Scanner fileInput;
        String returnString = null;
        try {
            fileInput = new Scanner(file);
            if (fileInput.hasNext()) {
                returnString = fileInput.useDelimiter("\\A").next();
            }
            fileInput.close();
        } catch (FileNotFoundException ex) {
            System.out.println("readTextFile(): " + ex);
        }
        return returnString;
    }

    public static void main(String[] args) {
//        ArrayList<Subtitle> readSrtFile = readSrtFile("E:\\The.Conjuring.2-Urdu.srt");
//        System.out.println(readSrtFile.toString());
        System.out.println(ProjectsTreeView.getExtension("E:\\The.Conjuring.2-Urdu.mp3"));
//        System.out.println(new Subtitle("dummy text !").toString());
//        System.out.println(readTextFile("E:\\videoSubtitle.srt"));
    }

    private static String insertString(String originalString, String stringToBeInserted, int insertionIndex) {
        return (originalString.substring(0, insertionIndex)
                + stringToBeInserted + originalString.substring(insertionIndex, originalString.length()));
    }

    private static ArrayList allIndexesOf(String input, String regex) {
        ArrayList indexes = new ArrayList();
        int regexLength = regex.length();
        for (int i = 0; i < (input.length() - regexLength + 1); i++) {

            if (input.substring(i, i + regexLength).equals(regex)) {
                indexes.add(i);
                i += regexLength;
            }
        }

        return indexes;
    }

    /**
     * *
     * replaces a substring in a String with a new string within specific range
     * of original string
     *
     */
    private static String replaceSubStringInRange(String originalString, String oldSubString, String newSubString,
            int startPosition, int endPosition) {
        String returnString1 = originalString.substring(0, startPosition);
        String returnString2 = originalString.substring(startPosition, endPosition);
        String returnString3 = originalString.substring(endPosition, originalString.length());
        returnString2 = returnString2.replace(oldSubString, newSubString);
        return returnString1 + returnString2 + returnString3;
    }

    public static boolean videoToAudio(File videoFile, File targetAudioFile, String targetAudioFormat) {
        
        
//        targetAudioFormat can be mp3 or wav
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");//wav:pcm_s16le, mp3:libmp3lame
        audio.setBitRate(128000);//original 128000
        audio.setChannels(2);
        audio.setSamplingRate(16000);//original 44100
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat(targetAudioFormat);
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        try {
            encoder.encode(videoFile, targetAudioFile, attrs);
        } catch (IllegalArgumentException | EncoderException ex) {
            System.out.println("videoToAudio(): " + ex);
            return false;
        }
        return true;
    }

    public static String sendFileToServer(String routeURL, String filePath) {
//        String url = "http://192.168.10.15:5000/path";//192.168.10.15

        String fileExtension = ProjectsTreeView.getExtension(filePath);
        String returnString = "";
        try {
            URLConnection connection = new URL(routeURL).openConnection();
            connection.setRequestProperty("Authorization", "Bearer token");
            connection.setRequestProperty("Content-Type", "audio/" + fileExtension);// fileExtension can be mp3 or wav
            connection.setRequestProperty("method", "post");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();//C:\Users\Lenovo 520\Desktop\ppp.mp3
            FileChannel fileChannel = new FileInputStream(filePath).getChannel();
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
