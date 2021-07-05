package Model;

import java.util.*;

/**
 * Classe que trata dos businesses
 */
public class Business implements InterfBusinesses {
    private String business_id;
    private String name;
    private String city;
    private String state;
    private String categories;
    private float total_stars;
    private Map<Integer, List<String>> reviews_por_ano;

    /**
     * Construtor por parâmetros da classe Business
     * @param business_id Id do business
     * @param name Nome do business
     * @param city Cidade do business
     * @param state estado do business
     * @param categories Categorias do business
     * @param total_stars Total de estrelas do business
     * @param reviews_por_ano Reviews por ano do business
     */
    public Business(String business_id, String name, String city
            , String state, String categories,float total_stars, Map<Integer,List<String>> reviews_por_ano){
        this.business_id = business_id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.categories = categories;
        this.total_stars = total_stars;
        this.reviews_por_ano = reviews_por_ano;
    }

    /**
     * Construtor por cópia da classe Business
     * @param outro Objeto de onde vamos tirar os parâmetros
     */
    public Business(Business outro){
        this.business_id = outro.getBusiness_id();
        this.name = outro.getName();
        this.city = outro.getCity();
        this.state = outro.getState();
        this.categories = outro.getCategories();
        this.total_stars = outro.getTotal_stars();
        this.reviews_por_ano = outro.getReviews_por_ano();
    }

    /**
     * Obtém o id do business
     * @return Id do business
     */
    public String getBusiness_id() {
        return business_id;
    }

    /**
     * Altera o id do business
     * @param business_id Novo Id
     */
    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    /**
     * Obtém o nome do business
     * @return Nome
     */
    public String getName() {
        return name;
    }

    /**
     * Altera o nome do business
     * @param name Novo nome
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém a cidade do business
     * @return Cidade
     */
    public String getCity() {
        return city;
    }

    /**
     * Altera a cidade do business
     * @param city Nova cidade
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtém o estado do business
     * @return Estado
     */
    public String getState(){
        return state;
    }

    /**
     * Altera o estado do business
     * @param state Novo estado
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Obtém as categorias do business
     * @return Categorias
     */
    public String getCategories() {
        return categories;
    }

    /**
     * Altera as categorias do business
     * @param categories Novas categorias
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     * Obtém as reviews por ano do business
     * @return Reviews por ano
     */
    public Map<Integer, List<String>> getReviews_por_ano() {
        return new HashMap<>(reviews_por_ano);
    }

    /**
     * Altera as reviews por ano do business
     * @param reviews_por_ano Novas reviews por ano
     */
    public void setReviews_por_ano(Map<Integer, List<String>> reviews_por_ano) {
        this.reviews_por_ano = new HashMap<>(reviews_por_ano);
    }

    /**
     * Obtém o total de estrelas do business
     * @return Total de estrelas
     */
    public float getTotal_stars() {
        return total_stars;
    }

    /**
     * Altera o total de estrelas do business
     * @param total_stars Novo total de estrelas
     */
    public void setTotal_stars(float total_stars) {
        this.total_stars = total_stars;
    }

    /**
     * Obtém o número de reviews do business
     * @return Número de reviews
     */
    public int getNum_reviews() {
        int total = 0;
        for(List<String> ano : this.reviews_por_ano.values())
            total += ano.size();
        return total;
    }

    /**
     * Clone da classe Business
     * @return Business clonado
     */
    public Business clone(){
        return new Business(this);
    }

    /**
     * ToString da classe Business
     * @return String com todos os parâmetros da classe Business
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(this.business_id).append(" | ");
        sb.append(this.name).append(" | ");
        sb.append(this.city).append(" | ");
        sb.append(this.state).append(" | ");
        sb.append(this.categories);

        return sb.toString();
    }

    /**
     * Equals da classe Business
     * @param o Objeto a comparar
     * @return true no caso de serem iguais
     *         false no caso de serem diferentes
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Business business = (Business) o;
        return (this.business_id.equals(business.getBusiness_id()))
                && (this.name.equals(business.getName()))
                && (this.city.equals(business.getCity()))
                && (this.state.equals(business.getState()))
                && (this.categories.equals(business.getCategories()))
                && (this.total_stars == business.getTotal_stars())
                && (this.reviews_por_ano.equals(business.getReviews_por_ano()));
    }

    /**
     * HashCode da classe business
     * @return HashCode
     */
    public int hashCode() {
        return Arrays.hashCode(new Object[] {business_id, name, city, state, categories, total_stars, reviews_por_ano});
    }

    /**
     * Atualiza a informação de uma review
     * @param review_stars Estrelas da review
     * @param ano Ano da review
     * @param review_id Id da review
     */
    public void update_info_from_review(float review_stars, int ano, String review_id){
        this.total_stars += review_stars;
        if(!(this.reviews_por_ano.containsKey(ano)))
            this.reviews_por_ano.put(ano,new ArrayList<>());
        this.reviews_por_ano.get(ano).add(review_id);
    }

    /**
     * Verifica se um business foi avaliado alguma vez
     * @return true se foi avaliado
     *         false se nunca foi avaliado
     */
    public boolean foi_avaliado(){
        return this.getNum_reviews() > 0;
    }

    /**
     * Media de estrelas de um business
     * @return Média de estrelas de um negócio
     */
    public float media_stars(){
        if(this.getNum_reviews() == 0 || this.total_stars == 0) return 0;
        return this.total_stars/(float)this.getNum_reviews();
    }
}