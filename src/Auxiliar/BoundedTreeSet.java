package Auxiliar;

import java.io.Serializable;
import java.util.*;

/**
 * Classe auxiliar para fazer treeSet's com tamanho estático
 */
public class BoundedTreeSet<E> extends TreeSet<E> implements Serializable {
    private int maxSize = Integer.MAX_VALUE;

    public BoundedTreeSet(int maxSize) {
        super();
        this.setMaxSize(maxSize);
    }
    public BoundedTreeSet(int maxSize, Collection<? extends E> c) {
        super(c);
        this.setMaxSize(maxSize);
    }
    public BoundedTreeSet(int maxSize, Comparator<? super E> c) {
        super(c);
        this.setMaxSize(maxSize);
    }
    public BoundedTreeSet(int maxSize, SortedSet<E> s) {
        super(s);
        this.setMaxSize(maxSize);
    }

    /**
     * Obtém o tamanho máximo
     * @return tamanho máximo
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Altera o tamanho máximo da tree
     * @param max Novo tamanho máximo
     */
    public void setMaxSize(int max) {
        maxSize = max;
        adjust();
    }

    /**
     * Ajusta a tree ao tamanho máximo
     */
    private void adjust() {
        while (maxSize < size()) {
            remove(last());
        }
    }

    /**
     * Adiciona um elemento à tree
     * @param item elemento
     * @return
     */
    public boolean add(E item) {
        boolean out = super.add(item);
        adjust();
        return out;
    }

    /**
     * Adiciona uma coleção de elementos à tree
     * @param c Coleção de elementos
     * @return
     */
    public boolean addAll(Collection<? extends E> c) {
        boolean out = super.addAll(c);
        adjust();
        return out;
    }
}