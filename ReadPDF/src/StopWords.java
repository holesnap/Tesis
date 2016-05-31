import java.util.*;
import java.io.*;


public class StopWords
{
	private List<String> list, stopWords;
	public StopWords(List<String> list, String swds){
		this.stopWords = readStopWords(swds);
		this.list = list;	
	}
	
    public Boolean isStopWord(String word, List<String> stopWords){
		boolean found = false;   
		
		for(int i=0 ; i<stopWords.size() && !found ; i++){
			if(word.equals(stopWords.get(i)))
				found = true;
		}
		return found;
    }

    
    public List<String> readStopWords(String stopWordsFilename){
		List<String> stopWords = new ArrayList<String>();
	
		try (BufferedReader br = new BufferedReader(new FileReader(stopWordsFilename))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       stopWords.add(line);
		    }
		} catch (IOException e) {}
		return stopWords;
    }

    public List<String> removeStopWords(){
		List<String> out = new ArrayList<String>();
		for(String a : list)
			if(!isStopWord(a.trim(), stopWords))
				out.add(a);
		return out;
    }
}