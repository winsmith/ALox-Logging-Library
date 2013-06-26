package com.aworx.lox;

import com.aworx.lox.core.CallerInfo;
import com.aworx.util.MString;

/**********************************************************************************************//**
 * A logger that logs all messages to a in-memory buffer of type MString. The name of the logger
 * defaults to "MEMORY".
 **************************************************************************************************/
public class MemoryLogger extends TextLogger
{
	// #################################################################################################
	// fields
	// #################################################################################################

	/**
	 *  The logging Buffer. This can be accessed publicly and hence used as you prefer. Especially,
	 *  the whole log can easily be cleared using the MString.Clear(). However, in
	 *  multithreaded environments, locking to the Log interface's mutex should be performed before
	 *  accessing this Buffer. The initial size of the buffer is 8kb.
	 */
	public		MString			buffer							= new MString( 8192 );

	/** The prefix for the caller method name */
	public		String			formatMemberPrefix				=".";

	/** The postfix for the caller method name */
	public		String			formatMemberPostfix				="()";

	/**
	 *  Tab position after caller info. This auto adjusts (increases) when longer source info occurs.
	 *  To avoid increases in the beginning, this value can be set upfront (after the logger was
	 *  created)
	 */
	public		int				tabAfterSourceInfo				=0;


	// #################################################################################################
	// Constructors
	// #################################################################################################

    /**********************************************************************************************//**
     * Creates a MemoryLogger with the given name.
     *
     * @param name  The name of the logger. Defaults to "MEMORY".
     **************************************************************************************************/
	public MemoryLogger( String	name )	{ super(name);		}

    /**********************************************************************************************//**
     * Creates a MemoryLogger with the name "MEMORY".
     **************************************************************************************************/
	public MemoryLogger( )				{ super("MEMORY");	}

	// #################################################################################################
	// Abstract interface implementation
	// #################################################################################################

    /**********************************************************************************************//**
     * The implementation of the abstract method of parent class TextLogger. Logs messages to the
     * Buffer field.
     *
     * @param domain        The log domain name. If not starting with a slash ('/')
     *                      this is appended to any default domain name that might have been specified
     *                      for the source file.
     * @param level         The log level. This has been checked to be active already on this stage
     *                      and is provided to be able to be logged out only.
     * @param msg           The log message.
     * @param indent        the indentation in the output. Defaults to 0.
     * @param caller        Once compiler generated and passed forward to here.
     * @param lineNumber    The line number of a multi-line message, starting with 0. For single line
     *                      messages this is -1.
     **************************************************************************************************/
	@Override protected void doTextLog( MString		domain,		Log.Level	level, 
										MString		msg,		int			indent,
										CallerInfo	caller, 	int			lineNumber)
 	{
		// append new line if buffer has already lines stored
		if ( buffer.length > 0 )
			buffer.newLine();

		// build filename/line number in a VStudio clickable format 
		if ( caller != null && logCallerInfo  )
		{
			// get actual length once
			int oldLength= buffer.length;

			buffer.append( caller.packageName );
			buffer.append( '.' );
			buffer.append( caller.className );
			buffer.append( '.' );
			buffer.append( caller.methodName );
			buffer.append( '(' );
			buffer.append( caller.fileNameAndLineNumber);
			buffer.append( ')' );


			// jump to next tab level
			if ( tabAfterSourceInfo < buffer.length - oldLength )
				tabAfterSourceInfo= buffer.length - oldLength + 5; // add some extra space too avoid to many increases
			for ( int i= buffer.length - oldLength ; i < tabAfterSourceInfo ; i++ )
				buffer.append( ' ' );
		}

		// append message 
		buffer.append( msg );
	}

} // class MemoryLogger
