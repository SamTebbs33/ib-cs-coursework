package zetta.library;

import java.util.LinkedList;

import zetta.Zetta;
import zetta.lang.Construct;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.util.FileFormat;

/**
 * 
 * @author samueltebbs, 20:06:36 - 4 Sep 2014
 */
public class Library {

    public String libName, libDesc, libFolderName;

    /**
     * Holds all registered methods
     */
    public LinkedList<Method> methods = new LinkedList<Method>();
    /**
     * Holds all registered constructs
     */
    public LinkedList<Construct> constructs = new LinkedList<Construct>();
    /**
     * Holds all registered data types
     */
    public LinkedList<DataType> dataTypes = new LinkedList<DataType>();
    /**
     * Holds all file formats
     */
    public LinkedList<FileFormat> fileFormats = new LinkedList<FileFormat>();
    /**
     * Holds all registered tokens
     */
    public LinkedList<Token> tokens = new LinkedList<Token>();

    public Library(final String name, final String desc, final String folderName) {
	libName = name;
	libDesc = desc;
	libFolderName = folderName;
	Zetta.currentLib = this;
	Zetta.libraries.add(this);
    }

}
