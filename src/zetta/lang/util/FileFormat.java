package zetta.lang.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

import zetta.Zetta;
import zetta.exception.EnumFileException;
import zetta.exception.FileException;

/**
 * 
 * @author samueltebbs, 00:23:39 - 30 Aug 2014
 */
public class FileFormat {

    public static LinkedList<FileFormat> formats = new LinkedList<FileFormat>();
    public String name;
    public static final String HTML_FILE_CONTENTS_TAG_OPEN = "<em>",
	    HTML_FILE_CONTENTS_TAG_CLOSE = "</em>",
	    HTML_CODE_TAG_OPEN = "<code>", HTML_CODE_TAG_CLOSE = "</code>";

    public FileFormat(final String name) {
	this.name = name;
	formats.add(this);
	Zetta.currentLib.fileFormats.add(this);
    }

    public HashMap<String, String> parse(final File f) {
	return null;
    }

    public void write(final HashMap<String, String> map, final int fileMode,
	    final String filePath, final PrintWriter pw) {

    }

    public static FileFormat getFormat(final String formatStr,
	    final boolean exception) {
	for (final FileFormat format : FileFormat.formats) {
	    if (format.name.equals(formatStr)) return format;
	}
	if (exception) {
	    new FileException(formatStr, null, EnumFileException.NO_FORMAT);
	}
	return null;
    }

    public String docInfo() {
	return "";
    }

}
