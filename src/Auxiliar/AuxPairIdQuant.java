package Auxiliar;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe auxiliar à realização da query 7
 */
public class AuxPairIdQuant implements Comparable<AuxPairIdQuant>, Serializable {
    private String info;
    private int quantidade;

    /**
     * Construtor por parâmetros da classe AuxPairIdQuant
     * @param info Id a guardar
     * @param quantidade Inteiro a guardar
     */
    public AuxPairIdQuant(String info, int quantidade){
        this.info = info;
        this.quantidade = quantidade;
    }

    /**
     * Construtor por cópia da classe AuxPairIdQuant
     * @param outro Objeto de onde tiramos os parâmetros
     */
    public AuxPairIdQuant(AuxPairIdQuant outro){
        this.info = outro.getInfo();
        this.quantidade = outro.getQuantidade();
    }

    /**
     * Obtém a info
     * @return info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Altera a info
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Obtém a quantidade
     * @return quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Altera a quantidade
     * @param quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Incrementa a quantidade
     */
    public void add_one_repetition(){
        this.quantidade++;
    }

    /**
     * Equals da classe AuxPairIdQuant
     * @param o objeto a comparar
     * @return true se forem iguais
     *         false se forem diferentes
     */
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || ! o.getClass().equals(this.getClass())) return false;
        AuxPairIdQuant v = (AuxPairIdQuant) o;
        return  (this.info.equals(v.getInfo())) &&
                (this.quantidade == v.getQuantidade());
    }

    /**
     * Compare to da classe AuxPairIdQuant
     * @param o objeto a comparar
     * @return resultado da comparação
     */
    public int compareTo(AuxPairIdQuant o) {
        int comp = o.getQuantidade() - this.quantidade;
        if(comp == 0){
            int comp2 = this.info.compareTo(o.getInfo());
            if(comp2 == 0){
                return 0;
            }
            return comp2;
        }
        return comp;
    }
    public int hashCode(){
        return Arrays.hashCode(new Object[] {this.info,this.quantidade});
    }
}
