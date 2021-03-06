﻿// #################################################################################################
//  cs.aworx.lox.unittests - ALox Logging Library
//
//  (c) 2013-2016 A-Worx GmbH, Germany
//  Published under MIT License (Open Source License, see LICENSE.txt)
// #################################################################################################
using System;
using System.Threading;
using System.Xml.Linq;
using ut_cs_aworx;

#if ALIB_MONO_DEVELOP
    using NUnit.Framework;
#endif
#if ALIB_VSTUDIO
    using Microsoft.VisualStudio.TestTools.UnitTesting;
#endif

using cs.aworx.lib;
//! [DOC_SAMPLES_ALOX_ESC_USING]
using cs.aworx.lox;
//! [DOC_SAMPLES_ALOX_ESC_USING]
using cs.aworx.lox.loggers;
using cs.aworx.lox.tools;
using System.Collections.Generic;

#if !WINDOWS_PHONE
    using cs.aworx.lox.tools.json;
#endif

namespace ut_cs_aworx_lox
{
    #if ALIB_MONO_DEVELOP
        [TestFixture ()]
    #endif
    #if ALIB_VSTUDIO
        [TestClass]
    #endif
    public class CS_ALox_Documentation_Samples   : AUnitTest
    {
        // used with unit test Log_ScopeInfoCacheTest
        public static void ScopeInfoCacheTest() { Log.Info("Test method of CS_ALox_Documentation_Samples"); }



/** ********************************************************************************************
 * class ESC (see also using statement above)
 **********************************************************************************************/
void docSampleESC()
{
//! [DOC_SAMPLES_ALOX_ESC]
Log.Info( "The result is " + ESC.RED + "42" );
//! [DOC_SAMPLES_ALOX_ESC]
}



    /** ********************************************************************************************
     * Log_JSON 
     **********************************************************************************************/
    #if !WINDOWS_PHONE
        #if ALIB_MONO_DEVELOP
            [Test ()]
        #endif
        #if ALIB_VSTUDIO
            [TestMethod]
            #if !WINDOWS_PHONE
                [TestCategory("CS_ALox_Documentation_Samples")]
            #endif
        #endif

        public void UT_Doc_Samples()
        {
            UT_INIT();

            docSampleESC();
        }
    #endif
    } // class
} // namespace
