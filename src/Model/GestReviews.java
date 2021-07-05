package Model;

import Auxiliar.*;
import Exceptions.NaoExisteException;
import com.company.*;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GestReviews implements InterfM {
    private InterfCatalogo<InterfBusinesses> business_collection;
    private InterfCatalogo<InterfReviews> reviews_colection;
    private InterfCatalogo<InterfUsers> users_collection;
    private Estatisticas estatisticas;

    public static final String busFile = "Input/business_full.csv";
    public static final String revFile = "Input/reviews_1M.csv";
    public static final String usrFile = "Input/users_full.csv";

    public GestReviews() {
        this.users_collection = new CatalogoUsers();
        this.business_collection = new CatalogoBusinesses();
        this.reviews_colection = new CatalogoReviews();
    }

    /**
     * Inicializa model
     * @param busFile Ficheiro business
     * @param revFile Ficheiro review
     * @param usrFile Ficheiro user
     * @throws IOException Caso haja erros a ler ficheiros
     */
    public void init_GestReviews(String busFile, String revFile, String usrFile) throws IOException {
        this.estatisticas = new Estatisticas(revFile, busFile, usrFile);
        this.leitura_business_file(busFile);
        this.leitura_user_file(usrFile);
        this.leitura_review_file(revFile);
        this.finishStats();
    }
    //INFO STATS

    /**
     * Obtém informações para mostrar ao user referentes aos dados lidos
     * @return Lista de Strings formatadas para tabelas
     */
    public List<String> get_info_stats(){
        return this.estatisticas.get_stats_for_table();
    }
    //INFO QUERY 1

    /**
     * Obtém conjunto de negócios que não foram avaliados em nenhuma review
     * @return Set com ID de os negócios
     */
    public Set<String> negocios_sem_avaliacoes() {
        Predicate<InterfBusinesses> foi_avaliado = (InterfBusinesses::foi_avaliado);
        return this.business_collection.filtered_get_keys(foi_avaliado.negate());
    }
    //INFO QUERY 2

    /**
     * Número de reviews feitas num mês de um ano especìfico
     * @param month Mês das reviews
     * @param year Ano das reviews
     * @return Total de reviews
     */
    public int reviews_num_mes_especifico(int month, int year) {
        return this.estatisticas.specific_month_reviews(month, year);
    }

    /**
     * Quantidade de users que fizeram review num mês de um dado ano
     * @param month Mês da review
     * @param year Ano da review
     * @return Total de users
     */
    public int users_com_reviews_num_mes_especifico(int month, int year) {
        return this.estatisticas.specific_month_users(month, year);
    }
    //INFO QUERY 3

    /**
     * Número de reviews de um user para todos os meses
     * @param user_id ID do user
     * @return Lista com o número para todos os meses
     * @throws NaoExisteException Caso user  não exista
     */
    public int[] user_num_reviews_por_mes(String user_id) throws NaoExisteException {
        int[] meses = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        InterfUsers user = this.users_collection.get_value(user_id);

        for (String rev_id : user.getReviews_id()) {
            InterfReviews rev = this.reviews_colection.get_value(rev_id);
            int month = rev.getMonth();
            meses[month]++;
        }

        return meses;
    }

    /**
     * Negócios avaliados por um user, organizado por mês
     * @param user_id ID do user
     * @return Número de negócios para cada mês
     * @throws NaoExisteException Caso user não exista
     */
    public Map<Integer, Set<String>> user_bus_unicos_por_mes(String user_id) throws NaoExisteException {
        HashMap<Integer, Set<String>> meses = new HashMap<>();
        InterfUsers user = this.users_collection.get_value(user_id);

        for (int i = 0; i < 12; i++) {
            meses.put(i, new TreeSet<>());
        }
        for (String rev_id : user.getReviews_id()) {
            InterfReviews rev = this.reviews_colection.get_value(rev_id);
            String bus_id = rev.getBusiness_id();
            meses.get(rev.getMonth()).add(bus_id);
        }
        return meses;
    }

    /**
     * Total de estrelas dadas por um user organizadas por Mês
     * @param user_id Id do user
     * @return Total de estrelas organizadas por mês
     * @throws NaoExisteException Caso user não exista
     */
    public float[] user_total_stars_por_mes(String user_id) throws NaoExisteException {
        float[] meses = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        InterfUsers user = this.users_collection.get_value(user_id);

        for (String rev_id : user.getReviews_id()) {
            InterfReviews rev = this.reviews_colection.get_value(rev_id);
            int month = rev.getMonth();
            meses[month] += rev.getStars();
        }

        return meses;
    }

    //INFO QUERY 4

    /**
     * Número de reviews feitas de um dado business, organizados por mês
     * @param bus_id ID do negócio
     * @return Total de reviews desse negócio por mês
     * @throws NaoExisteException Caso negócio não exista
     */
    public int[] bus_num_reviews_por_mes(String bus_id) throws NaoExisteException {
        int[] meses = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        InterfBusinesses bus = this.business_collection.get_value(bus_id);

        for (List<String> ano : bus.getReviews_por_ano().values()) {
            for (String rev_id : ano) {
                InterfReviews rev = this.reviews_colection.get_value(rev_id);
                int month = rev.getMonth();
                meses[month]++;
            }
        }

        return meses;
    }

    /**
     * Users que avalariam dado negócio, organizados por mês
     * @param bus_id ID do negócio
     * @return Users organizados por mês
     * @throws NaoExisteException Caso negócio não exista
     */
    public Map<Integer, Set<String>> bus_users_unicos_por_mes(String bus_id) throws NaoExisteException {
        HashMap<Integer, Set<String>> meses = new HashMap<>();
        InterfBusinesses bus = this.business_collection.get_value(bus_id);

        for (int i = 0; i < 12; i++) meses.put(i, new TreeSet<>());
        for (List<String> ano : bus.getReviews_por_ano().values()) {
            for (String rev_id : ano) {
                InterfReviews rev = this.reviews_colection.get_value(rev_id);
                String user_id = rev.getUser_id();
                meses.get(rev.getMonth()).add(user_id);
            }
        }
        return meses;
    }

    /**
     * Total de stars dados a um negócio, organizados por mês
     * @param bus_id ID do negócio
     * @return Stars organizadas por mês
     * @throws NaoExisteException Caso negócio não exista
     */
    public float[] bus_total_stars_por_mes(String bus_id) throws NaoExisteException {
        float[] meses = new float[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        InterfBusinesses bus = this.business_collection.get_value(bus_id);

        for (List<String> ano : bus.getReviews_por_ano().values()) {
            for (String rev_id : ano) {
                InterfReviews rev = this.reviews_colection.get_value(rev_id);
                int month = rev.getMonth();
                meses[month] += rev.getStars();
            }
        }

        return meses;
    }
    //INFO QUERY 5

    /**
     * Negócios que um user avaliou e as vezes que o fez
     * @param user_id ID do user
     * @return Ids do negócio e vezes que o user avaliou-o
     * @throws NaoExisteException
     */
    public Map<String,Integer> negocios_avaliados_por_user(String user_id) throws NaoExisteException{
        Map<String,Integer> negocios = new HashMap<>();
        InterfUsers user = this.users_collection.get_value(user_id);

        for(String rev_id : user.getReviews_id()){
            String bus_id = this.reviews_colection.get_value(rev_id).getBusiness_id();
            String bus_name = this.business_collection.get_value(bus_id).getName();

            if(negocios.containsKey(bus_name))
            {
                int before = negocios.get(bus_name);
                negocios.replace(bus_name,before+1);
            }
            else negocios.put(bus_name,1);
        }
        return negocios;
    }
    //INFO QUERY 6
    /**
     * Set de users únicos que fizeram dadas reviews
     * @return Coleção de users únicos
     */
    public Set<String> users_unicos(List<String> revs_ids) throws NaoExisteException {
        Set<String> resultado = new TreeSet<>();
        for(String rev_id : revs_ids){
            InterfReviews review = this.reviews_colection.get_value(rev_id);
            resultado.add(review.getUser_id());
        }
        return resultado;
    }

    /**
     * Negócios com mais reviews, organizados por ano
     * @param top_x Quantidade de negócios por ano
     * @return Informação pedida guardada na classe auxiliar organizada por anos
     * @throws NaoExisteException Caso negócio não exista
     */
    public Map<Integer, BoundedTreeSet<AuxTripleIdQuantQuant>> mais_avaliados_por_ano(int top_x) throws NaoExisteException {
        Map<Integer,BoundedTreeSet<AuxTripleIdQuantQuant>> resultado = new TreeMap<>();

        for(String bus_id : this.business_collection.get_keys()){
            InterfBusinesses bus = this.business_collection.get_value(bus_id);
            Map<Integer, List<String>> bus_revs = bus.getReviews_por_ano();

            for(Map.Entry<Integer,List<String>> ano : bus_revs.entrySet()){
                int n_revs = ano.getValue().size();
                int n_ano = ano.getKey();
                Set<String> users_unicos = users_unicos(ano.getValue());
                int n_users_unicos = users_unicos.size();

                if(!(resultado.containsKey(n_ano)))
                    resultado.put(n_ano,new BoundedTreeSet<>(top_x));
                resultado.get(n_ano).add(new AuxTripleIdQuantQuant(bus_id,n_revs,n_users_unicos));
            }
        }
        return resultado;
    }
    //INFO QUERY 7
    /**
     * Método que dado um Set de ids de negócios retorna os 3 mais avaliados
     * @param bus_ids Set de ids de negócios
     * @return TreeSet com os três melhores negócios, por ordem decrescente
     */
    public BoundedTreeSet<AuxPairIdQuant> top3_negocios(Set<String> bus_ids) throws NaoExisteException {
        BoundedTreeSet<AuxPairIdQuant> result = new BoundedTreeSet<>(3);
        for(String bus_id : bus_ids){
            InterfBusinesses bus = this.business_collection.get_value(bus_id);
            AuxPairIdQuant novo = new AuxPairIdQuant(bus_id,bus.getNum_reviews());
            result.add(novo);
        }
        return result;
    }

    /**
     * TOP 3 negócios numa cidade
     * @return Informação pedida guardada na classe AuxPairIdQuant
     * @throws NaoExisteException Caso negócio não exista
     */
    public Map<String,BoundedTreeSet<AuxPairIdQuant>> top_3_por_cidade() throws NaoExisteException{
        Map<String,BoundedTreeSet<AuxPairIdQuant>> resultado = new TreeMap<>();
        Map<String,Map<String,Set<String>>> negocios_por_estado_cidade = this.estatisticas.getNegocios_por_estado_cidade();

        for(Map<String,Set<String>> estado : negocios_por_estado_cidade.values()){
            for(Map.Entry<String,Set<String>> cidade : estado.entrySet()){
                BoundedTreeSet<AuxPairIdQuant> top3_cidade = top3_negocios(cidade.getValue());
                resultado.put(cidade.getKey(),top3_cidade);
            }
        }
        return resultado;
    }
    //INFO QUERY 8

    /**
     * Coleção de negócios dado uma lista de reviews
     * @param revs_ids Lista de reviews
     * @return Negócios únicos avaliados nessas reviews
     * @throws NaoExisteException Caso negócio não exista
     */
    public Set<String> negocios_unicos(List<String> revs_ids) throws NaoExisteException {
        Set<String> resultado = new TreeSet<>();
        for(String rev_id : revs_ids){
            InterfReviews review = this.reviews_colection.get_value(rev_id);
            resultado.add(review.getBusiness_id());
        }
        return resultado;
    }

    /**
     * Users que mais negócios avaliaram
     * @param top_x Quantidade de users a retornar
     * @return Users ordenados pela quantidade de negócios que avaliaram
     * @throws NaoExisteException
     */
    public BoundedTreeSet<AuxPairIdQuant> top_x_users(int top_x) throws NaoExisteException{
        BoundedTreeSet<AuxPairIdQuant> resultado = new BoundedTreeSet<>(top_x);

        for(String user_id : this.users_collection.get_keys()){
            InterfUsers user = this.users_collection.get_value(user_id);

            List<String> reviews = user.getReviews_id();
            int n_negocios_unicos = negocios_unicos(reviews).size();
            resultado.add(new AuxPairIdQuant(user_id,n_negocios_unicos));
        }
        return resultado;
    }
    //INFO QUERY 9
    /**
     * Método que dadas uma lista de reviews devolve todos os users que
     * fizeram essa reviews, o número de vezes, e a média das suas avaliações
     * @param revs_ids Lista de Review IDs
     * @return Informação dos users sobre essa reviews
     * @throws NaoExisteException Prevenção de erro
     */
    public Map<String,AbstractMap.SimpleEntry<Integer,Float>> info_users_sobre_reviews(List<String> revs_ids) throws NaoExisteException {
        Map<String,AbstractMap.SimpleEntry<Integer,Float>> user_reviews_x_times = new HashMap<>();

        for(String rev_id : revs_ids){
            InterfReviews rev = this.reviews_colection.get_value(rev_id);
            String user_id = rev.getUser_id();
            if(!(user_reviews_x_times.containsKey(user_id)))
                user_reviews_x_times.put(user_id,new AbstractMap.SimpleEntry<>(1,rev.getStars()));
            else{
                int novo_valor_rev_times = user_reviews_x_times.get(user_id).getKey() + 1;
                float novo_valor_stars = user_reviews_x_times.get(user_id).getValue() + rev.getStars();
                user_reviews_x_times.replace(user_id,new AbstractMap.SimpleEntry<>(novo_valor_rev_times,novo_valor_stars));
            }
        }
        return user_reviews_x_times;
    }

    /**
     * Dado um negócio, devolve os users que mais o avaliaram
     * @param bus_id ID do negócio
     * @return Informação dos users que mais avaliaram na classe AuxTripleQuery9
     * @throws NaoExisteException
     */
    public Set<AuxTripleQuery9> users_mais_avaliaram_negocio(String bus_id) throws NaoExisteException{
        Set<AuxTripleQuery9> result = new TreeSet<>();
        InterfBusinesses bus = this.business_collection.get_value(bus_id);
        Map<Integer, List<String>> reviews_por_ano = bus.getReviews_por_ano();
        List<String> reviews_ids = reviews_por_ano.values().stream().flatMap(Collection::stream).collect(Collectors.toList());

        Map<String,AbstractMap.SimpleEntry<Integer,Float>> user_reviews_x_times = info_users_sobre_reviews(reviews_ids);

        for(Map.Entry<String,AbstractMap.SimpleEntry<Integer,Float>> user_info : user_reviews_x_times.entrySet()){
            AuxTripleQuery9 novo = new AuxTripleQuery9(user_info.getKey(),user_info.getValue().getKey(),user_info.getValue().getValue());
            result.add(novo);
        }

        return result;
    }
    //INFO QUERY 10
    /**
     * Método auxiliar que dado um set de negócios
     * retorna uma lista de AuxPairIdFloat com o seu Id e Média de Stars
     * @return Lista preenchida com objetos AuxPairIdFloat
     */
    public List<AuxPairIdFloat> media_negocios(Set<String> bus_ids) throws NaoExisteException {
        List<AuxPairIdFloat> resultado = new ArrayList<>();
        for(String bus_id : bus_ids){
            InterfBusinesses bus = this.business_collection.get_value(bus_id);
            AuxPairIdFloat novo = new AuxPairIdFloat(bus_id,bus.media_stars());
            resultado.add(novo);
        }
        return resultado;
    }

    /**
     * Calcula média de reviews de todos os negócios, organizador por estado e cidade
     * @return Informação organizada de todos os negócios na classe AuxPairIdFloat
     * @throws NaoExisteException Caso negócio não exista
     */
    public Map<String,Map<String,List<AuxPairIdFloat>>> media_negocios_cidade_a_cidade() throws NaoExisteException{
        Map<String,Map<String,List<AuxPairIdFloat>>> resultado = new TreeMap<>();
        Map<String,Map<String,Set<String>>> bus_ids = this.estatisticas.getNegocios_por_estado_cidade();

        for(Map.Entry<String,Map<String,Set<String>>> estado : bus_ids.entrySet()){
            String estado_name = estado.getKey();
            resultado.put(estado_name,new TreeMap<>());

            for(Map.Entry<String,Set<String>> cidade : estado.getValue().entrySet()){
                String city_name = cidade.getKey();
                List<AuxPairIdFloat> info = media_negocios(cidade.getValue());
                resultado.get(estado_name).put(city_name,info);
            }
        }
        return resultado;
    }
    /******LEITURA DE FICHEIROS E PREENCHIMENTOS DE STATS******/
    /**
     * Método responsável por ler ficheiro dos negócios
     * @param filepath Localização do ficheiro
     * @throws IOException Prevenção de erros IO
     */
    private void leitura_business_file(String filepath) throws IOException {
        try {
            FileInputStream fstream = new FileInputStream(filepath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            br.readLine();
            while ((strLine = br.readLine()) != null) {
                if (InterfBusinesses.parse_valido(strLine)) {
                    InterfBusinesses novo = InterfBusinesses.parse(strLine);
                    this.business_collection.add_value(novo);
                    this.estatisticas.add_business_info(novo.getState(),novo.getCity(),novo.getBusiness_id());
                }
            }
            in.close();
            System.out.println("BUSINESS LIDO!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Método responsável por ler ficheiros de users
     * @param filepath Localização do ficheiro
     * @throws IOException Prevenção de erros IO
     */
    private void leitura_user_file(String filepath) throws IOException {
        try {
            FileInputStream fstream = new FileInputStream(filepath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            br.readLine();
            while ((strLine = br.readLine()) != null) {
                if (InterfUsers.parse_valido(strLine)) {

                    if(!GestReviewsAppMVC.leitura_friends)
                    this.users_collection.add_value(InterfUsers.parse(strLine)); //ADD collection
                    else this.users_collection.add_value(InterfUsers.parse_with_friends(strLine));

                    this.estatisticas.add_user(); //stats numero users
                }
            }
            in.close();
            System.out.println("USERS LIDO!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    /**
     * Método responsável por ler ficheiros de reviews
     * @param filepath Localização do ficheiro
     * @throws IOException Prevenção de erros IO
     */
    private void leitura_review_file(String filepath) throws IOException {
        try {
            FileInputStream fstream = new FileInputStream(filepath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            br.readLine();
            while ((strLine = br.readLine()) != null) {
                if (InterfReviews.parse_valido(strLine)) {
                    InterfReviews novo = InterfReviews.parse(strLine);

                    if (this.users_collection.contains_key(novo.getUser_id()) &&
                            this.business_collection.contains_key(novo.getBusiness_id())) {
                        //SIMPLIFICAR REPETIÇÕES DE GETS
                        Consumer<InterfBusinesses> atualiza_bus = b -> b.update_info_from_review(novo.getStars(), novo.getYear(), novo.getReview_id());
                        this.business_collection.consume(novo.getBusiness_id(), atualiza_bus);//stats business info

                        Consumer<InterfUsers> atualiza_user = u -> u.add_review_to_list(novo.getReview_id());
                        this.users_collection.consume(novo.getUser_id(), atualiza_user);//stats user info

                        if (novo.getUseful() == 0 && novo.getFunny() == 0 && novo.getCool() == 0)
                            this.estatisticas.add_review_zero_impacto(); //stats review 0 impacto

                        this.estatisticas.add_review_month_year(novo.getMonth(), novo.getYear(), novo.getReview_id(),novo.getStars());
                        this.estatisticas.add_user_month_year(novo.getMonth(), novo.getYear(), novo.getUser_id());

                        this.reviews_colection.add_value(novo); //ADD collection
                    } else {
                        this.estatisticas.add_review_errada(); //stats review errada
                    }
                }
            }
            in.close();
            System.out.println("REVIEWS LIDO!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Uma vez que os ficheiro estejam lidos, atualiza informações dos stats
     */
    private void finishStats() {
        Predicate<InterfUsers> fez_reviews = InterfUsers::fez_reviews;
        int users_com_reviews = this.users_collection.filtered_amount(fez_reviews);
        this.estatisticas.add_users_info_final(users_com_reviews);

        Predicate<InterfBusinesses> foi_avaliado = InterfBusinesses::foi_avaliado;
        int bus_com_reviews = this.business_collection.filtered_amount(foi_avaliado);
        this.estatisticas.add_businesses_info_final(bus_com_reviews);
    }

    /**
     * Guarda dados do model num ficheiro binário
     * @param file_name Nome do ficheiro
     * @throws IOException Prevenção de erros IO
     */
    public void saveToBinary(String file_name) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file_name));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public List<String> getAllUsers(){
        return this.users_collection.get_keys();
    }

    public List<String> getAllBusiness(){ return this.business_collection.get_keys();}
}

