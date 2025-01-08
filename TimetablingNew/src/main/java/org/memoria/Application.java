package org.memoria;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Application {
    private static final int MAX_DF_RESULTS = 200;  // Increased from default 100

    public static void main(String[] args) {
        try {
            // Set up JADE environment
            Runtime rt = Runtime.instance();
            Profile p = new ProfileImpl();
            p.setParameter("-jade.domain.df_maxresult", String.valueOf(MAX_DF_RESULTS));

            AgentContainer container = rt.createMainContainer(p);

            // Parse JSON files
            JSONParser parser = new JSONParser();
            JSONArray professors = (JSONArray) parser.parse(new FileReader("profesores.json"));
            JSONArray rooms = (JSONArray) parser.parse(new FileReader("salas.json"));

            // Create professor agents
            for (Object obj : professors) {
                JSONObject prof = (JSONObject) obj;
                AgentController ac = container.createNewAgent(
                        prof.get("RUT").toString(),
                        "ProfessorAgent",
                        new Object[]{prof}
                );
                ac.start();
            }

            // Create room agents
            for (Object obj : rooms) {
                JSONObject room = (JSONObject) obj;
                AgentController ac = container.createNewAgent(
                        room.get("Codigo").toString(),
                        "RoomAgent",
                        new Object[]{room}
                );
                ac.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}