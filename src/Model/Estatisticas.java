package Model;

import java.io.Serializable;
import java.time.Month;
import java.util.*;

public class Estatisticas implements Serializable {
    private String file_name_rev;
    private String file_name_bus;
    private String file_name_usr;

    private int numero_negocios;
    private int negocios_avaliados;
    private int negocio_nao_avaliados;

    private int numero_users;
    private int users_com_reviews;
    private int users_sem_reviews;

    private int review_erradas;
    private int reviews_zero_impacto;
    private float[] total_stars_mes;

    /**
     * Guarda ids de todas as reviews
     * Key de primeiro Map guarda o ano
     * Key do segundo ano guarda o mes
     * List guarda id de todas as reviews feitas nesse mes
     */
    private Map<Integer,Map<Integer,List<String>>> reviews_por_ano_mes;

    /**
     * Guarda ids de todos os users
     * Key do primeiro Map guarda o ano
     * Key do segundo Map guarda o mes
     * Set guarda id de todos os users que fizeram uma review esse mês
     */
    private Map<Integer,Map<Integer,Set<String>>> users_por_ano_mes;

    /**
     * Guarda ids de todas os negocios
     * Key do primeiro Map guarda o Estado
     * Key do segundo Map guarda a Cidade
     * List guarda ID de todos os negocios nessa cidade
     */
    private Map<String,Map<String,Set<String>>> negocios_por_estado_cidade;

    public Estatisticas(String fnr,String fnb,String fnu){
        this.file_name_rev = fnr;
        this.file_name_bus = fnb;
        this.file_name_usr = fnu;
        this.numero_negocios = 0;
        this.negocios_avaliados = 0;
        this.negocio_nao_avaliados = 0;
        this.numero_users = 0;
        this.users_com_reviews = 0;
        this.users_sem_reviews = 0;
        this.review_erradas = 0;
        this.reviews_zero_impacto = 0;
        this.total_stars_mes = new float[]{0,0,0,0,0,0,0,0,0,0,0,0};
        this.reviews_por_ano_mes = new HashMap<>();
        this.users_por_ano_mes = new HashMap<>();
        this.negocios_por_estado_cidade = new TreeMap<>();
    }

    /**
     * Obtém informação de todos os negócios orgazinados por estado/cidade
     * @return Negócios organizados
     */
    public Map<String,Map<String,Set<String>>> getNegocios_por_estado_cidade(){
        return new TreeMap<>(this.negocios_por_estado_cidade);
    }

    /**
     * Adiciona aos stats uma user
     */
    public void add_user(){this.numero_users++;}

    /**
     * Adiciona aos stats uma review errada
     */
    public void add_review_errada(){this.review_erradas++;}

    /**
     * Adiciona aos stats uma review com zero impacto
     */
    public void add_review_zero_impacto(){this.reviews_zero_impacto++;}

    /**
     * Atualiza informação dos stats referente aos negócios organizados por estado/cidade
     * @param state Estado da cidade
     * @param city Cidade do negócio
     * @param bus_id ID do negócio
     */
    public void add_business_info(String state,String city,String bus_id){
        this.numero_negocios++;
        if(!(this.negocios_por_estado_cidade.containsKey(state))){
            this.negocios_por_estado_cidade.put(state,new TreeMap<>());
        }
        if(!(this.negocios_por_estado_cidade.get(state).containsKey(city))){
            this.negocios_por_estado_cidade.get(state).put(city,new TreeSet<>());
        }
        this.negocios_por_estado_cidade.get(state).get(city).add(bus_id);
    }

    /**
     * Atualiza informação dos stats referente às reviews organizadas por ano/mês
     * @param mes Mês da review
     * @param year Ano da review
     * @param review_id ID da review
     * @param stars Stars dadas na review
     */
    public void add_review_month_year(int mes, int year, String review_id,float stars){
        if(!(this.reviews_por_ano_mes.containsKey(year))){
            this.reviews_por_ano_mes.put(year,new HashMap<>());
        }
        if(!(this.reviews_por_ano_mes.get(year).containsKey(mes))){
            this.reviews_por_ano_mes.get(year).put(mes,new ArrayList<>());
        }
        this.reviews_por_ano_mes.get(year).get(mes).add(review_id);
        this.total_stars_mes[mes] += stars;
    }

    /**
     * Atualiza informação dos stats referente aos users organizados por ano/mês (em que fizeram review)
     * @param mes Mês da review
     * @param year Ano da review
     * @param user_id ID do user
     */
    public void add_user_month_year(int mes, int year, String user_id){
        if(!(this.users_por_ano_mes.containsKey(year))){
            this.users_por_ano_mes.put(year,new HashMap<>());
        }
        if(!(this.users_por_ano_mes.get(year).containsKey(mes))){
            this.users_por_ano_mes.get(year).put(mes,new TreeSet<>());
        }
        this.users_por_ano_mes.get(year).get(mes).add(user_id);
    }

    /**
     * Atualiza informação dos stats referente aos users com reviews e sem reviews
     * @param users_with_reviews Quantidade de users que fizeram reviews
     */
    public void add_users_info_final(int users_with_reviews){
        this.users_com_reviews = users_with_reviews;
        this.users_sem_reviews = this.numero_users - users_with_reviews;
    }

    /**
     * Atualiza informação dos stats referente aos negócios avaliados e não avaliados
     * @param bus_with_reviews Quantidade de negócios avaliados
     */
    public void add_businesses_info_final(int bus_with_reviews){
        this.negocios_avaliados = bus_with_reviews;
        this.negocio_nao_avaliados = this.numero_negocios - bus_with_reviews;
    }

