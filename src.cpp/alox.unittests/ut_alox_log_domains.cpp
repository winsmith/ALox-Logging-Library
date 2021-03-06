// #################################################################################################
//  Unit Tests - ALox Logging Library
//  (Unit Tests to create tutorial sample code and output)
//
//  (c) 2013-2016 A-Worx GmbH, Germany
//  Published under MIT License (Open Source License, see LICENSE.txt)
// #################################################################################################
#include "alib/stdafx_alib.h"
#include "alib/compatibility/std_string.hpp"

#include "alox/alox_console_loggers.hpp"
#include "alox/loggers/memorylogger.hpp"

#include <iostream>
#include <fstream>
#include <string>
#include <vector>

#if !defined (HPP_ALIB_CONFIG_INI_FILE)
    #include "alib/config/inifile.hpp"
#endif

#define TESTCLASSNAME       CPP_ALox_Log_Domains
#include "aworx_unittests.hpp"

using namespace std;
using namespace ut_aworx;

using namespace aworx;
using namespace aworx::lox::core::textlogger;


namespace ut_alox {

// used with unit test Log_ScopeInfoCacheTest
void ScopeInfoCacheTest2() { Log_Info("Test Method 2"); }


#define LOG_CHECK( d, s, ml,lox )    {                  \
        ml.MemoryLog._();                               \
        ml.AutoSizes.Reset();                           \
        lox.SetScopeInfo(ALIB_SRC_INFO_PARAMS);         \
        lox.Info( d, "" );                              \
        lox.Release();                                  \
        UT_EQ(s, ml .MemoryLog);                        \
        }                                               \



/** ********************************************************************************************
* UT_CLASS
**********************************************************************************************/

// with GTEST macros it all gets wild. Fix the method name
#undef  ALIB_SRC_INFO_PARAMS
#define ALIB_SRC_INFO_PARAMS     __FILE__, __LINE__, aworxTestName

UT_CLASS()



/** ********************************************************************************************
 * Lox_IllegalDomainNames
 **********************************************************************************************/
#if defined (ALOX_DBG_LOG_CI)
UT_METHOD(Lox_IllegalDomainNames)
{
    UT_INIT();

    Log_AddDebugLogger();
    MemoryLogger ml;
    ml.MetaInfo->Format._()._("<%D>");
    Log_SetVerbosity(&ml, Verbosity::Verbose );
    Log_SetVerbosity(Log::DebugLogger, Verbosity::Verbose, ALox::InternalDomains );

    Lox& lox=  *ALox::Log();
    LOG_CHECK( ""        , "</>"              , ml,lox);
    LOG_CHECK( "LOC"     , "</LOC>"           , ml,lox);
    LOG_CHECK( "%"       , "</#>"             , ml,lox);
    LOG_CHECK( "\x03"    , "</#>"             , ml,lox);
    LOG_CHECK( "<"       , "</#>"             , ml,lox);
    LOG_CHECK( ">"       , "</#>"             , ml,lox);
    LOG_CHECK( "?"       , "</#>"             , ml,lox);
    LOG_CHECK( ","       , "</#>"             , ml,lox);
    LOG_CHECK( "-"       , "</->"             , ml,lox);
    LOG_CHECK( "_"       , "</_>"             , ml,lox);
    LOG_CHECK( "@"       , "</@>"             , ml,lox);
    LOG_CHECK( "."       , "</>"              , ml,lox);
    LOG_CHECK( ".."      , "</>"              , ml,lox);
    LOG_CHECK( "CU.."    , "</CU##>"          , ml,lox);

    LOG_CHECK( "$%! "    , "</####>"          , ml,lox);

    Log_SetDomain( "METH", Scope::Method );
    LOG_CHECK( "$%! "    , "</METH/####>"     , ml,lox);

    Log_SetDomain( "A\"C" , Scope::Method );
    LOG_CHECK( ""        , "</A#C>"           , ml,lox);
    LOG_CHECK( "LOC"     , "</A#C/LOC>"       , ml,lox);
    LOG_CHECK( "*X*"     , "</A#C/#X#>"       , ml,lox);

    Log_RemoveLogger( &ml );
}
#endif

/** ********************************************************************************************
 * Lox_DomainsRelative
 **********************************************************************************************/
