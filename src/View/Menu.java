package View;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe que trata do Menu
 */
public class Menu {
    private List<String> opcoes;
    private int op;

    /**
     * Construtor por parâmetros da classe Menu
     * @param opcoes Opções do menu
     */
    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.op = 0;
    }

    /**
     * Executa a opção do utilizador
     */
    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }

    /**
     * Imprime o menu
     */
    private void showMenu() {
        System.out.println("\n *** Menu *** ");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair");
    }

    /**
     * Lê a opção do utilizador
     * @return opção escolhida
     */
    private int lerOpcao() {
        int op;
        Scanner is = new Scanner(System.in);

        System.out.print("Opções: ");
        try {
            op = is.nextInt();
        }
        catch (InputMismatchException e) {
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opção Inválida!");
            op = -1;
        }
        return op;
    }

    /**
     * Obtém a opção escolhida
     * @return Opção escolhida
     */
    public int getOpcao() {
        return this.op;
    }
}
