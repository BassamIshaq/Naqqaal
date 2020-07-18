package Naqqaal;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo 520
 */
public class Project implements Serializable {

    private String projectPath;
    private String mediaPath;
    private ArrayList<Subtitle> subtitleArrayList;
    private ArrayList<String> speakerNamesArrayList;

    public Project(String projectPath, String mediaPath, ArrayList<Subtitle> subtitlesArrayList) {
        this.projectPath = projectPath;
        this.mediaPath = mediaPath;
        this.subtitleArrayList = subtitlesArrayList;
        this.speakerNamesArrayList = new ArrayList<>();
//        speakerNamesArrayList.add("Dummy 1");
//        speakerNamesArrayList.add("Dummy 2");
//        speakerNamesArrayList.add("Dummy 3");
//        speakerNamesArrayList.add("Dummy 4");

    }

    @Override
    public String toString() {
        return "Project{" + "projectPath=" + projectPath + ", mediaPath=" + mediaPath + ", subtitleArrayList=" + subtitleArrayList + ", speakerNamesArrayList=" + speakerNamesArrayList + '}';
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setSubtitleArrayList(ArrayList<Subtitle> subtitleArrayList) {
        this.subtitleArrayList = subtitleArrayList;
    }

    public ArrayList<String> getSpeakerNamesArrayList() {
        return speakerNamesArrayList;
    }

    public ArrayList<Subtitle> getSubtitleArrayList() {
        return subtitleArrayList;
    }

    public boolean createProjectFile() {
        File file = new File(projectPath + "\\Project.ser");
        ObjectOutputStream outputStream = null;
        if (file.exists()) {
            file.delete();
        }
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(this);

        } catch (IOException ex) {
            System.out.println("Project.createProjectFile(): " + ex);
            return false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    System.out.println("Project.createProjectFile(): " + ex);
                    return false;
                }
            }
        }
        return true;
    }

    public static Project readProjectFile(String projectFilePath) {
        System.out.println("projectFilePath: " + projectFilePath);
        Project project = null;
        ObjectInputStream inputStream = null;
        try {
            // open file for reading
            inputStream = new ObjectInputStream(new FileInputStream(projectFilePath));
            project = (Project) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
//            System.out.println("Project.readProjectFile(): " + e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return project;
    }

    public static void main(String[] args) {
        
        ArrayList<Subtitle> arrayList = AllStaticMethods.readSrtFile("E:\\videoSubtitle.srt");
        Project project = new Project("E:\\ASUG test files\\pakistan", "E:\\video.mp4", arrayList);
        System.out.println("project.createProjectFile(): " + project.createProjectFile());

        project = readProjectFile("E:\\ASUG test files\\pakistan\\Project.ser");
        System.out.println(project.getSubtitleArrayList().get(0).getText());
    }
}