#if defined (ALOX_REL_LOG_CI)
UT_METHOD(Lox_DomainsRelative)
{
    UT_INIT();
    Lox lox("ReleaseLox");
    #define LOX_LOX lox
    MemoryLogger ml;

    Lox_SetVerbosity ( &ml, Verbosity::Verbose );
    ml.MetaInfo->Format._()._("@%D#");
    Lox_SetDomain( "/D1/D2/D3", Scope::ThreadOuter );

    Lox_Info( "D4"                 , "" ); UT_EQ(  "@/D1/D2/D3/D4#"        , ml.MemoryLog ); ml.MemoryLog._(); ml.AutoSizes.Reset();
    Lox_Info( "./D4"               , "" ); UT_EQ(  "@/D1/D2/D3/D4#"        , ml.MemoryLog ); ml.MemoryLog._(); ml.AutoSizes.Reset();
    Lox_Info( "../D4"              , "" ); UT_EQ(  "@/D1/D2/D4#"           , ml.MemoryLog ); ml.MemoryLog._(); ml.AutoSizes.Reset();
    Lox_Info( ".././.././D4"       , "" ); UT_EQ(  "@/D1/D4#"              , ml.MemoryLog ); ml.MemoryLog._(); ml.AutoSizes.Reset();
    Lox_Info( "../../../../../D4"  , "" ); UT_EQ(  "@/D4#"                 , ml.MemoryLog ); ml.MemoryLog._(); ml.AutoSizes.Reset();
    Lox_Info( "../D4/../D5"        , "" ); UT_EQ(  "@/D1/D2/D5#"           , ml.MemoryLog ); ml.MemoryLog._(); ml.AutoSizes.Reset();

    Lox_RemoveLogger( &ml );

    #undef LOX_LOX
}
#endif


/** ********************************************************************************************
 * Log_DomainSubstitutions
 **********************************************************************************************/
#if defined (ALOX_DBG_LOG)

