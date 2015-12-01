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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andrew
 */
public interface BuildPartsOfSpeechDistributions {

    public enum PartOfSpeech {

        DOLLAR,
        OPENING_QUOTATION_MARK,
        CLOSING_QUOTATION_MARK,
        OPENING_PARENTHESIS,
        CLOSING_PARENTHESIS,
        COMMA,
        DASH,
        SENTENCE_TERMINATOR,
        COLON_OR_ELLIPSIS,
        CONJUNCTION_COORDINATING,
        NUMERAL_CARDINAL,
        DETERMINER,
        EXISTENTIAL_THERE,
        FOREIGN_WORD,
        PREPOSITION_OR_CONJUNCTION_SUBORDINATING,
        ADJECTIVE_OR_NUMERAL_ORDINAL,
        ADJECTIVE_COMPARATIVE,
        ADJECTIVE_SUPERLATIVE,
        LIST_ITEM_MARKER,
        MODAL_AUXILIARY,
        NOUN_COMMON_SINGULAR_OR_MASS,
        NOUN_PROPER_SINGULAR,
        NOUN_PROPER_PLURAL,
        NOUN_COMMON_PLURAL,
        PRE_DETERMINER,
        GENITIVE_MARKER,
        PRONOUN_PERSONAL,
        PRONOUN_POSSESSIVE,
        ADVERB,
        ADVERB_COMPARATIVE,
        ADVERB_SUPERLATIVE,
        PARTICLE,
        SYMBOL,
        TO_AS_PREPOSITION_OR_INFINITIVE_MARKER,
        INTERJECTION,
        VERB_BASE_FORM,
        VERB_PAST_TENSE,
        VERB_PRESENT_PARTICIPLE_OR_GERUND,
        VERB_PAST_PARTICIPLE,
        VERB_PRESENT_TENSE_NOT_3RD_PERSON_SINGULAR,
        VERB_PRESENT_TENSE_3RD_PERSON_SINGULAR,
        WH_DETERMINER,
        WH_PRONOUN,
        WH_PRONOUN_POSSESSIVE,
        WH_ADVERB
    }

    public static final Map<String, PartOfSpeech> PENN_TREEBANK_TAG_2_POS = createMap();

    // reference: https://www.comp.leeds.ac.uk/ccalas/tagsets/upenn.html
    static Map<String, PartOfSpeech> createMap() {
        Map<String, PartOfSpeech> result = new HashMap<>();
        result.put("$", PartOfSpeech.DOLLAR);
        result.put("``", PartOfSpeech.OPENING_QUOTATION_MARK);
        result.put("''", PartOfSpeech.CLOSING_QUOTATION_MARK);
        result.put("(", PartOfSpeech.OPENING_PARENTHESIS);
        result.put(")", PartOfSpeech.CLOSING_PARENTHESIS);
        result.put(",", PartOfSpeech.COMMA);
        result.put("__", PartOfSpeech.DASH);
        result.put(".", PartOfSpeech.SENTENCE_TERMINATOR);
        result.put(":", PartOfSpeech.COLON_OR_ELLIPSIS);
        result.put("CC", PartOfSpeech.CONJUNCTION_COORDINATING);
        result.put("CD", PartOfSpeech.NUMERAL_CARDINAL);
        result.put("DT", PartOfSpeech.DETERMINER);
        result.put("EX", PartOfSpeech.EXISTENTIAL_THERE);
        result.put("FW", PartOfSpeech.FOREIGN_WORD);
        result.put("IN", PartOfSpeech.PREPOSITION_OR_CONJUNCTION_SUBORDINATING);
        result.put("JJ", PartOfSpeech.ADJECTIVE_OR_NUMERAL_ORDINAL);
        result.put("JJR", PartOfSpeech.ADJECTIVE_COMPARATIVE);
        result.put("JJS", PartOfSpeech.ADJECTIVE_SUPERLATIVE);
        result.put("LS", PartOfSpeech.LIST_ITEM_MARKER);
        result.put("MD", PartOfSpeech.MODAL_AUXILIARY);
        result.put("NN", PartOfSpeech.NOUN_COMMON_SINGULAR_OR_MASS);
        result.put("NNP", PartOfSpeech.NOUN_PROPER_SINGULAR);
        result.put("NNPS", PartOfSpeech.NOUN_PROPER_PLURAL);
        result.put("NNS", PartOfSpeech.NOUN_COMMON_PLURAL);
        result.put("PDT", PartOfSpeech.PRE_DETERMINER);
        result.put("POS", PartOfSpeech.GENITIVE_MARKER);
        result.put("PRP", PartOfSpeech.PRONOUN_PERSONAL);
        result.put("PRP$", PartOfSpeech.PRONOUN_POSSESSIVE);
        result.put("RB", PartOfSpeech.ADVERB);
        result.put("RBR", PartOfSpeech.ADVERB_COMPARATIVE);
        result.put("RBS", PartOfSpeech.ADVERB_SUPERLATIVE);
        result.put("RP", PartOfSpeech.PARTICLE);
        result.put("SYM", PartOfSpeech.SYMBOL);
        result.put("TO", PartOfSpeech.TO_AS_PREPOSITION_OR_INFINITIVE_MARKER);
        result.put("UH", PartOfSpeech.INTERJECTION);
        result.put("VB", PartOfSpeech.VERB_BASE_FORM);
        result.put("VBD", PartOfSpeech.VERB_PAST_TENSE);
        result.put("VBG", PartOfSpeech.VERB_PRESENT_PARTICIPLE_OR_GERUND);
        result.put("VBN", PartOfSpeech.VERB_PAST_PARTICIPLE);
        result.put("VBP", PartOfSpeech.VERB_PRESENT_TENSE_NOT_3RD_PERSON_SINGULAR);
        result.put("VBZ", PartOfSpeech.VERB_PRESENT_TENSE_3RD_PERSON_SINGULAR);
        result.put("WDT", PartOfSpeech.WH_DETERMINER);
        result.put("WP", PartOfSpeech.WH_PRONOUN);
        result.put("WP$", PartOfSpeech.WH_PRONOUN_POSSESSIVE);
        result.put("WRB", PartOfSpeech.WH_ADVERB);
        return Collections.unmodifiableMap(result);
    }

    public Map<PartOfSpeech, Map<String, Integer>> build(String text);
}
