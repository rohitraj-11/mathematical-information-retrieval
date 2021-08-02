import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;

// Program extracts all math formula and text(words) from NTCIR Documents.
public class exp_query {
      public static void main(String[] args) throws Exception {
            // TODO Auto-generated method stub
            PrintWriter pwQuery = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Dell\\Desktop\\Intern\\src\\Output\\tempquery.csv"), true));
            CSVWriter writerQuery = new CSVWriter(pwQuery);

            // adding header to Formula csv file
            File Directory = new File("C:\\Users\\Dell\\Desktop\\Intern\\src\\query");
            File[] folder = Directory.listFiles();
            for (File f : folder) {
                  BufferedReader br = new BufferedReader(new FileReader(new File(f.getAbsolutePath())));
                  String line = "";
                  String text = "";
                  String content1 = "";
                  while ((line = br.readLine()) != null) {
                        text += line;
                  }
                  String open1 = "application/x-tex";
                  String inside1 = ".*?";
                  String close1 = "</annotation>";
                  String regexMath = open1 + inside1 + close1;
                  Matcher matcherMath = Pattern.compile(regexMath, Pattern.DOTALL).matcher(text);
                  String allQueries = "[";
                  while (matcherMath.find()) {
                        content1 = matcherMath.group().trim();
                        content1 = "<" + content1;
                        content1 = content1.replaceAll("\\<.*?\\>", "");
                        //String[] formulaData = { content1.toLowerCase() };                       
                        //writerQuery.writeNext(formulaData);
                        allQueries += "r"+ '"'+ content1.toLowerCase() + '"' + ',';
                  }
                  // allQueries[allQueries.length()-1] = "]";
                  int ind = allQueries.length()-1;
                  //allQueries.setCharAt(ind, ']');
                  String updatedAllQueries = allQueries.substring(0, ind) + ']';
                  System.out.println(updatedAllQueries);
                  String[] formulaData = { updatedAllQueries };
                  writerQuery.writeNext(formulaData);
                  br.close();
            }
            //closing writer connection
            writerQuery.close();
      }
}

// Command to Execute
// javac -classpath opencsv-5.4.jar exp_query.java
// java -classpath opencsv-5.4.jar exp_query.java