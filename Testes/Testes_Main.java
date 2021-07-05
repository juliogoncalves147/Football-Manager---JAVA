import Tests.*;
import View.Menu;

import java.io.IOException;

public class Testes_Main {

    public static void main(String args[]) {
        Menu m = new Menu(new String[]{"Teste Queries",
                "Teste Leitura"
        });

        int op;
        do {
            m.executa();
            op = m.getOpcao();
            switch (op) {
                case 1:
                    Query_Tests qt = new Query_Tests();

                    try {
                        System.out.println(qt.exec());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    Load_Tests lt = new Load_Tests();
                    try {
                        System.out.println(lt.exec());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Fim");
                    break;
            }
        } while (op != 0);
    }
}