UT_METHOD(Log_DomainSubstitutions)
{
    UT_INIT();

    Log_AddDebugLogger();
    MemoryLogger ml;
    ml.MetaInfo->Format._()._("<%D>");
    Log_SetVerbosity(&ml, Verbosity::Verbose );
    Log_SetVerbosity(Log::DebugLogger, Verbosity::Info, ALox::InternalDomains );
    Lox& lox=  *ALox::Log();


                                                        LOG_CHECK( ""     , "</>"                    , ml,lox);
                                                        LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);

    // wrong rules
    Log_SetVerbosity(Log::DebugLogger, Verbosity::Warning, ALox::InternalDomains ); int cntLogs= Log::DebugLogger->CntLogs;
    Log_SetDomainSubstitutionRule( "LOC"        , "X"       );   ;                        UT_TRUE( cntLogs + 1 == Log::DebugLogger->CntLogs );
    Log_SetDomainSubstitutionRule( "*LOC/"      , "X"       );   ;                        UT_TRUE( cntLogs + 2 == Log::DebugLogger->CntLogs );
    Log_SetDomainSubstitutionRule( "LOC/*"      , "X"       );   ;                        UT_TRUE( cntLogs + 3 == Log::DebugLogger->CntLogs );
    Log_SetDomainSubstitutionRule( "LOC*"       , "X"       );   ;                        UT_TRUE( cntLogs + 4 == Log::DebugLogger->CntLogs );
    Log_SetDomainSubstitutionRule( "*LOC*"      , "X"       );   ;                        UT_TRUE( cntLogs + 4 == Log::DebugLogger->CntLogs );
    Log_SetDomainSubstitutionRule( "*/LOC*"     , "X"       );   ;                        UT_TRUE( cntLogs + 4 == Log::DebugLogger->CntLogs );
    Log_SetDomainSubstitutionRule( "*/LOC/*"    , "X"       );   ;                        UT_TRUE( cntLogs + 4 == Log::DebugLogger->CntLogs );

    Log_SetVerbosity(Log::DebugLogger, Verbosity::Info, ALox::InternalDomains );


    // Exact match
    Log_SetDomainSubstitutionRule( nullptr       , nullptr    );
    Log_SetDomainSubstitutionRule( "/LOC"        , "X"       );   LOG_CHECK( "LOC"  , "</X>"                   , ml,lox);
    Log_SetDomainSubstitutionRule( "/LOC"        , ""        );   LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);
    Log_SetDomainSubstitutionRule( "/LO"         , "/X"      );   LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);
    Log_SetDomainSubstitutionRule( "/LOCX"       , "/X"      );   LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);

    Log_SetDomainSubstitutionRule( nullptr     , nullptr    );

    // postfix match
    Log_SetDomainSubstitutionRule( nullptr       , nullptr    );
    Log_SetDomainSubstitutionRule( "*/LOC"       , "X"       );   LOG_CHECK( "LOC"  , "</X>"                   , ml,lox);
    Log_SetDomainSubstitutionRule( "*/LOC"       , ""        );   LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);
    Log_SetDomainSubstitutionRule( "*LOC"        , "X"       );   LOG_CHECK( "LOC"  , "</X>"                   , ml,lox);
    Log_SetDomainSubstitutionRule( "*LOC"        , ""        );   LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);
    Log_SetDomainSubstitutionRule( "*C"          , "X"       );   LOG_CHECK( "LOC"  , "</LOX>"                 , ml,lox);
    Log_SetDomainSubstitutionRule( "*C"          , ""        );   LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);

    Log_SetDomainSubstitutionRule( "*C"          , "XY"      );   LOG_CHECK( "LOC/BC" , "</LOC/BXY>"           , ml,lox);
    Log_SetDomainSubstitutionRule( "*C"          , ""        );   LOG_CHECK( "LOC/BC" , "</LOC/BC>"            , ml,lox);
    Log_SetDomainSubstitutionRule( "*/BC"        , "/"       );   LOG_CHECK( "LOC/BC" , "</LOC>"               , ml,lox);
    Log_SetDomainSubstitutionRule( "*/BC"        , ""        );   LOG_CHECK( "LOC/BC" , "</LOC/BC>"            , ml,lox);
    Log_SetDomainSubstitutionRule( "*C/BC"       , "/VE"     );   LOG_CHECK( "LOC/BC" , "</LO/VE>"             , ml,lox);

    Log_SetDomainSubstitutionRule( nullptr     , nullptr    );

    // prefix match
    #if defined(ALOX_DBG_LOG_CI)
        Log_SetDomainSubstitutionRule( nullptr     , nullptr    );
        Log_SetDomainSubstitutionRule( "/LOC*"       , "X"       );   LOG_CHECK( "LOC"  , "</X>"                   , ml,lox);
        Log_SetDomainSubstitutionRule( "/LOC*"       , ""        );   LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);
        Log_SetDomainSubstitutionRule( "/LOC*"       , "/X"      );   LOG_CHECK( "LOC"  , "</X>"                   , ml,lox);
        Log_SetDomainSubstitutionRule( "/LOC*"       , ""        );   LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);

        Log_SetDomain( "METH", Scope::Filename );           LOG_CHECK( "LOC"  , "</METH/LOC>"            , ml,lox);
        Log_SetDomainSubstitutionRule( "/LOC*"       , "/X"      );   LOG_CHECK( "LOC"  , "</METH/LOC>"            , ml,lox);
        Log_SetDomainSubstitutionRule( "/LOC*"       , ""        );   LOG_CHECK( "LOC"  , "</METH/LOC>"            , ml,lox);
        Log_SetDomainSubstitutionRule( "/METH*"      , "/X"      );   LOG_CHECK( "LOC"  , "</X/LOC>"               , ml,lox);
        Log_SetDomainSubstitutionRule( "/METH*"      , "/Y"      );   LOG_CHECK( "LOC"  , "</Y/LOC>"               , ml,lox);
        Log_SetDomainSubstitutionRule( "/METH*"      , "/A/B/C"  );   LOG_CHECK( "LOC"  , "</A/B/C/LOC>"           , ml,lox);
        Log_SetDomain( "", Scope::Filename );               LOG_CHECK( "LOC"  , "</LOC>"                 , ml,lox);
    #endif

    // recursive (just for test, not useful), breaks after 10 recursions
    Log_SetDomainSubstitutionRule( "/R*"         , "/RR"      );   LOG_CHECK( "/REC" , "</RRRRRRRRRRREC>"       , ml,lox);
                                                                   LOG_CHECK( "/REC" , "</RRRRRRRRRRREC>"       , ml,lox);
    Log_SetDomainSubstitutionRule( "/R*"         , "/S"       );   LOG_CHECK( "/R"   , "</S>"                   , ml,lox);
    Log_SetDomainSubstitutionRule( "/S*"         , "/R"       );   LOG_CHECK( "/R"   , "</R>"                   , ml,lox);
    Log_SetDomainSubstitutionRule( "/S*"         , "/T"       );   LOG_CHECK( "/R"   , "</T>"                   , ml,lox);

    //Log_LogConfig( "", Verbosity::Info, "Configuration now is:" ); ml.MemoryLog._(); ml.AutoSizes.Reset();

    // substring rule
    Log_SetDomainSubstitutionRule( "*B*"         , "X"        );   LOG_CHECK( "ABC"  , "</AXC>"                 , ml,lox);
    Log_SetDomainSubstitutionRule( "*B*"         , ""         );   LOG_CHECK( "ABC"  , "</ABC>"                 , ml,lox);
    // substring rule
    Log_SetDomainSubstitutionRule( "*/ABC*"      , "DEF"      );   LOG_CHECK( "ABC"  , "</DEF>"                 , ml,lox);
    Log_SetDomainSubstitutionRule( "*EF*"        , "ZZZ"      );   LOG_CHECK( "ABC"  , "</DZZZ>"                , ml,lox);
    Log_SetDomainSubstitutionRule( "*Z*"         , "@@"       );   LOG_CHECK( "ABC"  , "</D@@@@@@>"             , ml,lox);

    Log_SetDomainSubstitutionRule( "*/q*"        , "v"        );   LOG_CHECK( "Q"    , "</v>"                   , ml,lox);

                                                                   LOG_CHECK( "/_/abc", "</_D@@@@@@>"           , ml,lox);

    // delete all rules
    Log_SetDomainSubstitutionRule( nullptr     , nullptr    );   LOG_CHECK( "/_/abc", "</_/abc>"              , ml,lox);
                                                        LOG_CHECK( "Q"     , "</Q>"                  , ml,lox);
                                                        LOG_CHECK( "ABC"   , "</ABC>"                , ml,lox);

    //Log_LogConfig( "", Verbosity::Info, "Configuration now is:" ); ml.MemoryLog._(); ml.AutoSizes.Reset();

    Log_RemoveLogger( &ml );
}
#endif

