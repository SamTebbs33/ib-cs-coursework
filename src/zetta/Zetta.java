package zetta;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import zetta.documentation.DocGenerator;
import zetta.exception.CannotRunTokenException;
import zetta.exception.CannotUseSubscriptForException;
import zetta.exception.InvalidDataTypeException;
import zetta.exception.InvalidPointerException;
import zetta.exception.MethodUndefinedException;
import zetta.exception.UndefinedVariableException;
import zetta.exception.VariableAlreadyDefinedException;
import zetta.exception.ZettaException;
import zetta.lang.Construct;
import zetta.lang.DataType;
import zetta.lang.Method;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.construct.ConstructElse;
import zetta.lang.construct.ConstructFor;
import zetta.lang.construct.ConstructForAll;
import zetta.lang.construct.ConstructForEach;
import zetta.lang.construct.ConstructIf;
import zetta.lang.construct.ConstructMethod;
import zetta.lang.construct.ConstructStruct;
import zetta.lang.construct.ConstructWhile;
import zetta.lang.datatype.CollectionDataType;
import zetta.lang.datatype.DataTypeArray;
import zetta.lang.datatype.DataTypeBoolean;
import zetta.lang.datatype.DataTypeDictionary;
import zetta.lang.datatype.DataTypeFile;
import zetta.lang.datatype.DataTypeGui;
import zetta.lang.datatype.DataTypeInteger;
import zetta.lang.datatype.DataTypeNull;
import zetta.lang.datatype.DataTypeNumber;
import zetta.lang.datatype.DataTypeRandom;
import zetta.lang.datatype.DataTypeString;
import zetta.lang.datatype.DataTypeTime;
import zetta.lang.datatype.DataTypeURL;
import zetta.lang.method.array.MethodArrayCopy;
import zetta.lang.method.array.MethodArrayGet;
import zetta.lang.method.array.MethodArrayMerge;
import zetta.lang.method.array.MethodArrayRandom;
import zetta.lang.method.array.MethodArraySet;
import zetta.lang.method.array.MethodArraySort;
import zetta.lang.method.array.MethodArrayToDict;
import zetta.lang.method.bool.MethodBooleanAnd;
import zetta.lang.method.bool.MethodBooleanNand;
import zetta.lang.method.bool.MethodBooleanNor;
import zetta.lang.method.bool.MethodBooleanNot;
import zetta.lang.method.bool.MethodBooleanOr;
import zetta.lang.method.bool.MethodBooleanXnor;
import zetta.lang.method.bool.MethodBooleanXor;
import zetta.lang.method.dictionary.MethodDictionaryGet;
import zetta.lang.method.dictionary.MethodDictionaryHasKey;
import zetta.lang.method.dictionary.MethodDictionaryKeys;
import zetta.lang.method.dictionary.MethodDictionaryMerge;
import zetta.lang.method.dictionary.MethodDictionarySet;
import zetta.lang.method.dictionary.MethodDictionaryValues;
import zetta.lang.method.file.MethodFileAbsolute;
import zetta.lang.method.file.MethodFileAllChildren;
import zetta.lang.method.file.MethodFileArchive;
import zetta.lang.method.file.MethodFileChildren;
import zetta.lang.method.file.MethodFileClear;
import zetta.lang.method.file.MethodFileClearDir;
import zetta.lang.method.file.MethodFileCopy;
import zetta.lang.method.file.MethodFileCreate;
import zetta.lang.method.file.MethodFileCreateDir;
import zetta.lang.method.file.MethodFileDateModified;
import zetta.lang.method.file.MethodFileDelete;
import zetta.lang.method.file.MethodFileDiff;
import zetta.lang.method.file.MethodFileEscape;
import zetta.lang.method.file.MethodFileExists;
import zetta.lang.method.file.MethodFileGetChild;
import zetta.lang.method.file.MethodFileGetExtension;
import zetta.lang.method.file.MethodFileGetParent;
import zetta.lang.method.file.MethodFileHasChild;
import zetta.lang.method.file.MethodFileIsDirectory;
import zetta.lang.method.file.MethodFileIsHidden;
import zetta.lang.method.file.MethodFileMode;
import zetta.lang.method.file.MethodFileParse;
import zetta.lang.method.file.MethodFileRAFPointer;
import zetta.lang.method.file.MethodFileRAFReadNext;
import zetta.lang.method.file.MethodFileRAFReadNextBool;
import zetta.lang.method.file.MethodFileRAFReadNextInt;
import zetta.lang.method.file.MethodFileRAFSeek;
import zetta.lang.method.file.MethodFileRAFSeekLine;
import zetta.lang.method.file.MethodFileRAFWriteNext;
import zetta.lang.method.file.MethodFileRAFWriteNextBool;
import zetta.lang.method.file.MethodFileRAFWriteNextInt;
import zetta.lang.method.file.MethodFileRead;
import zetta.lang.method.file.MethodFileReadBytes;
import zetta.lang.method.file.MethodFileReadNums;
import zetta.lang.method.file.MethodFileRename;
import zetta.lang.method.file.MethodFileRootDirs;
import zetta.lang.method.file.MethodFileScriptDir;
import zetta.lang.method.file.MethodFileSearch;
import zetta.lang.method.file.MethodFileSeparator;
import zetta.lang.method.file.MethodFileUnarchive;
import zetta.lang.method.file.MethodFileUserDir;
import zetta.lang.method.file.MethodFileWrite;
import zetta.lang.method.file.MethodFileWriteFormat;
import zetta.lang.method.file.MethodFileWriteln;
import zetta.lang.method.gui.MethodGuiShow;
import zetta.lang.method.gui.MethodGuiShowConfirmation;
import zetta.lang.method.gui.MethodGuiShowInput;
import zetta.lang.method.maths.MethodMaths;
import zetta.lang.method.maths.MethodMathsAbs;
import zetta.lang.method.maths.MethodMathsAcos;
import zetta.lang.method.maths.MethodMathsAdd;
import zetta.lang.method.maths.MethodMathsAsin;
import zetta.lang.method.maths.MethodMathsAtan;
import zetta.lang.method.maths.MethodMathsCeil;
import zetta.lang.method.maths.MethodMathsCos;
import zetta.lang.method.maths.MethodMathsCosH;
import zetta.lang.method.maths.MethodMathsCubeRoot;
import zetta.lang.method.maths.MethodMathsCyclicAddition;
import zetta.lang.method.maths.MethodMathsDivide;
import zetta.lang.method.maths.MethodMathsE;
import zetta.lang.method.maths.MethodMathsExp;
import zetta.lang.method.maths.MethodMathsFibonacci;
import zetta.lang.method.maths.MethodMathsFloor;
import zetta.lang.method.maths.MethodMathsHypot;
import zetta.lang.method.maths.MethodMathsInRange;
import zetta.lang.method.maths.MethodMathsInt;
import zetta.lang.method.maths.MethodMathsIsEven;
import zetta.lang.method.maths.MethodMathsIsNum;
import zetta.lang.method.maths.MethodMathsIsOdd;
import zetta.lang.method.maths.MethodMathsLn;
import zetta.lang.method.maths.MethodMathsLog10;
import zetta.lang.method.maths.MethodMathsLog2;
import zetta.lang.method.maths.MethodMathsLogB;
import zetta.lang.method.maths.MethodMathsMax;
import zetta.lang.method.maths.MethodMathsMean;
import zetta.lang.method.maths.MethodMathsMin;
import zetta.lang.method.maths.MethodMathsMultiply;
import zetta.lang.method.maths.MethodMathsPi;
import zetta.lang.method.maths.MethodMathsPow;
import zetta.lang.method.maths.MethodMathsRemainder;
import zetta.lang.method.maths.MethodMathsRound;
import zetta.lang.method.maths.MethodMathsRoundEven;
import zetta.lang.method.maths.MethodMathsRoundOdd;
import zetta.lang.method.maths.MethodMathsSin;
import zetta.lang.method.maths.MethodMathsSinH;
import zetta.lang.method.maths.MethodMathsSquareRoot;
import zetta.lang.method.maths.MethodMathsSubtract;
import zetta.lang.method.maths.MethodMathsTan;
import zetta.lang.method.maths.MethodMathsToDegrees;
import zetta.lang.method.maths.MethodMathsToNum;
import zetta.lang.method.maths.MethodMathsToRadians;
import zetta.lang.method.num.MethodNumMax;
import zetta.lang.method.num.MethodNumMin;
import zetta.lang.method.random.MethodRandomNextInt;
import zetta.lang.method.standard.MethodSize;
import zetta.lang.method.standard.MethodStandardString;
import zetta.lang.method.string.MethodStringCapitalise;
import zetta.lang.method.string.MethodStringChar;
import zetta.lang.method.string.MethodStringCharAt;
import zetta.lang.method.string.MethodStringConcat;
import zetta.lang.method.string.MethodStringContains;
import zetta.lang.method.string.MethodStringEndsWith;
import zetta.lang.method.string.MethodStringEval;
import zetta.lang.method.string.MethodStringIndexOf;
import zetta.lang.method.string.MethodStringReplace;
import zetta.lang.method.string.MethodStringReverse;
import zetta.lang.method.string.MethodStringSplit;
import zetta.lang.method.string.MethodStringStartsWith;
import zetta.lang.method.string.MethodStringSub;
import zetta.lang.method.string.MethodStringSwapCase;
import zetta.lang.method.string.MethodStringToLowerCase;
import zetta.lang.method.string.MethodStringToUpperCase;
import zetta.lang.method.string.MethodStringTrim;
import zetta.lang.method.string.MethodStringWords;
import zetta.lang.method.system.MethodDate;
import zetta.lang.method.system.MethodExit;
import zetta.lang.method.system.MethodImport;
import zetta.lang.method.system.MethodInput;
import zetta.lang.method.system.MethodPrint;
import zetta.lang.method.system.MethodPrintln;
import zetta.lang.method.system.MethodProperties;
import zetta.lang.method.system.MethodProperty;
import zetta.lang.method.time.MethodTimeCheck;
import zetta.lang.method.time.MethodTimeMicro;
import zetta.lang.method.time.MethodTimeMilli;
import zetta.lang.method.time.MethodTimeNano;
import zetta.lang.method.url.MethodUrlDownload;
import zetta.lang.util.FileFormat;
import zetta.lang.util.FileFormatConfig;
import zetta.library.Library;
import zetta.token.TokenBooleanExpression;
import zetta.token.TokenBooleanOperator;
import zetta.token.TokenConstruct;
import zetta.token.TokenDataType;
import zetta.token.TokenExpression;
import zetta.token.TokenInteger;
import zetta.token.TokenMethodArgument;
import zetta.token.TokenMethodCall;
import zetta.token.TokenNestedMethodCall;
import zetta.token.TokenNumber;
import zetta.token.TokenOperator;
import zetta.token.TokenSequence;
import zetta.token.TokenString;
import zetta.token.TokenUserMethodCall;
import zetta.token.TokenVariable;
import zetta.token.TokenVariableDeclaration;