    /**
     * Retorna quantidade de reviews feitas num dado mês
     * Percorre reviews_por_ano_mes e analisa para todos os anos, o mesmo mês
     * @return Valor resultante
     */
    public int monthly_reviews(int mes){
        int total = 0;
        for(Map<Integer,List<String>> ano : this.reviews_por_ano_mes.values()){
            if(ano.containsKey(mes))
                total += ano.get(mes).size();
        }
        return total;
    }

    /**
     * Número de users únicos que fizeram review num dado mês
     * @param mes Mês das reviews
     * @return Resultado total de reviews
     */
    public int monthly_unique_users(int mes)
    {
        Set<String> resutado = new HashSet<>();
        for(Map<Integer,Set<String>> ano : this.users_por_ano_mes.values()){
            if(ano.containsKey(mes))
                resutado.addAll(ano.get(mes));
        }
        return resutado.size();
    }
    /**
     * Quantidade de reviews feitas num mes de um ano também forncedio
     * @param mes Mês a pesquisar
     * @param ano Ano a pesquisar
     * @return Quantida total
     */
    public int specific_month_reviews(int mes,int ano){
        if(this.reviews_por_ano_mes.containsKey(ano)){
            if(this.reviews_por_ano_mes.get(ano).containsKey(mes)){
                return this.reviews_por_ano_mes.get(ano).get(mes).size();
            }
            else
                return 0;
        }
        else
            return 0;
    }
    /**
     * Quantidade de users que fizeram reviews num mês especifico
     * @param mes Mês a pesquisar
     * @param ano Ano a pesquisar
     * @return Quantida total
     */
    public int specific_month_users(int mes,int ano){
        if(this.users_por_ano_mes.containsKey(ano)){
            if(this.users_por_ano_mes.get(ano).containsKey(mes)){
                return this.users_por_ano_mes.get(ano).get(mes).size();
            }
            else
                return 0;
        }
        else
            return 0;
    }

    /**
     * Calcula o número de reviews feitas num mês e guarda todos a informação de todos os meses numa lista
     * @return Lista com o número de reviews de cada mês
     */
    public List<Integer> numero_total_reviews_por_mes(){
        List<Integer> resultado = new ArrayList<>(12);

        for(int i = 0 ; i < 12 ; i++){
            resultado.add(i,this.monthly_reviews(i));
        }

        return resultado;
    }
    /**
     * Calcula os utilizadores únicos que fizeram reviews num dado mês e junta tudo numa lista de todos os meses
     * @return Lista com total de utilizadores que fizeram reviews num mês
     */
    public List<Integer> numero_utilizadores_avaliaram_por_mes(){
        List<Integer> resultado = new ArrayList<>(12);

        for(int i = 0 ; i < 12 ; i++){
            resultado.add(i,this.monthly_unique_users(i));
        }

        return resultado;
    }
    /**
     * Calcula as médias de todas as reviews feitas num determinado mês
     * @return Lista das médias para cada mês
     */
    public List<Float> medias_reviews_por_mes(){
        List<Float> resultado = new ArrayList<>(12);
        List<Integer> auxiliar = this.numero_total_reviews_por_mes();

        for(int i = 0 ; i < 12 ; i++){
            resultado.add(i,this.total_stars_mes[i]/(float)auxiliar.get(i));
        }
        return  resultado;
    }
    /**
     * Calcula a média de todas as reviews guardadas no programa
     * @return Valor da média
     */
    public float media_global_reviews(){
        float total_stars = 0;
        for(int i = 0 ; i < 12 ; i++)
            total_stars += this.total_stars_mes[i];
        int total_reviews = this.numero_total_reviews_por_mes()
                .stream().mapToInt(Integer::intValue).sum();
        return total_stars/(float)total_reviews;
    }
    /**
     * Método que organiza estatisticas para serem apresentadas ao user
     * numa tabela JTable
     * @return Lista de Strings formatadas para representação
     */
    public List<String> get_stats_for_table(){
        List<String> resultado = new ArrayList<>();
        List<Float> media_reviews = this.medias_reviews_por_mes();
        List<Integer> users_unicos = this.numero_utilizadores_avaliaram_por_mes();

        resultado.add("Ficheiro Users;"+this.file_name_usr+";-;-");
        resultado.add("Ficheiro Reviews;"+this.file_name_rev+";-;-");
        resultado.add("Ficheiro Businesses;"+this.file_name_bus+";-;-");
        resultado.add("Reviews Erradas;"+this.review_erradas+";-;-");
        resultado.add("Reviews 0 impactio;"+this.reviews_zero_impacto+";-;-");
        resultado.add("Negócios;"+this.numero_negocios+";Users;"+this.numero_users);
        resultado.add("Negócios avaliados;"+this.negocios_avaliados+";Users c/ Reviews;"+this.users_com_reviews);
        resultado.add("Negócios não avaliados;"+this.negocio_nao_avaliados+";Users s/ Reviews;"+this.users_sem_reviews);
        resultado.add("Mês;Total de Reviews;Média de Stars;Users Únicos");
        for(int i = 0 ; i < 12 ; i++){
            resultado.add(Month.of(i + 1).name()+";"+this.monthly_reviews(i)+";"+media_reviews.get(i)
                    +";"+users_unicos.get(i));
        }
        resultado.add("Media Global;"+this.media_global_reviews()+";-;-");
        return resultado;
    }
}
