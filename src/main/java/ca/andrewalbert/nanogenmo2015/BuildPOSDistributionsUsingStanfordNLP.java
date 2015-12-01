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

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 *
 * @author andrew
 */
public class BuildPOSDistributionsUsingStanfordNLP implements BuildPartsOfSpeechDistributions {

    private static final StanfordCoreNLP NLP;

    static {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        NLP = new StanfordCoreNLP(props);
    }

    @Override
    public Map<PartOfSpeech, Map<String, Integer>> build(String text) {
        Map<PartOfSpeech, Map<String, Integer>> result = new HashMap<>();

        Annotation document = new Annotation(text);
        NLP.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase();
                // todo - consider lemmas? String lemma = token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase();
                String tag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                Optional<Map<String, Integer>> optDistribution = Optional.empty();

                if (PENN_TREEBANK_TAG_2_POS.containsKey(tag)) {
                    PartOfSpeech partOfSpeech = PENN_TREEBANK_TAG_2_POS.get(tag);

                    if (result.get(partOfSpeech) == null) {
                        result.put(partOfSpeech, new HashMap<>());
                    }

                    optDistribution = Optional.of(result.get(partOfSpeech));
                }

                // if it was none of the above, do nothing...
                if (optDistribution.isPresent()) {
                    Map<String, Integer> distribution = optDistribution.get(); // "unbox" the Optional
                    Integer newValue = distribution.getOrDefault(word, 0) + 1; // increment the word count
                    distribution.put(word, newValue);                          // update the word count
                }

            }
        }

        return result;
    }

}