/**
 * The main class for the Zetta Interpreter
 * 
 * @author samueltebbs
 * 
 */
public class Zetta {

    /**
     * Holds all libraries
     */
    public static LinkedList<Library> libraries = new LinkedList<Library>();

    public static Library stdLib = new Library(
	    "Zetta Standard Library",
	    "The standard library for Zetta that is automatically imported into very script ",
	    "ZStdLib");

    public static Library currentLib;

    /**
     * Holds the tokens that represent the program
     */
    public static LinkedList<Token> programTokens = new LinkedList<Token>();
    /**
     * Holds the declared variables
     */
    public static LinkedList<Variable> variables = new LinkedList<Variable>();
    /**
     * Holds all registered data types
     */
    public static LinkedList<DataType> dataTypes = new LinkedList<DataType>();
    /**
     * Holds all registered methods
     */
    public static LinkedList<Method> methods = new LinkedList<Method>();
    /**
     * Holds all registered constructs
     */
    public static LinkedList<Construct> constructs = new LinkedList<Construct>();

    /**
     * Holds the methods defined by the user
     */
    public static final LinkedList<TokenConstruct> userMethods = new LinkedList<TokenConstruct>();

    /**
     * Holds all variables for which TokenVariableDeclarations have been found.
     * Used for type checking
     */
    public static HashMap<String, String> compVariables = new HashMap<String, String>();

