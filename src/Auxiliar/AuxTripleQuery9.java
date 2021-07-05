package Auxiliar;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Classe auxiliar à query 9
 */
public class AuxTripleQuery9 implements Comparable<AuxTripleQuery9>, Serializable {
    private String info;
    private int quantidade1;
    private float quantidade2;

    /**
     * Construtor por parâmetros da classe AuxTripleQuery9
     * @param info ID a guardar
     * @param quantidade1 Int a guardar
     * @param quantidade2 float a guardar
     */
    public AuxTripleQuery9(String info, int quantidade1,float quantidade2){
        this.info = info;
        this.quantidade1 = quantidade1;
        this.quantidade2 = quantidade2;
    }

    /**
     * Construtor por cópia da classe AuxTripleQuery9
     * @param outro Objeto de onde tiramos todos os parâmetros
     */
    public AuxTripleQuery9(AuxTripleIdQuantQuant outro){
        this.info = outro.getInfo();
        this.quantidade1 = outro.getQuantidade1();
        this.quantidade2 = outro.getQuantidade2();
    }

    /**
     * Obtém a informação
     * @return Info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Altera a informação
     * @param info Nova info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Obtém a primeira quantidade
     * @return quantidade1
     */
    public int getQuantidade1() {
        return quantidade1;
    }

    /**
     * Altera a primeira quantidade
     * @param quantidade Nova quantidade 1
     */
    public void setQuantidade1(int quantidade) {
        this.quantidade1 = quantidade;
    }

    /**
     * Obtém a segunda quantidade
     * @return quantidade2
     */
    public float getQuantidade2() {
        return quantidade2;
    }

    /**
     * Altera a segunda quantidade
     * @param quantidade Nova quantidade2
     */
    public void setQuantidade2(float quantidade) {
        this.quantidade2 = quantidade;
    }

    /**
     * Equals da classe AuxTripleQuery9
     * @param o Objeto a comparar
     * @return true no caso de serem iguais
     *         false no caso de serem diferentes
     */
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || ! o.getClass().equals(this.getClass())) return false;
        AuxTripleQuery9 v = (AuxTripleQuery9) o;
        return  (this.info.equals(v.getInfo())) &&
                (this.quantidade1 == v.getQuantidade1()) &&
                (this.quantidade2 == v.getQuantidade2());
    }

    /**
     * Compare to da classe AuxTripleQuery9
     * @param o objeto a comparar
     * @return resultado da comparação
     */
    public int compareTo(AuxTripleQuery9 o) {
        int comp = o.getQuantidade1() - this.quantidade1;
        if(comp == 0){
            int comp2 = this.info.compareTo(o.getInfo());
            if(comp2 == 0){
                return 0;
            }
            return comp2;
        }
        return comp;
    }

    /**
     * Faz a média da quantidade2 pela quantidade1
     */
    public void media(){
        this.quantidade2 = this.quantidade2 / (float) this.quantidade1;
    }

    /**
     * Método hashCode da classe AuxTripleQuery9
     * @return hashCode
     */
    public int hashCode(){
        return Arrays.hashCode(new Object[] {this.info,this.quantidade1,this.quantidade2});
    }
}
