package Model;

import Exceptions.NaoExisteException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe do catálogo das reviews
 */
public class CatalogoReviews implements InterfCatalogo<InterfReviews> {
    private Map<String,InterfReviews> review_collection;

    /**
     * Construtor por omissão da classe CatalogoReviews
     */
    public CatalogoReviews(){
        this.review_collection = new HashMap<>();
    }

    /**
     * Construtor por parâmetros da classe CatalogoReviews
     * @param review_collection Coleção de reviews
     */
    public CatalogoReviews(Map<String,InterfReviews> review_collection){
        this.review_collection = review_collection
                .values()
                .stream()
                .collect(Collectors.toMap(InterfReviews::getReview_id, InterfReviews::clone));
    }

    /**
     * Construtor por cópia da classe CatalogoReviews
     * @param outro Objeto de onde tiramos todos os parâmetros
     */
    public CatalogoReviews(CatalogoReviews outro){
        this.review_collection = outro.get_review_collection();
    }

    /**
     * Obtém a coleção de reviews
     * @return coleção de reviews
     */
    public Map<String,InterfReviews> get_review_collection(){
        return this.review_collection.values()
                .stream()
                .collect(Collectors.toMap(InterfReviews::getReview_id,InterfReviews::clone));
    }

    /**
     * Clone da classe CatalogoReviews
     * @return Coleção de reviews clonado
     */
    public CatalogoReviews clone(){
        return new CatalogoReviews(this);
    }

    /**
     * Tamanho da coleção de reviews
     * @return Tamanho
     */
    public int size() {
        return this.review_collection.size();
    }

    /**
     * Adiciona um review à coleção
     * @param e Review a adicionar
     */
    public void add_value(InterfReviews e) {
        this.review_collection.put(e.getReview_id(), e.clone());
    }

    /**
     * Obtém uma review através do Id do review
     * @param ident Id da review
     * @return Review
     * @throws NaoExisteException
     */
    public InterfReviews get_value(String ident) throws NaoExisteException {
        if(!(this.review_collection.containsKey(ident))) throw new NaoExisteException(ident);
        return this.review_collection.get(ident).clone();
    }

    /**
     * Remove uma review da coleção
     * @param ident Id da review a retirar
     */
    public void delete_value(String ident) {
        this.review_collection.remove(ident);
    }

    /**
     * Obtém todos os Id's da coleção de reviews
     * @return Lista com todos os Id's
     */
    public List<String> get_keys(){
        return new ArrayList<String>(this.review_collection.keySet());
    }

    /**
     * Aplica um consumer numa review
     * @param id Id da review a aplicar o consumer
     * @param cons Consumer
     */
    public void consume(String id,Consumer<InterfReviews> cons){
        cons.accept(this.review_collection.get(id));
    }

    /**
     * Verifica se contém uma review através do Id
     * @param key Id a procurar
     * @return true no caso de pertencer a coleção
     *         false no caso de não pertencer
     */
    public boolean contains_key(String key){
        return this.review_collection.containsKey(key);
    }

    /**
     * Retorna o número de elementos com filtragem do predicate
     * @param p Predicate
     * @return Número de elementos com filtragem do predicate
     */
    public int filtered_amount(Predicate<InterfReviews> p) {
        int total = 0;
        for(InterfReviews ir : this.review_collection.values()){
            if(p.test(ir))
                total++;
        }
        return total;
    }

    /**
     * Devolve as keys dos elementos que satisfazem a filtragem
     * @param p Predicate
     * @return Keys
     */
    public Set<String> filtered_get_keys(Predicate<InterfReviews> p) {
        Set<String> resultado = new TreeSet<>();
        for(InterfReviews ir : this.review_collection.values()){
            if(p.test(ir))
                resultado.add(ir.getReview_id());
        }
        return resultado;
    }
}
