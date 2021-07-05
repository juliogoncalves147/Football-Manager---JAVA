package Auxiliar;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe auxiliar à realização da query 6
 */
public class AuxTripleIdQuantQuant implements Comparable<AuxTripleIdQuantQuant>, Serializable {
    private String info;
    private int quantidade1;
    private int quantidade2;

    /**
     * Construtor por parâmetros da classe AuxTripleIdQuantQuant
     * @param info id a guardar
     * @param quantidade1 inteiro a guardar
     * @param quantidade2 inteiro a guardar
     */
    public AuxTripleIdQuantQuant(String info, int quantidade1,int quantidade2){
        this.info = info;
        this.quantidade1 = quantidade1;
        this.quantidade2 = quantidade2;
    }

    /**
     * Construtor por cópia da classe AuxTripleIdQuantQuant
     * @param outro Objeto de onde tiramos os parâmetros
     */
    public AuxTripleIdQuantQuant(AuxTripleIdQuantQuant outro){
        this.info = outro.getInfo();
        this.quantidade1 = outro.getQuantidade1();
        this.quantidade2 = outro.getQuantidade2();
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
     * Obtém o primeiro inteiro
     * @return quantidade
     */
    public int getQuantidade1() {
        return quantidade1;
    }

    /**
     * Altera a primeira quantidade
     * @param quantidade
     */
    public void setQuantidade1(int quantidade) {
        this.quantidade1 = quantidade;
    }

    /**
     * Obtém a segunda quantidade
     * @return quantidade
     */
    public int getQuantidade2() {
        return quantidade2;
    }

    /**
     * Altera a segunda quantidade
     * @param quantidade
     */
    public void setQuantidade2(int quantidade) {
        this.quantidade2 = quantidade;
    }

    /**
     * Equals da classe AuxPairIdFloat
     * @param o objeto a comparar
     * @return true se forem iguais
     *         false se forem diferentes
     */
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || ! o.getClass().equals(this.getClass())) return false;
        AuxTripleIdQuantQuant v = (AuxTripleIdQuantQuant) o;
        return  (this.info.equals(v.getInfo())) &&
                (this.quantidade1 == v.getQuantidade1()) &&
                (this.quantidade2 == v.getQuantidade2());
    }

    /**
     * Compare to da classe AuxTripleIdQuantQuant
     * @param o objeto a comparar
     * @return resultado da comparação
     */
    public int compareTo(AuxTripleIdQuantQuant o) {
        int comp = o.getQuantidade1() - this.quantidade1;
        if(comp == 0){
            int comp2 = o.getQuantidade2() - this.quantidade2;
            if(comp2 == 0){
                return 0;
            }
            return comp2;
        }
        return comp;
    }
    public int hashCode(){
        return Arrays.hashCode(new Object[] {this.info,this.quantidade1,this.quantidade2});
    }
}
