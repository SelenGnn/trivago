package parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    private static String pageKey;

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getPageObject(String pageObject) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("src/test/resources/configs/pages.json"));
        JSONObject jsonObject = (JSONObject) object;
        JSONObject returnObject = (JSONObject) jsonObject.get(pageKey);
        return returnObject.get(pageObject).toString();
    }

    public String getElementKey(String elementKey) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader("src/test/resources/configs/pages.json"));
        JSONObject jsonObject = (JSONObject) object;
        JSONObject returnObject = (JSONObject) jsonObject.get(pageKey);
        JSONObject elementObject = (JSONObject) returnObject.get("elements");
        return elementObject.get(elementKey).toString();
    }

}