    /**
     * Holds all registered tokens
     */
    public static LinkedList<Token> tokens = new LinkedList<Token>();

    public static DataType dtString = new DataTypeString("String"),
	    dtDictionary = new DataTypeDictionary("Dictionary"),
	    dtNumber = new DataTypeNumber("Num"), dtFile = new DataTypeFile(
		    "File"), dtArray = new DataTypeArray("Array"),
	    dtBoolean = new DataTypeBoolean("Bool"), dtTime = new DataTypeTime(
		    "Time"), dtMaths = new DataType("Maths") {
		@Override
		public boolean canBeInstantiated() {
		    return false;
		};
	    }, dtGui = new DataTypeGui("Gui"), dtNull = new DataTypeNull(),
	    dtURL = new DataTypeURL("URL"), dtRandom = new DataTypeRandom(
		    "Random"), dtInteger = new DataTypeInteger("Int");

    public static Construct constructIf = new ConstructIf("if"),
	    constructFor = new ConstructFor("for"),
	    constructForEach = new ConstructForEach("foreach"),
	    constructForAll = new ConstructForAll("forall"),
	    constructElse = new ConstructElse("else"),
	    constructWhile = new ConstructWhile("while"),
	    constructMethod = new ConstructMethod("method"),
	    constructStruct = new ConstructStruct("struct");

    public static Method methodPrintln = new MethodPrintln("println"),
	    methodInput = new MethodInput("input"),
	    methodPrint = new MethodPrint("print"),
	    methodProperty = new MethodProperty("property"),
	    methodProperties = new MethodProperties("properties"),
	    methodSize = new MethodSize("size"),
	    methodString = new MethodStandardString("string"),
	    methodDate = new MethodDate("date"),
	    methodExit = new MethodExit("exit"),
	    methodImport = new MethodImport("import"),

	    methodArraySet = new MethodArraySet("set"),
	    methodArrayGet = new MethodArrayGet("get"),
	    methodArrayMerge = new MethodArrayMerge("merge"),
	    methodArrayRandom = new MethodArrayRandom("rand"),
	    methodArrayToDict = new MethodArrayToDict("toDict"),
	    methodArraySort = new MethodArraySort("sort"),
	    methodArrayCopy = new MethodArrayCopy("copy"),

	    methodFileArchive = new MethodFileArchive("archive"),
	    methodFileCreateDir = new MethodFileCreateDir("createDir"),
	    methodFileRename = new MethodFileRename("rename"),
	    methodFileUnarchive = new MethodFileUnarchive("unarchive"),
	    methodFileWriteln = new MethodFileWriteln("writeln"),
	    methodFileClear = new MethodFileClear("clear"),
	    methodFileClearDir = new MethodFileClearDir("clearDir"),
	    methodFileRead = new MethodFileRead("read"),
	    methodFileWrite = new MethodFileWrite("write"),
	    methodFileDelete = new MethodFileDelete("delete"),
	    methodFileExists = new MethodFileExists("exists"),
	    methodFileIsDir = new MethodFileIsDirectory("isDir"),
	    methodFileCopy = new MethodFileCopy("copy"),
	    methodFileContents = new MethodFileChildren("getChildren"),
	    methodFileCreate = new MethodFileCreate("create"),
	    methodFileGetExtenstion = new MethodFileGetExtension("getExtension"),
	    methodFileHasChild = new MethodFileHasChild("hasChild"),
	    methodFileGetChild = new MethodFileGetChild("getChild"),
	    methodFileGetParent = new MethodFileGetParent("getParent"),
	    methodFileEscape = new MethodFileEscape("getEscaped"),
	    methodFileAbsolute = new MethodFileAbsolute("getAbsolute"),
	    methodFileBytes = new MethodFileReadBytes("readBytes"),
	    methodFileParse = new MethodFileParse("parse"),
	    methodFileMode = new MethodFileMode("setMode"),
	    methodFileRafSeek = new MethodFileRAFSeek("seek"),
	    methodFileRafSeekLine = new MethodFileRAFSeekLine("seekLine"),
	    methodFileRafPointer = new MethodFileRAFPointer("getPointer"),
	    methodFileReadNums = new MethodFileReadNums("readNums"),
	    methodFileReadNext = new MethodFileRAFReadNext("readNext"),
	    methodFileReadNextInt = new MethodFileRAFReadNextInt("readNextNum"),
	    methodFileReadNextBool = new MethodFileRAFReadNextBool(
		    "readNextBool"),
	    methodFileRootDirs = new MethodFileRootDirs("getRoots"),
	    methodFileUserDir = new MethodFileUserDir("getUserDir"),
	    methodFileScriptDir = new MethodFileScriptDir("getScriptDir"),
	    methodFileDateModified = new MethodFileDateModified(
		    "getDateModified"),
	    methodFileIsHidden = new MethodFileIsHidden("isHidden"),
	    methodFileAllChildren = new MethodFileAllChildren("getAllChildren"),
	    methodFileSearch = new MethodFileSearch("search"),
	    methodFileWriteNext = new MethodFileRAFWriteNext("writeNext"),
	    methodFileWriteNextBool = new MethodFileRAFWriteNextBool(
		    "writeNextBool"),
	    methodFileWriteNextInt = new MethodFileRAFWriteNextInt(
		    "writeNextNum"),
	    methodFileDiff = new MethodFileDiff("diff"),
	    methodFileSeparator = new MethodFileSeparator("separator"),
	    methodFileWriteFormat = new MethodFileWriteFormat("writeFormat"),

