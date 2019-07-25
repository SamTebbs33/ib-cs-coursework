package zetta.lang.method.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import zetta.Zetta;
import zetta.exception.FileException;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.datatype.DataTypeFile;
import zetta.token.TokenMethodCall;
import zetta.token.TokenString;

public class MethodFileUnarchive extends MethodFileArchive {

    public MethodFileUnarchive(final String name) {
	super(name);
    }

    @Override
    public String[] docDescriptions() {
	return new String[] { "Unarchives the compressed file to the argument and returns the new unarchived file" };
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	final Variable obj = Zetta.getVariableOfType(object, Zetta.dtFile);
	String folderName = "";
	if (args[0] instanceof TokenString) {
	    folderName = args[0].value;
	} else if (args[0] instanceof TokenMethodCall) {
	    folderName = ((TokenMethodCall) args[0]).method.execute(
		    args[0].subTokens, ((TokenMethodCall) args[0]).object)
		    .toString();
	} else {
	    final Variable v = Zetta.getVariableOfType(args[0]);
	    folderName = v.dType.string(v);
	}
	final byte[] buffer = new byte[1024];

	try {

	    final File zipFile = (File) obj.getIndex(DataTypeFile.INDEX_FILE);
	    // create output directory if not exists
	    final File folder = new File(folderName);
	    if (!folder.exists()) {
		folder.mkdir();
	    }
	    if (!folder.isDirectory()) {
		new FileException(folder.getName(), null);
	    }

	    // get the zip file content
	    final ZipInputStream zis = new ZipInputStream(new FileInputStream(
		    zipFile));
	    // get the zipped file list entry
	    ZipEntry ze = zis.getNextEntry();
	    while (ze != null) {

		final String fileName = ze.getName();
		final File newFile = new File(folderName + File.separator
			+ fileName);

		// create all non exists folders
		// else you will hit FileNotFoundException for compressed folder
		new File(newFile.getParent()).mkdirs();

		final FileOutputStream fos = new FileOutputStream(newFile);

		int len;
		while ((len = zis.read(buffer)) > 0) {
		    fos.write(buffer, 0, len);
		}

		fos.close();
		ze = zis.getNextEntry();
	    }

	    zis.closeEntry();
	    zis.close();

	} catch (final IOException ex) {
	    new FileException(ex.getMessage(), ex);
	}
	return null;
    }

    @Override
    public DataType getReturnType() {
	return null;
    }

}