/** ********************************************************************************************
 * Log_DomainSubstitutions_IniFile
 **********************************************************************************************/
#if defined (ALOX_DBG_LOG)

UT_METHOD(Log_DomainSubstitutions_IniFile)
{
    UT_INIT();

    // create ini file
    const char* iniFileContents=
        "[ALOX]\n"
        "TESTMEMLOGGER_FORMAT= \"<%D>\"  \n"
        "MYLOX_DOMAIN_SUBSTITUTION = /A_DOM -> /BETTER_NAME  ;\\ \n"
                                "    /UI    -> /LIBS/UI    \n"
        "x\n"
       ;

    AString fileName;
    Directory::CurrentDirectory( fileName );
    fileName._("/unittest_testiniFile.cfg");

    // write sample config file
    {
        ofstream iniFile;
        iniFile.open ( fileName.ToCString() );
        iniFile << iniFileContents;
        iniFile.close();
    }

    aworx::IniFile iniFile( fileName );
    iniFile.ReadFile();
    UT_TRUE( (IniFile::Status::Ok == iniFile.LastStatus) );

    // add to config
    ALIB::Config.InsertPlugin( &iniFile, Configuration::PrioIniFile );

    // create lox, loggers
    Lox myLox( "MyLox" ); // name will be upper case
    myLox.SetScopeInfo(ALIB_SRC_INFO_PARAMS);

        Logger* consoleLogger= Lox::CreateConsoleLogger("CONSOLE");
        myLox.SetVerbosity( "CONSOLE" , Verbosity::Verbose );
        myLox.SetVerbosity( "CONSOLE" , Verbosity::Verbose, ALox::InternalDomains );

        MemoryLogger ml("TESTMEMLOGGER");
        myLox.SetVerbosity ( &ml, Verbosity::Verbose );

        LOG_CHECK( "DOM"    , "</DOM>"              , ml,myLox);
        LOG_CHECK( "A_DOM"  , "</BETTER_NAME>"      , ml,myLox);
        LOG_CHECK( "UI"     , "</LIBS/UI>"          , ml,myLox);

        //myLox.LogConfig( "", Verbosity.Info, "Configuration now is:" );

        myLox.RemoveLogger( &ml );
        myLox.RemoveLogger( "CONSOLE" );
        delete consoleLogger;
    myLox.Release();
    ALIB::Config.RemovePlugin( &iniFile );
}
#endif

/** ********************************************************************************************
 * Log_Domain_IniFile
 **********************************************************************************************/
#if defined (ALOX_REL_LOG)

