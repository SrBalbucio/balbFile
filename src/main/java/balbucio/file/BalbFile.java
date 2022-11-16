package balbucio.file;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalbFile {

    private File file;
    private String source = null;
    private Map<String, Object> values = new HashMap<>();

    public BalbFile(File file, boolean createIfNotExists){
        this.file = file;
        if(createIfNotExists){
            create();
        }
        load();
    }

    private void load(){
        try {
            if(file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }

                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
                source = builder.toString();
                parse();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void create(){
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(){
        try {
            if (file.exists()) {
                convertToSource();
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(source.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parse(){
        values.clear();
        if(!source.equalsIgnoreCase("")){
            String[] options = source.split("/");
            for(String v : options){
                String[] vp = v.split("=");
                String key = vp[0].replace("{barra}", "/").replace("{igual}", "=").replace("{doisponto}", ":");
                String value = vp[1].replace("{barra}", "/").replace("{igual}", "=").replace("{doisponto}", ":");
                if(value.equals("{nulled}")){
                    values.put(key, null);
                    return;
                }
                values.put(key, value);
            }
        }
    }

    private synchronized void convertToSource(){
        source = "";
        for(String k : values.keySet()){
            if(source.equalsIgnoreCase("")){
                source = k+"=";
                String value = values.getOrDefault(k, "{nulled}").toString()
                        .replace("/", "{barra}")
                        .replace("=", "{igual}")
                        .replace(":", "{doisponto}");
                source += value;

            } else {
                source += "/" + k + "=";
                String value = values.getOrDefault(k, "{nulled}").toString()
                        .replace("/", "{barra}")
                        .replace("=", "{igual}")
                        .replace(":", "{doisponto}");
                source += value;
            }
        }
    }

    public synchronized void put(String key, Object value){
        values.put(key, value);
        save();
    }

    public synchronized void putList(String key, List<String> list){
        String s = "null";
        for(String l : list){
            if(s.equalsIgnoreCase("null")){
                s = l;
            } else{
                s += ":"+l;
            }
        }
        values.put(key, s);
        save();
    }

    public Object get(String key){
        return values.getOrDefault(key, null);
    }

    public String getString(String key){
        return (String) values.getOrDefault(key, null);
    }

    public int getInteger(String key){
        return (int) values.getOrDefault(key, "0");
    }

    public List<String> getStringList(String key){
        List<String> lista = new ArrayList<>();
        if(values.containsKey(key)){
            String s = (String) values.get(key);
            for(String l : s.split(":")){
                lista.add(l);
            }
        }
        return lista;
    }
}
