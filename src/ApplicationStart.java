import db_objects.Author;
import db_objects.Picture;
import windows.MainPicturesWindow;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ApplicationStart {
    public static void main(String[] args) {
        Map<String, Picture> pictureMap = new HashMap<>();
        int pictureID = 0;
        int authorID = 0;

        Author author1 = new Author(
                authorID++,
                "Ivan Borisovich",
                1967,
                "My bio..............................");
        Picture pic1 = new Picture(
                pictureID++,
                author1,
                1980,
                "the war",
                "linkxscdcsdcsd");
        pictureMap.put("picture" + pic1.ID, pic1);
        Picture pic2 = new Picture(
                pictureID++,
                author1,
                1991,
                "crash",
                "xxx.www.3"
        );
        pictureMap.put("picture" + pic2.ID, pic2);
        new MainPicturesWindow(pictureMap, new Scanner(System.in)).execute();
    }
}