package View;

import Auxiliar.*;
import Controller.InterfC;
import Exceptions.NaoExisteException;
import Model.GestReviews;

import java.awt.*;
import java.io.IOException;
import java.time.Month;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableModel;

/**
 * Classe que trata da view
 */
public class View implements InterfV {
    private InterfC controller;

    /**
     * Construtor por parâmetros da classe View
     * @param controller Controlador
     */
    public View(InterfC controller) {
        this.controller = controller;
    }

    /**
     * Inicia o programa
     */
    public void run() {
        Menu m = new Menu(new String[]{"Carregar ficheiros",
                "Negocios Não Avaliados (Alf.)",
                "N. Reviews/Users Únicos num Mês-Ano",
                "Informação Mensal de User",
                "Informação Mensal de Business",
                "Negócios mais avaliados por um User (Qntd.)",
                "Top Negócios por Ano",
                "Top 3 Negócios por Cidade",
                "Top Users (Bus)",
                "Top Users a avaliar um Negócio",
                "Media Negocios (Est/Cid)",
                "Salvar Dados",
                "Carregar Dados"
        });

        int op;
        do {
            m.executa();
            op = m.getOpcao();
            switch (op) {
                case 1:
                    carregar_ficheiros();
                    break;
                case 2:
                    query1_negocios_nao_avaliados();
                    break;
                case 3:
                    query2_reviews_users_unicos_mes_ano();
                    break;
                case 4:
                    query3_info_mensal_user();
                    break;
                case 5:
                    query4_info_mensal_business();
                    break;
                case 6:
                    query5_negocios_mais_avaliados_por_um_user();
                    break;
                case 7:
                    query6_negocios_mais_avaliados_por_ano();
                    break;
                case 8:
                    query7_top_3_por_cidade();
                    break;
                case 9:
                    query8_top_users();
                    break;
                case 10:
                    query9_top_users_negocio_especifico();
                    break;
                case 11:
                    query10_media_negocios_todos();
                    break;
                case 12:
                    salvar_dados();
                    break;
                case 13:
                    carregar_dados();
                    break;
                default:
                    System.out.println("Fim");
                    break;
            }
        } while (op != 0);
    }

    /**
     * Visualização do carregamento de ficheiros e estatísticas
     */
    private void carregar_ficheiros() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Ficheiro dos Negócios: ");
            String bus_file = sc.nextLine();
            if (bus_file.equals("")) bus_file = GestReviews.busFile;
            System.out.println("Ficheiro das Reviews: ");
            String rev_file = sc.nextLine();
            if (rev_file.equals("")) rev_file = GestReviews.revFile;
            System.out.println("Ficheiro dos Users: ");
            String usrs_file = sc.nextLine();
            if (usrs_file.equals("")) usrs_file = GestReviews.usrFile;

            this.controller.carrega_ficheiros(bus_file, rev_file, usrs_file);

