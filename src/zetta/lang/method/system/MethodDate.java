package zetta.lang.method.system;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import zetta.Zetta;
import zetta.lang.DataType;
import zetta.lang.Token;
import zetta.lang.Variable;
import zetta.lang.method.MethodAbstract;

public class MethodDate extends MethodAbstract {

    public MethodDate(final String name) {
	super(name);
    }

    @Override
    public boolean canConstruct(final Token[] args) {
	return checkLength(args, true, 0);
    }

    @Override
    public Variable execute(final Token[] args, final Token object) {
	return new Variable(Zetta.dtString, "",
		new Object[] { new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
			.format(Calendar.getInstance().getTime()) });
    }

    @Override
    public DataType getReturnType() {
	return Zetta.dtString;
    }

}
