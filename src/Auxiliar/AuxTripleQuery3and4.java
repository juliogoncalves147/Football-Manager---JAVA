package Auxiliar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Classe auxiliar às queries 3 e 4
 */
public class AuxTripleQuery3and4 implements Serializable {
    private int[] num_revs_mes;
    private Map<Integer, Set<String>> variavel_mensal_unica;
    private float[] media_stars_mes;

    /**
     * Construto por parâmetros da classe AuxTripleQuery3and4
     * @param num_revs_mes Número de reviews por mẽs
     * @param variavel_mensal_unica variável mensal única
     * @param total_stars_mes total de estrelas do mês
     */
    public AuxTripleQuery3and4(int[] num_revs_mes, Map<Integer,Set<String>> variavel_mensal_unica, float[] total_stars_mes){
        //VERIFICAR SE É PRECISO FAZER DEEP CLONE
        this.num_revs_mes = num_revs_mes;
        this.variavel_mensal_unica = variavel_mensal_unica;
        this.media_stars_mes = new float[12];
        for(int i = 0 ; i < 12 ; i++){
            if(num_revs_mes[i] == 0) this.media_stars_mes[i] = 0;
            else
            this.media_stars_mes[i] = total_stars_mes[i]/(float)num_revs_mes[i];
        }
    }
    //MESMA VERIFICAÇÃO => GETTERS DEEP COPY??

    /**
     * Obtém o número de reviews por mês
     * @return reviews por mês
     */
    public int[] getNum_revs_mes() {
        return num_revs_mes;
    }


    /**
     * Obtém a variável mensal única
     * @return variável mensal única
     */
    public Map<Integer, Set<String>> getVariavel_mensal_unica() {
        return new HashMap<>(this.variavel_mensal_unica);
    }

    /**
     * Obtém a média de estrelas por mês
     * @return média de estrelas por mês
     */
    public float[] getMedia_stars_mes() {
        return media_stars_mes;
    }
}
