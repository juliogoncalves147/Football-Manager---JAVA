package Controller;

import Auxiliar.*;
import Exceptions.NaoExisteException;
import Model.InterfM;

import java.io.IOException;
import java.util.*;

public class Controller implements InterfC {
    private InterfM gest_Reviews_Model;

    public Controller(InterfM model){
        this.gest_Reviews_Model = model;
    }

    public void carrega_ficheiros(String bf,String rf,String uf) throws IOException {
        this.gest_Reviews_Model.init_GestReviews(bf,rf,uf);
    }
    public List<String> info_estatisticas(){
        return this.gest_Reviews_Model.get_info_stats();
    }
    public Set<String> ids_negocios_nao_avaliados(){
        return this.gest_Reviews_Model.negocios_sem_avaliacoes();
    }
    public AbstractMap.SimpleEntry<Integer,Integer> numeros_users_reviews_mes_especifico(int month,int year){
        int usrs = this.gest_Reviews_Model.users_com_reviews_num_mes_especifico(month,year); //VALUES
        int revs = this.gest_Reviews_Model.reviews_num_mes_especifico(month,year); //KEY
        return new AbstractMap.SimpleEntry<>(revs,usrs);
    }
    public AuxTripleQuery3and4 info_mensal_user(String user_id) throws NaoExisteException {
        int[] num_revs = this.gest_Reviews_Model.user_num_reviews_por_mes(user_id);
        Map<Integer,Set<String>> bus_mes = this.gest_Reviews_Model.user_bus_unicos_por_mes(user_id);
        float[] total_stars = this.gest_Reviews_Model.user_total_stars_por_mes(user_id);

        return new AuxTripleQuery3and4(num_revs,bus_mes,total_stars);
    }
    public AuxTripleQuery3and4 info_mensal_bus(String bus_id) throws NaoExisteException{
        int[] num_revs = this.gest_Reviews_Model.bus_num_reviews_por_mes(bus_id);
        Map<Integer,Set<String>> user_unicos_mes = this.gest_Reviews_Model.bus_users_unicos_por_mes(bus_id);
        float[] total_stars = this.gest_Reviews_Model.bus_total_stars_por_mes(bus_id);

        return new AuxTripleQuery3and4(num_revs,user_unicos_mes,total_stars);
    }
    public Set<AuxPairIdQuant> top_negocios_avaliados_por_user(String user_id) throws NaoExisteException {
        Set<AuxPairIdQuant> resultado = new TreeSet<>();
        for(Map.Entry<String,Integer> e : this.gest_Reviews_Model.negocios_avaliados_por_user(user_id).entrySet()){
            AuxPairIdQuant novo = new AuxPairIdQuant(e.getKey(),e.getValue());
            resultado.add(novo);
        }
        return resultado;
    }
    public Map<Integer, BoundedTreeSet<AuxTripleIdQuantQuant>> negocios_mais_avaliados_por_ano(int top_x) throws NaoExisteException {
        return this.gest_Reviews_Model.mais_avaliados_por_ano(top_x);
    }
    public Map<String,BoundedTreeSet<AuxPairIdQuant>> negocios_top_3_por_cidade() throws NaoExisteException{
        return this.gest_Reviews_Model.top_3_por_cidade();
    }
    public BoundedTreeSet<AuxPairIdQuant> top_x_users_gest_reviews(int top_x) throws NaoExisteException{
        return this.gest_Reviews_Model.top_x_users(top_x);
    }
    public Set<AuxTripleQuery9> users_com_mais_reviews(String bus_id) throws NaoExisteException{
        Set<AuxTripleQuery9> result =  this.gest_Reviews_Model.users_mais_avaliaram_negocio(bus_id);
        result.forEach(AuxTripleQuery9::media);
        return result;
    }
    public Map<String,Map<String, List<AuxPairIdFloat>>> media_negocio_por_estado_cidade() throws NaoExisteException{
        return this.gest_Reviews_Model.media_negocios_cidade_a_cidade();
    }
    public void saveBinaryFile(String file_name) throws IOException {
        this.gest_Reviews_Model.saveToBinary(file_name);
    }
    public void loadBinaryFile(String file_name) throws IOException, ClassNotFoundException {
        this.gest_Reviews_Model = InterfM.loadFromBinary(file_name);
    }
}
