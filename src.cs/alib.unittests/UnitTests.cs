﻿// #################################################################################################
//  cs.aworx.unittests - AWorx Util
//
//  (c) 2013-2016 A-Worx GmbH, Germany
//  Published under MIT License (Open Source License, see LICENSE.txt)
// #################################################################################################
using System;
using System.IO;
using System.Runtime.CompilerServices;
using cs.aworx.lox;
using cs.aworx.lox.loggers;
using cs.aworx.lib.strings;
using cs.aworx.lib.enums;


#if ALIB_MONO_DEVELOP
    using NUnit.Framework;
#endif
#if ALIB_VSTUDIO
    using Microsoft.VisualStudio.TestTools.UnitTesting;
#endif
using cs.aworx.lib;
using cs.aworx.lox.core.textlogger;

namespace ut_cs_aworx  {


/** ************************************************************************************************
 *  All aworx unit test class are derived from this class.
 *  Ensures alignment and maximum compatibility of unit test methods between C++, C# and Java
 ***************************************************************************************************/
public class UnitTest
{
    #if ALOX_DBG_LOG 
        static AString lastAutoSizes= new AString();
    #endif 
    static UnitTestReportWriter reportWriter=  null;

    public UnitTest()
    {
        if ( reportWriter == null )
        {
            reportWriter= new UnitTestReportWriter();
            TextLogger utLogger= Lox.CreateConsoleLogger( "Unittest Logger" );
            utLogger.SetDomain( reportWriter.lox.InternalDomain, Log.DomainLevel.WarningsAndErrors );
            reportWriter.lox.AddLogger( utLogger );
            reportWriter.lox.SetDomain( "UT", Log.DomainLevel.All );
        }

        ResetLox();
    }

    public void ResetLox()
    {
        #if ALOX_DBG_LOG 
            if (Log.DebugLogger != null )
            {
                lastAutoSizes.Clear();
                Log.DebugLogger.AutoSizes.Export( lastAutoSizes );
            }
        #endif

        Log.Reset();

        #if ALOX_DBG_LOG 
            Log.AddDebugLogger();
            if ( lastAutoSizes.IsNotEmpty() )
            {
                Substring s= new Substring( lastAutoSizes );
                Log.DebugLogger.AutoSizes.Import( s, CurrentData.Keep );
            }
        #endif

        Log.MapThreadName( "UT" );
    }

    public void UT_PRINT( Object  msg, [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="" )   {  reportWriter.Print( csf,cln,cmn, 0, msg );  }

    private AString     ASS= new AString();
    private void        ASM(String csf,int cln,String cmn,  AString msg )  {  reportWriter.Print(csf,cln,cmn, 2, msg );   }

    public void UT_EQ( char    exp,       char    c , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (c!=exp)                         ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(c)        ._("\".") ); Assert.AreEqual( exp            , c             );   }
    public void UT_EQ( String  exp,       String  s , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (!s.Equals(exp))                 ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(s)        ._("\".") ); Assert.AreEqual( exp            , s             );   }
    public void UT_EQ( AString exp,       String  s , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (!s.Equals(exp))                 ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(s)        ._("\".") ); Assert.AreEqual( exp.ToString() , s             );   }
    public void UT_EQ( String  exp,       AString s , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (!exp.Equals(s))                 ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(s)        ._("\".") ); Assert.AreEqual( exp            , s.ToString()  );   }
    public void UT_EQ( AString exp,       AString s , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (!s.Equals(exp))                 ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(s)        ._("\".") ); Assert.AreEqual( exp.ToString() , s.ToString()  );   }

    public void UT_EQ( bool    exp,     bool    b   , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (b!=exp)                         ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp?"T":"F")._("\", Given: \"")._(b?"T":"F")._("\".") ); Assert.AreEqual( exp            , b             );   }
    public void UT_EQ( int     exp,     int     i   , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (i!=exp)                         ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(i)        ._("\".") ); Assert.AreEqual( exp            , i             );   }
    public void UT_EQ( long    exp,     long    l   , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (l!=exp)                         ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(l)        ._("\".") ); Assert.AreEqual( exp            , l             );   }

    public void UT_EQ( double  exp,     double  d   , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (d!=exp)                         ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(d)        ._("\".") ); Assert.AreEqual( exp            , d             );   }
    public void UT_EQ( double exp,double d,double p , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if ((d < exp ? exp-d : d-exp) > p)  ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: \"")._(exp)        ._("\", Given: \"")._(d)        ._("\".") ); Assert.AreEqual( exp            , d,   p        );   }

    public void UT_TRUE ( bool    cond              , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if (!cond)                          ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: true, but false given.") );                                                         Assert.IsTrue  ( cond                           );   }
    public void UT_FALSE( bool    cond              , [CallerFilePath] String csf="",[CallerLineNumber] int cln= 0,[CallerMemberName] String cmn="")   {  if ( cond)                          ASM(csf,cln,cmn,ASS.Clear()._("UT Failure: Expected: false, but true given.") );                                                         Assert.IsFalse ( cond                           );   }

} // class UnitTests

