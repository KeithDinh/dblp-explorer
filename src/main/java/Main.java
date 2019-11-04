import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) throws IOException {
        new File("src//main//resources//result.txt").delete();

        // **************************************** READ INPUT *****************************************************

        String num;
        int tier;
        String keyword = JOptionPane.showInputDialog("Keyword");
        if(keyword == null)
        {
            JOptionPane.showMessageDialog(null, "No keyword. Terminate program");
            System.exit(0);
        }
        while(true){
            try {
                num = JOptionPane.showInputDialog("Tier");
                if(num == null)
                {
                    JOptionPane.showMessageDialog(null, "No tier. Terminate program");
                    System.exit(0);
                }
                tier = Integer.parseInt(num);
                if(tier <0)
                    throw new NumberFormatException();
                break;
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        }

        int tracking =0;
        JSONParser parser = new JSONParser();
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        Queue<String> current_ref = new LinkedList<String>();

        // **************************************** FILE CHOOSER *****************************************************
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("src//main//resources//"));
        chooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); 		//will save current dir/selected dir
        int r = chooser.showOpenDialog(null);
        String path = null;
        if (r == JFileChooser.APPROVE_OPTION)
        {
            path = chooser.getSelectedFile().getPath();
            File single_file = new File(path);

            if(!single_file.exists())
            {
                JOptionPane.showMessageDialog(null, "Cannot Open File", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else System.exit(0);

        // **************************************** FILE WRITER *****************************************************

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File("src//main//resources//result.txt"));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not Found");
        }
        JOptionPane.showMessageDialog(null, "Outputting to src/main/resources/result.txt\nWait until a message display");
        writer.println("Keyword: " + keyword);
        writer.println("Number of Tier: " + tier +"\n");
        writer.println("************************************************* ARTICLES CONTAINING KEYWORD *************************************************\n");
        Boolean keyword_contain = false;

        // **************************************** FILE READER *****************************************************
        BufferedReader reader = null;
        String line = null;
        try
        {
            reader = new BufferedReader(new FileReader(path));
        }
        catch (IOException e) {writer.println("Error Reading File");}

        try
        {
            line = reader.readLine();
        }
        catch (IOException e) {writer.println("Read Error");}

        int count = 0;
        while (line != null && count < 50000) {
            try {
                list.add((JSONObject) parser.parse(line));

                if (((String) list.get(tracking).get("title")).toLowerCase().contains(keyword.toLowerCase())) {
                    keyword_contain = true;
                    writer.println("ID: " + ((String) list.get(tracking).get("id")));
                    writer.println("Title: " + ((String) list.get(tracking).get("title")) + "\n");
                    if (list.get(tracking).containsKey("references")) {
                        JSONArray refs = (JSONArray) list.get(tracking).get("references");
                        for (int i = 0; i < refs.size(); i++) {
                            current_ref.add(refs.get(i).toString());
                        }
                        list.remove(tracking);
                        tracking--;
                    }
                }
            } catch (ParseException e) {
                writer.println("Parse Error");
            }
            line = reader.readLine();
            tracking++;
            count++;
        }
        if(keyword_contain == false)
        {
            writer.println("No Article Found");
        }

        // **************************************** TIER SEARCH *****************************************************

        for (int i = 1; i <= tier; i++) {
            if (current_ref.isEmpty())
                break;
            writer.println("\n************************************************* " + "Tier " + i + ":" + " *************************************************\n");
            Boolean isFound = false;
            int size = current_ref.size();
            for (int s = 0; s < size; s++) {
                String search_id = current_ref.remove();
                for (int j = 0; j < list.size(); j++) {
                    if (((String) list.get(j).get("id")).equals(search_id)) {
                        isFound = true;
                        writer.println("ID: " + search_id);
                        writer.println("Title: " + ((String) list.get(j).get("title")) + "\n");
                        if (list.get(j).containsKey("references")) {
                            JSONArray refs = (JSONArray) list.get(j).get("references");
                            for (int k = 0; k < refs.size(); k++) {
                                current_ref.add(refs.get(k).toString());
                            }
                        }
                        break;
                    }
                }
            }
            if (!isFound)
                writer.println("Not Found");
        }

        current_ref.clear();
        list.clear();
        line = reader.readLine();
        reader.close();
        writer.close();
        JOptionPane.showMessageDialog(null, "Completed");
    }
}
