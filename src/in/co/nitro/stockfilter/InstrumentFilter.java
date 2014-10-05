package in.co.nitro.stockfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstrumentFilter {
	public static List<Instrument> filterOnName(List<Instrument> instrumentList, String patternString) {
		List<Instrument> filteredInstrumentList = new ArrayList<Instrument>();
		Pattern pattern = Pattern.compile(wildcardToRegex(patternString));
		for(Instrument instrument : instrumentList) {
			Matcher matcher = pattern.matcher(instrument.getName());
			if(matcher.matches()) 
				filteredInstrumentList.add(instrument);
		}
		return filteredInstrumentList;
	}

	private static String wildcardToRegex(String wildcard){
		StringBuffer s = new StringBuffer(wildcard.length());
		s.append('^');
		for (int i = 0, is = wildcard.length(); i < is; i++) {
			char c = wildcard.charAt(i);
			switch(c) {
			case '*':
				s.append(".*");
				break;
			case '?':
				s.append(".");
				break;
				// escape special regexp-characters
			case '(': case ')': case '[': case ']': case '$':
			case '^': case '.': case '{': case '}': case '|':
			case '\\':
				s.append("\\");
				s.append(c);
				break;
			default:
				s.append(c);
				break;
			}
		}
		s.append('$');
		return(s.toString());
	}

	public static List<Instrument> filterOnLastTrade(List<Instrument> instrumentList, char operator, float value) {
		List<Instrument> filteredInstrumentList = new ArrayList<Instrument>();
		boolean outcome = false;
		for(Instrument instrument : instrumentList) {
			switch(operator) {
			case '=' :
				outcome = instrument.getLastTrade() == value;
				break;
			case '>' :
				outcome = instrument.getLastTrade() >= value;
				break;
			case '<' :
				outcome = instrument.getLastTrade() <= value;
				break;
			default :
				break;
			}
			if(outcome == true)
				filteredInstrumentList.add(instrument);
		}
		return filteredInstrumentList;
	}
}
