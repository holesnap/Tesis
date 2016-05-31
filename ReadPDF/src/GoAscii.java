import java.util.ArrayList;
import java.util.List;

public class GoAscii {
	private List<String> text;
	
	public GoAscii(List<String> text) {
		this.text = text;
	}
	
	public List<String> preprocess(){
		List<String> out1 = new ArrayList<String>();
		int i = 0;
		while(i<text.size()){
			if(text.get(i).endsWith("-")){
				out1.add(text.get(i).substring(0, text.get(i).length()-1) + text.get(i+1));
				i++;
			}
			else out1.add(text.get(i));
			i++;
		}
		
		List<String> out = new ArrayList<String>();
		for(int i1=0 ; i1<out1.size() ; i1++){
			String aux = this.getAsciiText(out1.get(i1));
			if(!aux.isEmpty() && aux.length()>1)
				out.add(aux);
		}
		return out;
	}
	
	private String getAsciiText(String text){		
		String resultString = text;
		resultString = resultString.toLowerCase();
		resultString = resultString.replaceAll("[\\x21-\\x60]", "");
		resultString = resultString.replaceAll("[^\\x20-\\x7A]", "");
		resultString = resultString.replaceAll("[0-9]", "");
		return resultString;
	}
}
//prime length our first