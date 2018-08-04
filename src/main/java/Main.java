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

        System.out.println("Usuario: ");
        Scanner in = new Scanner(System.in);

        String s = in.nextLine();

        HttpResponse<JsonNode> jsonResponse3 = Unirest.get("http://localhost:4567/listaPost/" + s).asJson();
        JsonArray jsonObject = new Gson().fromJson(jsonResponse3.getBody().toString(),JsonArray.class);
        for(int i = 0; i < jsonObject.size(); i++){
           JsonObject j = jsonObject.get(i).getAsJsonObject();
           System.out.println("ID= "+j.get("id")+" Descripcion: "+j.get("descripcion"));
        }

        System.out.println("Poster: ");
        String s1 = in.nextLine();
        System.out.println("Texto del post: ");
        String texto = in.nextLine();
        System.out.println("Path imagen a postear (opcional): ");
        String imgPath = in.nextLine();
        if(imgPath.equalsIgnoreCase("")){
                HttpResponse<String> jsonResponse = Unirest.post("http://localhost:4567/crearPost/"+s1)
                        .field("descripcion",texto)
                        .field("myfile","noim")
                        .asString();

                System.out.println(jsonResponse.getBody());
            }else{
                HttpResponse<String> jsonResponse = Unirest.post("http://localhost:4567/crear/"+s1)
                        .field("texto",texto)
                        .field("myfile",new File(imgPath))
                        .asString();

                System.out.println(jsonResponse.getBody());
            }

        }

    }
