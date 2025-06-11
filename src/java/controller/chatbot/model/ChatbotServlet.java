package controller.chatbot;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet; 
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ChatbotServlet extends HttpServlet {
    private static final String GEMINI_API_KEY = "AIzaSyAMoaaySRAnKgDUulEdBAZjW6Uo5xQCHVo";
    private static final String GEMINI_API_URL = 
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + GEMINI_API_KEY;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userMessage = request.getParameter("message");
        String botResponse = queryGeminiAPI(userMessage);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(botResponse);
    }

    private String queryGeminiAPI(String userMessage) {
        try {
            URL url = new URL(GEMINI_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create request JSON
            String jsonInputString = createRequestJson(userMessage);

            // Send request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // Get response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return parseGeminiResponse(connection);
            } else {
                return "Error: API returned code " + responseCode;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with Gemini API: " + e.getMessage();
        }
    }

    private String createRequestJson(String message) {
        JSONObject jsonRequest = new JSONObject();
        JSONArray contentsArray = new JSONArray();
        JSONObject contentObject = new JSONObject();
        JSONArray partsArray = new JSONArray();
        JSONObject partObject = new JSONObject();

        partObject.put("text", message);
        partsArray.add(partObject);
        contentObject.put("parts", partsArray);
        contentsArray.add(contentObject);
        jsonRequest.put("contents", contentsArray);

        return jsonRequest.toString();
    }

    private String parseGeminiResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());

            JSONArray candidates = (JSONArray) jsonResponse.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                JSONObject firstCandidate = (JSONObject) candidates.get(0);
                JSONObject content = (JSONObject) firstCandidate.get("content");
                JSONArray parts = (JSONArray) content.get("parts");

                if (parts != null && !parts.isEmpty()) {
                    JSONObject firstPart = (JSONObject) parts.get(0);
                    return (String) firstPart.get("text");
                }
            }
            return "No response from the API";
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error parsing API response: " + e.getMessage();
        }
    }
}