/** ****************************************************************************************
 *  The \b %ReportWriter for unit tests using AWorx Libraries
 ******************************************************************************************/
public class UnitTestReportWriter : ReportWriter
{
    public Lox     lox= new Lox();

    public void Print( String csf,int cln,String cmn, int level, Object msg )
    {
             if ( level == 0 )  lox.Info    ( "UT", msg, 0,csf,cln,cmn );
        else if ( level == 1 )  lox.Warning ( "UT", msg, 0,csf,cln,cmn );
        else                    lox.Error   ( "UT", msg, 0,csf,cln,cmn );
    }

    /** ************************************************************************************
     * Write ALib reports using ALox.
     * @param report The report.
     **************************************************************************************/
    public virtual void Report  (Report.Message report)
    {
        if( report.Type == 0 )
            lox.Error  ( "REPORT", report.Contents, 0, report.File, report.Line, report.Func );
        else
            lox.Warning( "REPORT", report.Contents, 0, report.File, report.Line, report.Func );
    }
}


/** ************************************************************************************************
 *  Writes sample output of a unit test to a file used as input source for doxygen
 ***************************************************************************************************/
public class UnitTestSampleWriter
{
    protected System.IO.TextWriter      origOut;
    protected System.IO.StreamWriter    utWriter;
    protected static String             GeneratedSamplesDir= null;   

    // defaults to "docs/ALox.CS/".
    // Set this to a suitable value in your bootstrap code, before using this class with
    // other projects!
    public    static String             GeneratedSamplesSearchDir= "docs/ALox.CS";
    
    public static String GetGeneratedSamplesDir()
    {
        // if invoked the first time, search the right directory
        if ( GeneratedSamplesDir == null )
        {
            GeneratedSamplesDir= "";
            String testDir= "../" + GeneratedSamplesSearchDir;
            for( int depth= 0; depth < 10 ; depth++ )
            {
                if ( Directory.Exists( testDir ) )
                {
                    GeneratedSamplesDir= testDir + "/generated";
                    if ( !Directory.Exists( GeneratedSamplesDir ) )
                        Directory.CreateDirectory( GeneratedSamplesDir );
                    GeneratedSamplesDir= GeneratedSamplesDir + "/";

                    break;
                }
                testDir=  "../" + testDir;
            }
        }
        return GeneratedSamplesDir;
    }

    public UnitTestSampleWriter(String filename)
    {
        if ( GetGeneratedSamplesDir().Length == 0 )
            return;

        origOut= Console.Out;

        utWriter= new StreamWriter( GeneratedSamplesDir +  filename );
        Console.SetOut( utWriter );

        Console.WriteLine( "//! [OUTPUT]" );
    }

    public void FlushAndResetConsole()
    {
        if ( GetGeneratedSamplesDir().Length == 0 )
            return;
        Console.WriteLine( "//! [OUTPUT]" );
        utWriter.Flush();
        utWriter.Close();
        Console.SetOut( origOut );
    }

} // class UnitTestSampleWriter

} //namespace
