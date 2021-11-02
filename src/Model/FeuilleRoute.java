package Model;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


public class FeuilleRoute {




   public void createFeuilleRoute(){



            Path path = Paths.get("/home/besme/my_work_space/S3-IF/FeuilleRoutes/testCreateFeuille.txt"); //creates Path instance
            try
            {
                Path p= Files.createFile(path);     //creates file at specified location
                System.out.println("File Created at Path: "+p);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
}

