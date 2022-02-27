package inputoutputtask3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import inputoutputtask2.GameProgress;

public class InputOutputTask3 {
    
    static final String DIR_PREFIX = "/home/aurumbeats/games/savegames";
    
    public static void openZip(String inputPath, String outputPath) {
        try (ZipInputStream zis = new ZipInputStream(
                new FileInputStream(inputPath))) {
            ZipEntry ze;
            String zipEntryName;
            String unzippedEntryName;
            while ((ze = zis.getNextEntry()) != null) {
                zipEntryName = ze.getName();
                unzippedEntryName = zipEntryName.substring(0,
                        zipEntryName.length() - 7);
                FileOutputStream fos = new FileOutputStream(outputPath
                        + "/" + unzippedEntryName);
                
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static GameProgress openProgress(String path) {
        GameProgress gp = null;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(path))) {
            gp = (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gp;
    }
    
    public static void main(String[] args) {
        
        openZip(DIR_PREFIX + "/zip.zip", DIR_PREFIX);

        System.out.println(openProgress(DIR_PREFIX + "/save0.dat"));
        System.out.println(openProgress(DIR_PREFIX + "/save5.dat"));
        System.out.println(openProgress(DIR_PREFIX + "/save9.dat"));
    }
    
}