            List<String> info_estatisticas = this.controller.info_estatisticas();
            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(4, new String[]{"1","2","3","4"}));
            table.setAutoCreateRowSorter(true);

            PaginationDataProvider<String> dataProvider = createDataProvider(info_estatisticas);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 24);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Visualização da query 1
     */
    private void query1_negocios_nao_avaliados() {
        JFrame frame = createFrame();
        JTable table = new JTable(createObjectDataModel(2, new String[]{"Num", "Business ID"}));
        table.setAutoCreateRowSorter(true);

        Crono.start();
        Set<String> ids = this.controller.ids_negocios_nao_avaliados();
        Crono.printElapsedTime();

        List<String> auxiliar = new ArrayList<>();
        int i = 1;

        for (String s : ids) {
            auxiliar.add(i + ";" + s);
            i++;
        }

        PaginationDataProvider<String> dataProvider = createDataProvider(auxiliar);
        PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 10);
        frame.add(paginatedDecorator.getContentPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * Visualização da query 2
     */
    private void query2_reviews_users_unicos_mes_ano() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira ano: ");
        int ano = scan_int(sc, 1990, 2040); //Analisar valores para max e min, talvez variáves globais
        System.out.println("Insira mês: ");
        int mes = scan_int(sc, 1, 12);
        mes--; //Resto do programa trabalha com este intervalo de valores
        Crono.start();
        AbstractMap.SimpleEntry<Integer, Integer> result = this.controller.numeros_users_reviews_mes_especifico(mes, ano);
        Crono.printElapsedTime();
        //REVS em KEY | USERS em VALUES
        System.out.println("Número de reviews: " + result.getKey());
        System.out.println("Número de Users únicos: " + result.getValue());
    }

    /**
     * Visualização da query 3
     */
    private void query3_info_mensal_user() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira ID do user: ");
        String user_id = sc.nextLine();

        try {
            Crono.start();
            AuxTripleQuery3and4 resultado = this.controller.info_mensal_user(user_id);
            Crono.printElapsedTime();
            int[] n_revs = resultado.getNum_revs_mes();
            Map<Integer, Set<String>> neg_unicos = resultado.getVariavel_mensal_unica();
            float[] medias = resultado.getMedia_stars_mes();

            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(4, new String[]{"Mes", "N. Reviews", "Negócios Únicos", "Média Stars"}));
            table.setAutoCreateRowSorter(true);
            List<String> auxiliar = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                auxiliar.add(Month.of(i + 1).name() + ";" + n_revs[i] + ";" + neg_unicos.get(i).size() + ";" + medias[i]);
            }
            PaginationDataProvider<String> dataProvider = createDataProvider(auxiliar);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 12);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (NaoExisteException e) {
            System.out.println("User não existe!");
        }
    }

    /**
     * Visualização da query 4
     */
    private void query4_info_mensal_business() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira ID do business: ");
        String bus_id = sc.nextLine();

        try {
            Crono.start();
            AuxTripleQuery3and4 resultado = this.controller.info_mensal_bus(bus_id);
            Crono.printElapsedTime();
            int[] n_revs = resultado.getNum_revs_mes();
            Map<Integer, Set<String>> neg_unicos = resultado.getVariavel_mensal_unica();
            float[] medias = resultado.getMedia_stars_mes();

            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(4, new String[]{"Mes", "N. Reviews", "Users Únicos", "Média Stars"}));
            table.setAutoCreateRowSorter(true);
            List<String> auxiliar = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                auxiliar.add(Month.of(i + 1).name() + ";" + n_revs[i] + ";" + neg_unicos.get(i).size() + ";" + medias[i]);
            }
            PaginationDataProvider<String> dataProvider = createDataProvider(auxiliar);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 12);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (NaoExisteException e) {
            System.out.println("User não existe!");
        }
    }

    /**
     * Visualização da query 5
     */
    private void query5_negocios_mais_avaliados_por_um_user() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira ID do user: ");
        String user_id = sc.nextLine();
        try {
            Crono.start();
            Set<AuxPairIdQuant> resultado = this.controller.top_negocios_avaliados_por_user(user_id);
            Crono.printElapsedTime();
            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(2, new String[]{"Nome Negócio", "Avaliado Qnt."}));
            table.setAutoCreateRowSorter(true);
            List<String> auxiliar = new ArrayList<>();
            for (AuxPairIdQuant s : resultado) {
                auxiliar.add(s.getInfo() + ";" + s.getQuantidade());
            }
            PaginationDataProvider<String> dataProvider = createDataProvider(auxiliar);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 10);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (NaoExisteException e) {
            System.out.println("User não existe!");
        }

    }

    /**
     * Visualização da query 6
     */
    private void query6_negocios_mais_avaliados_por_ano() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quantos negócios por ano: ");
        int top = scan_int(sc, 1, 100000); //TORNAR EM VARIAVEIS GLOBAIS?
        try {
            Crono.start();
            Map<Integer, BoundedTreeSet<AuxTripleIdQuantQuant>> result = this.controller.negocios_mais_avaliados_por_ano(top);
            Crono.printElapsedTime();
            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(4, new String[]{"Ano", "Business ID", "Reviews", "Users Distintos"}));
            table.setAutoCreateRowSorter(true);

            List<String> aux = new ArrayList<>();
            for (Map.Entry<Integer, BoundedTreeSet<AuxTripleIdQuantQuant>> ano : result.entrySet()) {
                for (AuxTripleIdQuantQuant info : ano.getValue()) {
                    aux.add(ano.getKey().toString() + ";" + info.getInfo() + ";"
                            + info.getQuantidade1() + ";" + info.getQuantidade2());
                }
            }

            PaginationDataProvider<String> dataProvider = createDataProvider(aux);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 10);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (NaoExisteException e) {
            System.out.println("Erro na pesquisa!");
        }
    }

    /**
     * Visualização da query 7
     */
    private void query7_top_3_por_cidade() {
        try {
            Crono.start();
            Map<String, BoundedTreeSet<AuxPairIdQuant>> resultado = this.controller.negocios_top_3_por_cidade();
            Crono.printElapsedTime();
            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(3, new String[]{"Cidade", "Business ID", "Reviews"}));
            table.setAutoCreateRowSorter(true);

            List<String> aux = new ArrayList<>();
            for (Map.Entry<String, BoundedTreeSet<AuxPairIdQuant>> top_3 : resultado.entrySet()) {
                for (AuxPairIdQuant info : top_3.getValue()) {
                    aux.add(top_3.getKey() + ";" + info.getInfo() + ";"
                            + info.getQuantidade());
                }
            }
            PaginationDataProvider<String> dataProvider = createDataProvider(aux);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 10);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (NaoExisteException e) {
            System.out.println("Erro na pesquisa!");
        }
    }

    /**
     * Visualização da query 8
     */
    private void query8_top_users() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Quantos users: ");
        int top = scan_int(sc, 1, 1000000); //TORNAR EM VARIAVEIS GLOBAIS?
        try {
            Crono.start();
            BoundedTreeSet<AuxPairIdQuant> result = this.controller.top_x_users_gest_reviews(top);
            Crono.printElapsedTime();
            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(2, new String[]{"User ID", "Negócios Distintos"}));
            table.setAutoCreateRowSorter(true);
            List<String> aux = new ArrayList<>();
            for (AuxPairIdQuant info : result) {
                aux.add(info.getInfo() + ";" + info.getQuantidade());
            }
            PaginationDataProvider<String> dataProvider = createDataProvider(aux);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 10);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (NaoExisteException e) {
            System.out.println("Erro na pesquisa!");
        }
    }

    /**
     * Visualização da query 9
     */
    private void query9_top_users_negocio_especifico() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira ID do negócio: ");
        String bus_id = sc.nextLine();
        try {
            Crono.start();
            Set<AuxTripleQuery9> resultado = this.controller.users_com_mais_reviews(bus_id);
            Crono.printElapsedTime();
            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(3, new String[]{"User ID", "Avaliado Qnt.", "Média"}));
            table.setAutoCreateRowSorter(true);
            List<String> auxiliar = new ArrayList<>();
            for (AuxTripleQuery9 s : resultado) {
                auxiliar.add(s.getInfo() + ";" + s.getQuantidade1() + ";" + s.getQuantidade2());
            }
            PaginationDataProvider<String> dataProvider = createDataProvider(auxiliar);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 10);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (NaoExisteException e) {
            System.out.println("User não existe!");
        }
    }

    /**
     * Visualização da query 10
     */
    private void query10_media_negocios_todos() {
        try {
            Crono.start();
            Map<String,Map<String, List<AuxPairIdFloat>>> resultado = this.controller.media_negocio_por_estado_cidade();
            Crono.printElapsedTime();
            JFrame frame = createFrame();
            JTable table = new JTable(createObjectDataModel(4, new String[]{"Estado", "Cidade", "Negócio","Média"}));
            table.setAutoCreateRowSorter(true);
            List<String> auxiliar = new ArrayList<>();
            for(Map.Entry<String,Map<String,List<AuxPairIdFloat>>> estado : resultado.entrySet()){
                String estado_name = estado.getKey();

                for(Map.Entry<String,List<AuxPairIdFloat>> cidade : estado.getValue().entrySet()){
                    String city_name = cidade.getKey();

                    for(AuxPairIdFloat info : cidade.getValue()){
                        auxiliar.add(estado_name+";"+city_name+";"+info.getInfo()+";"+info.getQuantidade());
                    }
                }
            }
            PaginationDataProvider<String> dataProvider = createDataProvider(auxiliar);
            PaginatedTableDecorator<String> paginatedDecorator = PaginatedTableDecorator.decorate(table,
                    dataProvider, new int[]{5, 10, 20, 50, 75, 100}, 10);
            frame.add(paginatedDecorator.getContentPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (NaoExisteException e) {
            System.out.println("User não existe!");
        }
    }

    /**
     * Opção de Guardar dados em ficheiro binário
     */
    private void salvar_dados() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nome do ficheiro onde guardar: ");
            String file_name = sc.nextLine();
            if (file_name.equals(""))
                this.controller.saveBinaryFile("gestReviews.dat");
            else
                this.controller.saveBinaryFile(file_name);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Opção de guardar dados em ficheiro binário
     */
    private void carregar_dados() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nome do ficheiro a carregar: ");
            String file_name = sc.nextLine();
            this.controller.loadBinaryFile(file_name);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Cria tabela para representação de Informação
     * @param columns Colunas
     * @param column_names Strings de cabeçalho
     * @return Modelo da Table
     */
    private static TableModel createObjectDataModel(int columns, String[] column_names) {
        return new ObjectTableModel<String>() {
            @Override
            public Object getValueAt(String frase, int columnIndex) {
                String[] partes = frase.split(";");
                return partes[columnIndex];
            }

            @Override
            public int getColumnCount() {
                return columns;
            }

            @Override
            public String getColumnName(int column) {
                return column_names[column];
            }
        };
    }

    private static PaginationDataProvider<String> createDataProvider(List<String> lista) {

        final List<String> list = lista;

        return new PaginationDataProvider<String>() {
            @Override
            public int getTotalRowCount() {
                return list.size();
            }

            @Override
            public List<String> getRows(int startIndex, int endIndex) {
                return list.subList(startIndex, endIndex);
            }
        };
    }

    /**
     * Cria Frame da Tabela
     * @return JFRame
     */
    private static JFrame createFrame() {
        JFrame frame = new JFrame("Tabela");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(900, 900));
        return frame;
    }

    /**
     * Métodos usado para ler ints de forma segura
     *
     * @param in  Scanner a ler input
     * @param min Valor menor a ser aceitado
     * @param max Valor máximo a ser aceitado
     * @return int correspondente ao user input
     */
    public static int scan_int(Scanner in, int min, int max) {
        int integer;
        try {
            integer = in.nextInt();
            if (integer < min || integer > max) {
                System.out.println("Número inválido, tente outra vez!");
                in.nextLine();
                integer = scan_int(in, min, max);
            } else
                in.nextLine();
        } catch (Exception e) {
            System.out.println("Formato inválido: ");
            if (in.hasNext()) in.nextLine();
            integer = scan_int(in, min, max);
        }
        return integer;
    }
}
