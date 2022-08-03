import bussinesslogic.KVServer;
import interactive_menu.Menu;

import java.io.IOException;

public class TaskTraker {
    public static void main(String[] args) throws IOException {
        new KVServer().start();
        Menu menu = new Menu();
        menu.prinMenu();
   }
}
