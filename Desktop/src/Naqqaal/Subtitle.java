/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Naqqaal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javafx.collections.ObservableList;

/**
 *
 * @author Lenovo 520
 */
public class Subtitle implements Serializable, Comparable<Subtitle> { // implemented comparable for Collections.sort method...

    private int id;

    private int startHours;
    private int startMin;
    private int startSec;
    private int startMilliSec;
    private int totalStartMillis;

    private int stopHours;
    private int stopMin;
    private int stopSec;
    private int stopMilliSec;
    private int totalStopMillis;

    private String text;

    private String note;
    private boolean completed;
    private String speaker;

    public Subtitle() {
    }

    public Subtitle(int id, int startHours, int startMin, int startSec, int startMilliSec, int stopHours, int stopMin, int stopSec, int stopMilliSec, String text, String note, boolean completed, String speaker) {
        this.id = id;
        this.startHours = startHours;
        this.startMin = startMin;
        this.startSec = startSec;
        this.startMilliSec = startMilliSec;
        this.stopHours = stopHours;
        this.stopMin = stopMin;
        this.stopSec = stopSec;
        this.stopMilliSec = stopMilliSec;
        this.text = text;
        this.note = note;
        this.completed = completed;
        this.speaker = speaker;
        this.totalStartMillis = (3600000 * this.startHours)
                + (60000 * this.startMin) + (1000 * this.startSec) + this.startMilliSec;
        this.totalStopMillis = (3600000 * this.stopHours)
                + (60000 * this.stopMin) + (1000 * this.stopSec) + this.stopMilliSec;

    }

    public Subtitle(String text) {
        this.id = 1;
        this.text = text;
        this.note = "";
    }

    public Subtitle(int id) {
        this.id = id;
        this.text = "";
        this.note = "";
    }

    /**
     *
     * @param timeInMilliSeconds
     * @param subtitlesArrayList
     * @return Subtitle Object for which timeInMillis lies within the
     * totalStartMillis & totalStopMillis
     */
    public static Subtitle searchSubtitleByTime(int timeInMilliSeconds, ObservableList<TextNode> subtitlesArrayList) {
        for (int i = 0; i < subtitlesArrayList.size(); i++) {
            if (subtitlesArrayList.get(i).getSubtitle().getTotalStartMillis() < timeInMilliSeconds
                    && timeInMilliSeconds < subtitlesArrayList.get(i).getSubtitle().getTotalStopMillis()) {
                // if timeInMilliSeconds lies between totalStartMillis and totalStopMillis
                return subtitlesArrayList.get(i).getSubtitle();

            } else if (subtitlesArrayList.get(i).getSubtitle().getTotalStartMillis() > timeInMilliSeconds) {
                // if timeInMilliSeconds does not lie between totalStartMillis and totalStopMillis
                if (i >= 1) { // to avoid array index out of bound exception
                    return subtitlesArrayList.get(i - 1).getSubtitle();
                }
            }
        }
        System.out.println("Subtitle Not Found :(");
        return null;
    }

    public static boolean writeSrtFile(ObservableList<TextNode> subtitleArrayList, String destinationPath) {
        String toBeWritten = "";
        for (int i = 0; i < subtitleArrayList.size(); i++) {
            Subtitle subtitle = subtitleArrayList.get(i).getSubtitle();
            toBeWritten
                    += subtitle.getId() + "\r\n"
                    + subtitle.getStartHours() + ":" + subtitle.getStartMin() + ":" + subtitle.getStartSec() + "," + subtitle.getStartMilliSec()
                    + " --> "
                    + subtitle.getStopHours() + ":" + subtitle.getStopMin() + ":" + subtitle.getStopSec() + "," + subtitle.getStopMilliSec()
                    + "\r\n";
            if (!subtitle.speaker.isEmpty()) {
                toBeWritten += subtitle.getSpeaker().toUpperCase() + ": ";
            }
            toBeWritten += (subtitle.getText().replaceAll("\n", "\r\n")) + "\r\n\r\n";
        }
        return AllStaticMethods.createFile(destinationPath, toBeWritten);
    }

    public static boolean writeTxtFile(ObservableList<TextNode> subtitleArrayList, String destinationPath) {
        String toBeWritten = "";
        for (int i = 0; i < subtitleArrayList.size(); i++) {
            Subtitle subtitle = subtitleArrayList.get(i).getSubtitle();
            toBeWritten += (subtitle.getText().replaceAll("\n", "\r\n")) + "\r\n";
        }
        return AllStaticMethods.createFile(destinationPath, toBeWritten);
    }

    @Override
    public String toString() {
        return "Subtitle{" + "id=" + id + ", startHours=" + startHours + ", startMin=" + startMin + ", startSec=" + startSec + ", startMilliSec=" + startMilliSec + ", totalStartMillis=" + totalStartMillis + ", stopHours=" + stopHours + ", stopMin=" + stopMin + ", stopSec=" + stopSec + ", stopMilliSec=" + stopMilliSec + ", totalStopMillis=" + totalStopMillis + ", text=" + text + ", note=" + note + ", completed=" + completed + ", speaker=" + speaker + '}';
    }

    public int getId() {
        return id;
    }

    public int getStartHours() {
        return startHours;
    }

    public int getStartMin() {
        return startMin;
    }

    public int getStartSec() {
        return startSec;
    }

    public int getStartMilliSec() {
        return startMilliSec;
    }

    public int getTotalStartMillis() {
        return totalStartMillis;
    }

    public int getStopHours() {
        return stopHours;
    }

    public int getStopMin() {
        return stopMin;
    }

    public int getStopSec() {
        return stopSec;
    }

    public int getStopMilliSec() {
        return stopMilliSec;
    }

    public int getTotalStopMillis() {
        return totalStopMillis;
    }

    public String getText() {
        return text;
    }

    public String getNote() {
        return note;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartHours(int startHours) {
        this.startHours = startHours;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public void setStartSec(int startSec) {
        this.startSec = startSec;
    }

    public void setStartMilliSec(int startMilliSec) {
        this.startMilliSec = startMilliSec;
    }

    public void setStopHours(int stopHours) {
        this.stopHours = stopHours;
    }

    public void setStopMin(int stopMin) {
        this.stopMin = stopMin;
    }

    public void setStopSec(int stopSec) {
        this.stopSec = stopSec;
    }

    public void setStopMilliSec(int stopMilliSec) {
        this.stopMilliSec = stopMilliSec;
    }

    public void setTotalStartMillis(int totalStartMillis) {
        this.totalStartMillis = totalStartMillis;
    }

    public void setTotalStopMillis(int totalStopMillis) {
        this.totalStopMillis = totalStopMillis;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    @Override
    public int compareTo(Subtitle subtitle) {
        return Integer.compare(id, subtitle.id);
    }

//    public static void main(String[] args) {
//        ArrayList<Subtitle> subtitles = new ArrayList();
//        subtitles.add(new Subtitle(3));
//        subtitles.add(new Subtitle(4));
//        subtitles.add(new Subtitle(2));
//        subtitles.add(new Subtitle(10));
//
//        System.out.println("Before sortt ...");
//        subtitles.forEach((_item) -> {
//            System.out.println(_item.toString());
//        });
//        Collections.sort(subtitles);
//
//        System.out.println("AFTER sortt ...");
//        subtitles.forEach((_item) -> {
//            System.out.println(_item.toString());
//        });
//    }
}
