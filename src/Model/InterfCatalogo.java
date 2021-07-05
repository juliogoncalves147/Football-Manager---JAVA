package Model;

import Exceptions.NaoExisteException;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface InterfCatalogo<T> extends Serializable {
    int size();
    void add_value(T e);
    T get_value(String ident) throws NaoExisteException;
    void delete_value(String ident);
    List<String> get_keys();
    void consume(String id, Consumer<T> cons);
    boolean contains_key(String key);
    int filtered_amount(Predicate<T> p);
    Set<String> filtered_get_keys(Predicate<T> p);
}