	    methodStringSub = new MethodStringSub("sub"),
	    methodStringEndsWith = new MethodStringEndsWith("endsWith"),
	    methodStringStartsWith = new MethodStringStartsWith("startsWith"),
	    methodStringCharAt = new MethodStringCharAt("charAt"),
	    methodStringContains = new MethodStringContains("contains"),
	    methodStringIndexOf = new MethodStringIndexOf("indexOf"),
	    methodStringReplace = new MethodStringReplace("replace"),
	    methodStringToLowerCase = new MethodStringToLowerCase("toLowerCase"),
	    methodStringToUpperCase = new MethodStringToUpperCase("toUpperCase"),
	    methodStringSwapCase = new MethodStringSwapCase("swapCase"),
	    methodStringTrim = new MethodStringTrim("trim"),
	    methodStringReverse = new MethodStringReverse("reverse"),
	    methodStringCapitalise = new MethodStringCapitalise("capitalise"),
	    methodStringEval = new MethodStringEval("eval"),
	    methodStringConcat = new MethodStringConcat("concat"),
	    methodStringChar = new MethodStringChar("char"),
	    methodStringSplit = new MethodStringSplit("split"),
	    methodStringWords = new MethodStringWords("words"),

	    methodMathsAbs = new MethodMathsAbs("abs"),
	    methodMathsMax = new MethodMathsMax("max"),
	    methodMathsAcos = new MethodMathsAcos("acos"),
	    methodMathsAsin = new MethodMathsAsin("asin"),
	    methodMathsAtan = new MethodMathsAtan("atan"),
	    methodMathsCeil = new MethodMathsCeil("ceil"),
	    methodMathsCos = new MethodMathsCos("cos"),
	    methodMathsCosH = new MethodMathsCosH("cosH"),
	    methodMathsCubeRoot = new MethodMathsCubeRoot("cubrt"),
	    methodMathsE = new MethodMathsE("e"),
	    methodMathsExp = new MethodMathsExp("epow"),
	    methodMathsFloor = new MethodMathsFloor("floor"),
	    methodMathsHypot = new MethodMathsHypot("hypot"),
	    methodMathsLog = new MethodMathsLn("ln"),
	    methodMathsLog10 = new MethodMathsLog10("log"),
	    methodMathsLog2 = new MethodMathsLog2("log2"),
	    methodMathsLogB = new MethodMathsLogB("logB"),
	    methodMathsMin = new MethodMathsMin("min"),
	    methodMathsPow = new MethodMathsPow("pow"),
	    methodMathsRound = new MethodMathsRound("round"),
	    methodMathsSin = new MethodMathsSin("sin"),
	    methodMathsSinH = new MethodMathsSinH("sinH"),
	    methodMathsSquareRoot = new MethodMathsSquareRoot("sqrt"),
	    methodMathsTan = new MethodMathsTan("tan"),
	    methodMathsTanH = new MethodMaths("tanH"),
	    methodMathsToDegrees = new MethodMathsToDegrees("degrees"),
	    methodMathsToRadians = new MethodMathsToRadians("radians"),
	    methodMathsPi = new MethodMathsPi("pi"),
	    methodMathsRoundEven = new MethodMathsRoundEven("roundEven"),
	    methodMathsRoundOdd = new MethodMathsRoundOdd("roundOdd"),
	    methodMathsIsEven = new MethodMathsIsEven("isEven"),
	    methodMathsIsOdd = new MethodMathsIsOdd("isOdd"),
	    methodMathsRemainder = new MethodMathsRemainder("mod"),
	    methodMathsAdd = new MethodMathsAdd("add"),
	    methodMathsSubtract = new MethodMathsSubtract("sub"),
	    methodMathsDivide = new MethodMathsDivide("div"),
	    methodMathsMultiply = new MethodMathsMultiply("mul"),
	    methodMathsInt = new MethodMathsInt("int"),
	    methodMathsMean = new MethodMathsMean("mean"),
	    methodMathsIsNum = new MethodMathsIsNum("isNum"),
	    methodMathsToNum = new MethodMathsToNum("toNum"),
	    methodMathsInRange = new MethodMathsInRange("inRange"),
	    methodMathsCyclicAddition = new MethodMathsCyclicAddition(
		    "cyclicAdd"),
	    methodMathsFib = new MethodMathsFibonacci("fib"),

