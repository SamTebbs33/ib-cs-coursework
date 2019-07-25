package zetta.lang.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.datatype.DataTypeDictionary;

/**
 * 
 * @author samueltebbs, 21:59:08 - 30 Aug 2014
 */
public class FileFormatConfig extends FileFormat {

    public FileFormatConfig(final String name) {
	super(name);
    }

    @Override
    public HashMap<String, String> parse(final File f) {
	try {
	    final Scanner in = new Scanner(f);
	    final HashMap<String, String> result = new HashMap<String, String>();
	    while (in.hasNextLine()) {
		final String str = in.nextLine();
		if (str.length() >= 2) {
		    final int index = str.indexOf('=');
		    if (index > 0) {
			result.put(str.substring(0, index),
				str.substring(index + 1));
		    }
		}
	    }
	    in.close();
	    return result;
	} catch (final FileNotFoundException e) {
	    new FileException(f.getName(), e);
	}
	return null;
    }

    @Override
    public void write(final HashMap<String, String> map, final int fileMode,
	    final String fileName, final PrintWriter pw) {
	final Set<String> keys = map.keySet();
	for (final String key : keys) {
	    pw.write(key + "=" + map.get(key) + "\n");
	}
	pw.close();
    }

    @Override
    public String docInfo() {
	return "This file format will split each line in a file by an equals sign.<br>"
		+ "<br>For example:<br><br>"
		+ HTML_FILE_CONTENTS_TAG_OPEN
		+ "name=John<br>"
		+ "surname=Smith<br>"
		+ "location=Zettaland<br>"
		+ "job=doctor"
		+ HTML_FILE_CONTENTS_TAG_CLOSE
		+ "<br><br>"
		+ "Would return the following "
		+ Zetta.dtDictionary.name
		+ ":<br><br>"
		+ HTML_CODE_TAG_OPEN
		+ DataTypeDictionary.DICT_START
		+ "name"
		+ DataTypeDictionary.DICT_SEP
		+ "John"
		+ ", "
		+ "surname"
		+ DataTypeDictionary.DICT_SEP
		+ "Smith"
		+ ", "
		+ "location"
		+ DataTypeDictionary.DICT_SEP
		+ "Zettaland"
		+ ", "
		+ "job"
		+ DataTypeDictionary.DICT_SEP
		+ "doctor"
		+ DataTypeDictionary.DICT_END + HTML_CODE_TAG_CLOSE;
    }

}
