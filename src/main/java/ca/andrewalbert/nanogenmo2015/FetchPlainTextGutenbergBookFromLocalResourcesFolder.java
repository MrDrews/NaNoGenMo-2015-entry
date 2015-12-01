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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author andrew
 */
public class FetchPlainTextGutenbergBookFromLocalResourcesFolder implements BookFetcher {

    @Override
    public String fetch(String bookFileName) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/" + bookFileName), Charset.forName("UTF-8"));
        List<String> linesNoHeaderNoFooter = new LinkedList<>();
        boolean isHeader = true;
        for (String line : lines) {
            if (isHeader) {
                if (line.matches(".*\\*\\*\\* START OF THIS PROJECT GUTENBERG EBOOK .*")) {
                    isHeader = false;
                }
            } else {
                if (line.matches(".*\\*\\*\\* END OF THIS PROJECT GUTENBERG EBOOK.*")) {
                    break;
                }
                linesNoHeaderNoFooter.add(line);
            }
        }

        return linesNoHeaderNoFooter.stream()
                .collect(joining("\n"));
    }
}
