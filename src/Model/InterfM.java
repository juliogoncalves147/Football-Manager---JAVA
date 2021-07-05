package Model;

import Auxiliar.*;
import Exceptions.NaoExisteException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

public interface InterfM extends Serializable {
    void init_GestReviews(String busFile, String revFile, String usrFile) throws IOException;
    List<String> get_info_stats();
    Set<String> negocios_sem_avaliacoes(); //INFO QUERY 1
    int users_com_reviews_num_mes_especifico(int month, int year); //INFO QUERY 2
    int reviews_num_mes_especifico(int month,int year); //INFO QUERY 2
    int[] user_num_reviews_por_mes(String user_id) throws NaoExisteException; //INFO QUERY 3
    Map<Integer,Set<String>> user_bus_unicos_por_mes(String user_id) throws NaoExisteException; //INFO QUERY 3
    float[] user_total_stars_por_mes(String user_id) throws NaoExisteException; //INFO QUERY 3
    int[] bus_num_reviews_por_mes(String bus_id) throws NaoExisteException; //INFO QUERY 4
    Map<Integer,Set<String>> bus_users_unicos_por_mes(String bus_id) throws NaoExisteException; //INFO QUERY 4
    float[] bus_total_stars_por_mes(String bus_id) throws NaoExisteException; //INFO QUERY 4
    Map<String,Integer> negocios_avaliados_por_user(String user_id) throws NaoExisteException; //INFO QUERY 5
    Map<Integer, BoundedTreeSet<AuxTripleIdQuantQuant>> mais_avaliados_por_ano(int top_x) throws NaoExisteException; //INFO QUERY 6
    Map<String,BoundedTreeSet<AuxPairIdQuant>> top_3_por_cidade() throws NaoExisteException; //INFO QUERY 7
    BoundedTreeSet<AuxPairIdQuant> top_x_users(int top_x) throws NaoExisteException; //INFO QUERY 8
    Set<AuxTripleQuery9> users_mais_avaliaram_negocio(String bus_id) throws NaoExisteException; //INFO QUERY 9
    Map<String,Map<String,List<AuxPairIdFloat>>> media_negocios_cidade_a_cidade() throws NaoExisteException; //INFO QUERY 10
    void saveToBinary(String file_name) throws IOException; //SALVAR MODEL
    static InterfM loadFromBinary(String file_name) throws IOException, ClassNotFoundException{ //CARREGAR MODEL
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file_name));
        InterfM new_model = (InterfM) ois.readObject();
        ois.close();
        return new_model;
    }
    List<String> getAllUsers();
    List<String> getAllBusiness();
}