UT_METHOD(Log_Domain_IniFile)
{
    UT_INIT();

    // Without priorities
    {
        // create iniFile
        IniFile iniFile("*"); // don't read
        iniFile.Save( ALox::ConfigCategoryName, "TESTML_FORMAT","%Sp"   );
        iniFile.Save( ALox::ConfigCategoryName, "T_LOX_TESTML_VERBOSITY",
                         "/DOM_VERB  = VerboseXX  ;" // xx is allowed!
                         "/DOM_INFO  = Info       ;"
                         "/DOM_WARN  = WARNING    ;"
                         "/DOM_ERR   = erRor      ;"
                         "/DOM_OFF   = off        ;"
                         "/DOM_OFF2  = xxx        ;"
                         "/ATSTART*  = Info       ;"
                         "*ATEND     = Info       ;"
                         "*SUBSTR*   = Info       ;"
                         "/OVERWRITE = Info       ;"
                );
        ALIB::Config.InsertPlugin( &iniFile, Configuration::PrioIniFile );


        // test
        Lox lox("T_LOX", false);
        lox.SetScopeInfo(ALIB_SRC_INFO_PARAMS);
            Logger* consoleLogger= Lox::CreateConsoleLogger("CONSOLE");

            lox.SetVerbosity( "CONSOLE", Verbosity::Verbose );
            lox.SetVerbosity( "CONSOLE"                           , Verbosity::Verbose, ALox::InternalDomains );

            // pre-create one of the domains to test if loggers added later get config for existing domains
            lox.Verbose( "DOM_INFO"     , "test" );

            MemoryLogger ml("TESTML");
            lox.SetVerbosity( &ml, Verbosity::Off );

            lox.Info   (                  "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Error  ( "NOSETTING"    , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;

            lox.Verbose( "DOM_VERB"     , "test" );
            UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            lox.Verbose( "DOM_INFO"     , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "DOM_INFO"     , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            lox.Info   ( "DOM_WARN"     , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Warning( "DOM_WARN"     , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            lox.Warning( "DOM_ERR"      , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Error  ( "DOM_ERR"      , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            lox.Error  ( "DOM_OFF"      , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Error  ( "DOM_OFF2"     , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;

            lox.Verbose( "ATSTART"      , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "ATSTART"      , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "ATSTARTXX"    , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "ATSTARTXX"    , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "XXATSTART"    , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "XXATSTART"    , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "XATSTARTX"    , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "XATSTARTX"    , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;

            lox.Verbose( "ATEND"        , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "ATEND"        , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "ATENDXX"      , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "ATENDXX"      , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "XXATEND"      , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "XXATEND"      , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "XATENDX"      , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "XATENDX"      , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;


            lox.Verbose( "SUBSTR"       , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "SUBSTR"       , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "SUBSTRXX"     , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "SUBSTRXX"     , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "XXSUBSTR"     , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "XXSUBSTR"     , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "XSUBSTRX"     , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "XSUBSTRX"     , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            // overwrite config
            lox.Verbose( "/OVERWRITE"   , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "/OVERWRITE"   , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            lox.SetVerbosity( &ml , Verbosity::Warning, "/OVERWRITE" ); // does not overwrite
            lox.Verbose( "/OVERWRITE"   , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "/OVERWRITE"   , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            lox.SetVerbosity( &ml , Verbosity::Warning, "/OVERWRITE", 1000 ); // does overwrite
            lox.Verbose( "/OVERWRITE"   , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "/OVERWRITE"   , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Warning( "/OVERWRITE"   , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;


            // overwrite non-config
            lox.Error  ( "/A"           , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Error  ( "/A/B"         , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Error  ( "/A/C"         , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;

            lox.SetVerbosity( &ml , Verbosity::Info, "/A/B", Lox::PrioSource -1 ); // does not overwrite
            lox.Verbose( "/A/B"         , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "/A/B"         , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;

            lox.SetVerbosity( &ml , Verbosity::Info, "/A/B", Lox::PrioSource ); // does overwrite
            lox.Verbose( "/A/B"         , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "/A/B"         , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            lox.SetVerbosity( &ml , Verbosity::Info, "/A/B", Lox::PrioSource + 1 ); // one higher
            lox.Verbose( "/A/B"         , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "/A/B"         , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            lox.SetVerbosity( &ml , Verbosity::Verbose, "/A" );
            lox.Verbose( "/A"           , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "/A/B"         , "test" );    UT_EQ(  0, ml.CntLogs ); ml.CntLogs= 0;
            lox.Info   ( "/A/B"         , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;
            lox.Verbose( "/A/C"         , "test" );    UT_EQ(  1, ml.CntLogs ); ml.CntLogs= 0;

            //lox.LogConfig( "", Verbosity::Info, "Configuration now is:" ); ml.MemoryLog._(); ml.AutoSizes.Reset();

            ALIB::Config.RemovePlugin( &iniFile );
            lox.RemoveLogger( &ml );
            lox.RemoveLogger( "CONSOLE" );
            delete consoleLogger;
        lox.Release();
    }
}
#endif

UT_CLASS_END


} // namespace


