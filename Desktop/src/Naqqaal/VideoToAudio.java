package Naqqaal;

import java.io.File;
import java.io.IOException;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

public class VideoToAudio {

//    static String path = "E:\\Format conversion\\Sample_English_Video.wav";
//    static String pathmp3 = "E:\\Format conversion\\Sample_English_Video.mp3";
//    public static void convertToAudio(File video) throws IllegalArgumentException, InputFormatException, EncoderException, IOException {
//        File target = new File(path);
//        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("libmp3lame");
//        audio.setBitRate(128000);
//        audio.setChannels(2);
//        audio.setSamplingRate(44100);
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("wav");
//        attrs.setAudioAttributes(audio);
//        Encoder encoder = new Encoder();
//        encoder.encode(video, target, attrs);
//
//    }
//
//    public static void convertToMP3(File video) throws IllegalArgumentException, InputFormatException, EncoderException {
//        File target = new File(pathmp3);
//        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("libmp3lame");
//        audio.setBitRate(128000);
//        audio.setChannels(2);
//        audio.setSamplingRate(44100);
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("mp3");
//        attrs.setAudioAttributes(audio);
//        Encoder encoder = new Encoder();
//        encoder.encode(video, target, attrs);
//    }
    public static boolean videoToAudio(File videoFile, File targetAudioFile, String targetAudioFormat) {
//        targetAudioFormat can be mp3 or wav
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
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

//    public static void main(String[] args) {
//        File videoFile = new File("E:\\Sample_English_Video.mp4");
//        File audioFile = new File("E:\\Format conversion\\converted_S16000_B16000.mp3");
//        try {
//
//            boolean videoToAudio = videoToAudio(videoFile, audioFile, "mp3");
//            if (videoToAudio) {
//                System.out.println("Conversion successful !");
//            } else {
//                System.out.println("Conversion unsuccessful !");
//            }
//        } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
