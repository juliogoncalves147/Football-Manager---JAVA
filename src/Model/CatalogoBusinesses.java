package Model;

import Exceptions.NaoExisteException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe do catálogo dos businesses
 */
public class CatalogoBusinesses implements InterfCatalogo<InterfBusinesses> {
    private Map<String,InterfBusinesses> business_collection;

    /**
     * Construtor por omissão da classe CatalogoBusinesses
     */
    public CatalogoBusinesses(){
        this.business_collection = new HashMap<>();
    }

    /**
     * Construtor por parâmetros da classe CatalogoBusinesses
     * @param business_collection Coleção de businesses
     */
    public CatalogoBusinesses(Map<String,InterfBusinesses> business_collection) {
        this.business_collection = business_collection
                .values()
                .stream()
                .collect(Collectors.toMap(InterfBusinesses::getBusiness_id, InterfBusinesses::clone));
    }

    /**
     * Construtor por cópia da classe CatalogoBusinesses
     * @param outro Objeto de onde tiramos todos os parâmetros
     */
    public CatalogoBusinesses(CatalogoBusinesses outro){
        this.business_collection = outro.getBusiness_collection();
    }

    /**
     * Obtém a coleção de businesses
     * @return coleção de businesses
     */
    public Map<String,InterfBusinesses> getBusiness_collection(){
        return this.business_collection.values()
                .stream()
                .collect(Collectors.toMap(InterfBusinesses::getBusiness_id, InterfBusinesses::clone));
    }

    /**
     * Clone da classe CatalogoBusinesses
     * @return Coleção de businesses clonado
     */
    public CatalogoBusinesses clone(){
        return new CatalogoBusinesses(this);
    }

    /**
     * Tamanho da coleção de businesses
     * @return Tamanho
     */
    public int size() {
        return this.business_collection.size();
    }

    /**
     * Adiciona um business à coleção
     * @param e Business a adicionar
     */
    public void add_value(InterfBusinesses e) { this.business_collection.put(e.getBusiness_id(), e.clone()); }

    /**
     * Obtém um business através do Id do business
     * @param ident Id do business
     * @return Business
     * @throws NaoExisteException
     */
    public InterfBusinesses get_value(String ident) throws NaoExisteException {
        if(!(this.business_collection.containsKey(ident))) throw new NaoExisteException(ident);
        return this.business_collection.get(ident).clone();
    }

    /**
     * Remove um business da coleção
     * @param ident Id do business a retirar
     */
    public void delete_value(String ident) {
        this.business_collection.remove(ident);
    }

    /**
     * Obtém todos os Id's da coleção de businesses
     * @return Lista com todos os Id's
     */
    public List<String> get_keys(){ return new ArrayList<String>(this.business_collection.keySet()); }

    /**
     * Aplica um consumer num business
     * @param id Id do business a aplicar o consumer
     * @param cons Consumer
     */
    public void consume(String id, Consumer<InterfBusinesses> cons){
        cons.accept(this.business_collection.get(id));
    }

    /**
     * Verifica se contém um business através do Id
     * @param key Id a procurar
     * @return true no caso de pertencer a coleção
     *         false no caso de não pertencer
     */
    public boolean contains_key(String key){
        return this.business_collection.containsKey(key);
    }

    /**
     * Retorna o número de elementos com filtragem do predicate
     * @param p Predicate
     * @return Número de elementos com filtragem do predicate
     */
    public int filtered_amount(Predicate<InterfBusinesses> p) {
        int total = 0;
        for(InterfBusinesses ib : this.business_collection.values()){
            if(p.test(ib))
                total++;
        }
        return total;
    }

    /**
     * Devolve as keys dos elementos que satisfazem a filtragem
     * @param p Predicate
     * @return Keys
     */
    public Set<String> filtered_get_keys(Predicate<InterfBusinesses> p) {
        Set<String> resultado = new TreeSet<>();
        for(InterfBusinesses ib : this.business_collection.values()){
            if(p.test(ib))
                resultado.add(ib.getBusiness_id());
        }
        return resultado;
    }
}
