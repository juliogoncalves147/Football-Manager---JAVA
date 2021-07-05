package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que trata dos users
 */
public class User implements InterfUsers {
    private String user_id;
    private String name;
    private String friends;
    private List<String> reviews_id;

    /**
     * Construtor por parâmetros da classe User
     * @param user_id Id do user
     * @param name Nome do user
     * @param friends Friends do user
     * @param reviews_id Reviews feitas pelo user
     */
    public User(String user_id, String name, String friends,List<String> reviews_id){
        this.user_id = user_id;
        this.name = name;
        this.friends = friends;
        this.reviews_id = new ArrayList<>(reviews_id);
    }

    /**
     * Construtor por cópia da classe User
     * @param outro Objeto de onde tiramos todos os parâmetros
     */
    public User(User outro){
        this.user_id = outro.getUser_id();
        this.name = outro.getName();
        this.friends = outro.getFriends();
        this.reviews_id = outro.getReviews_id();
    }

    /**
     * Obtém o Id do user
     * @return Id do user
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Altera o Id do user
     * @param user_id Novo id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Obtém o nome do user
     * @return Nome do user
     */
    public String getName() {
        return name;
    }

    /**
     * Altera o nome do user
     * @param name Novo nome
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém os friends do user
     * @return friends
     */
    public String getFriends() {
        return friends;
    }

    /**
     * Altera os friends do user
     * @param friends Novos friends
     */
    public void setFriends(String friends) {
        this.friends = friends;
    }

    /**
     * Obtém a lista de reviews do user
     * @return Lista de reviews
     */
    public List<String> getReviews_id() {
        return new ArrayList<>(this.reviews_id);
    }

    /**
     * Altera a lista de reviews do user
     * @param reviews_id Novas reviews
     */
    public void setReviews_id(List<String> reviews_id) {
        this.reviews_id = new ArrayList<>(reviews_id);
    }

    /**
     * Clone da classe User
     * @return User clonado
     */
    public User clone(){
        return new User(this);
    }

    /**
     * ToString da classe User
     * @return String com todos os parâmetros do user
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(this.user_id).append(" | ");
        sb.append(this.name).append(" | ");
        sb.append(this.friends).append("\n");

        return sb.toString();
    }

    /**
     * Equals da classe User
     * @param o Objeto a comparar
     * @return true caso sejam iguais
     *         false caso sejam diferentes
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return (this.user_id.equals(user.getUser_id())
                && (this.name.equals(user.getName()))
                && (this.friends.equals(user.getFriends()))
                && (this.reviews_id.equals(user.getReviews_id())));
    }

    /**
     * HashCode da classe User
     * @return HashCode
     */
    public int hashCode() {
        return Arrays.hashCode(new Object[] {user_id,name,friends,reviews_id});
    }

    /**
     * Adiciona uma review a lista de reviews do user
     * @param review_id review a adicionar
     */
    public void add_review_to_list(String review_id){this.reviews_id.add(review_id);}

    /**
     * Verifica se um User fez alguma review
     * @return true no caso de ter feito reviews
     *         false caso não tenha feito reviews
     */
    public boolean fez_reviews() {
        return this.reviews_id.size() > 0;
    }
}