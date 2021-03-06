// #################################################################################################
//  Unit Tests - AWorx Library
//
//  (c) 2013-2016 A-Worx GmbH, Germany
//  Published under MIT License (Open Source License, see LICENSE.txt)
// #################################################################################################
package ut_alib;
import org.junit.Test;

import ut_com_aworx_uttools.AUnitTest;

import com.aworx.lib.enums.Alignment;
import com.aworx.lib.enums.Case;
import com.aworx.lib.strings.AString;
import com.aworx.lib.strings.CString;
import com.aworx.lib.strings.NumberFormat;
import com.aworx.lib.strings.Substring;
import com.aworx.lox.Log;
import com.aworx.lox.Scope;


public class UT_alib_strings_AString extends AUnitTest
{

    //---------------------------------------------------------------------------------------------------------
    //--- Test Constructors
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void Constructors()
    {
        UT_INIT();
        
        AString ms= new AString();
        ms= new AString();                            UT_TRUE ( ms.capacity() ==   0 && ms.length() == 0 );
        ms= new AString(25);                          UT_TRUE ( ms.capacity() ==  25 && ms.length() == 0 );
        ms= new AString("Test");                      UT_TRUE ( ms.capacity() >=   4 && ms.length() == 4 );

        AString tMSEmpty= new AString ( );
        ms= new AString( (AString) null );            UT_TRUE ( ms.length() == 0 );    UT_TRUE ( ms.capacity() == 0 );
        ms._( "was null" );                      UT_TRUE ( ms.length() == 8 );
        ms= new AString( tMSEmpty );                  UT_TRUE ( ms.length() == 0 );    UT_TRUE ( ms.capacity() > 0 );
        ms= new AString( tMSEmpty, 1,     0 );        UT_TRUE ( ms.length() == 0 );    UT_TRUE ( ms.capacity() > 0 );
        ms= new AString( tMSEmpty, -1000, 0 );        UT_TRUE ( ms.length() == 0 );    UT_TRUE ( ms.capacity() > 0 );
        ms= new AString( tMSEmpty, -1000, 1 );        UT_TRUE ( ms.length() == 0 );    UT_TRUE ( ms.capacity() > 0 );
        ms= new AString( tMSEmpty, -100,  1000);      UT_TRUE ( ms.length() == 0 );    UT_TRUE ( ms.capacity() > 0 );

        AString tMS=        new AString ( "0123456789" );
        ms= new AString( tMS );                       UT_TRUE ( ms.capacity() >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");
        ms= new AString( tMS, 5);                     UT_TRUE ( ms.capacity() >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "56789");
        ms= new AString( tMS, 5, 100);                UT_TRUE ( ms.capacity() >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "56789");
        ms= new AString( tMS, -5);                    UT_TRUE ( ms.capacity() >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");
        ms= new AString( tMS, -5,   3);               UT_TRUE ( ms.capacity() >    0 && ms.length()  ==  0 );        UT_EQ( ms, "");
        ms= new AString( tMS, 50,   3);               UT_TRUE ( ms.capacity() >    0 && ms.length()  ==  0 );        UT_EQ( ms, "");
        ms= new AString( tMS, 10,   3);               UT_TRUE ( ms.capacity() >    0 && ms.length()  ==  0 );        UT_EQ( ms, "");
        ms= new AString( tMS, -5,  10);               UT_TRUE ( ms.capacity() >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "01234");
        ms= new AString( tMS, -5, 100);               UT_TRUE ( ms.capacity() >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");

        Substring tSS=   new Substring ( "ABCDE0123456789FG".toCharArray(), 5,10 );
        ms= new AString( tSS );                       UT_TRUE ( ms.capacity() >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");
        // these constructors are only available in Java. In fact, this is using Charsequence
        ms= new AString( tSS, 5);                     UT_TRUE ( ms.capacity() >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "56789");
        ms= new AString( tSS, 5, 100);                UT_TRUE ( ms.capacity() >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "56789");
        ms= new AString( tSS, -5);                    UT_TRUE ( ms.capacity() >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");
        ms= new AString( tSS, -5,   3);               UT_TRUE ( ms.capacity() >    0 && ms.length()  ==  0 );        UT_EQ( ms, "");
        ms= new AString( tSS, 50,   3);               UT_TRUE ( ms.capacity() >    0 && ms.length()  ==  0 );        UT_EQ( ms, "");
        ms= new AString( tSS, 10,   3);               UT_TRUE ( ms.capacity() >    0 && ms.length()  ==  0 );        UT_EQ( ms, "");
        ms= new AString( tSS, -5,  10);               UT_TRUE ( ms.capacity() >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "01234");
        ms= new AString( tSS, -5, 100);               UT_TRUE ( ms.capacity() >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");

        String tS= "0123456789";
        ms= new AString( (String) null );             UT_TRUE ( ms.length() == 0 );
        ms= new AString( "" );                        UT_TRUE ( ms.length() == 0 );
        ms= new AString( "",     1,    0 );           UT_TRUE ( ms.length() == 0 );
        ms= new AString( "", -1000,    0 );           UT_TRUE ( ms.length() == 0 );
        ms= new AString( "", -1000,    1 );           UT_TRUE ( ms.length() == 0 );
        ms= new AString( "",  -100, 1000 );           UT_TRUE ( ms.length() == 0 );
        ms= new AString( tS );                        UT_TRUE ( ms.buffer().length >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");
        ms= new AString( tS, 5);                      UT_TRUE ( ms.buffer().length >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "56789");
        ms= new AString( tS, 5, 100);                 UT_TRUE ( ms.buffer().length >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "56789");
        ms= new AString( tS, -5);                     UT_TRUE ( ms.buffer().length >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");
        ms= new AString( tS, -5, 10);                 UT_TRUE ( ms.buffer().length >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "01234");
        ms= new AString( tS, -5, 100);                UT_TRUE ( ms.buffer().length >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");


        StringBuffer tSBEmpty= new StringBuffer ( );
        ms= new AString( (StringBuffer) null );       UT_TRUE ( ms.length() == 0 );      UT_TRUE ( ms.capacity() == 0 );
        ms._( "was null" );                      UT_TRUE ( ms.length() == 8 );
        ms= new AString( tSBEmpty );                  UT_TRUE ( ms.length() == 0 );      UT_TRUE ( ms.capacity() > 0 );
        ms= new AString( tSBEmpty, 1,     0 );        UT_TRUE ( ms.length() == 0 );      UT_TRUE ( ms.capacity() > 0 );
        ms= new AString( tSBEmpty, -1000, 0 );        UT_TRUE ( ms.length() == 0 );      UT_TRUE ( ms.capacity() > 0 );
        ms= new AString( tSBEmpty, -1000, 1 );        UT_TRUE ( ms.length() == 0 );      UT_TRUE ( ms.capacity() > 0 );
        ms= new AString( tSBEmpty, -100,  1000);      UT_TRUE ( ms.length() == 0 );      UT_TRUE ( ms.capacity() > 0 );

        StringBuffer tSB= new StringBuffer ( "0123456789" );
        ms= new AString( tSB );                       UT_TRUE ( ms.buffer().length >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");
        ms= new AString( tSB, 5);                     UT_TRUE ( ms.buffer().length >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "56789");
        ms= new AString( tSB, 5, 100);                UT_TRUE ( ms.buffer().length >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "56789");
        ms= new AString( tSB, -5);                    UT_TRUE ( ms.buffer().length >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");
        ms= new AString( tSB, -5, 10);                UT_TRUE ( ms.buffer().length >=   5 && ms.length()  ==  5 );        UT_EQ( ms, "01234");
        ms= new AString( tSB, -5, 100);               UT_TRUE ( ms.buffer().length >=  10 && ms.length()  == 10 );        UT_EQ( ms, "0123456789");

    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test IsNull
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void IsNull()
    {
        UT_INIT();
        
        AString ms
        = new AString();     UT_TRUE( ms.capacity() == 0 ); UT_TRUE(  ms.isNull() );  UT_TRUE( !ms.isNotNull() );  UT_TRUE(  ms.isEmpty() );  UT_TRUE( !ms.isNotEmpty() );
        ms._("");       UT_TRUE( ms.capacity() != 0 ); UT_TRUE( !ms.isNull() );  UT_TRUE(  ms.isNotNull() );  UT_TRUE(  ms.isEmpty() );  UT_TRUE( !ms.isNotEmpty() );
        ms._("x");      UT_TRUE( ms.capacity() != 0 ); UT_TRUE( !ms.isNull() );  UT_TRUE(  ms.isNotNull() );  UT_TRUE( !ms.isEmpty() );  UT_TRUE(  ms.isNotEmpty() );

        ms.setNull();        UT_TRUE( ms.capacity() == 0 ); UT_TRUE(  ms.isNull() );  UT_TRUE( !ms.isNotNull() );  UT_TRUE(  ms.isEmpty() );  UT_TRUE( !ms.isNotEmpty() );
        ms._("");       UT_TRUE( ms.capacity() != 0 ); UT_TRUE( !ms.isNull() );  UT_TRUE(  ms.isNotNull() );  UT_TRUE(  ms.isEmpty() );  UT_TRUE( !ms.isNotEmpty() );
        ms._("x");      UT_TRUE( ms.capacity() != 0 ); UT_TRUE( !ms.isNull() );  UT_TRUE(  ms.isNotNull() );  UT_TRUE( !ms.isEmpty() );  UT_TRUE(  ms.isNotEmpty() );

        ms.setNull();        UT_TRUE( ms.capacity() == 0 ); UT_TRUE(  ms.isNull() );  UT_TRUE( !ms.isNotNull() );  UT_TRUE(  ms.isEmpty() );  UT_TRUE( !ms.isNotEmpty() );
        ms.setBuffer(5); UT_TRUE( ms.capacity() != 0 ); UT_TRUE( !ms.isNull() );  UT_TRUE(  ms.isNotNull() );  UT_TRUE(  ms.isEmpty() );  UT_TRUE( !ms.isNotEmpty() );
        ms._("x");      UT_TRUE( ms.capacity() != 0 ); UT_TRUE( !ms.isNull() );  UT_TRUE(  ms.isNotNull() );  UT_TRUE( !ms.isEmpty() );  UT_TRUE(  ms.isNotEmpty() );
    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test FrontEndCharAt
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void CharAt()
    {
        UT_INIT();
        
        AString ms= new AString();
        UT_EQ(  ms.charAtStart()    ,    '\0' );
        UT_EQ(  ms.charAtEnd  ()    ,    '\0' );
        UT_EQ(  ms.charAt     ( -1 ),    '\0' );
        UT_EQ(  ms.charAt     (  0 ),    '\0' );
        UT_EQ(  ms.charAt     (  1 ),    '\0' );


        ms._()._("A");

        UT_EQ(  ms.charAtStart   ()    ,    'A'  );
        UT_EQ(  ms.charAtStart_NC()    ,    'A'  );
        UT_EQ(  ms.charAtEnd     ()    ,    'A'  );
        UT_EQ(  ms.charAtEnd_NC  ()    ,    'A'  );
        UT_EQ(  ms.charAt        ( -1 ),    '\0' );
        UT_EQ(  ms.charAt        (  0 ),    'A'  );
        UT_EQ(  ms.charAt        (  1 ),    '\0' );
        UT_EQ(  ms.charAt_NC     (  0 ),    'A'  );

        ms._()._("ABC");

        UT_EQ(  ms.charAtStart   ()    ,    'A'  );
        UT_EQ(  ms.charAtStart_NC()    ,    'A'  );
        UT_EQ(  ms.charAtEnd     ()    ,    'C'  );
        UT_EQ(  ms.charAtEnd_NC  ()    ,    'C'  );
        UT_EQ(  ms.charAt        ( -1 ),    '\0' );
        UT_EQ(  ms.charAt        (  0 ),    'A'  );
        UT_EQ(  ms.charAt        (  1 ),    'B'  );
        UT_EQ(  ms.charAt        (  2 ),    'C'  );
        UT_EQ(  ms.charAt        (  3 ),    '\0' );

        UT_EQ(  ms.charAt_NC     (  0 ),    'A'  );
        UT_EQ(  ms.charAt_NC     (  1 ),    'B'  );
        UT_EQ(  ms.charAt_NC     (  2 ),    'C'  );

        ms._()._("ABC");
        ms.setCharAt( -1, '@' );    UT_EQ    ( "ABC",   ms );
        ms.setCharAt(  0, 'X' );    UT_EQ    ( "XBC",   ms );
        ms.setCharAt(  1, 'Y' );    UT_EQ    ( "XYC",   ms );
        ms.setCharAt(  2, 'Z' );    UT_EQ    ( "XYZ",   ms );
        ms.setCharAt(  3, '$' );    UT_EQ    ( "XYZ",   ms );
    }


    //---------------------------------------------------------------------------------------------------------
    //--- Test DeleteInsert
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void DeleteInsertSet()
    {
        UT_INIT();
        
        AString ms= new AString();
        // delete
        {
            ms._()._("0123456789");    ms.delete        (  5       );       UT_EQ( "01234",        ms );
            ms._()._("0123456789");    ms.delete        (  5,    0 );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.delete        (  5,   -1 );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.delete        (  5,    1 );       UT_EQ( "012346789",    ms );
            ms._()._("0123456789");    ms.delete        (  5,  100 );       UT_EQ( "01234",        ms );
            ms._()._("0123456789");    ms.delete        (  -5,  10 );       UT_EQ( "56789",        ms );


            ms._()._("0123456789");    ms.delete_NC      (  5,    5 );       UT_EQ( "01234",        ms );
            ms._()._("0123456789");    ms.delete_NC      (  0,    5 );       UT_EQ( "56789",        ms );
            ms._()._("0123456789");    ms.delete_NC      (  0,    1 );       UT_EQ( "123456789",    ms );
            ms._()._("0123456789");    ms.delete_NC      (  9,    1 );       UT_EQ( "012345678",    ms );

            ms._()._("0123456789");    ms.deleteStart    (  -2      );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.deleteStart    (  -1      );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.deleteStart    (   0      );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.deleteStart    (   1      );       UT_EQ(  "123456789",   ms );
            ms._()._("0123456789");    ms.deleteStart    (   2      );       UT_EQ(   "23456789",   ms );
            ms._()._("0123456789");    ms.deleteStart    (   9      );       UT_EQ(          "9",   ms );
            ms._()._("0123456789");    ms.deleteStart    (  10      );       UT_EQ(           "",   ms );
            ms._()._("0123456789");    ms.deleteStart    (  11      );       UT_EQ(           "",   ms );

            ms._()._("0123456789");    ms.deleteStart_NC (   0      );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.deleteStart_NC (   1      );       UT_EQ(  "123456789",   ms );
            ms._()._("0123456789");    ms.deleteStart_NC (   2      );       UT_EQ(   "23456789",   ms );
            ms._()._("0123456789");    ms.deleteStart_NC (   9      );       UT_EQ(          "9",   ms );
            ms._()._("0123456789");    ms.deleteStart_NC (  10      );       UT_EQ(           "",   ms );

            ms._()._("0123456789");    ms.deleteEnd     (  -2      );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.deleteEnd     (  -1      );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.deleteEnd     (   0      );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.deleteEnd     (   1      );       UT_EQ( "012345678" ,   ms );
            ms._()._("0123456789");    ms.deleteEnd     (   2      );       UT_EQ( "01234567"  ,   ms );
            ms._()._("0123456789");    ms.deleteEnd     (   9      );       UT_EQ( "0"         ,   ms );
            ms._()._("0123456789");    ms.deleteEnd     (  10      );       UT_EQ( ""          ,   ms );
            ms._()._("0123456789");    ms.deleteEnd     (  11      );       UT_EQ( ""          ,   ms );

            ms._()._("0123456789");    ms.deleteEnd_NC  (   0      );       UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.deleteEnd_NC  (   1      );       UT_EQ( "012345678" ,   ms );
            ms._()._("0123456789");    ms.deleteEnd_NC  (   2      );       UT_EQ( "01234567"  ,   ms );
            ms._()._("0123456789");    ms.deleteEnd_NC  (   9      );       UT_EQ( "0"         ,   ms );
            ms._()._("0123456789");    ms.deleteEnd_NC  (  10      );       UT_EQ( ""          ,   ms );
        }

        // insertChars
        {
            ms._()._("0123456789");    ms.insertChars    ( ' ',  1,  -1 );   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.insertChars    ( ' ',  1,  20 );   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.insertChars    ( ' ',  0,   0 );   UT_EQ( "0123456789",   ms );

            ms._()._("0123456789");    ms.insertChars    ( ' ',  1,   0 );   UT_EQ( " 0123456789",  ms );
            ms._()._("0123456789");    ms.insertChars    ( ' ',  1,   1 );   UT_EQ( "0 123456789",  ms );
            ms._()._("0123456789");    ms.insertChars    ( ' ',  2,   0 );   UT_EQ( "  0123456789", ms );
            ms._()._("0123456789");    ms.insertChars    ( ' ',  2,   1 );   UT_EQ( "0  123456789", ms );

            ms._()._("0123456789");    ms.insertChars    ( ' ',  1,  11 );   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.insertChars    ( ' ',  1,  10 );   UT_EQ( "0123456789 ",  ms );
            ms._()._("0123456789");    ms.insertChars    ( '@',  3,   5 );   UT_EQ( "01234@@@56789",ms );
        }

        // InsertAt
        {
            ms._()._("0123456789");   ms.insertAt        ( "TST",  -2   );   UT_EQ( "0123456789",    ms );
            ms._()._("0123456789");   ms.insertAt        ( "TST",  -1   );   UT_EQ( "0123456789",    ms );
            ms._()._("0123456789");   ms.insertAt        ( "TST",   0   );   UT_EQ( "TST0123456789", ms );
            ms._()._("0123456789");   ms.insertAt        ( "TST",   1   );   UT_EQ( "0TST123456789", ms );
            ms._()._("0123456789");   ms.insertAt        ( "TST",   8   );   UT_EQ( "01234567TST89", ms );
            ms._()._("0123456789");   ms.insertAt        ( "TST",   9   );   UT_EQ( "012345678TST9", ms );
            ms._()._("0123456789");   ms.insertAt        ( "TST",  10   );   UT_EQ( "0123456789TST", ms );
            ms._()._("0123456789");   ms.insertAt        ( "TST",  11   );   UT_EQ( "0123456789",    ms );
            ms._()._("0123456789");   ms.insertAt        ( "TST",  12   );   UT_EQ( "0123456789",    ms );
        }


        // ReplaceSubstring
        {
            String r= "ABC";
            ms._()._("0123456789");   ms.replaceSubstring( r,   0,  10)  ;   UT_EQ( "ABC",          ms );
            ms._()._("0123456789");   ms.replaceSubstring( r, -10, 100)  ;   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");   ms.replaceSubstring( r, -10,  5 )  ;   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");   ms.replaceSubstring( r, -10, 10 )  ;   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");   ms.replaceSubstring( r, -10, 11 )  ;   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");   ms.replaceSubstring( r,   0,  1 )  ;   UT_EQ( "ABC123456789", ms );
            ms._()._("0123456789");   ms.replaceSubstring( r,   0,  2 )  ;   UT_EQ( "ABC23456789",  ms );
            ms._()._("0123456789");   ms.replaceSubstring( r,   1,  1 )  ;   UT_EQ( "0ABC23456789", ms );
            ms._()._("0123456789");   ms.replaceSubstring( r,   1,  2 )  ;   UT_EQ( "0ABC3456789",  ms );
            ms._()._("0123456789");   ms.replaceSubstring( r,   8,  1 )  ;   UT_EQ( "01234567ABC9", ms );
            ms._()._("0123456789");   ms.replaceSubstring( r,   8,  2 )  ;   UT_EQ( "01234567ABC",  ms );
            ms._()._("0123456789");   ms.replaceSubstring( r,   8,  3 )  ;   UT_EQ( "0123456789",   ms );

            Substring s= new Substring( "0123ABCxyz".toCharArray(), 4, 3 );
            ms._()._("0123456789");   ms.replaceSubstring( s,   0,  10)  ;   UT_EQ( "ABC",          ms );
            ms._()._("0123456789");   ms.replaceSubstring( s, -10, 100)  ;   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");   ms.replaceSubstring( s, -10,  5 )  ;   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");   ms.replaceSubstring( s, -10, 10 )  ;   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");   ms.replaceSubstring( s, -10, 11 )  ;   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");   ms.replaceSubstring( s,   0,  1 )  ;   UT_EQ( "ABC123456789", ms );
            ms._()._("0123456789");   ms.replaceSubstring( s,   0,  2 )  ;   UT_EQ( "ABC23456789",  ms );
            ms._()._("0123456789");   ms.replaceSubstring( s,   1,  1 )  ;   UT_EQ( "0ABC23456789", ms );
            ms._()._("0123456789");   ms.replaceSubstring( s,   1,  2 )  ;   UT_EQ( "0ABC3456789",  ms );
            ms._()._("0123456789");   ms.replaceSubstring( s,   8,  1 )  ;   UT_EQ( "01234567ABC9", ms );
            ms._()._("0123456789");   ms.replaceSubstring( s,   8,  2 )  ;   UT_EQ( "01234567ABC",  ms );
            ms._()._("0123456789");   ms.replaceSubstring( s,   8,  3 )  ;   UT_EQ( "0123456789",   ms );
        }

        // replaceRegion
        {
            ms._()._("0123456789");    ms.replaceRegion  ( '@',   0, 10 );   UT_EQ( "@@@@@@@@@@",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@', -10,100 );   UT_EQ( "@@@@@@@@@@",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@', -10,  5 );   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@', -10, 10 );   UT_EQ( "0123456789",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@', -10, 11 );   UT_EQ( "@123456789",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@',   0,  1 );   UT_EQ( "@123456789",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@',   0,  2 );   UT_EQ( "@@23456789",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@',   1,  1 );   UT_EQ( "0@23456789",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@',   1,  2 );   UT_EQ( "0@@3456789",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@',   8,  1 );   UT_EQ( "01234567@9",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@',   8,  2 );   UT_EQ( "01234567@@",   ms );
            ms._()._("0123456789");    ms.replaceRegion  ( '@',   8,  3 );   UT_EQ( "01234567@@",   ms );
        }

    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test Conversion
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void Conversion()
    {
        UT_INIT();
        
        AString   ms;
        ms= new AString("0123456789");
        UT_EQ( ms.toString()         , "0123456789"  );
        UT_EQ( ms.toString( 5)       , "56789"       );
        UT_EQ( ms.toString( 5, 100)  , "56789"       );
        UT_EQ( ms.toString( -5)      , "0123456789"  );
        UT_EQ( ms.toString( -5, 10 ) , "01234"       );
        UT_EQ( ms.toString( -5, 100) , "0123456789"  );

        UT_EQ( ms.toString(  0,    0), "" );
        UT_EQ( ms.toString(  5,    0), "" );
        UT_EQ( ms.toString( 20,    0), "" );
        UT_EQ( ms.toString(  0,   -1), "" );
        UT_EQ( ms.toString(  5,   -5), "" );
        UT_EQ( ms.toString(  5, -100), "" );
        UT_EQ( ms.toString( 20,   -1), "" );
        UT_EQ( ms.toString( 20,   20), "" );
        UT_EQ( ms.toString( 20, -100), "" );
        UT_EQ( ms.toString( 20,    0), "" );
        UT_EQ( ms.toString( 20,  100), "" );

        StringBuilder sb= new StringBuilder();
        UT_EQ( ms.toString( sb, 0, ms.length(), false ).toString() , "0123456789"           );
        UT_EQ( ms.toString( sb, 0, ms.length(), true  ).toString() , "01234567890123456789" );
        UT_EQ( ms.toString( sb, 0, ms.length(), false ).toString() , "0123456789"           );
        UT_EQ( ms.toString( sb, 5, 100,         false ).toString() , "56789"                );
        UT_EQ( ms.toString( sb, -5, 10,         true  ).toString() , "5678901234"           );
    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test Append
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void _()
    {
        UT_INIT();
        
        // append char[]
        {
            AString ms= new AString();  char[] csNull= null;  char[] csEmpty= "".toCharArray();


            ms._( csNull );            UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()    );
            ms._( csEmpty);            UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNotNull() );
            ms.setNull();                   UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()    );
            ms._("1234567".toCharArray());  UT_EQ  ( ms.capacity(), 7 );  UT_EQ( "1234567",   ms );
            ms._("89"     .toCharArray());  UT_TRUE( ms.length()> 7     );  UT_EQ( "123456789", ms );

            char[] t= "0123456789".toCharArray();
            ms._()._   ( t, 5);                     UT_EQ( ms, "56789"      );
            ms._()._   ( t, 5, 100);                UT_EQ( ms, "56789"      );
            ms._()._   ( t, -5);                    UT_EQ( ms, "0123456789" );
            ms._()._   ( t, -5, 3);                 UT_EQ( ms, ""           );
            ms._()._   ( t, 50, 3);                 UT_EQ( ms, ""           );
            ms._()._   ( t, 10, 3);                 UT_EQ( ms, ""           );
            ms._()._   ( t, -5, 10);                UT_EQ( ms, "01234"      );
            ms._()._   ( t, -5, 100);               UT_EQ( ms, "0123456789" );

            // _NC
            ms.setNull();                  UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()    );
            ms._NC( csEmpty,0,0);    UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()   );
            ms._()._NC( t, 5,3);                   UT_EQ( ms, "567"      );
        }

        // append AString
        {
            AString ms= new AString();  AString asNull= null; AString asEmpty= new AString(""); AString t= new AString( "012" );
            ms._( asNull );            UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()   );
            ms._( asEmpty);            UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNotNull() );
            ms.setNull();                   UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()    );
            ms._( t );                 UT_EQ  ( ms.capacity(), 3 );  UT_EQ( "012"   , ms );
            ms._( t );                 UT_TRUE( ms.length()> 3     );  UT_EQ( "012012", ms );
            t._()._( "0123456789" );
            ms._()._   ( t, 5);                     UT_EQ( ms, "56789"      );
            ms._()._   ( t, 5, 100);                UT_EQ( ms, "56789"      );
            ms._()._   ( t, -5);                    UT_EQ( ms, "0123456789" );
            ms._()._   ( t, -5, 3);                 UT_EQ( ms, ""           );
            ms._()._   ( t, 50, 3);                 UT_EQ( ms, ""           );
            ms._()._   ( t, 10, 3);                 UT_EQ( ms, ""           );
            ms._()._   ( t, -5, 10);                UT_EQ( ms, "01234"      );
            ms._()._   ( t, -5, 100);               UT_EQ( ms, "0123456789" );

            ms._()._NC( t       );                  UT_EQ( ms, "0123456789" );
            ms._()._NC( t, 1, 9 );                  UT_EQ( ms,  "123456789" );
            ms._()._NC( t, 1, 2 );                  UT_EQ( ms,  "12"        );
            ms._()._NC( t, 1, 8 );                  UT_EQ( ms,  "12345678"  );
            ms._()._NC( t, 1, 9 );                  UT_EQ( ms,  "123456789" );
            ms._()._NC( t, 5, 5 );                  UT_EQ( ms,      "56789" );

            // _NC
            ms.setNull();                  UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()    );
            ms._NC( asEmpty);        UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()   );
            ms._()._NC( t, 5, 3);                  UT_EQ( ms, "567"      );
        }

        // append Substring
        {
            AString ms= new AString();  Substring ssNull= null; Substring ssEmpty= new Substring( "" ); Substring t= new Substring( "01234" );
            ms._( ssNull );            UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()    );
            ms._( ssEmpty);            UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNotNull() );
            ms.setNull();                   UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()    );
            ms._( t );                 UT_EQ  ( ms.capacity(), 5 );  UT_EQ( "01234"     , ms );
            ms._( t );                 UT_TRUE( ms.length()> 5     );  UT_EQ( "0123401234", ms );

            t.consume();      ms._()._( t );           UT_EQ( ms,  "1234"      );
            t.consume();      ms._()._( t );           UT_EQ( ms,   "234"      );
            t.consumeFromEnd();  ms._()._( t );           UT_EQ( ms,  "23"        );

            // _NC
            ms.setNull();                   UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()   );
            ms._NC( ssEmpty );        UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()   );
            ms._()._NC( t);                       UT_EQ( ms, "23"   );
        }

        // append String
        {
            AString ms= new AString();   String sEmpty= ""; String t= "012";
            ms._(  sEmpty);            UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNotNull() );
            ms.setNull();                   UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()    );
            ms._( t );                 UT_EQ  ( ms.capacity(), 3 );  UT_EQ( "012"   , ms );
            ms._( t );                 UT_TRUE( ms.length()> 3     );  UT_EQ( "012012", ms );
            t= "0123456789";
            ms._()._( t, 5);                     UT_EQ( ms, "56789"      );
            ms._()._( t, 5, 100);                UT_EQ( ms, "56789"      );
            ms._()._( t, -5);                    UT_EQ( ms, "0123456789" );
            ms._()._( t, -5, 3);                 UT_EQ( ms, ""           );
            ms._()._( t, 50, 3);                 UT_EQ( ms, ""           );
            ms._()._( t, 10, 3);                 UT_EQ( ms, ""           );
            ms._()._( t, -5, 10);                UT_EQ( ms, "01234"      );
            ms._()._( t, -5, 100);               UT_EQ( ms, "0123456789" );

            // _NC
            ms.setNull();                   UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()   );
            ms._NC(  sEmpty );        UT_EQ  ( ms.length(), 0     );  UT_TRUE( ms.isNull()   );
            ms._()._NC( t);                      UT_EQ( ms, "0123456789"   );
            ms._()._NC( t ,2,3);                 UT_EQ( ms, "234"          );

        }

        // append StringBuffer
        {
            AString ms= new AString();   StringBuffer t= new StringBuffer( "012");
            ms._( t );                 UT_EQ  ( ms.capacity(), 3 );  UT_EQ( "012"   , ms );
            ms._( t );                 UT_TRUE( ms.length()> 3     );  UT_EQ( "012012", ms );
            t.setLength( 0 ); t.append("0123456789");
            ms._()._( t, 5);                     UT_EQ( ms, "56789"      );
            ms._()._( t, 5, 100);                UT_EQ( ms, "56789"      );
            ms._()._( t, -5);                    UT_EQ( ms, "0123456789" );
            ms._()._( t, -5, 3);                 UT_EQ( ms, ""           );
            ms._()._( t, 50, 3);                 UT_EQ( ms, ""           );
            ms._()._( t, 10, 3);                 UT_EQ( ms, ""           );
            ms._()._( t, -5, 10);                UT_EQ( ms, "01234"      );
            ms._()._( t, -5, 100);               UT_EQ( ms, "0123456789" );
        }
    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test Capacity
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void Capacitylength()
    {
        UT_INIT();
        
        Log.setDomain( "CapacityLength", Scope.METHOD );

        Log.info( "### CapacityLength ###" );

        // null-allocated
        {
            AString ms= new AString();       UT_TRUE ( ms.capacity() == 0 );
                    ms= new AString( 0 );    UT_TRUE ( ms.capacity() == 0 );
                    ms= new AString( null ); UT_TRUE ( ms.capacity() == 0 );
            ms._( "" );        UT_TRUE ( ms.capacity() > 0 );
        }

        {
            AString ms= new AString( 5 );
            ms._( 'a' );        UT_TRUE ( ms.buffer().length == 5 );
            ms._( 'b' );        UT_TRUE ( ms.buffer().length == 5 );
            ms._( 'c' );        UT_TRUE ( ms.buffer().length == 5 );
            ms._( 'd' );        UT_TRUE ( ms.buffer().length == 5 );
            ms._( 'e' );        UT_TRUE ( ms.buffer().length == 5 );
            ms._( 'x' );
            UT_TRUE ( ms.buffer().length >= 6 );
            UT_EQ( "abcdex", ms );

            ms.ensureRemainingCapacity( 2000 ); UT_TRUE ( ms.buffer().length >= 2000 );
        }

        {
            AString   ms= new AString();
            int as;
            ms._( "0123456789" );  UT_EQ    ( ms.length(), 10 );    UT_TRUE  ( ms.capacity() >= 10 );

            as= ms.capacity();

            Log.info( "A warning should follow" );
            ms.setLength(20);           UT_EQ    ( ms.length(), 10 );    UT_EQ  ( ms.capacity(), as );    UT_EQ( ms, "0123456789" );
            ms.setLength(5);            UT_EQ    ( ms.length(), 5 );     UT_EQ  ( ms.capacity(), as );    UT_EQ( ms, "01234"     );
            ms.setBuffer(3);        UT_EQ    ( ms.length(), 3 );     UT_EQ  ( ms.capacity(), 3  );    UT_EQ( ms, "012"         );
            ms._("ABC");           UT_EQ    ( ms.length(), 6 );     UT_TRUE( ms.capacity() >= 6);    UT_EQ( ms, "012ABC"     );
        }

    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test appendWithPadding
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void Fields()
    {
        UT_INIT();
        
        AString ms= new AString();

        // empty fields
        ms._();
        ms.field().field( 3, Alignment.LEFT,     '#' );    UT_EQ( ms, "###"           );
        ms.field().field( 4, Alignment.CENTER,   '*' );    UT_EQ( ms, "###****"       );
        ms.field().field( 5, Alignment.RIGHT,    '+' );    UT_EQ( ms, "###****+++++"  );

        // field, alignment left
        ms._();
        ms.field()._( "A" )        .field( 5,    Alignment.LEFT );
        ms.field()._( "AB" )       .field( 5,    Alignment.LEFT );
        ms.field()._( "ABC" )      .field( 5,    Alignment.LEFT );
        ms.field()._( "ABCD" )     .field( 5,    Alignment.LEFT );
        ms.field()._( "ABCDE" )    .field( 5,    Alignment.LEFT );
        ms.field()._( "ABCDEF" )   .field( 5,    Alignment.LEFT );
        ms.field()._( "ABCDEFG" )  .field( 5,    Alignment.LEFT );
        UT_EQ( ms, "A    AB   ABC  ABCD ABCDEABCDEFABCDEFG" );

        // field, alignment right
        ms._();
        ms.field()._( "A" )        .field( 5,    Alignment.RIGHT );
        ms.field()._( "AB" )       .field( 5,    Alignment.RIGHT );
        ms.field()._( "ABC" )      .field( 5 ); // btw: right is default
        ms.field()._( "ABCD" )     .field( 5 );
        ms.field()._( "ABCDE" )    .field( 5 );
        ms.field()._( "ABCDEF" )   .field( 5,    Alignment.RIGHT );
        ms.field()._( "ABCDEFG" )  .field( 5,    Alignment.RIGHT );
        UT_EQ( ms, "    A   AB  ABC ABCDABCDEABCDEFABCDEFG" );

        // field, alignment center
        ms._();
        ms.field()._( "A" )        .field( 5,    Alignment.CENTER );
        ms.field()._( "AB" )       .field( 5,    Alignment.CENTER );
        ms.field()._( "ABC" )      .field( 5,    Alignment.CENTER );
        ms.field()._( "ABCD" )     .field( 5,    Alignment.CENTER );
        ms.field()._( "ABCDE" )    .field( 5,    Alignment.CENTER );
        ms.field()._( "ABCDEF" )   .field( 5,    Alignment.CENTER );
        ms.field()._( "ABCDEFG" )  .field( 5,    Alignment.CENTER );
        UT_EQ( ms, "  A   AB   ABC ABCD ABCDEABCDEFABCDEFG" );

        // a nested field
        ms._();
        ms._("***");
            int outerFieldStart= ms.length();
            ms._( "OS:" )  .field()._( "Linux" ).field( 10, Alignment.RIGHT, '-' );
            ms._( " Lang:").field()._( "en_US" ).field(  8, Alignment.RIGHT, '-' );
            ms.field(35, Alignment.CENTER, '#', outerFieldStart );
        ms._("***");
        UT_EQ( ms, "***####OS:-----Linux Lang:---en_US####***" );
    }


    //---------------------------------------------------------------------------------------------------------
    //--- Test Tabs
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void Tabs()
    {
        UT_INIT();
        
        AString ms= new AString("");

        // test some bad input
                 ms.tab(  0,    -1,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab(  0,     0,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab( -1,     0,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab( -1,     0,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab( -1,    -1,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab( -5,     0,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab( -5,  -100,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab(  2,  -100,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab(  0,     0,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab(  1,     0,  '@' );  UT_EQ( "",  ms );
        ms._();  ms.tab(  1,     1,  '@' );  UT_EQ( "@", ms );
        ms._();  ms.tab(  0,     1,  '@' );  UT_EQ( "@", ms );

        // minpad 0 (not common, default is 1, tab wont move if on tab position)
        ms._();                        ms.tab( 2, 0,    '@' )._( '-' );        UT_EQ( "-",   ms);
        ms._();    ms._( 'x' );    ms.tab( 2, 0,    '@' )._( '-' );        UT_EQ( "x@-", ms);

        // more standard tabs
        ms._();
        ms._( "12" ).tab( 2 )
          ._( "1" ).tab( 2 )
          .tab(2)
          ._( "@" );
        UT_EQ( "12  1   @", ms );

        ms._();
        ms._( "12" ).tab( 2, 0 )
          ._( "1"  ).tab( 2, 0 )
                           .tab( 2, 0 )
          ._( "@" );
        UT_EQ( "121 @", ms );


        ms._();
        ms._( "12345" ).tab( 5 )
          ._( "1234" ) .tab( 5 )
          ._( "123" )  .tab( 5 )
          ._( "12" )   .tab( 5 )
          ._( "1" )    .tab( 5 )
                            .tab( 5 )
          ._( "@" );
        UT_EQ( "12345     1234 123  12   1         @", ms );

        ms._();
        ms._( "12345" ).tab( 5, 0 )
          ._( "1234" ) .tab( 5, 0 )
          ._( "123" )  .tab( 5, 0 )
          ._( "12" )   .tab( 5, 0 )
          ._( "1" )    .tab( 5, 0 )
                            .tab( 5, 0 )
          ._( "@" );
        UT_EQ( "123451234 123  12   1    @", ms );

        ms._();                        ms.tab( 0 )._( '-' ); UT_EQ( " -"  , ms );
        ms._();                        ms.tab( 1 )._( '-' ); UT_EQ( " -"  , ms );
        ms._();                        ms.tab( 2 )._( '-' ); UT_EQ( "  -" , ms );
        ms._();                        ms.tab( 3 )._( '-' ); UT_EQ( "   -", ms );
        ms._();    ms._( 'x' );   ms.tab( 1 )._( '-' ); UT_EQ( "x -" , ms );
        ms._();    ms._( 'x' );   ms.tab( 2 )._( '-' ); UT_EQ( "x -" , ms );
        ms._();    ms._( 'x' );   ms.tab( 3 )._( '-' ); UT_EQ( "x  -", ms );

        // tabs with tab reference set (designed for multi line tabs)
        ms._()._("ABC").newLine();
        ms._( "12345" ).tab( 5 )
          ._( "1234" ) .tab( 5 )
          ._( "123" )  .tab( 5 )
          ._( "12" )   .tab( 5 )
          ._( "1" )    .tab( 5 )
                            .tab( 5 )
          ._( "@" );
        UT_EQ( "ABC" + CString.NEW_LINE_CHARS  + "12345     1234 123  12   1         @", ms );

        ms._()._("ABC").newLine();
        ms._( "12345" ).tab( 5, 0 )
          ._( "1234" ) .tab( 5, 0 )
          ._( "123" )  .tab( 5, 0 )
          ._( "12" )   .tab( 5, 0 )
          ._( "1" )    .tab( 5, 0 )
                            .tab( 5, 0 )
          ._( "@" );
        UT_EQ( "ABC" + CString.NEW_LINE_CHARS  + "123451234 123  12   1    @", ms );
    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test SearchCharsAndTrim
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void SearchAndReplace()
    {
        UT_INIT();
        
        AString ms= new AString();
        int result;


        // searchAndReplace null
        ms._()._("Hello");
        {
            ms.searchAndReplace( null, null );   UT_EQ( ms, "Hello" );
            ms.searchAndReplace( "l",  null );   UT_EQ( ms, "Heo" );
            ms.searchAndReplace( null, "xx" );   UT_EQ( ms, "Heo" );
        }

        ms._()._("Hello");
        {
            result= ms.searchAndReplace( null, null );  UT_EQ( ms, "Hello" );  UT_EQ( 0, result );
            result= ms.searchAndReplace( "l",  null );  UT_EQ( ms, "Heo"   );  UT_EQ( 2, result );
            result= ms.searchAndReplace( null, "xx" );  UT_EQ( ms, "Heo"   );  UT_EQ( 0, result );
        }

        // searchAndReplace
        ms._()._("Hello W!");
        {
            result= ms.searchAndReplace( "W!",    "world!"  );    UT_EQ( ms, "Hello world!"   );    UT_EQ( 1, result );
            result= ms.searchAndReplace( " ",    "* *"      );    UT_EQ( ms, "Hello* *world!" );    UT_EQ( 1, result );
            result= ms.searchAndReplace( "*",    "#", 0, 1  );    UT_EQ( ms, "Hello# *world!" );    UT_EQ( 1, result );
            result= ms.searchAndReplace( "*",    "#"        );    UT_EQ( ms, "Hello# #world!" );    UT_EQ( 1, result );
            result= ms.searchAndReplace( "#",    "$$$"      );    UT_EQ( ms, "Hello$$$ $$$world!"); UT_EQ( 2, result );
            result= ms.searchAndReplace( "$$$",    "*"      );    UT_EQ( ms, "Hello* *world!" );    UT_EQ( 2, result );
            result= ms.searchAndReplace( "*",    ""         );    UT_EQ( ms, "Hello world!"   );    UT_EQ( 2, result );
        }

        // replace one space by two spaces in a string of spaces
        ms._()._("     ");
        {
            result= ms.searchAndReplace( " ",    "  "  );        UT_EQ( ms, "          " );        UT_EQ( 5, result );
        }

        // replace ignoreCase
        {
            ms._()._("ABC"); result= ms.searchAndReplace( "abc", "xxx",0, 1, Case.SENSITIVE ); UT_EQ( ms, "ABC" ); UT_EQ( 0, result );
            ms._()._("ABC"); result= ms.searchAndReplace( "abc", "xxx",0, 1, Case.IGNORE    ); UT_EQ( ms, "xxx" ); UT_EQ( 1, result );
            ms._()._("ABC"); result= ms.searchAndReplace( "ABC", "xxx",0, 1, Case.IGNORE    ); UT_EQ( ms, "xxx" ); UT_EQ( 1, result );

            ms._()._("abc");         ms.searchAndReplace( "ABC", "xxx",0, 1, Case.SENSITIVE ); UT_EQ( ms, "abc" );
            ms._()._("abc");         ms.searchAndReplace( "ABC", "xxx",0, 1, Case.IGNORE    ); UT_EQ( ms, "xxx" );
            ms._()._("abc");         ms.searchAndReplace( "abc", "xxx",0, 1, Case.IGNORE    ); UT_EQ( ms, "xxx" );
        }

    }


    //---------------------------------------------------------------------------------------------------------
    //--- Test SearchAndReplace
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void Trim()
    {
        UT_INIT();
        
        AString ms= new AString();

        // ------------------ TrimAt ------------------
        {
            for (int i= -2; i < 6; i++ )
            {
                ms._()._("abc").trimAt( i );
                UT_EQ( "abc", ms );
            }

            ms._()._(" abc").trimAt( -1 );        UT_EQ( " abc"        , ms );
            ms._()._(" abc").trimAt(  0 );        UT_EQ( "abc"         , ms );
            ms._()._(" abc").trimAt(  1 );        UT_EQ( " abc"        , ms );

            ms._()._("abc ").trimAt( -1 );        UT_EQ( "abc "        , ms );
            ms._()._("abc ").trimAt(  0 );        UT_EQ( "abc "        , ms );
            ms._()._("abc ").trimAt(  2 );        UT_EQ( "abc "        , ms );
            ms._()._("abc ").trimAt(  3 );        UT_EQ( "abc"         , ms );
            ms._()._("abc ").trimAt(  4 );        UT_EQ( "abc "        , ms );

            ms._()._("a bc").trimAt( -1 );        UT_EQ( "a bc"        , ms );
            ms._()._("a bc").trimAt(  0 );        UT_EQ( "a bc"        , ms );
            ms._()._("a bc").trimAt(  1 );        UT_EQ( "abc"         , ms );
            ms._()._("a bc").trimAt(  2 );        UT_EQ( "a bc"        , ms );
            ms._()._("a bc").trimAt(  3 );        UT_EQ( "a bc"        , ms );

            ms._()._("a  c").trimAt( -1 );        UT_EQ( "a  c"        , ms );
            ms._()._("a  c").trimAt(  0 );        UT_EQ( "a  c"        , ms );
            ms._()._("a  c").trimAt(  1 );        UT_EQ( "ac"          , ms );
            ms._()._("a  c").trimAt(  2 );        UT_EQ( "ac"          , ms );
            ms._()._("a  c").trimAt(  3 );        UT_EQ( "a  c"        , ms );

            ms._()._(" \t abc \t ").trimStart();   UT_EQ( "abc \t "     , ms );
                                 ms.trimEnd();  UT_EQ( "abc"         , ms );
        }

        // ------------------ Trim ------------------
        {
            ms._()._(""          ).trim();        UT_EQ( ""            , ms );
            ms._()._(" "         ).trim();        UT_EQ( ""            , ms );
            ms._()._("  "        ).trim();        UT_EQ( ""            , ms );
            ms._()._("abc"       ).trim();        UT_EQ( "abc"         , ms );
            ms._()._("abc"       ).trim();        UT_EQ( "abc"         , ms );
            ms._()._(" abc"      ).trim();        UT_EQ( "abc"         , ms );
            ms._()._("  abc"     ).trim();        UT_EQ( "abc"         , ms );
            ms._()._(" \tabc"    ).trim();        UT_EQ( "abc"         , ms );
            ms._()._("\t abc"    ).trim();        UT_EQ( "abc"         , ms );
            ms._()._("abc\r\n"   ).trim();        UT_EQ( "abc"         , ms );
            ms._()._("\tabc\r\n" ).trim();        UT_EQ( "abc"         , ms );
            ms._()._("\tabc\rx\n").trim();       UT_EQ( "abc\rx"      , ms );
        }

    }

    @SuppressWarnings("static-method")
    @Test
    public void CompareTo()
    {
        UT_INIT();
        
        AString ms= new AString();
        int result;

        // null string comparison
        String  nullString= null;
        AString aString= new AString(0);
        UT_TRUE( aString.compareTo( nullString) == 0 );
        UT_TRUE( aString.equals( nullString) );

        aString._( "" );
        UT_TRUE( aString.compareTo( nullString) != 0 );
        UT_TRUE( !aString.equals( nullString) );

        aString.setBuffer( 0 );
        UT_TRUE( aString.compareTo( nullString) == 0 );
        UT_TRUE( aString.equals( nullString) );


        String t= "abcde";
        ms._( t );
        result= ms.compareTo( t      );                                                UT_EQ( t.compareTo( t ),                                result );
        result= ms.compareTo( t + "x"      );                                          UT_EQ( t.compareTo( t + "x" ),                          result );
        result= ms.compareTo( t.substring (0, t.length() -1 )  );                      UT_EQ( t.compareTo( t.substring (0, t.length() -1 ) ),  result );
        result= ms.compareTo( "pad" + t,  Case.SENSITIVE, 3            );   UT_EQ(  0,                                              result );
        result= ms.compareTo( "pad" + t,  Case.SENSITIVE, 3, 2         );   UT_EQ(  1,                                              result );
        result= ms.compareTo( "pad" + t,  Case.SENSITIVE, 3, 100, 0, 4 );   UT_EQ( -1,                                              result );
        result= ms.compareTo( "pad" + t,  Case.SENSITIVE, 3, 100, 0, 5 );   UT_EQ(  0,                                              result );
        result= ms.compareTo( t,          Case.SENSITIVE, 2,2,   2,2   );   UT_EQ(  0,                                              result );
        result= ms.compareTo( t,          Case.SENSITIVE, 2,2,   2,1   );   UT_EQ( -1,                                              result );
        result= ms.compareTo( t,          Case.SENSITIVE, 2,2,   2,3   );   UT_EQ(  1,                                              result );

        result= ms.compareTo( t,          Case.SENSITIVE, -2,5,   -2, 5 );                                   UT_EQ( 0, result );
        result= ms.compareTo( t,          Case.SENSITIVE,  2, Integer.MAX_VALUE,    2, Integer.MAX_VALUE );  UT_EQ( 0, result );
        result= ms.compareTo( t,          Case.SENSITIVE, -2, Integer.MAX_VALUE,   -2, Integer.MAX_VALUE );  UT_EQ( 0, result );
        result= ms.compareTo( t,          Case.SENSITIVE, -2, 100,            -2, 99 );                      UT_EQ( 0, result );
        result= ms.compareTo( t,          Case.SENSITIVE, -2, 5,                -2, Integer.MAX_VALUE );     UT_EQ( 1, result );
        result= ms.compareTo( t,          Case.SENSITIVE, -2, Integer.MAX_VALUE,    -2, 5 );                 UT_EQ(-1, result );

        // greater/smaller strings
        String greater=     "x";
        String greater2= "abcdef";
        String smaller=  "aaa";
        String smaller2= "abcd";
        result= ms.compareTo( greater  );   UT_EQ( t.compareTo( greater  ),  result );
        result= ms.compareTo( greater2 );   UT_EQ( t.compareTo( greater2 ),  result );
        result= ms.compareTo( smaller  );   UT_EQ( t.compareTo( smaller ),   result );
        result= ms.compareTo( smaller2 );   UT_EQ( t.compareTo( smaller2 ),  result );

        // ignore case
        String ut= t.toUpperCase();
        result= ms.compareTo( ut );                      UT_EQ( t.compareTo(ut),       result );
        result= ms.compareTo( ut, Case.SENSITIVE );               UT_EQ( t.compareTo(ut),       result );
        result= ms.compareTo( ut, Case.IGNORE );                UT_EQ( 0,                     result );
        result= ms.compareTo( ut, Case.SENSITIVE, 2,2,   2,2 );   UT_EQ( "cd".compareTo("CD"),  result );
        result= ms.compareTo( ut, Case.IGNORE,  2,2,   2,2 );   UT_EQ( 0,                     result );


        // we do just a very little testing with StringBuffer and AString parameter version of this function, as
        // the code differences are very small
        StringBuffer tSB= new StringBuffer(); tSB.append( t );
        result= ms.compareTo( tSB      );                            UT_EQ( 0,  result );
        result= ms.compareTo( tSB, Case.SENSITIVE, -5, 100, -10, 100      );  UT_EQ( 0,  result );
        result= ms.compareTo( tSB, Case.SENSITIVE,  2,   3,   2,   3      );  UT_EQ( 0,  result );

        AString tMS= new AString(); tMS._( t );
        result= ms.compareTo( tMS      );                            UT_EQ( 0,  result );
        result= ms.compareTo( tMS, Case.SENSITIVE, -5, 100, -10, 100      );  UT_EQ( 0,  result );
        result= ms.compareTo( tMS, Case.SENSITIVE,  2,   3,   2,   3      );  UT_EQ( 0,  result );

        // different ranges
        String r1=   "*ABCDEF*";
        String r2= "##*ABCDEF*##";
        ms._()._( r1 );
        result= ms.compareTo( r2, Case.SENSITIVE, 2,8          );        UT_EQ( 0, result );
        result= ms.compareTo( r2, Case.SENSITIVE, 3,6,   1,6      );     UT_EQ( 0, result );

    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test ConvertCase
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void ConvertCase()
    {
        UT_INIT();
        
        AString ms= new AString();
        String t;

        // test substring conversion
        t= "AAAbbb";
        ms._()._( t ).toLower(  0, 2 ); UT_TRUE( ms.equals( "aaAbbb" ) );
        ms._()._( t ).toLower( -2, 3 ); UT_TRUE( ms.equals( "aAAbbb" ) );
        ms._()._( t ).toLower( -2    ); UT_TRUE( ms.equals( "aaabbb" ) );

        ms._()._( t ).toUpper(  3, 2 ); UT_TRUE( ms.equals( "AAABBb" ) );
        ms._()._( t ).toUpper( -2, 6 ); UT_TRUE( ms.equals( "AAABbb" ) );
        ms._()._( t ).toUpper( -2    ); UT_TRUE( ms.equals( "AAABBB" ) );
    }

    //---------------------------------------------------------------------------------------------------------
    //--- Test ConvertIntegers
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void ConvertIntegers()
    {
        UT_INIT();
        
        AString a=  new AString();
        int[] posa= new int[1];
        int   posOrig;

        // parse on empty
        a._();
        {
            posOrig= posa[0]= 0;   UT_EQ(   0,  a.toInt( posa[0], posa )  );     UT_EQ( posa[0], posOrig );
            posOrig= posa[0]= -5;  UT_EQ(   0,  a.toInt( posa[0], posa )  );     UT_EQ( posa[0], posOrig );
        }

        // parse on non number
        a._()._( "Hello");
        {
            posOrig= posa[0]= 0;   UT_EQ(   0,  a.toInt( posa[0], posa )  );     UT_EQ( posa[0], posOrig );
            posOrig= posa[0]= -5;  UT_EQ(   0,  a.toInt( posa[0], posa )  );     UT_EQ( posa[0], posOrig );
            posOrig= posa[0]= 2;   UT_EQ(   0,  a.toInt( posa[0], posa )  );     UT_EQ( posa[0], posOrig );
        }

        // check that leading whitespaces do not move pointer
        a._()._( "   Hello");
        {
            posOrig= posa[0]= 0;   UT_EQ(   0,  a.toInt( posa[0], posa )  );     UT_EQ( posa[0], posOrig );
            posOrig= posa[0]= -5;  UT_EQ(   0,  a.toInt( posa[0], posa )  );     UT_EQ( posa[0], posOrig );
            posOrig= posa[0]= 2;   UT_EQ(   0,  a.toInt( posa[0], posa )  );     UT_EQ( posa[0], posOrig );
        }

        // parse integers
        {
            a._()._( "123456789");     posa[0]= 0;  UT_EQ( 123456789,  a.toInt( posa[0], posa ) ); UT_EQ(  0  + a.length() , posa[0] );
            a._()._( "  123");         posa[0]= 0;  UT_EQ( 123,        a.toInt( posa[0], posa ) ); UT_EQ(  0  + a.length() , posa[0] );
            a._()._( "  -23  ");       posa[0]= 0;  UT_EQ( -23,        a.toInt( posa[0], posa ) ); UT_EQ( -2  + a.length() , posa[0] );
            a._()._(   "123  ");       posa[0]= 0;  UT_EQ( 123,        a.toInt( posa[0], posa ) ); UT_EQ( -2  + a.length() , posa[0] );

            a._()._( "xxx123456789");  posa[0]= 3;  UT_EQ( 123456789,  a.toInt( posa[0], posa ) ); UT_EQ(  0  + a.length() , posa[0] );
            a._()._( "xxx  123");      posa[0]= 3;  UT_EQ( 123,        a.toInt( posa[0], posa ) ); UT_EQ(  0  + a.length() , posa[0] );
            a._()._( "xxx  123  ");    posa[0]= 3;  UT_EQ( 123,        a.toInt( posa[0], posa ) ); UT_EQ( -2  + a.length() , posa[0] );
            a._()._(   "xxx123  ");    posa[0]= 3;  UT_EQ( 123,        a.toInt( posa[0], posa ) ); UT_EQ( -2  + a.length() , posa[0] );
        }

        // append int
        {
            AString ms= new AString();
            int  i;

            ms._(); i=    0;                ms._( i      );   UT_EQ(                    "0", ms);
            ms._(); i=    0;                ms._( i,   1 );   UT_EQ(                    "0", ms);
            ms._(); i=    0;                ms._( i,   3 );   UT_EQ(                  "000", ms);
            ms._(); i=   10;                ms._( i      );   UT_EQ(                   "10", ms);
            ms._(); i=  100;                ms._( i,   1 );   UT_EQ(                  "100", ms);
            ms._(); i=  100;                ms._( i,   3 );   UT_EQ(                  "100", ms);
            ms._(); i=  100;                ms._( i,   4 );   UT_EQ(                 "0100", ms);
            ms._(); i=   23;                ms._( i      );   UT_EQ(                   "23", ms);
            ms._(); i=   99;                ms._( i,   5 );   UT_EQ(                "00099", ms);
            ms._(); i=   49;                ms._( i      );   UT_EQ(                   "49", ms);
            ms._(); i=   -5;                ms._( i,   5 );   UT_EQ(               "-00005", ms);
            ms._(); i=-5324;                ms._( i,   2 );   UT_EQ(                "-5324", ms);
            ms._(); i= Integer.MAX_VALUE;   ms._( i      );   UT_EQ(           "2147483647", ms);
            ms._(); i= Integer.MIN_VALUE;   ms._( i      );   UT_EQ(          "-2147483648", ms);
            ms._(); i= Integer.MAX_VALUE;   ms._( i , 12 );   UT_EQ(         "002147483647", ms);
            ms._(); i= Integer.MIN_VALUE;   ms._( i , 12 );   UT_EQ(        "-002147483648", ms);

            long l;
            ms._();  l= Long.MAX_VALUE;     ms._( l      );   UT_EQ(  "9223372036854775807", ms);
            ms._();  l= Long.MIN_VALUE;     ms._( l      );   UT_EQ( "-9223372036854775808", ms);
            ms._();  l= Long.MIN_VALUE +1;  ms._( l      );   UT_EQ( "-9223372036854775807", ms);
            ms._();  l= Long.MIN_VALUE
                                      + 75008;  ms._( l      );   UT_EQ( "-9223372036854700800", ms);
        }



    }

        void floatTest( double d, char decimalPoint, int minDigitsBeforeDot, int digitsAfterDot, String expectedString )
        {
           floatTest( d, decimalPoint, minDigitsBeforeDot, digitsAfterDot, expectedString, false );
        }

        void floatTest( double d, char decimalPoint, int minDigitsBeforeDot, int digitsAfterDot, String expectedString, boolean forceScientific )
        {
            AString ms= new AString();
            NumberFormat nf= new NumberFormat();
            nf.forceScientificFormat=   forceScientific;
            nf.decimalPointCharacter=   decimalPoint == '\0' ? NumberFormat.global.decimalPointCharacter : decimalPoint;
            nf.minIntegralDigits=       minDigitsBeforeDot;
            nf.fractionalDigits=        digitsAfterDot;

            ms._( d, nf );
            if ( expectedString != null )
            {
                UT_EQ( expectedString, ms );
            }

            double precision= digitsAfterDot < 0 ?  Math.pow( 10,  (d != 0.0 ? (int) Math.floor((Math.log10( d < 0 ? -d : d ) )) : 0 )   - 14 )
                                                 :  Math.pow( 10,  digitsAfterDot ) / 2.0;

            // check with system parsing (only if system specific decimal point format was given)
            if ( decimalPoint == '\0' )
            {
                double dSystem= 0.0;
                try
                {
                    java.text.NumberFormat jnf = java.text.NumberFormat.getInstance();
                    dSystem= jnf.parse( ms.toString() ).doubleValue();
                }
                catch( Exception e )
                {
                    UT_TRUE( digitsAfterDot == 0 ); // this is not really allowed in Java when parsing
                    dSystem= d;
                }
                UT_EQ( d, dSystem, precision );
            }

            // check with aworx parsing
            double dAWorx= ms.toFloat();
            UT_EQ( d, dAWorx, precision );
        }

    //---------------------------------------------------------------------------------------------------------
    //--- Test ConvertFloats
    //---------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-method")
    @Test
    public void ConvertFloats()
    {
        UT_INIT();
        
        AString a=  new AString();
        int[] posa= new int[1];
        int pos;
        int posOrig;

        // parse on empty
        a._();
        {
            posOrig= pos= posa[0]= 0;   UT_EQ( 0.0,        a.toFloat  ( pos  ) ,0 ); UT_EQ( posa[0], posOrig );
            posOrig= pos= posa[0]= -5;  UT_EQ( 0.0,        a.toFloat  ( pos  ) ,0 ); UT_EQ( posa[0], posOrig );
        }

        // parse on non number
        a._()._( "Hello");
        {
            posOrig= pos= posa[0]= 0;   UT_EQ( 0.0,        a.toFloat  ( pos  ) ,0 ); UT_EQ( posa[0], posOrig );
            posOrig= pos= posa[0]= -5;  UT_EQ( 0.0,        a.toFloat  ( pos  ) ,0 ); UT_EQ( posa[0], posOrig );
            posOrig= pos= posa[0]= 2;   UT_EQ( 0.0,        a.toFloat  ( pos  ) ,0 ); UT_EQ( posa[0], posOrig );
        }

        // check that leading whitespaces do not move pointer
        a._()._( "   Hello");
        {
            posOrig= pos= posa[0]= 0;   UT_EQ( 0.0,        a.toFloat  ( pos  ) ,0 ); UT_EQ( posa[0], posOrig );
            posOrig= pos= posa[0]= -5;  UT_EQ( 0.0,        a.toFloat  ( pos  ) ,0 ); UT_EQ( posa[0], posOrig );
            posOrig= pos= posa[0]= 2;   UT_EQ( 0.0,        a.toFloat  ( pos  ) ,0 ); UT_EQ( posa[0], posOrig );
        }

        // parse floats
        {
            NumberFormat nf= new NumberFormat();
            double delta= 0.00000001;
            a._()._( "12345.789");      UT_EQ( 12345.789,        a.toFloat( 0, posa, nf ), delta );   UT_EQ(  0  + a.length()  , posa[0] );

            a._()._( "");               UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  0                , posa[0] );
            a._()._( "-");              UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  0                , posa[0] );
            a._()._( "-X");             UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  0                , posa[0] );
            a._()._( ".");              UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  1                , posa[0] );
            a._()._( ".0");             UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  2                , posa[0] );
            a._()._( "0.");             UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  2                , posa[0] );
            a._()._( "+.");             UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  2                , posa[0] );
            a._()._( "-.");             UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  2                , posa[0] );
            a._()._( "-.0");            UT_EQ(   0.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  3                , posa[0] );
            a._()._( "-.08");           UT_EQ(  -0.08,           a.toFloat( 0, posa, nf ), delta );   UT_EQ(  4                , posa[0] );
            a._()._( "  123");          UT_EQ( 123.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  0  + a.length()  , posa[0] );
            a._()._( "+42");            UT_EQ(  42.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  3                , posa[0] );
            a._()._( "  +42  ");        UT_EQ(  42.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ( -2  + a.length()  , posa[0] );
            a._()._( "  +42.  ");       UT_EQ(  42.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ( -2  + a.length()  , posa[0] );
            a._()._( "-23");            UT_EQ( -23.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ(  3                , posa[0] );
            a._()._( "  -23  ");        UT_EQ( -23.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ( -2  + a.length()  , posa[0] );
            a._()._(   "123  ");        UT_EQ( 123.,             a.toFloat( 0, posa, nf ), delta );   UT_EQ( -2  + a.length()  , posa[0] );

            a._()._( "xxx123456789");   UT_EQ( 123456789.,       a.toFloat( 3, posa, nf ), delta );   UT_EQ(  0  + a.length()  , posa[0] );
            a._()._( "xxx  1.3");       UT_EQ( 1.3,              a.toFloat( 3, posa, nf ), delta );   UT_EQ(  0  + a.length()  , posa[0] );
            a._()._( "xxx  1.3  ");     UT_EQ( 1.3,              a.toFloat( 3, posa, nf ), delta );   UT_EQ( -2  + a.length()  , posa[0] );
            a._()._(   "xxx1.3  ");     UT_EQ( 1.3,              a.toFloat( 3, posa, nf ), delta );   UT_EQ( -2  + a.length()  , posa[0] );

            nf.decimalPointCharacters= "@".toCharArray();
            a._()._(   "-2495@321");    UT_EQ( -2495.321,        a.toFloat( 0, posa, nf ), delta );   UT_EQ(  0  + a.length()  , posa[0] );
        }

        // write floats
        {
            // digits after dot: -1
            floatTest(     3.0              ,'.' , -1, -1 ,      "3.0"                  );
            floatTest(     3.1              ,'.' , -1, -1 ,      "3.1"                  );
            floatTest(     3.14             ,'.' , -1, -1 ,      "3.14"                 );
            floatTest(     3.145            ,'.' , -1, -1 ,      "3.145"                );
            floatTest(     3.02             ,'.' , -1, -1 ,      "3.02"                 );
            floatTest(     3.001            ,'.' , -1, -1 ,      "3.001"                );
            floatTest(     3.09             ,'.' , -1, -1 ,      "3.09"                 );
            floatTest(     3.009            ,'.' , -1, -1 ,      "3.009"                );

            floatTest(     0.               ,'.' , -1, -1 ,      "0.0"                  );
            floatTest(     0.1              ,'.' , -1, -1 ,      "0.1"                  );
            floatTest(     0.14             ,'.' , -1, -1 ,      "0.14"                 );
            floatTest(     0.145            ,'.' , -1, -1 ,      "0.145"                );
            floatTest(     0.02             ,'.' , -1, -1 ,      "0.02"                 );
            floatTest(     0.001            ,'.' , -1, -1 ,      "0.001"                );
            floatTest(     0.09             ,'.' , -1, -1 ,      "0.09"                 );
            floatTest(     0.009            ,'.' , -1, -1 ,      "0.009"                );


            // forces scientific notation
            floatTest(     3.               ,'.' , -1, -1 ,      "3.0E00"              , true );
            floatTest(     3.1              ,'.' , -1, -1 ,      "3.1E00"              , true );
            floatTest(     3.14             ,'.' , -1, -1 ,      "3.14E00"             , true );
            floatTest(     3.145            ,'.' , -1, -1 ,      "3.145E00"            , true );
            floatTest(     3.02             ,'.' , -1, -1 ,      "3.02E00"             , true );
            floatTest(     3.001            ,'.' , -1, -1 ,      "3.001E00"            , true );
            floatTest(     3.09             ,'.' , -1, -1 ,      "3.09E00"             , true );
            floatTest(     3.009            ,'.' , -1, -1 ,      "3.009E00"            , true );

            floatTest(     3.145            ,'.' , -1,  1 ,      "3.1E00"              , true  );
            floatTest(     3.145            ,'.' ,  1,  1 ,      "3.1E00"              , true  );
            floatTest(     3.145            ,'.' ,  5,  3 ,  "00003.145E00"            , true  );
            floatTest(     123.12341234     ,'.' , -1,  6 ,      "1.231234E02"         , true  );
            floatTest(       0.00123123123  ,'.' , -1,  6 ,      "1.231231E-03"        , true  );
            floatTest(       0.321          ,'.' ,  0,  6 ,      "3.210000E-01"        , true  );
            floatTest(     3.789            ,'.' , -1,  1 ,      "3.8E00"              , true  );
            floatTest(     123.78978978     ,'.' , -1,  6 ,      "1.237898E02"         , true  );
            floatTest(       0.00789789789  ,'.' , -1,  6 ,      "7.897898E-03"        , true  );


            // many digits after dot
            //                  123456789 123456789 12345
            double RoundDown= 1.123412341234123412341234;
            floatTest(  RoundDown  ,'.' ,  1,  12 ,   "1.123412341234"       );
            floatTest(  RoundDown  ,'.' ,  1,  13 ,   "1.1234123412341"      );
            floatTest(  RoundDown  ,'.' ,  1,  14 ,   "1.12341234123412"     );
            floatTest(  RoundDown  ,'.' ,  1,  15 ,   "1.123412341234123"    );

                   RoundDown= 0.123412341234123412341234;
            floatTest(  RoundDown  ,'.' ,  1,  12 ,   "0.123412341234"       );
            floatTest(  RoundDown  ,'.' ,  1,  13 ,   "0.1234123412341"      );
            floatTest(  RoundDown  ,'.' ,  1,  14 ,   "0.12341234123412"     );
            floatTest(  RoundDown  ,'.' ,  1,  15 ,   "0.123412341234123"    );

            double RoundUp=   1.678967896789678967896789;
            floatTest(  RoundUp    ,'.' ,  1,  12 ,   "1.678967896790"       );
            floatTest(  RoundUp    ,'.' ,  1,  13 ,   "1.6789678967897"      );
            floatTest(  RoundUp    ,'.' ,  1,  14 ,   "1.67896789678968"     );
            floatTest(  RoundUp    ,'.' ,  1,  15 ,   "1.678967896789679"    );

                   RoundUp=   0.678967896789678967896789;
            floatTest(  RoundUp    ,'.' ,  1,  12 ,   "0.678967896790"       );
            floatTest(  RoundUp    ,'.' ,  1,  13 ,   "0.6789678967897"      );
            floatTest(  RoundUp    ,'.' ,  1,  14 ,   "0.67896789678968"     );
            floatTest(  RoundUp    ,'.' ,  1,  15 ,   "0.678967896789679"    );

            //            123456789 123456789                           123456789 12345
            floatTest(  0.00001234123412341234    ,'.' ,  1,  15 ,   "0.000012341234123"    );
            floatTest(  0.00000123412341234123    ,'.' ,  1,  15 ,   "0.000001234123412"    );
            floatTest(  0.00000012341234123412    ,'.' ,  1,  15 ,   "0.000000123412341"    );
            floatTest(  0.00000001234123412341    ,'.' ,  1,  15 ,   "0.000000012341234"    );
            floatTest(  0.00000000123412341234    ,'.' ,  1,  15 ,   "0.000000001234123"    );
            floatTest(  0.00000000012341234123    ,'.' ,  1,  15 ,   "0.000000000123412"    );
            floatTest(  0.00000000001234123412    ,'.' ,  1,  15 ,   "0.000000000012341"    );
            floatTest(  0.00000000000123412341    ,'.' ,  1,  15 ,   "0.000000000001234"    );
            floatTest(  0.00000000000012341234    ,'.' ,  1,  15 ,   "0.000000000000123"    );
            floatTest(  0.00000000000001234123    ,'.' ,  1,  15 ,   "0.000000000000012"    );
            floatTest(  0.00000000000000123412    ,'.' ,  1,  15 ,   "0.000000000000001"    );
            floatTest(  0.00000000000000012341    ,'.' ,  1,  15 ,   "0.000000000000000"    );
            floatTest(  0.00000000000000001234    ,'.' ,  1,  15 ,   "0.000000000000000"    );

            floatTest(  0.00006789678967896789    ,'.' ,  1,  15 ,   "0.000067896789679"    );
            floatTest(  0.00000678967896789678    ,'.' ,  1,  15 ,   "0.000006789678968"    );
            floatTest(  0.00000067896789678967    ,'.' ,  1,  15 ,   "0.000000678967897"    );
            floatTest(  0.00000006789678967896    ,'.' ,  1,  15 ,   "0.000000067896790"    );
            floatTest(  0.00000000678967896789    ,'.' ,  1,  15 ,   "0.000000006789679"    );
            floatTest(  0.00000000067896789678    ,'.' ,  1,  15 ,   "0.000000000678968"    );
            floatTest(  0.00000000006789678967    ,'.' ,  1,  15 ,   "0.000000000067897"    );
            floatTest(  0.00000000000678967896    ,'.' ,  1,  15 ,   "0.000000000006790"    );
            floatTest(  0.00000000000067896789    ,'.' ,  1,  15 ,   "0.000000000000679"    );
            floatTest(  0.00000000000006789678    ,'.' ,  1,  15 ,   "0.000000000000068"    );
            floatTest(  0.00000000000000678967    ,'.' ,  1,  15 ,   "0.000000000000007"    );
            floatTest(  0.00000000000000067896    ,'.' ,  1,  15 ,   "0.000000000000001"    );
            floatTest(  0.00000000000000006789    ,'.' ,  1,  15 ,   "0.000000000000000"    );
            floatTest(  0.00000000000000000678    ,'.' ,  1,  15 ,   "0.000000000000000"    );

            // digits after dot: 0 or positive
            floatTest(  300.00050000001     ,'.' ,  1,  2 ,   "300.00"               );
            floatTest(  300.00050000001     ,'.' ,  1,  3 ,   "300.001"              );
            floatTest(  300.00050000001     ,'.' ,  1,  4 ,   "300.0005"             );
            floatTest(  300.00050000001     ,'.' ,  1,  5 ,   "300.00050"            );
            floatTest(  300.00050000001     ,'.' ,  1,  6 ,   "300.000500"           );
            floatTest(    5.01              ,'.' ,  1,  3 ,      "5.010"             );


            floatTest(     0.5              ,'.' ,  0,  0 ,       "."                );
            floatTest(     0.5              ,'.' ,  0,  1 ,       ".5"               );
            floatTest(     0.5              ,'.' ,  1,  0 ,      "0."                );
            floatTest(     0.5              ,'.' ,  1,  1 ,      "0.5"               );
            floatTest(     0.5              ,'.' ,  2,  2 ,     "00.50"              );
            floatTest(     0.5              ,'.' ,  3,  3 ,    "000.500"             );
            floatTest(     0.54             ,'.' ,  3,  3 ,    "000.540"             );
            floatTest(     0.543            ,'.' ,  3,  3 ,    "000.543"             );
            floatTest(     0.5432           ,'.' ,  3,  3 ,    "000.543"             );


            floatTest(    23.37             ,'.' ,  2,  1 ,     "23.4"               );
            floatTest(     1.5              ,'.' ,  0,  0 ,      "1."                );
            floatTest(     1.9              ,'.' ,  0,  0 ,      "2."                );
            floatTest(     1.9              ,'.' ,  0,  4 ,      "1.9000"            );
            floatTest(     1.500001         ,'.' ,  0,  0 ,      "2."                );
            floatTest(     1.54             ,'.' ,  3,  3 ,    "001.540"             );
            floatTest(     1.543            ,'.' ,  3,  3 ,    "001.543"             );
            floatTest(     1.5432           ,'.' ,  3,  3 ,    "001.543"             );
            floatTest(    23.37             ,'.' ,  2,  3 ,     "23.370"             );
            floatTest(    12.345            ,'.' ,  0,  3 ,     "12.345"             );
            floatTest(    12.345            ,'.' ,  5,  1 ,  "00012.3"               );
            floatTest(    12.36789          ,'.' ,  5,  1 ,  "00012.4"               );
            floatTest(     1.5              ,'.' ,  0,  0 ,      "1."                );
            floatTest(     1.5              ,'.' ,  1,  0 ,      "1."                );
            floatTest(     1.5              ,'.' ,  0,  1 ,      "1.5"               );
            floatTest(     1.5              ,'.' ,  1,  1 ,      "1.5"               );
            floatTest(     1.5              ,'.' ,  2,  2 ,     "01.50"              );
            floatTest(     1.5              ,'.' ,  3,  3 ,    "001.500"             );
            floatTest(     1.54             ,'.' ,  3,  3 ,    "001.540"             );
            floatTest(     1.543            ,'.' ,  3,  3 ,    "001.543"             );
            floatTest(     1.5432           ,'.' ,  3,  3 ,    "001.543"             );


            floatTest(     0.0              ,'.' ,  0,  0 ,       "."                );
            floatTest(     0.0              ,'.' ,  1,  0 ,      "0."                );
            floatTest(     0.0              ,'.' ,  0,  1 ,       ".0"               );
            floatTest(     0.0              ,'.' ,  1,  1 ,      "0.0"               );
            floatTest(     0.0              ,'.' ,  2,  2 ,     "00.00"              );
            floatTest(    10.0              ,'.' ,  2,  2 ,     "10.00"              );
            floatTest(    23.37             ,'.' ,  2,  1 ,     "23.4"               );
            floatTest(    23.37             ,'.' ,  2,  3 ,     "23.370"             );
            floatTest(    12.345            ,'.' ,  0,  3 ,     "12.345"             );
            floatTest( -   5.1              ,'.' ,  5,  0 , "-00005."                );
            floatTest( -   5.1              ,'.' ,  5,  1 , "-00005.1"               );
            floatTest( -  53.24             ,'.' ,  2,  2 ,    "-53.24"              );
            floatTest( -   0.2              ,'.' ,  1,  3 ,     "-0.200"             );
            floatTest( -   0.6              ,'.' ,  1,  3 ,     "-0.600"             );
            floatTest( -   0.999            ,'.' ,  1,  3 ,     "-0.999"             );

            floatTest(     0.999            ,'.' ,  1,  3 ,      "0.999"             );
            floatTest(     0.99             ,'.' ,  1,  3 ,      "0.990"             );
            floatTest(     0.999            ,'.' ,  1,  3 ,      "0.999"             );
            floatTest(     0.9999           ,'.' ,  1,  3 ,      "1.000"             );
            floatTest(     0.0999           ,'.' ,  2,  3 ,     "00.100"             );
            floatTest(     0.99999          ,'.' ,  1,  3 ,      "1.000"             );
            floatTest(     0.099            ,'.' ,  2,  3 ,     "00.099"             );
            floatTest(     0.0999           ,'.' ,  1,  3 ,      "0.100"             );
            floatTest(     0.09999          ,'.' ,  2,  3 ,     "00.100"             );
            floatTest(     0.999            ,'.' ,  2,  3 ,     "00.999"             );

            floatTest(     0.0099           ,'.' ,  1,  3 ,      "0.010"             );
            floatTest(     0.00999          ,'.' ,  1,  3 ,      "0.010"             );
            floatTest(     0.009            ,'.' ,  1,  3 ,      "0.009"             );
            floatTest(     0.00099          ,'.' ,  1,  3 ,      "0.001"             );
            floatTest(     0.00009          ,'.' ,  1,  3 ,      "0.000"             );

            floatTest(     1.0099           ,'.' ,  1,  3 ,      "1.010"             );
            floatTest(     1.00999          ,'.' ,  1,  3 ,      "1.010"             );
            floatTest(     1.009            ,'.' ,  1,  3 ,      "1.009"             );
            floatTest(     1.00099          ,'.' ,  1,  3 ,      "1.001"             );
            floatTest(     1.00009          ,'.' ,  1,  3 ,      "1.000"             );

            floatTest(     0.00050          ,'.' ,  1,  3 ,      "0.000"             );
            floatTest(     0.00051          ,'.' ,  1,  3 ,      "0.001"             );
            floatTest(     0.00050000001    ,'.' ,  1,  3 ,      "0.001"             );
            floatTest(  300.00050000001     ,'.' ,  1,  3 ,    "300.001"             );

            floatTest(     0.10099          ,'.' ,  1,  4 ,     "0.1010"             );
            floatTest(     0.10099          ,'.' ,  1,  4 ,     "0.1010"             );
            floatTest(     0.00099          ,'.' ,  1,  4 ,     "0.0010"             );
            floatTest(     1.099            ,'.' ,  1,  1 ,     "1.1"                );
            floatTest(     0.00999          ,'.' ,  1,  4 ,     "0.0100"             );


            NumberFormat.global.setFromLocale();

            // roundtrip -3.0 to 3.0 step 0.001
            {
                for (int digits= -1 ; digits < 5 ; digits++ )
                {
                    double d= -3.0;
                    double end= 3.0;
                    double inc= 0.001;
                    while ( d <= end )
                    {
                        floatTest( d, '\0' , -1, digits, null );
                        d+= inc;
                    }
                }
            }

            // roundtrip e-20 to e+20
            {
                for ( int exp= -200 ; exp <= 200 ; exp+=1 )
                {
                    double expFactor= Math.pow( 10, exp );

                    for ( int t= 0 ; t <= 1000 ; t++)
                    {
                        // create a random number of type r.rrrrr *10^exp
                        double d= Math.random();
                        while ( d < 1.0 )
                            d*= 10.0;
                        d*= expFactor;
                        if ( (t & 1  ) != 0 )
                            d= -d;

                        floatTest( d, '\0' , -1, -1, null );
                    }
                }
            }

            // special exponent symbol
            {
                NumberFormat nf= new NumberFormat();
                nf.decimalExponentSeparator= "*10^".toCharArray();
                AString as= new AString();
                double v= 7.5E42;
                as._( v, nf );
                UT_EQ( as, "7.5*10^42" );
                double back= as.toFloat( 0, null, nf );

                UT_EQ( v, back, 0.0000000001 );
            }

        }
    }

}