	    methodNumMax = new MethodNumMax("max"),
	    methodNumMin = new MethodNumMin("min"),

	    methodDictionaryGet = new MethodDictionaryGet("get"),
	    methodDictionarySet = new MethodDictionarySet("set"),
	    methodDictionaryMerge = new MethodDictionaryMerge("merge"),
	    methodDictionaryHasKey = new MethodDictionaryHasKey("hasKey"),
	    methodDictionaryKeys = new MethodDictionaryKeys("keys"),
	    methodDictionaryValues = new MethodDictionaryValues("values"),

	    methodTimeCheck = new MethodTimeCheck("check"),
	    methodTimeMilli = new MethodTimeMilli("milli"),
	    methodTimeMicro = new MethodTimeMicro("micro"),
	    methodTimeNano = new MethodTimeNano("nano"),

	    methodBooleanAnd = new MethodBooleanAnd("and"),
	    methodBooleanOr = new MethodBooleanOr("or"),
	    methodBooleanNot = new MethodBooleanNot("not"),
	    methodBooleanXor = new MethodBooleanXor("xor"),
	    methodBooleanNor = new MethodBooleanNor("nor"),
	    methodBooleanNand = new MethodBooleanNand("nand"),
	    methodBooleanXnor = new MethodBooleanXnor("xnor"),

	    methodRandomNextInt = new MethodRandomNextInt("nextInt"),

	    methodGuiShow = new MethodGuiShow("show"),
	    methodGuiShowInput = new MethodGuiShowInput("showInput"),
	    methodGuiShowConfirm = new MethodGuiShowConfirmation("showConfirm"),

	    methodUrlDownlaod = new MethodUrlDownload("download");

    public static Token tokenNestedMethodCall = new TokenNestedMethodCall(),
	    tokenVariableDec = new TokenVariableDeclaration(),
	    tokenString = new TokenString(),
	    tokenMethodCall = new TokenMethodCall(),
	    tokenInteger = new TokenInteger(), tokenNumber = new TokenNumber(),
	    tokenVariable = new TokenVariable(),
	    tokenConstruct = new TokenConstruct(),
	    tokenDataType = new TokenDataType(),
	    tokenExpression = new TokenExpression(),
	    tokenOperator = new TokenOperator(),
	    tokenBooleanOperator = new TokenBooleanOperator(),
	    tokenSequence = new TokenSequence(),
	    tokenMethodArgument = new TokenMethodArgument(),
	    tokenBooleanExpression = new TokenBooleanExpression(),
	    tokenUserMethodCall = new TokenUserMethodCall();

    public static FileFormat fileFormatConfig = new FileFormatConfig("config");

    /**
     * A var name that is reserved for internal processes
     */
    public static final String RESERVED_VAR_NAME = "-.-";

    /**
     * Holds the program lines in String form
     */
    public static String[] lines;
    
    /**
     * The address that is currently being tokenised or run
     */
    public static int address = 0, lineNo = 1;
    
    /**
     * The file used to input the program
     */
    public static File inFile = null;
    
    /**
     * The current interpreter state
     */
    public static InterpreterState state = InterpreterState.COMPILATION;
    
    /**
     * The variable name used for the $args variable
     */
    public static String ARGS_VAR = "args",
	/**
	 * The file extension used for scripts
	 */
	FILE_EXTENSION = ".zta",
	/**
	* The regex used to remove whitespaces
	*/
	regex = "\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";
    
    /**
     * Holds the stacked active constructs. Used mainly to handle scoped
     * variable access
     */
    public static Stack<TokenConstruct> activeConstruct = new Stack<TokenConstruct>();
    
    /** 
     * Stacks the line numbers used when entering and exiting a new scope
     */
    public static Stack<Integer> addressStack = new Stack<Integer>();

    /**
     * The Scanner used to get input from the command line
     */
    public static final Scanner in = new Scanner(System.in);
    
    /**
     * The directory in which libraries are expected to be found
     */
    public static final File LIBRARY_DIR = new File("lib");

    /**
     * Set to true if Zetta.println() is called. Used in shell to determine if a
     * new line should be printed before input prompt
     */
    private static boolean printedLine = false;

    /**
     * The raw lines in the program with whitespace preserved. Used when printing error messages associated with certain lines
     */
    public static String[] pureLines;

    /**
     * Hold the major, minor, patch or full version. Replaced by Ant during
     * build.
     */
    public static final String VERSION_MAJOR = "0",
	    VERSION_MINOR = "0",
	    VERSION_PATCH = "1", VERSION_TYPE = "alpha",
	    VERSION_FULL = VERSION_MAJOR + "." + VERSION_MINOR + "."
		    + VERSION_PATCH + VERSION_TYPE;

    public static boolean doDocGen;

