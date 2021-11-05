package Model;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.FileWriter;

import java.io.BufferedWriter;

public class FeuilleRoute {




   public void createFeuilleRoute(){



           // Path path = Paths.get("/home/besme/my_work_space/S3-IF/FeuilleRoutes/testCreateFeuille.html"); //creates Path instance
            try
            {
     /*           Path p= Files.createFile(path);     //creates file at specified location
                System.out.println("File Created at Path: "+p);*/
                File f = new File("/home/besme/my_work_space/S3-IF/FeuilleRoutes/test_feuille_route.htm");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write("<html><body><h1>Blah, Blah!</h1>");
                bw.write("<textarea cols=75 rows=10>");
                for (int ii=0; ii<20; ii++) {
                    bw.write("Blah blah..");
                }
                bw.write("</textarea>");
                bw.write("</body></html>");
                bw.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
}

