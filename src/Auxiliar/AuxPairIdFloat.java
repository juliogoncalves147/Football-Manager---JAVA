package Auxiliar;

import java.util.Arrays;

/**
 * Classe auxiliar à realização da query 10
 */
public class AuxPairIdFloat {
    private String info;
    private float quantidade;

    /**
     * Construtor por parâmetros da classe AuxPairIdFloat
     * @param info id a guardar
     * @param quantidade float a guardar
     */
    public AuxPairIdFloat(String info, float quantidade){
        this.info = info;
        this.quantidade = quantidade;
    }

    /**
     * Construtor por cópia da classe AuxPairIdFloat
     * @param outro Objeto de onde tiramos os parâmetros
     */
    public AuxPairIdFloat(AuxPairIdFloat outro){
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
    public float getQuantidade() {
        return quantidade;
    }

    /**
     * Altera a quantidade
     * @param quantidade
     */
    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
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
        AuxPairIdQuant v = (AuxPairIdQuant) o;
        return  (this.info.equals(v.getInfo())) &&
                (this.quantidade == v.getQuantidade());
    }
    public int hashCode(){
        return Arrays.hashCode(new Object[] {this.info,this.quantidade});
    }
}
