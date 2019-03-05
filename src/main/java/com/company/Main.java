package com.company;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        int limit;
        double suma = 0, srednia;
        String inline = "";
        Scanner scanner = new Scanner(System.in);

        limit = scanner.nextInt();

        try {
            URL url = new URL("http://api.nbp.pl/api/cenyzlota/last/" + limit + "/?format=json\n");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {

                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();
            }


            JSONParser parse = new JSONParser();

            JSONArray o = (JSONArray) parse.parse(inline);


            for (int i = 0; i < o.size(); i++) {

                JSONObject jsonobj_1 = (JSONObject) o.get(i);
                suma += (Double) jsonobj_1.get("cena");
                System.out.println("Cena: " + jsonobj_1.get("cena") + " PLN");
                System.out.println("Data: " + jsonobj_1.get("data"));
            }

            srednia = suma / limit;
            DecimalFormat df = new DecimalFormat("0.00");
            System.out.println(df.format(srednia));


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();

        }


    }
}