    /**
     * Loads the program and then calls interpret() and then exit(), or runs the
     * shell if no valid program was given
     */
    public Zetta(final String[] args) {
	// Create library directory of it doesn't exist
	if (!LIBRARY_DIR.exists()) {
	    LIBRARY_DIR.mkdir();
	}
	// Parse args for arguments such as "-debug" or "-doc"
	if (args != null) {
	    if (args.length >= 1) {
		int c = 0;
		for (; c < args.length; c++) {
		    if (args[c] != null) {
			if (args[c].equals("-debug")) { // Enable debugging
			    ZettaException.debug = true;
			} else if (args[c].equals("-doc")) { // Enable doc
							     // generation
			    doDocGen = true;
			} else {
			    if (args[c] != null) {
				if (args[c].endsWith(FILE_EXTENSION)) { // This
									// argument
									// is
									// the
									// name
									// of
									// the
									// program
									// file
				    inFile = new File(args[c]);
				    if (inFile != null) {
					if (inFile.exists()) {
					    break;
					}
				    }
				}
				println("Invalid file: \"" + args[c] + "\"");
				return;
			    }
			}
		    }
		}
		c++;
		// Create "$args" variable with script arguments
		final Variable v = new Variable(dtArray, ARGS_VAR,
			new Object[1]);
		// Make a String array
		v.setIndex(0, dtString);
		compVariables.put(ARGS_VAR, dtArray.name);
		variables.add(v);
		// Add script arguments to variable
		if (c < args.length) {
		    v.setValue(new Object[(args.length - c) + 1]);
		    v.setIndex(0, dtString);
		    for (int i = c; i < args.length; i++) {
			v.setIndex((i - c) + 1, args[i]);
		    }
		}
	    }
	}
	// Start shell if an invalid file was given, otherwise start
	// interpretation
	if (inFile != null) {
	    interpret(inFile);
	    exit();
	} else {
	    shell();
	}
    }

    public static void shell() {
	println("Zetta v" + VERSION_FULL + " - by Sam Tebbs");
	String str = "";
	do {
	    // Print input prompt
	    if (printedLine) {
		print(">>> ");
	    } else {
		println(">>> ");
	    }
	    // Get input
	    printedLine = false;
	    str = in.nextLine();
	    if (!str.equals("exit")) {
		// Split by semicolons 
		final String[] args2 = split(str);
		// Remove whitespaces and convert split String into tokens
		for (int k = 0; k < args2.length; k++) {
		    programTokens.add(Token.getTokenType(args2[k].replaceAll(
			    regex, "")));
		}
		// Run tokens
		run(programTokens);
	    }
	} while (!str.equals("exit"));
    }

    /**
     * Main program. Tokenises source, runs programs and generates documentation
     * 
     * @param The
     *            file to interpret
     */
    public static void interpret(final File file) {
	// Convert file contents to a String
	String contents = toString(file, "");
	// Keep raw lines with whitespaces for error checking
	pureLines = split(contents);
	// Remove whitespaces
	contents = contents.replaceAll(regex, "");
	lines = split(contents);
	final long time = System.currentTimeMillis();
	// Convert the lines to tokens
	tokenise(lines);
	// Print compilation time if debugging is enabled
	if (ZettaException.debug) {
	    println("Compilation time: " + (System.currentTimeMillis() - time)
		    + " ms");
	}
	lineNo = 1;
	// Exit if a compilation error occured
	if (ZettaException.error) {
	    exit();
	}
	// Run tokens
	run(programTokens);
	// Generate documentation if enabled
	if (doDocGen) {
	    DocGenerator.start();
	}
    }

    /**
     * Prints the programTokens variable
     */
    public static void printTokens() {
	println();
	Zetta.print("{");
	for (final Token t : programTokens) {
	    print(t.toString());
	    if (t.subTokens != null) {
		if (t.subTokens.length > 0) {
		    printSubTokens(t.subTokens);
		}
	    }
	    print(",");
	}
    }

    /**
     * Prints the Token array
     * 
     * @param the
     *            Token array to print
     */
    private static void printSubTokens(final Token[] array) {
	print("[");
	for (int c = 0; c < array.length; c++) {
	    final Token t = array[c];
	    print(t.toString());
	    if ((c + 1) < array.length) {
		print(",");
	    }
	}
	print("]");
    }

    /**
     * Sets the active construct, runs the token list, restores the current
     * address and then clears the token's local variables (also runs
     * startConstruct and exitConstruct if b == true). It is recommended that
     * all constructs use this method when running their body of tokens.
     * 
     * @param the
     *            construct's body
     * @param the
     *            construct token itself
     * @param true if startConstruct() and exitConstruct() should be called
     */
    public static void runFromConstruct(final LinkedList<Token> list,
	    final TokenConstruct t, final boolean stackConstruct) {
	if (stackConstruct) {
	    startConstruct(t); // Prepares the interpreter's state for running the construct and enters a new scope
	}
	// Save the address so it can be restored
	int prevAddress = address;
	// Run the construct's body of tokens
	run(list);
	// Increment and restore the address
	address = ++prevAddress;
	if (stackConstruct) {
	    exitConstruct(); // Safely exits the scope of the construct
	}
    }

    /**
     * Adds activeConstruct.peek()'s local vars to t's vars, calls
     * activeConstruc.push(t) incremenets lineNo and calls
     * addressStack.push(lineNo). It is recommended to call this before running
     * a token's body if not using runFromConstruct()
     * 
     * @param the
     *            construct token
     */
    public static void startConstruct(final TokenConstruct t) {
	if (activeConstruct.size() > 0) {
	    t.localVariables.addAll(activeConstruct.peek().localVariables);
	}
	activeConstruct.push(t);
	Zetta.lineNo++;
	Zetta.addressStack.push(Zetta.lineNo);
    }

    /**
     * Adds the size of activeConstruct.peek()'s body to lineNo, calls
     * activeConstruct.pop().localVariables.clear() and increments lineNo
     * if(activeConstruct.size() == 0). It is recommended to call this after
     * running a construct's body if not using runFromConstruct()
     */
    public static void exitConstruct() {
	Zetta.lineNo += activeConstruct.peek().body.size();
	activeConstruct.pop().localVariables.clear();
	if (activeConstruct.size() == 0) {
	    lineNo++;
	}
    }

