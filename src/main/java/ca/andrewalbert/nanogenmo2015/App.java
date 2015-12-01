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

import ca.andrewalbert.nanogenmo2015.BuildPartsOfSpeechDistributions.PartOfSpeech;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;

/**
 *
 * @author andrew
 */
public class App {

    public static void main(String... args) throws Exception {
        System.out.println("Starting " + App.class.getCanonicalName());

        BuildPartsOfSpeechDistributions buildPartsOfSpeechDistributions = new BuildPOSDistributionsUsingStanfordNLP();

        // words from patientBookFilename will be replaced with words of the same part-of-speech from donorBookFilename
        String donorBookFilename;
        String patientBookFilename;
        if (args.length == 2) {
            donorBookFilename = args[0];
            patientBookFilename = args[1];
        } else {
            donorBookFilename = "pg42236.txt";
            patientBookFilename = "pg74.txt";
        }
        System.out.println("Using books '" + donorBookFilename + "' and '" + patientBookFilename + "' from local resources folder.");

        BookFetcher bookFetcher = new FetchPlainTextGutenbergBookFromLocalResourcesFolder();
        String bookA = bookFetcher.fetch(donorBookFilename);
        String bookB = bookFetcher.fetch(patientBookFilename);

        Map<PartOfSpeech, Map<String, Integer>> posDistributionsA = buildPartsOfSpeechDistributions.build(bookA);
        Map<PartOfSpeech, Map<String, Integer>> posDistributionsB = buildPartsOfSpeechDistributions.build(bookB);

        String transformedText = new TransformText().transform(bookA, bookB,
                posDistributionsA, posDistributionsB, EnumSet.of(
                        // change this set to change which parts of speech get "donated"
                        PartOfSpeech.NOUN_PROPER_PLURAL,
                        PartOfSpeech.NOUN_PROPER_SINGULAR
                )
        );

        Arrays.asList(transformedText.split("\\n"))
                .stream()
                .forEach(System.out::println);
    }
}
