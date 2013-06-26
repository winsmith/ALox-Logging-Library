﻿using System;
using System.Xml.Linq;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;

using com.aworx.lox;
using com.aworx.lox.core;
using com.aworx.util;

namespace com.aworx.unittests.lox
{
 
	[TestClass]
	public class TestsALox
	{
		/** ***********************************************************************************************
		 * <summary>	The loggers. </summary>
		 **************************************************************************************************/
		#if ALOX_DEBUG || ALOX_REL_LOG
			public static ConsoleLogger		cl;
			public static MemoryLogger		ml;
		#endif  // ALOX_DEBUG || ALOX_REL_LOG

		/** ***********************************************************************************************
		 * <summary>	Creates loggers. </summary>
		 **************************************************************************************************/
		public void   clearCreateAndAddLoggers( bool memoryLogger= false, bool consoleLogger= true)
		{
			#if ALOX_DEBUG || ALOX_REL_LOG
				Log.Reset();
				cl= null;
				ml= null;

				if ( consoleLogger )
				{
					cl=		new ConsoleLogger( "Console" ) {TabAfterSourceInfo = 60};
					Log.AddLogger( cl, Log.DomainLevel.All );

					//cl.EnableAppConsole=		true;
					//cl.EnableVSDebugConsole=	true;
				}

				if ( memoryLogger )
				{
					ml=		new MemoryLogger( "Memory" ) {TabAfterSourceInfo = 60};
					Log.AddLogger( ml, Log.DomainLevel.Off );
				}

				Log.MapThreadName( "UnitTest" );
			#endif  // ALOX_DEBUG || ALOX_REL_LOG
		}



	/** ***********************************************************************************************
	 * <summary>	Log_TestLogLevelSetting </summary>
	 **************************************************************************************************/
	protected class T : TextLogger
	{
			#if ALOX_DEBUG || ALOX_REL_LOG
					public 		T()		: base( "TLTest" )							{ }
		override 	protected void 	doTextLog(MString d, Log.Level l, MString m,
											  int i,CallerInfo c, int lineNumber) 	{}
					public 	  void 	t(MString buf, long diff) 					{ logBuf= buf; logTimeDiff( diff ); }
			#endif
	}
		
	[TestMethod]
	#if !WINDOWS_PHONE
		[TestCategory("ALox")]
	#endif
	public void Log_TestTextLoggerTimeDiff()
	{
		#if ALOX_DEBUG || ALOX_REL_LOG
		T t= new T();
		MString ms= new MString();
		long diff;
		int lenFMT_TimeDiffPrefix= 	t.FMT_TimeDiffPrefix .Length;
		int lenFMT_TimeDiffPostfix= t.FMT_TimeDiffPostfix.Length;
		long millis= 	1000L;
		long secs=		1000L * millis;
		long mins=		60 * secs;
		long hours=		60 * mins;
		long days=		24 * hours;
		
		
		diff= 0; 							ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "000" + t.FMT_TimeDiffMicros,	ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 15; 							ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "015" + t.FMT_TimeDiffMicros,	ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99; 							ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "099" + t.FMT_TimeDiffMicros,	ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 600; 							ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "600" + t.FMT_TimeDiffMicros,	ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 999; 							ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "999" + t.FMT_TimeDiffMicros,	ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 1   * millis;					ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "001" + t.FMT_TimeDiffMillis,	ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 999 * millis;					ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "999" + t.FMT_TimeDiffMillis,	ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 1   * secs;					ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "1.00" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 2   * secs + 344 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "2.34" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 3   * secs + 345 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "3.35" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9   * secs + 994 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.99" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9   * secs + 995 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "10.0" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9   * secs + 999 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "10.0" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 10  * secs + 940 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "10.9" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 10  * secs + 950 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "11.0" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		
		diff= 99  * secs + 900 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99  * secs + 949 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffSecs,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );


		diff= 2  * mins + 0 * secs;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "2.00" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 2  * mins + 30 * secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "2.50" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9  * mins + 45 * secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.75" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9  * mins + 59 * secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.98" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9  * mins + 59500 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.99" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9  * mins + 59999 * millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "10.0" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );

		diff= 99 * mins + 0 * secs;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.0" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * mins + 30* secs;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.5" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * mins + 59* secs;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * mins + 59500* millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * mins + 59999* millis;	ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "1.66" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 1 * hours + 30* mins;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "90.0" + t.FMT_TimeDiffMins,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );

