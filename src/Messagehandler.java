package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Messagehandler {
    private HashMap<Integer, List<String>> mensagens;
    private List<Integer> clients;

    public Messagehandler(){
        this.mensagens = new HashMap<Integer, List<String>>();
        this.clients = new ArrayList<Integer>();
    }

    public void addmessage(Integer cliente, String mensagem){
        if (clients.contains(cliente)){
            mensagens.get(cliente).add(mensagem);
        }else {
            System.out.println("cliente inexistente");
        }
    }
    public void removemessage(Integer cliente, String mensagem){
        if (clients.contains(cliente)){
            mensagens.get(cliente).remove(mensagem);
        }else {
            System.out.println("cliente inexistente");
        }
    }
    public void addcliente(Integer i){
        if (clients.contains(i)){
            System.out.println("cliente ja existe");
        }
        else{
            clients.add(i);
            mensagens.put(i,new ArrayList<String>());
        }
    }
    public void removecliente(Integer i){
        if (clients.contains(i)){
            System.out.println("cliente ja existe");
        }
        else{
            clients.remove(i);
            mensagens.remove(i);
        }
    }
}

