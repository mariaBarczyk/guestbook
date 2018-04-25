import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.EntryDao;
import model.Entry;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class GuestBook implements HttpHandler {


    public void handle(HttpExchange httpExchange) throws IOException {

        EntryDao entryDao = new EntryDao();
        List<Entry> entries = entryDao.getAllEntries();

        String method = httpExchange.getRequestMethod();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/form.twig");
        JtwigModel model = JtwigModel.newModel();

        if(method.equals("POST")){

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Entry newEntry = parseFormData(formData);
            entries.add(newEntry);
            entryDao.saveEntry(newEntry);
        }

        model.with("entries", entries);
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static Entry parseFormData(String formData) throws UnsupportedEncodingException {
        List<String> values = new ArrayList<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            values.add(value);
        }
        return new Entry(values.get(0), values.get(1));
    }
}
