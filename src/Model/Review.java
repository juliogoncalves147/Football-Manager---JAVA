package Model;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Classe que trata das reviews
 */
public class Review implements InterfReviews {
    private String review_id;
    private String user_id;
    private String business_id;
    private float stars;
    private int useful;
    private int funny;
    private int cool;
    private LocalDateTime date;
    private String text;

    /**
     * Construtor por parâmetros da classe Review
     * @param review_id Id da review
     * @param user_id Id do User
     * @param business_id Id do business
     * @param stars Estrelas
     * @param useful Useful
     * @param funny Funny
     * @param cool Cool
     * @param date Data
     * @param text Texto
     */
    public Review(String review_id, String user_id, String business_id
            , float stars, int useful, int funny
            , int cool, LocalDateTime date, String text) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.business_id = business_id;
        this.stars = stars;
        this.useful = useful;
        this.funny = funny;
        this.cool = cool;
        this.date = date;
        this.text = text;
    }

    /**
     * Construtor por cópia da classe Review
     * @param outro Objeto de onde tiramos todos os parâmetros
     */
    public Review(Review outro){
        this.review_id = outro.getReview_id();
        this.user_id = outro.getUser_id();
        this.business_id = outro.getBusiness_id();
        this.stars = outro.getStars();
        this.useful = outro.getUseful();
        this.funny = outro.getFunny();
        this.cool = outro.getCool();
        this.date = outro.getDate();
        this.text = outro.getText();
    }

    /**
     * Obtém o review Id
     * @return Id da review
     */
    public String getReview_id() {
        return review_id;
    }

    /**
     * Altera o id da review
     * @param review_id Novo review id
     */
    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    /**
     * Obtém o user id
     * @return id do user
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Altera o user id
     * @param user_id Novo User id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
     * @param business_id Novo business id
     */
    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    /**
     * Obtém as estrelas da review
     * @return Estrelas
     */
    public float getStars() {
        return stars;
    }

    /**
     * Altera as estrelas da review
     * @param stars Novas estrelas
     */
    public void setStars(float stars) {
        this.stars = stars;
    }

    /**
     * Obtém o useful da review
     * @return useful
     */
    public int getUseful() {
        return useful;
    }

    /**
     * Altera o useful de uma review
     * @param useful Novo useful
     */
    public void setUseful(int useful) {
        this.useful = useful;
    }

    /**
     * Obtém o funny da review
     * @return funny
     */
    public int getFunny() {
        return funny;
    }

    /**
     * Altera o funny da review
     * @param funny Novo funny
     */
    public void setFunny(int funny) {
        this.funny = funny;
    }

    /**
     * Obtém o cool da review
     * @return cool
     */
    public int getCool() {
        return cool;
    }

    /**
     * Altera o cool da review
     * @param cool novo cool
     */
    public void setCool(int cool) {
        this.cool = cool;
    }

    /**
     * Obtém a data da review
     * @return data
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Altera a data da review
     * @param date nova data
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Obtém o texto da review
     * @return texto
     */
    public String getText() {
        return text;
    }

    /**
     * Altera o texto da review
     * @param text Novo texto
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Clone da classe Review
     * @return Review clonada
     */
    public Review clone(){
        return new Review(this);
    }

    /**
     * ToString da classe Review
     * @return String com todos os parâmetros da review
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(this.review_id).append(" | ");
        sb.append(this.business_id).append(" | ");
        sb.append(this.user_id).append(" | ");
        sb.append(this.stars).append(" | ");
        sb.append(this.useful).append(" | ");
        sb.append(this.funny).append(" | ");
        sb.append(this.cool).append(" | ");
        sb.append(this.date).append(" | ");
        sb.append(this.text).append("\n");

        return sb.toString();
    }

    /**
     * Obtém valor do mês de 0 a 11
     * @return Valor do mês no campo date
     */
    public int getMonth(){
        return this.date.getMonthValue()-1;
    }

    /**
     * Obtém ano da Review
     * @return Ano do campo date
     */
    public int getYear(){
        return this.date.getYear();
    }

    /**
     * Equals da classe Review
     * @param o Objeto a comparar
     * @return true no caso de serem iguais
     *         false no caso de serem diferentes
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review rev = (Review) o;
        return (this.review_id.equals(rev.getBusiness_id()))
                && (this.user_id.equals(rev.getUser_id()))
                && (this.business_id.equals(rev.getBusiness_id()))
                && (this.text.equals(rev.getText()))
                && (this.stars == rev.getStars())
                && (this.useful == rev.getUseful())
                && (this.funny == rev.getFunny())
                && (this.cool == rev.getCool())
                && (this.date.isEqual(rev.getDate()));
    }

    /**
     * HashCode da classe Review
     * @return HashCode
     */
    public int hashCode() {
        return Arrays.hashCode(new Object[] {business_id,user_id,review_id,text,stars,useful,funny,cool,date});
    }
}
