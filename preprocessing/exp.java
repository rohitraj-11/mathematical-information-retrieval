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
public class exp {
      static int wordcount(String string) {
            int count = 0;
            char ch[] = new char[string.length()];
            for (int i = 0; i < string.length(); i++) {
                  ch[i] = string.charAt(i);
                  if (((i > 0) && (ch[i] != ' ') && (ch[i - 1] == ' ')) || ((ch[0] != ' ') && (i == 0)))
                        count++;
            }
            return count;
      }

      public static void main(String[] args) throws Exception {
            // TODO Auto-generated method stub
            String prePath = "C:\\Users\\Dell\\Desktop\\Intern\\src\\NTCIR12_MathIR_WikiCorpus_v2.1.0\\MathTagArticles\\wpmath00000";
            String postPath = "\\Articles";
            PrintWriter pwText = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Dell\\Desktop\\Intern\\src\\Output\\Text.csv"), true));
            PrintWriter pwFormula = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Dell\\Desktop\\Intern\\src\\Output\\Formula.csv"), true));
            CSVWriter writerText = new CSVWriter(pwText);
            CSVWriter writerFormula = new CSVWriter(pwFormula);

            // adding header to Formula csv file
            String[] headerFormula = { "Filename", "Formula" };
            writerFormula.writeNext(headerFormula);

            // adding header to Text csv file
            String[] headerText = { "Filename", "Text" };
            writerText.writeNext(headerText);

            int formulaCount = 0, textCount = 0;

            for (int i = 1; i <= 16; i++) {
                  String fileNumber = String.valueOf(i);
                  if (fileNumber.length() == 1) {
                        fileNumber = "0" + fileNumber;
                  }
                  File Directory = new File(prePath + fileNumber + postPath);
                  File[] folder = Directory.listFiles();
                  int count = 0;
                  for (File f : folder) {
                        count++;
                        // System.out.println(count+" "+f.getName()+"\n");

                        System.out.println("Processing: " + count);
                        PrintWriter pw = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Dell\\Desktop\\Intern\\src\\Output\\Corpus1\\" + f.getName()), true));
                        BufferedReader br = new BufferedReader(new FileReader(new File(f.getAbsolutePath())));
                        String line = "";
                        String text = "";
                        String content1 = " ";

                        pw.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n"
                                    + "<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=UTF-8\"/></head><body>");
                        while ((line = br.readLine()) != null) {
                              text += line;
                        }

                        /////////////////////////////////////////////////////////////////////////
                        /////////////////////////// Formula Count ///////////////////////////////
                        /////////////////////////////////////////////////////////////////////////
                        String open1 = "application/x-tex";
                        String inside1 = ".*?";
                        String close1 = "</annotation>";
                        String regexMath = open1 + inside1 + close1;
                        Matcher matcherMath = Pattern.compile(regexMath, Pattern.DOTALL).matcher(text);
                        while (matcherMath.find()) {
                              content1 = matcherMath.group().trim();
                              content1 = "<" + content1;
                              content1 = content1.replaceAll("\\<.*?\\>", "");
                              String[] formulaData = { f.getName(), content1 }
;                             writerFormula.writeNext(formulaData);
                              pw.println(content1);
                              formulaCount++;
                        }
                        // System.out.println(f.getName());
                        // System.out.println(String.valueOf(formulaCount));

                        //////////////////////////////////////////////////////////////////////////
                        ////////////////////////////// Text Count ////////////////////////////////
                        //////////////////////////////////////////////////////////////////////////

                        content1 = "";
                        String regexTitle = "<title.*?</title>";
                        Matcher matcherTitle = Pattern.compile(regexTitle, Pattern.DOTALL).matcher(text);
                        while (matcherTitle.find()) {
                              content1 += matcherTitle.group().trim();
                        }
                        String regexHeading = "<h[1-6].*?</h[1-6]>";
                        Matcher matcherHeading = Pattern.compile(regexHeading, Pattern.DOTALL).matcher(text);
                        while (matcherHeading.find()) {
                              content1 += matcherHeading.group().trim();
                        }
                        String regexPara = "<p.*?</p>";
                        Matcher matcherPara = Pattern.compile(regexPara, Pattern.DOTALL).matcher(text);
                        while (matcherPara.find()) {
                              content1 += matcherPara.group().trim();
                        }

                        content1 = content1.replaceAll("\\<.*?\\>", "");
                        String[] textData = { f.getName(), content1 };
                        writerText.writeNext(textData);
                        // System.out.println(textCount);

                        // pw.println("</body>\n" +
                        // "</html>");
                        br.close();
                        pw.flush();
                        pw.close();
                  }
            }
            System.out.println(formulaCount);
            // closing writer connection
            writerFormula.close();
            writerText.close();
      }
}

// Command to Execute
// javac -classpath opencsv-5.4.jar exp.java
// java -classpath opencsv-5.4.jar exp.java
