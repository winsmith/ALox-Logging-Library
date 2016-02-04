// #################################################################################################
//  com.aworx.lox.loggers - ALox Logging Library
//
//  (c) 2013-2016 A-Worx GmbH, Germany
//  Published under MIT License (Open Source License, see LICENSE.txt)
// #################################################################################################
package com.aworx.lox.core.textlogger;

import com.aworx.lib.ALIB;
import com.aworx.lib.Util;
import com.aworx.lib.enums.Phase;
import com.aworx.lib.strings.AString;
import com.aworx.lox.Log;
import com.aworx.lox.core.CallerInfo;
import com.aworx.lox.core.textlogger.TextLogger;


/** ************************************************************************************************
 * A text logger that either removes or ignores (just writes through) ALox ESC sequences.
 * Implements abstract method #doTextLog and introduces two new abstract methods
 * doLogSubstring.
 **************************************************************************************************/
public abstract class PlainTextLogger extends TextLogger
{
    /**
     * If this field is set to \c true (the default), all \ref aworx::lox::ESC "ESC" color and
     * style codes get removed when logging into this Logger. \c ESC::TAB is processed.
     *
     * It might be useful, to set this to false, e.g. in the case, the contents of the
     * Logger is (later) written into a different logger (e.g. as a multi line message)
     * which is capable of interpreting the escape sequences of class ESC.
     */
    public      boolean             pruneESCSequences                                   = true;

    /** ********************************************************************************************
     * Creates a PlainTextLogger
     * @param name     The name of the logger. If empty, it defaults to the type name.
     * @param typeName The type of the logger.
     **********************************************************************************************/
    protected    PlainTextLogger( String  name, String typeName )
    {
        super( name, typeName );
    }

    /** ********************************************************************************************
     * Abstract method to be implemented by descendants. This method is called when a new
     * log message is started. It is called exactly once before a series of doLogPortion()
     * calls and exactly once after such series. If either the start or one of the calls
     * #doLogSubstring returns \c false, the invocation that would indicate the end of a log
     * message is omitted.
     *
     * Implementing this method allows the acquisition of system resources (e.g. opening a file).
     *
     * @param phase  Indicates the beginning or end of a log operation.
     * @return If \c false is returned, the log line is aborted without an invocation of
     *         \ref notifyLogOp "notifyLogOp(Phase.END)".
     **********************************************************************************************/
    abstract protected boolean notifyLogOp( Phase phase );

    /** ********************************************************************************************
     * Abstract method to be implemented by descendants. Has to write the given region of
     * the given AString to the destination.
     *
     * @param buffer   The string to write a portion of.
     * @param start    The start of the portion in \p buffer to write out.
     * @param length   The length of the portion in \p buffer to write out.
     * @return If \c false is returned, the log line is aborted without an invocation of
     *         \ref notifyLogOp "notifyLogOp(Phase.END)".
     **********************************************************************************************/
    abstract protected boolean doLogSubstring( AString buffer, int start, int length );

    /** ********************************************************************************************
     * The implementation of the abstract method of parent class TextLogger.
     * Loops over the log text, removes or ignores ESC sequences (all but ESC.TAB) and invokes
     * abstract methods of descendants as follows:
     * - \ref notifyLogOp "notifyLogOp(true)"
     * -   #doLogSubstring()
     * -   ...
     * - \ref notifyLogOp "notifyLogOp(Phase.END)"
     *
     *
     * @param domain        The log domain name. If not starting with a slash ('/')
     *                      this is appended to any default domain name that might have been
     *                      specified for the source file.
     * @param level         The log level. This has been checked to be active already on this stage
     *                      and is provided to be able to be logged out only.
     * @param msg           The log message.
     * @param indent        the indentation in the output. Defaults to 0.
     * @param caller        Once compiler generated and passed forward to here.
     * @param lineNumber    The line number of a multi-line message, starting with 0. For single
     *                      line messages this is -1.
     **********************************************************************************************/
    @Override
    protected void doTextLog( AString       domain,     Log.Level    level,
                              AString       msg,        int          indent,
                              CallerInfo    caller,     int          lineNumber)
     {
        if ( !notifyLogOp( Phase.BEGIN ) )
            return;

        // loop over message, print the parts between the escape sequences
        {
            int  msgLength=  msg.length();
            int  start=      0;
            int  end;
            int  column=     0;
            while( start < msgLength )
            {
                boolean foundESC=  true;
                end=    msg.indexOf( '\033', start );
                if( end < 0 )
                {
                    foundESC= false;
                    end=      msgLength ;
                }


                if (!doLogSubstring( msg,  start, end - start ) )  return;
                column+= end - start;

                // interpret escape sequence
                if ( foundESC )
                {
                    char c=  msg.buffer()[++end];
                    // auto tab or end of meta info part
                    if ( c == 't' || c == 'A' )
                    {
                        end++;
                        c=  msg.buffer()[end++];
                        int extraSpace=  c >= '0' && c <= '9' ? (int)  ( c - '0' )
                                                              : (int)  ( c - 'A' ) + 10;

                        int tabStop= autoSizes.next( column, extraSpace );

                        if ( tabStop > column )
                        {
                            AString spaces= Util.getSpaces();
                            int spacesLength= spaces.length();
                            int qty= tabStop - column;
                            while ( qty > 0 )
                            {
                                int size= qty < spacesLength ? qty : spacesLength;
                                if(!doLogSubstring( spaces, 0, size ) ) return;
                                qty-= size;
                            }
                            column= tabStop;
                        }

                    }

                    // prune or ignore all others
                    else
                    {
                        if ( !pruneESCSequences )
                            if(!doLogSubstring( msg, end - 1, 3 )) return;
                        end+= 2;
                    }
                }

                // next
                start= end;

            } // write loop

            ALIB.ASSERT_WARNING( start == msgLength, "Loop error when pruning ESC codes" );
        }

        notifyLogOp( Phase.END );

    }

} // class PlainTextLogger