    /**
     * Runs the token list
     * 
     * @param the
     *            list of tokens to run
     */
    public static void run(final LinkedList<Token> list) {
	state = InterpreterState.RUNTIME; // Set the current state
	if (inFile != null) {
	    address = 0;
	}
	while (address < list.size()) {
	    // Run the token, if it can't be run, then throw and error
	    if (!list.get(address).runToken()) {
		new CannotRunTokenException(list.get(address));
	    }
	    // Increment the address
	    address++;
	    // Increment the line number (used for error reports)
	    lineNo++;
	}
    }

    /**
     * Calls the garbage collector and exits the interpreter
     */
    public static void exit() {
	System.gc();
	System.exit(0);
    }

    /**
     * Tokenises the program lines
     * 
     * @param the
     *            String array to tokenise
     */
    private static void tokenise(final String[] lines) {
	// Loop through each line in the file
	for (address = 0; address < lines.length; address++) {
	    // If the current line isn't a comment
	    if (!lines[address].startsWith("//")) {
		// Add token form of line to program tokens
		programTokens.add(Token.getTokenType(lines[address]));
	    }
	    lineNo++;
	}
    }

    /**
     * Initialises a library .jar (calls the jar's main method)
     * 
     * @param the
     *            name of the library
     * @return true if the library could was successfully loaded
     */
    public static boolean loadLib(final String substring) {
	if (LIBRARY_DIR.exists()) {
	    final File libDir = new File(LIBRARY_DIR.getAbsolutePath()
		    + File.separator + substring);
	    if (libDir.exists()) {
		final File jarFile = new File(libDir.getAbsolutePath()
			+ File.separator + substring + ".jar");
		if (jarFile.exists()) {
		    JarFile jar = null;
		    try {
			jar = new JarFile(jarFile);
			Manifest manifest = null;
			manifest = jar.getManifest();
			final Attributes attrs = manifest.getMainAttributes();
			final String mainClassName = attrs
				.getValue("Main-Class");
			URL url = null;
			url = new URL("file", null, jarFile.getAbsolutePath());
			final ClassLoader cl = new URLClassLoader(
				new URL[] { url });
			Class<?> mainClass = null;
			mainClass = cl.loadClass(mainClassName);
			java.lang.reflect.Method mainMethod = null;
			mainMethod = mainClass.getMethod("main",
				new Class[] { String[].class });
			mainMethod.invoke(mainClass, (Object[]) new String[1]);
			jar.close();
			return true;
		    } catch (final Exception e) {
			// new CannotManipulateLibraryException(substring, e);
			return false;
		    }
		}
	    }
	}
	// new LibraryNotFoundException(substring);
	return false;
    }

    public static void print() {
	print("");
    }

    public static void println() {
	println("");
    }

    public static void print(final Object obj) {
	if (obj != null) {
	    if (obj instanceof Double) {
		if (((double) obj == Math.floor((double) obj))
			&& !Double.isInfinite((double) obj)) {
		    System.out.print((int) obj);
		    return;
		}
	    }
	    System.out.print(obj);
	}
    }

    public static void println(final Object obj) {
	if (obj != null) {
	    if (obj instanceof Double) {
		if (((double) obj == Math.floor((double) obj))
			&& !Double.isInfinite((double) obj)) {
		    System.out.println((int) obj);
		    return;
		}
	    }
	    System.out.println(obj);
	    printedLine = true;
	}
    }

    /**
     * Returns the file's contents in the form of a string without line breaks
     * (\n)
     * 
     * @param the
     *            file to parse
     * @return the string of the file's contents
     */
    public static String toString(final File f, final String separator) {
	String result = "";
	try {
	    final Scanner in = new Scanner(f);
	    while (in.hasNextLine()) {
		final String str = in.nextLine().replaceAll(regex, "");
		if (!str.startsWith("//")) {
		    result += str + separator;
		}
	    }
	    in.close();
	} catch (final Exception e) {
	    println("Cannot initialise file reader, check file permissions");
	}
	return result;
    }

    /**
     * Returns the variable associated with str, null if it doesn't exist
     * 
     * @param the
     *            name of the variable
     * @return the variable found
     */
    public static Variable getVariable(final TokenVariable var) {
	if (var == null) {
	    new InvalidPointerException();
	    return null;
	}
	if (state == InterpreterState.COMPILATION) {
	    if (compVariables.containsKey(var.value)) {
		final String type = compVariables.get(var.value);
		for (final DataType d : dataTypes) {
		    if (d.name.equals(type)) return new Variable(d, var.value,
			    "");
		}
	    }
	} else {
	    for (final Variable v : Zetta.variables) {
		if (v != null) {
		    if (var.value.equals(v.name)) {
			if (var.subScript != null) {
			    if (v.dType instanceof CollectionDataType) return ((CollectionDataType) v.dType)
				    .getCollectionIndex(v, var.subScript);
			    else {
				new CannotUseSubscriptForException(v.dType);
			    }
			} else return v;
		    }
		}
	    }
	    if (activeConstruct.size() > 0) {
		for (final Variable v : activeConstruct.peek().localVariables) {
		    if (v != null) {
			if (var.value.equals(v.name)) {
			    if (var.subScript != null) {
				if (v.dType instanceof CollectionDataType) return ((CollectionDataType) v.dType)
					.getCollectionIndex(v, var.subScript);
				else {
				    new CannotUseSubscriptForException(v.dType);
				}
			    } else return v;
			}
		    }
		}
	    }
	}
	new UndefinedVariableException(var.value);
	return null;
    }

