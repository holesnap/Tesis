import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Read {
    
   private PDFParser parser;
   private PDFTextStripper pdfStripper;
   private PDDocument pdDoc ;
   private COSDocument cosDoc ;
   
   private String text ;
   private String filePath;
   private File file;

    public Read() {
        
    }
   public String toText() throws IOException
   {
       this.pdfStripper = null;
       this.pdDoc = null;
       this.cosDoc = null;
       
       file = new File(filePath);
       parser = new PDFParser(new RandomAccessFile(file,"r")); 
       
       parser.parse();
       cosDoc = parser.getDocument();
       pdfStripper = new PDFTextStripper();
       pdDoc = new PDDocument(cosDoc);
       pdDoc.getNumberOfPages();
       pdfStripper.setStartPage(1);
       pdfStripper.setEndPage(pdDoc.getNumberOfPages());
       
       text = pdfStripper.getText(pdDoc);
       return text;
   }
   
   public List<String> getWordsList(){
	   List<String> out = new ArrayList<String>();
	   try {
		String list = this.toText();
		String aux = "";
		for(int i=0 ; i<list.length() ; i++){
			if(list.charAt(i)!=' ' && list.charAt(i)!= '\n' && list.charAt(i)!= '\r')
				aux += String.valueOf(list.charAt(i));
			else {
				if(!aux.isEmpty())
					out.add(aux);
				aux = "";
			}
		}
		
		
	   } catch (IOException e) {}
	   
	   
	   
	   return out;
   }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
   
    public static void main(String[] args) throws IOException {

    	List<String> list = new ArrayList<String>(), paths = new ArrayList<String>();
    	
    	paths.clear();
    	try (BufferedReader br = new BufferedReader(new FileReader("paths.txt"))) {
		    String line = "";
		    while ((line = br.readLine()) != null) {
		       paths.add(line);
		    }
		} catch (IOException e) {}
    	
    	System.out.println(paths.toString());
    	
    	for(String b : paths){
    		Read a = new Read();
        	a.setFilePath(b);
        	list.addAll(a.getWordsList());
    	}
        
        
        GoAscii ga = new GoAscii(list);
        StopWords sw = new StopWords(ga.preprocess(),"stopwords.txt");
        
        List<String> stringOut = sw.removeStopWords();
     
        PrintWriter writer = new PrintWriter("out.txt");
        for(String b: stringOut)
        	writer.println(b + " ");
        writer.close();
        
 }    
}