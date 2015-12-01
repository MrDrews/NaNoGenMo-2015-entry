/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Andrew Albert
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ca.andrewalbert.nanogenmo2015;

import static ca.andrewalbert.nanogenmo2015.BuildPartsOfSpeechDistributions.PENN_TREEBANK_TAG_2_POS;
import ca.andrewalbert.nanogenmo2015.BuildPartsOfSpeechDistributions.PartOfSpeech;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author andrew
 */
public class TransformText {

    private static final StanfordCoreNLP NLP;

    static {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        NLP = new StanfordCoreNLP(props);
    }

    public String transform(String bookA, String bookB,
            Map<PartOfSpeech, Map<String, Integer>> distributionsMapsA,
            Map<PartOfSpeech, Map<String, Integer>> distributionsMapsB,
            Set<PartOfSpeech> partsOfSpeechToTransform) {

        Map<PartOfSpeech, List<String>> distributionsListsA = new HashMap<>();
        Map<PartOfSpeech, List<String>> distributionsListsB = new HashMap<>();
        for (PartOfSpeech pos : partsOfSpeechToTransform) {
            List<String> valueA = distributionsMapsA.get(pos)
                    .keySet()
                    .stream()
                    .sorted((a, b) -> distributionsMapsA.get(pos).get(b).compareTo(distributionsMapsA.get(pos).get(a)))
                    .collect(Collectors.toList());
            List<String> valueB = distributionsMapsB.get(pos)
                    .keySet()
                    .stream()
                    .sorted((a, b) -> distributionsMapsB.get(pos).get(b).compareTo(distributionsMapsB.get(pos).get(a)))
                    .collect(Collectors.toList());
            distributionsListsA.put(pos, valueA);
            distributionsListsB.put(pos, valueB);
        }
        StringBuilder sb = new StringBuilder();

        Annotation document = new Annotation(bookB);
        NLP.annotate(document);
        Optional<CoreLabel> lastToken = Optional.empty();
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {

            // start a new paragraph (thanks http://stackoverflow.com/a/20172593/301111)
            int sentenceOffsetStart = sentence.get(CharacterOffsetBeginAnnotation.class);
            if (sentenceOffsetStart > 1 && bookB.substring(sentenceOffsetStart - 2, sentenceOffsetStart).equals("\n\n")) {
                sb.append("\n\n");
            }

            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase();
                String tag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                PartOfSpeech pos = PENN_TREEBANK_TAG_2_POS.get(tag);
                String originalText = token.originalText();

                String textToAppend;
                if (partsOfSpeechToTransform.contains(pos)) {

                    // find word in distribution B
                    int i = distributionsListsB.get(pos).indexOf(word);
                    String newWord;
                    // replace with corresponding element in distribution A
                    if (i < distributionsListsA.get(pos).size()) {
                        newWord = distributionsListsA.get(pos).get(i);
                    } else {
                        // if "b" has fewer words than "a", don't transform the rarest ones
                        newWord = originalText;
                    }

                    // match capitalization
                    if (Character.isUpperCase(originalText.charAt(0))) {
                        newWord = newWord.substring(0, 1).toUpperCase() + newWord.substring(1, newWord.length());
                    }
                    textToAppend = newWord;

                } else {
                    textToAppend = originalText;
                }

                if (lastToken.isPresent()) {
                    // decide if a space should separate this token and the last token
                    boolean addSpace = true;
                    PartOfSpeech lastTokenPOS = PENN_TREEBANK_TAG_2_POS.get(lastToken.get().get(CoreAnnotations.PartOfSpeechAnnotation.class));
                    // don't add space after certain "opening" punctuation
                    if (lastTokenPOS == PartOfSpeech.OPENING_PARENTHESIS
                            || lastTokenPOS == PartOfSpeech.OPENING_QUOTATION_MARK
                            || lastTokenPOS == PartOfSpeech.DASH) {
                        addSpace = false;
                    }
                    // don't add space between a contraction (e.g. we don't want "don 't" or "boy 's")
                    // TODO - rather than trying to capture contractions with a simple pattern, it might be better to
                    // test against a dictionary of words: if the current token isn't in the dictionary, 
                    // try joining it with the previous token... but there will be exceptions either way
                    if (addSpace
                            && pos == PartOfSpeech.GENITIVE_MARKER
                            || originalText.toLowerCase().endsWith("'t")
                            || originalText.equalsIgnoreCase("'d")
                            || originalText.equalsIgnoreCase("'ve")
                            || originalText.equalsIgnoreCase("'ll")
                            || originalText.equalsIgnoreCase("'s")
                            || originalText.equalsIgnoreCase("'m")
                            || originalText.equalsIgnoreCase("'re")
                            || originalText.equalsIgnoreCase("'m")
                            || originalText.equalsIgnoreCase("'am")
                            || originalText.equalsIgnoreCase("'clock")
                            || originalText.equalsIgnoreCase("'all")) {
                        addSpace = false;
                    }
                    // don't add space before certain punctuation
                    if (addSpace
                            && pos == PartOfSpeech.CLOSING_PARENTHESIS
                            || pos == PartOfSpeech.CLOSING_QUOTATION_MARK
                            || pos == PartOfSpeech.COMMA
                            || pos == PartOfSpeech.SENTENCE_TERMINATOR
                            || pos == PartOfSpeech.COLON_OR_ELLIPSIS) {
                        addSpace = false;
                    }
                    if (addSpace) {
                        sb.append(" ");
                    }
                }

                sb.append(textToAppend);
                lastToken = Optional.of(token);
            }

        }
        return sb.toString();
    }

}