    /**
     * Returns a variable of any type, null if it doesn't exist.
     * 
     * @param the
     *            name of the variable
     * @return the variable found
     */
    public static Variable getVariableOfType(final TokenVariable var) {
	final Variable v = getVariable(var);
	if (v != null) return v;
	else {
	    new UndefinedVariableException(var.value);
	    System.exit(0);
	}
	return null;
    }

    /**
     * Splits string by delimeters (";", "{", "}")
     * 
     * @param the
     *            String to split
     * @return the String array
     */
    protected static String[] split(final String string) {
	final LinkedList<String> strings = new LinkedList<String>();
	String str = "";
	int brackets = 0, quotes = 0;
	for (int c = 0; c < string.length(); c++) {
	    final char ch = string.charAt(c);
	    switch (ch) {
		case '\"':
		    quotes++;
		    str += "\"";
		    break;
		case '(':
		    brackets++;
		    if (brackets > 0) {
			str += "(";
		    }
		    break;
		case ')':
		    brackets--;
		    if (brackets >= 0) {
			str += ")";
		    }
		    break;
		case ';':
		case '{':
		case '}':
		    if ((brackets == 0) && ((quotes % 2) == 0)) {
			if ((ch == '{') || (ch == '}')) {
			    str += String.valueOf(ch);
			}
			if (!str.equals("")) {
			    strings.add(str);
			}
			str = "";
		    } else {
			str += String.valueOf(ch);
		    }
		    break;
		/*
		 * case '\\': c++; break;
		 */
		default:
		    str += String.valueOf(ch);
		    break;
	    }
	}
	if (!str.equals("")) {
	    strings.add(str);
	}
	final String[] array = new String[strings.size()];
	for (int c = 0; c < strings.size(); c++) {
	    array[c] = strings.get(c);
	}
	return array;
    }

    /**
     * @param variable
     *            name
     * @param types
     * @return a variable of a type, null if it doesn't exist or isn't of the
     *         specified data types.
     */
    public static Variable getVariableOfType(final Token var,
	    final DataType... dTypes) {
	final Variable v = getVariable((TokenVariable) var);
	if (v != null) {
	    for (final DataType d : dTypes) {
		if (v.dType == d) return v;
	    }
	    new InvalidDataTypeException(v, dTypes);
	} else {
	    new UndefinedVariableException(var.value);
	}
	return null;
    }

    /**
     * Prints the contents of Zetta.variables
     */
    public static void printVars() {
	for (final Variable v : variables) {
	    if (v != null) {
		println("Name: " + v.name + ", Val: " + v.dType.get(v));
	    }
	}
    }

    /**
     * Executes a method from a TokenMethodCall
     * 
     * @param the
     *            token to use
     * @return the variable returned by the method
     */
    public static Variable executeMethod(final TokenMethodCall t) {
	if (t instanceof TokenUserMethodCall) {
	    for (final TokenConstruct con : userMethods) {
		if (con.subTokens[0].value.equals(t.value)) return ConstructMethod
			.run(con, (TokenUserMethodCall) t);
	    }
	    new MethodUndefinedException(t.value);
	    return null;
	} else if (t instanceof TokenNestedMethodCall) return ((TokenNestedMethodCall) t)
		.execute();
	else return t.method.execute(t.subTokens, t.object);
    }

    /**
     * Initiates a variable by using a variable declaration token
     * 
     * @param the
     *            variable declaration token
     */
    public static void doVariableDeclaration(final TokenVariableDeclaration t) {

	if (activeConstruct.size() != 0) {
	    for (final Variable var : activeConstruct.peek().localVariables) {
		if (var.name.equals(t.subTokens[1].value)) {
		    new VariableAlreadyDefinedException(t.subTokens[1].value);
		}
	    }
	}
	for (final Variable var : variables) {
	    if (var.name.equals(t.subTokens[1].value)) {
		new VariableAlreadyDefinedException(t.subTokens[1].value);
	    }
	}
	final Variable v = ((TokenDataType) t.subTokens[0]).dType.initVariable(
		t.subTokens[1].value,
		Arrays.copyOfRange(t.subTokens, 2, t.subTokens.length));
	if (activeConstruct.size() == 0) {
	    variables.add(v);
	} else {
	    activeConstruct.peek().localVariables.add(v);
	}
    }

    /**
     * Returns the DataType returned by the method call
     * 
     * @todo Add support for return types from user defined methods
     * @param the
     *            method call token
     * @return the return type
     */
    // TODO: Add support for return types from user defined methods
    public static DataType getMethodReturnType(final TokenMethodCall t) {
	if (t instanceof TokenNestedMethodCall) return getMethodReturnType((TokenMethodCall) t.subTokens[t.subTokens.length - 1]);
	else {
	    if (t.method.pointerMode.equals(Method.POINTER_SPECIFIC)) return t.method
		    .getReturnType(getVariable((TokenVariable) t.object));
	    else return t.method.getReturnType();
	}
    }

    /**
     * Calls println() on the argument. Mainly used to localise where debug
     * messages are being used.
     * 
     * @param the
     *            object to print
     */
    public static void debug(final Object m) {
	println(m);
    }

    public static Object getVariable(final String value) {
	for (final Variable v : variables) {
	    if (v.name.equals(value)) return v;
	}

	if (activeConstruct.size() > 0) {
	    for (final Variable v : activeConstruct.peek().localVariables) {
		if (v.name.equals(value)) return v;
	    }
	}
	return null;
    }

}