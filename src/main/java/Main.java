import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.management.Query;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnirestException {
        int a = 0;
        Scanner in = new Scanner(System.in);
        while(a==0) {

            System.out.println("Usuario: ");
            String s = in.nextLine();

            System.out.println("Password: ");
            String p = in.nextLine();
            try { HttpResponse<JsonNode> jsonResponse3 = Unirest.get("http://localhost:4567/listaPost/" + s + "/" + p).asJson();
                JsonArray jsonObject = new Gson().fromJson(jsonResponse3.getBody().toString(),JsonArray.class);
                a=1;
                for(int i = 0; i < jsonObject.size(); i++){
                    JsonObject j = jsonObject.get(i).getAsJsonObject();
                    System.out.println("ID= "+j.get("id")+" Descripcion: "+j.get("descripcion"));
                }
            }catch (Exception e){
                System.out.println("User o Pass equivocado");
                a=0;
            }
        }




        System.out.println("Poster: ");
        String s1 = in.nextLine();
        System.out.println("Texto del post: ");
        String texto = in.nextLine();
        System.out.println("Path imagen a postear (opcional): ");
        String img = in.nextLine();
        if(img.equalsIgnoreCase("")){
                HttpResponse<String> jsonResponse = Unirest.post("http://localhost:4567/crearPost/"+s1)
                        .field("descripcion",texto)
                        .field("myfile","noim")
                        .asString();

                System.out.println(jsonResponse.getBody());
            }else{
                HttpResponse<String> jsonResponse = Unirest.post("http://localhost:4567/crearPost/"+s1)
                        .field("descripcion",texto)
                        .field("myfile",new File(img))
                        .asString();

                System.out.println(jsonResponse.getBody());
            }

        }

    }
