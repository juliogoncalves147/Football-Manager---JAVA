package Model;

import Exceptions.NaoExisteException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe do catálogo dos users
 */
public class CatalogoUsers implements InterfCatalogo<InterfUsers> {
    private Map<String,InterfUsers> user_collection;

    /**
     * Construtor por omissão da classe CatalogoUsers
     */
    public CatalogoUsers(){
        this.user_collection = new HashMap<>();
    }

    /**
     * Construtor por parâmetros da classe CatalogoUsers
     * @param user_collection Coleção de users
     */
    public CatalogoUsers(Map<String,InterfUsers> user_collection){
        this.user_collection = user_collection
                .values()
                .stream()
                .collect(Collectors.toMap(InterfUsers::getUser_id, InterfUsers::clone));
    }

    /**
     * Construtor por cópia da classe CatalogoUsers
     * @param outro Objeto de onde tiramos todos os parâmetros
     */
    public CatalogoUsers(CatalogoUsers outro){
        this.user_collection = outro.get_user_collection();
    }

    /**
     * Obtém a coleção de users
     * @return coleção de users
     */
    public Map<String,InterfUsers> get_user_collection(){
        return this.user_collection.values()
                .stream()
                .collect(Collectors.toMap(InterfUsers::getUser_id, InterfUsers::clone));
    }

    /**
     * Clone da classe CatalogoUsers
     * @return Coleção de users clonado
     */
    public CatalogoUsers clone(){
        return new CatalogoUsers(this);
    }

    /**
     * Tamanho da coleção de users
     * @return Tamanho
     */
    public int size(){
        return this.user_collection.size();
    }

    /**
     * Adiciona um user à coleção
     * @param novo User a adicionar
     */
    public void add_value(InterfUsers novo){
        this.user_collection.put(novo.getUser_id(),novo.clone());
    }

    /**
     * Obtém um user através do Id do user
     * @param ident Id do user
     * @return User
     * @throws NaoExisteException
     */
    public InterfUsers get_value(String ident) throws NaoExisteException {
        if(!(this.user_collection.containsKey(ident))) throw new NaoExisteException(ident);
        return this.user_collection.get(ident).clone();
    }

    /**
     * Remove um user da coleção
     * @param ident Id do user a retirar
     */
    public void delete_value(String ident){
        this.user_collection.remove(ident);
    }

    /**
     * Obtém todos os Id's da coleção de users
     * @return Lista com todos os Id's
     */
    public List<String> get_keys(){
        return new ArrayList<String>(this.user_collection.keySet());
    }

    /**
     * Aplica um consumer num user
     * @param id Id do user a aplicar o consumer
     * @param cons Consumer
     */
    public void consume(String id, Consumer<InterfUsers> cons){
        cons.accept(this.user_collection.get(id));
    }

    /**
     * Verifica se contém um user através do Id
     * @param key Id a procurar
     * @return true no caso de pertencer a coleção
     *         false no caso de não pertencer
     */
    public boolean contains_key(String key){
        return this.user_collection.containsKey(key);
    }

    /**
     * Retorna o número de elementos com filtragem do predicate
     * @param p Predicate
     * @return Número de elementos com filtragem do predicate
     */
    public int filtered_amount(Predicate<InterfUsers> p) {
        int total = 0;
        for(InterfUsers iu : this.user_collection.values()){
            if(p.test(iu))
                total++;
        }
        return total;
    }

    /**
     * Devolve as keys dos elementos que satisfazem a filtragem
     * @param p Predicate
     * @return Keys
     */
    public Set<String> filtered_get_keys(Predicate<InterfUsers> p) {
        Set<String> resultado = new TreeSet<>();
        for(InterfUsers iu : this.user_collection.values()){
            if(p.test(iu))
                resultado.add(iu.getUser_id());
        }
        return resultado;
    }
}
