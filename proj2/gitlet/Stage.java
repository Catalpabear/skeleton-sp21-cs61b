package gitlet;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Stage implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    Map<String,String> addHashId;

    Map<String,String> rmHashId;

    public Stage() {
        addHashId = new HashMap<>();
        rmHashId = new HashMap<>();
    }
    public void clear(){
        addHashId.clear();
        rmHashId.clear();
    }


    public void addHashId(String filename, String hashId){
        addHashId.put(filename,hashId);
    }
    public void addRmHashId(String filename,String hashId){
        rmHashId.put(filename,hashId);
    }


    public boolean containInRmStage(String filename){
        return rmHashId.containsKey(filename);
    }
    public boolean containInAddStage(String filename){
        return addHashId.containsKey(filename);
    }
    public boolean containHashIdInAddStage(String hashId){
        return addHashId.containsValue(hashId);
    }


    public void RmFromRmStage(String filename, String hashId){
        rmHashId.remove(filename,hashId);
    }
    public void RmFromAddStage(String filename,String hashId){
        addHashId.remove(filename,hashId);
    }

    public boolean isEmpty(){
        return addHashId.isEmpty() && rmHashId.isEmpty();
    }
    public boolean addHashIsEmpty(){
        return addHashId.isEmpty();
    }
    public boolean rmHashIsEmpty(){
        return rmHashId.isEmpty();
    }

    public Map<String,String> getAddHashId(){
        return addHashId;
    }
    public Map<String,String> getRmHashId(){
        return rmHashId;
    }
}
