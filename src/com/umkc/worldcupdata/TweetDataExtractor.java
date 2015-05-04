package com.umkc.worldcupdata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class TweetDataExtractor {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 */
	public static void main(String[] args) throws InvalidFormatException, IOException {		
		URL tokenurl = TweetDataExtractor.class.getResource("models/en-token.bin");
//		URL posUrl = TweetDataExtractor.class.getResource("models/en-pos-perceptron.bin");
		URL personUrl = TweetDataExtractor.class.getResource("models/en-ner-person.bin");		
		URL locationUrl = TweetDataExtractor.class.getResource("models/en-ner-location.bin");
		
		TokenizerModel model = new TokenizerModel(tokenurl);
		TokenizerME tokenizer = new TokenizerME(model);
		String[] tokens = tokenizer.tokenize("Peter Vincent and Mark Brown are players from England and West Indies.");
		System.out.println(Arrays.toString(tokens));
		
		TokenNameFinderModel locationModel = new TokenNameFinderModel(locationUrl);
		NameFinderME locFinderME = new NameFinderME(locationModel);
		Span locationSpans[] = locFinderME.find(tokens);
		System.out.println(Arrays.toString(locationSpans));
		
		TokenNameFinderModel nameModel = new TokenNameFinderModel(personUrl);
		NameFinderME nameFinderME = new NameFinderME(nameModel);
		Span nameSpans[] = nameFinderME.find(tokens);
		
		nameFinderME.clearAdaptiveData();
		System.out.println(Arrays.toString(nameSpans));
	}

}