		diff= 5 * hours + 30* mins;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "5.50" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
	
		diff= 9 * hours + 45* mins;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.75" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * hours + 59* mins;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.98" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * hours + 3540* secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.98" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * hours + 3580* secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.99" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * hours + 3599* secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.99" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * hours + 3600* secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "10.0" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		
		diff= 50 * hours + 15 *mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "50.2" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * hours + 45 *mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.7" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * hours + 48 *mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.8" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * hours + 59 *mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * hours + 3540* secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * hours + 3580* secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * hours + 3599* secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * hours + 3600* secs;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "4.16" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );

		diff= 1 * days + 12* hours;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "36.0" + t.FMT_TimeDiffHours,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );

		diff= 5 * days + 18* hours;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "5.75" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * days + 23* hours;			ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.95" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * days + 1380 * mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.95" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * days + 1400 * mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.97" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * days + 1439 * mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "9.99" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 9 * days + 1440 * mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "10.0" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 15 * days + 6 * hours;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "15.2" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * days + 18 * hours;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.7" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * days + 1439 * mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "99.9" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		diff= 99 * days + 1440 * mins;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "100.0" + t.FMT_TimeDiffDays,			ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );

		diff= 13452 * days+ 12* hours;		ms.Clear(); t.t( ms, diff ); Assert.AreEqual(  "13452.5" + t.FMT_TimeDiffDays,		ms.ToString(lenFMT_TimeDiffPrefix, ms.Length - lenFMT_TimeDiffPrefix - lenFMT_TimeDiffPostfix) );
		//System.out.println(ms.ToString());		
		#endif
	}

		/** ***********************************************************************************************
		 * <summary>	Log_TestLogLevelSetting </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestLogLevelSetting()
		{

			#if ALOX_DEBUG || ALOX_REL_LOG
			clearCreateAndAddLoggers();

			Log.RegDomain( "TLLS", Log.Scope.Method );
			Log.SetDomain( Log.LOX.InternalDomain, Log.DomainLevel.Off );

			// Test log level setting
			uint logLinesBefore= cl.CntLogs;
			Log.SetDomain( "TLLS", Log.DomainLevel.All );
			Log.Verbose	   ( "TLLS", "This Verbose line should be logged" );
			Log.Info	   ( "TLLS", "This Info    line should be logged" );
			Log.Warning	   ( "TLLS", "This WARN    line should be logged" );
			Log.Error	   ( "TLLS", "This Error   line should be logged" );

			Log.SetDomain( "TLLS", Log.DomainLevel.InfoWarningsAndErrors );
			Log.Verbose	   ( "TLLS", "This Verbose line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Info	   ( "TLLS", "This Info    line should be logged" );
			Log.Warning	   ( "TLLS", "This Warning line should be logged" );
			Log.Error	   ( "TLLS", "This Error   line should be logged" );


			Log.SetDomain( "TLLS", Log.DomainLevel.WarningsAndErrors );
			Log.Verbose	   ( "TLLS", "This Verbose line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Info	   ( "TLLS", "This Info    line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Warning	   ( "TLLS", "This Warning line should be logged" );
			Log.Error	   ( "TLLS", "This Error   line should be logged" );

			Log.SetDomain( "TLLS", Log.DomainLevel.Errors );
			Log.Verbose	   ( "TLLS", "This Verbose line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Info	   ( "TLLS", "This Info    line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Warning	   ( "TLLS", "This Warning line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Error	   ( "TLLS", "This Error   line should be logged" );
			
			Log.SetDomain( "TLLS", Log.DomainLevel.Off );
			Log.Verbose	   ( "TLLS", "This Verbose line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Info	   ( "TLLS", "This Info    line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Warning	   ( "TLLS", "This Warning line should NOT be logged. !!!!!Test Error!!!!!" );
			Log.Error	   ( "TLLS", "This Error   line should NOT be logged. !!!!!Test Error!!!!!" );

			Assert.AreEqual ( (uint) 10, cl.CntLogs - logLinesBefore );
		}

		/** ***********************************************************************************************
		 * <summary>	Log_TestDefaultDomain </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestDefaultDomain()
		{
			uint cntLL;
			clearCreateAndAddLoggers( true );
			Log.Error("No domain and nothing set");

			Log.RegDomain( "DFLT", Log.Scope.Method );

			String testOK=  "OK";
			String testERR= "Error";

			// Test log level setting
			cntLL= cl.CntLogs;
			Log.SetDomain( "DFLT", Log.DomainLevel.InfoWarningsAndErrors );
			cntLL= cl.CntLogs;	Log.Verbose	   ( testERR );	Assert.AreEqual ( (uint) 0, cl.CntLogs - cntLL );
			cntLL= cl.CntLogs;	Log.Info	   ( testOK  );	Assert.AreEqual ( (uint) 1, cl.CntLogs - cntLL );

			Log.SetDomain( "~", Log.DomainLevel.WarningsAndErrors );
			cntLL= cl.CntLogs;	Log.Info	   ( testERR );	Assert.AreEqual ( (uint) 0, cl.CntLogs - cntLL );
			cntLL= cl.CntLogs;	Log.Warning	   ( testOK  );	Assert.AreEqual ( (uint) 1, cl.CntLogs - cntLL );

			Log.SetDomain( "~/",Log.DomainLevel.Errors );
			cntLL= cl.CntLogs;	Log.Warning	   ( testERR );	Assert.AreEqual ( (uint) 0, cl.CntLogs - cntLL );
			cntLL= cl.CntLogs;	Log.Error	   ( testOK  );	Assert.AreEqual ( (uint) 1, cl.CntLogs - cntLL );


			// test sub domains
			Log.RegDomain( "DFLT/WARN",	Log.Scope.None );
			Log.RegDomain(    "~/ERR",	Log.Scope.None );
			Log.LogConfig( "TEST",	Log.Level.Info, "Dumping Log Configuration:", cl.Name );

			Log.SetDomain( "DFLT",		Log.DomainLevel.InfoWarningsAndErrors,	true );
			Log.SetDomain( "DFLT/WARN",	Log.DomainLevel.WarningsAndErrors,		true );
			Log.SetDomain(    "~/ERR",	Log.DomainLevel.Errors,					true );

			Log.LogConfig( "TEST",			Log.Level.Info, "Dumping Log Configuration:", cl.Name );
	
			// log with leading "/" on domain
			cntLL= ml.CntLogs;	Log.Verbose	   ( "/DFLT",		testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Verbose	   ( "/DFLT/ERR",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Verbose	   ( "/DFLT/WARN",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Info	   ( "/DFLT",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Info	   ( "/DFLT/ERR",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Info	   ( "/DFLT/WARN",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Warning	   ( "/DFLT",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Warning	   ( "/DFLT/WARN",	testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Warning	   ( "/DFLT/ERR",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Error	   ( "/DFLT",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Error	   ( "/DFLT/WARN",	testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Error	   ( "/DFLT/ERR",	testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );

			// log without leading "/" on domain, still absolute
			cntLL= ml.CntLogs;	Log.Verbose	   ( "DFLT",		testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Verbose	   ( "DFLT/ERR",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Verbose	   ( "DFLT/WARN",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Info	   ( "DFLT",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Info	   ( "DFLT/ERR",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Info	   ( "DFLT/WARN",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Warning	   ( "DFLT",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Warning	   ( "DFLT/WARN",	testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Warning	   ( "DFLT/ERR",	testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Error	   ( "DFLT",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Error	   ( "DFLT/WARN",	testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Error	   ( "DFLT/ERR",	testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );


			// relative addressing with "~"domain
			cntLL= ml.CntLogs;	Log.Verbose	   (				testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Verbose	   ( "~",			testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Verbose	   ( "~ERR",		testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Verbose	   ( "~WARN",		testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Info	   (				testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Info	   ( "~",			testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Info	   ( "~ERR",		testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Info	   ( "~WARN",		testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Warning	   ( 				testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Warning	   ( "~",			testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Warning	   ( "~WARN",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Warning	   ( "~ERR",		testERR );	Assert.AreEqual ( (uint) 0, ml.CntLogs - cntLL );

			cntLL= ml.CntLogs;	Log.Error	   (				testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Error	   ( "~",			testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Error	   ( "~WARN",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );
			cntLL= ml.CntLogs;	Log.Error	   ( "~ERR",		testOK  );	Assert.AreEqual ( (uint) 1, ml.CntLogs - cntLL );

			#endif  // ALOX_DEBUG || ALOX_REL_LOG

		}

		/** ***********************************************************************************************
		 * <summary>	Log_TestDefaultDomain </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestAssertAndConditional()
		{
			#if ALOX_DEBUG || ALOX_REL_LOG
			clearCreateAndAddLoggers( true );

			Log.RegDomain( "ASSERT", Log.Scope.Method );

			String testOK=  "OK";
			String testERR= "Error";

			// Test log level setting
			uint cntLL= cl.CntLogs;
			Log.SetDomain( "ASSERT", Log.DomainLevel.InfoWarningsAndErrors);

				cntLL= cl.CntLogs;	Log.Assert( true,  testERR );							Assert.AreEqual ( (uint) 0, cl.CntLogs - cntLL );
				cntLL= cl.CntLogs;	Log.Assert( false, testOK  );							Assert.AreEqual ( (uint) 1, cl.CntLogs - cntLL );
				cntLL= cl.CntLogs;	Log.Line   ( false, null, Log.Level.Info, testERR );	Assert.AreEqual ( (uint) 0, cl.CntLogs - cntLL );
				cntLL= cl.CntLogs;	Log.Line   ( true,  null, Log.Level.Info, testOK  );	Assert.AreEqual ( (uint) 1, cl.CntLogs - cntLL );
			#endif
		}

		/** ***********************************************************************************************
		 * <summary>	Log_TestDefaultDomain </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestOutputFlags()
		{
			#if ALOX_DEBUG || ALOX_REL_LOG
			clearCreateAndAddLoggers();

			Log.RegDomain( "FMT", Log.Scope.Method );
			Log.SetDomain( "FMT", Log.DomainLevel.All);
			
			Log.Info( "This is the default ConsoleLogger log line" );

			cl.LogDate=			true;  
			cl.LogTimeOfDay=	true;		Log.Info( "LogDate= on, LogTimeOfDay= on" );
			cl.LogLogCounter=	true;		Log.Info( "LogLogCounter= true" ); Log.Info( "LogLogCounter= true" ); Log.Info( "LogLogCounter= true" );
			cl.LogThreadInfo=	false;  	Log.Info( "LogThreadInfo= false" );
			cl.LogDomainName=	false;  	Log.Info( "LogDomainName= false" );
			cl.LogLogLevel=		false;  	Log.Info( "LogLogLevel= false" );
			cl.LogTimeDiff=		false;  	Log.Info( "LogTimeDiff= false" );
			cl.LogTimeElapsed=	false;  	Log.Info( "LogTimeElapsed= false" );
			cl.LogCallerInfo=	false;  	Log.Info( "LogCallerInfo= false" );
			cl.LogDate=			false;		Log.Info( "LogDate= false" );
			cl.LogTimeOfDay=	false;  	Log.Info( "LogTimeOfDay= false" );
			cl.LogLogCounter=	false;		Log.Info( "LogLogCounter= false" ); Log.Info( "LogLogCounter= false" ); Log.Info( "LogLogCounter= false" );

			cl.FMT_MessagePrefix="";		Log.Info( "cl.FMT_MessagePrefix=\"\""); Log.Info( "cl.FMT_MessagePrefix=\"\""); Log.Info( "cl.FMT_MessagePrefix=\"\"");
			#endif
		}

		/** ***********************************************************************************************
		 * <summary>	Log_TestMarker </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestMarker()
		{
			clearCreateAndAddLoggers();

			Log.RegDomain( "MARKER", Log.Scope.Method );
			Log.SetDomain( "MARKER", Log.DomainLevel.All );


			Log.Info( "*** Testing markers ***" );
			const String markerDisposedGlobal=	"overwritten global level marker (should NOT appear in the test log)";
			const String markerGlobal=			"global marker (this is right to appear in the test log!)";
			const String markerDisposedSrc=		"overwritten source level marker (should NOT appear in the test log)";
			const String markerSrc=				"source level marker (this is right to appear in the test log!)";
			const String markerDisposedMethod=	"overwritten method marker (should NOT appear in the test log)";
			const String markerMethod=			"method marker (this is right to appear in the test log!)";


			/*#/AWX_LOG// Log.SetMarker( markerDisposedSrc,	true );		//#/AWX_LOG*/
			Log.SetMarker( markerDisposedGlobal,	Log.Scope.None );
			Log.SetMarker( markerGlobal,			Log.Scope.None );
			Log.SetMarker( markerDisposedSrc,		Log.Scope.SourceFile );
			Log.SetMarker( markerSrc,				Log.Scope.SourceFile );
			Log.SetMarker( markerDisposedMethod,	Log.Scope.Method );
			Log.SetMarker( markerMethod,			Log.Scope.Method );

			Object[] markerpointer= new Object[1];

			Log.GetMarker( markerpointer, Log.Scope.None );			Log.Info( "The current global level marker is: " + markerpointer[0] );	 Assert.IsTrue( (String) markerpointer[0] == markerGlobal);
			Log.GetMarker( markerpointer, Log.Scope.SourceFile );	Log.Info( "The current source level marker is: " + markerpointer[0] );	 Assert.IsTrue( (String) markerpointer[0] == markerSrc	 );
			Log.GetMarker( markerpointer, Log.Scope.Method );		Log.Info( "The current method level marker is: " + markerpointer[0] );	 Assert.IsTrue( (String) markerpointer[0] == markerMethod);
		}


		/** ***********************************************************************************************
		 * <summary>	Log_TestThreads </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestThreads()
		{
			clearCreateAndAddLoggers();

			Log.RegDomain ( "TEST/THREAD1", Log.Scope.Method );
			Log.SetDomain( "TEST/THREAD1", Log.DomainLevel.All );

			#if ALOX_DEBUG || ALOX_REL_LOG
				cl.LogThreadInfo=	true;
			#endif  // ALOX_DEBUG || ALOX_REL_LOG

			// if this gets commented out, the test might crash. At least the console will
			// become scrambled!
			//Log.LOX.Lock.setUnsafe( true );
			

			Thread thread = new Thread( new ThreadStart( testDifferentThreadMethod ) ) {Name = "Thread2"};
			thread.Start();

			for ( int i= 0 ; i < 50 ; i++ )
			{
				Log.Info( "This is the main thread ");// + i );
				AWXU.Sleep( 4 );
			}
		}

		public void testDifferentThreadMethod()
		{
			Log.RegDomain ( "TEST/THREAD2", Log.Scope.Method );
			Log.SetDomain( "TEST/THREAD2", Log.DomainLevel.All );

			for ( int i= 0 ; i < 20 ; i++ )
			{ 
				Log.Info( "This is a different Thread. Cnt= " + i );
				AWXU.Sleep( 3 );
			}
		}


		/** ***********************************************************************************************
		 * <summary>	Log_TestException. </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestException()
		{
			clearCreateAndAddLoggers();

			Log.RegDomain( "EXCEPT", Log.Scope.Method );
			Log.SetDomain( "EXCEPT", Log.DomainLevel.All );


			Exception testException=  new Exception( "TestException Message", new Exception ("InnerException Message", new Exception("Inner, inner Exception") ) );

			LogTools.Exception( null, Log.Level.Warning, testException, "Logging an exception: " );
		}

		/** ***********************************************************************************************
		 * <summary>	Log_TestException. </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestMultiline()
		{
			clearCreateAndAddLoggers();

			#if ALOX_DEBUG || ALOX_REL_LOG
				Log.RegDomain( "MLine", Log.Scope.Method );
				Log.SetDomain( "MLine", Log.DomainLevel.All );


				cl.MultiLineMsgMode= 0;
				Log.Info( "" );
				Log.Info( "-------- ML Mode = 0 (single line) --------" );
				Log.LogConfig( "MLine", Log.Level.Info, "Our Log configuration is:" );

				cl.MultiLineMsgMode= 0;
				cl.MultiLineDelimiterRepl= "~|~";
				Log.Info( "" );
				Log.Info( "-------- ML Mode = 0 (single line) with delimiter replacement set to ~|~ --------" );
				Log.LogConfig( "MLine", Log.Level.Info, "Our Log configuration is:" );

				cl.MultiLineMsgMode= 0;
				cl.MultiLineDelimiter= "";
				Log.Info( "" );
				Log.Info( "-------- ML Mode = 0 (single line) with delimiter set to \"\" (stops multi line processing) --------" );
				Log.LogConfig( "MLine", Log.Level.Info, "Our Log configuration is:" );
				cl.MultiLineDelimiter= null; // reset

				cl.MultiLineMsgMode= 1;
				Log.Info( "" );
				Log.Info( "-------- ML Mode = 1 (multi line, all meta info per line) --------" );
				Log.LogConfig( "MLine", Log.Level.Info, "Our Log configuration is:" );
			
				cl.MultiLineMsgMode= 2;
				Log.Info( "" );
				Log.Info( "-------- ML Mode = 2 (multi line, meta info blanked) --------" );
				Log.LogConfig( "MLine", Log.Level.Info, "Our Log configuration is:" );

				cl.MultiLineMsgMode= 3;
				Log.Info( "" );
				Log.Info( "-------- ML Mode = 3 (multi line, just caller info) --------" );
				Log.LogConfig( "MLine", Log.Level.Info, "Our Log configuration is:" );

				cl.MultiLineMsgMode= 4;
				Log.Info( "" );
				Log.Info( "-------- ML Mode = 4 (multi line, no meta, no caller, starts at pos 0)) --------" );
				Log.LogConfig( "MLine", Log.Level.Info, "Our Log configuration is:" );
			#endif
		}

		/** ***********************************************************************************************
		 * <summary>	Log_TestXML </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestInstance()
		{
			clearCreateAndAddLoggers();

			Log.RegDomain( "INST", Log.Scope.Method );
			Log.SetDomain( "INST", Log.DomainLevel.All );

			// simple type double
			{
				Double o= 3.14;
				
				LogTools.Instance( Log.Level.Info, o, 2, "Logging an object of type 'Double':", 1 );
			}

			// Double array
			{
				Double[] o= new Double[3];
				o[0]= 12.5;
				o[1]= 123456789.987654321;
				o[2]= 100;

				LogTools.Instance( Log.Level.Info, o, 2, "Logging an object of type 'Double[]':", 1 );
			}

			// byte array
			{
				byte[] o= new byte[3];
				o[0]= 2;
				o[1]= 8;
				o[2]= 16;

				LogTools.Instance( Log.Level.Info, o, 2, "Logging an object of type 'byte[]':", 1 );
			}


			// simple type String
			{
				String o= "Hello reflection";
				LogTools.Instance( Log.Level.Info, o, 2, "Logging an object of type 'String':", 1 );
			}


			// String array
			{
				String[] o= new String[3];
				o[0]= "Entry 0";
				o[1]= "Entry 1";
				o[2]= "Entry 2";

				LogTools.Instance( Log.Level.Info, o, 2, "Logging an object of type 'String[]':", 1 );
			}

			// Object array
			{
				Object[] o= new Object[10];
				int i= 0;
				o[i++]= "Entry 0";
				o[i++]= 3.14;
				o[i++]= "next is array itself!";
				o[i++]= o;
				o[i++]= "next is console logger";
				#if ALOX_DEBUG || ALOX_REL_LOG
					o[i++]= cl;
				#endif  // ALOX_DEBUG || ALOX_REL_LOG
				o[i++]= "last filled object";

				LogTools.Instance( Log.Level.Info, o, 2, "Logging an object of type 'Object[]':", 1 );
			}

			// This thread
			{
				Thread o= Thread.CurrentThread;
				LogTools.Instance( Log.Level.Info, o, 2, ("Actual Thread: " + o.Name), 1 );
			}


		}


		/** ***********************************************************************************************
		 * <summary>	Log_TestXML </summary>
		 **************************************************************************************************/
		[TestMethod]
		#if !WINDOWS_PHONE
			[TestCategory("ALox")]
		#endif
		public void Log_TestXML()
		{
			clearCreateAndAddLoggers();

			Log.RegDomain( "XML", Log.Scope.Method );
			Log.SetDomain( "XML", Log.DomainLevel.All );


			String xmltext= "<MainTag> <SubTag>Hallo ALox XML</SubTag> <SubTag2> <SubsubTag>A sub tags' string</SubsubTag> </SubTag2> </MainTag>";
			XDocument xdoc= XDocument.Parse( xmltext );

			LogTools.XML(Log.Level.Info, xdoc, "Logging an xml document: " );
		}

	}
}