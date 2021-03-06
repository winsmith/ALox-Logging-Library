﻿// #################################################################################################
//  aworx::lox - ALox Logging Library
//
//  (c) 2013-2016 A-Worx GmbH, Germany
//  Published under MIT License (Open Source License, see LICENSE.txt)
// #################################################################################################
/** @file */ //<- needed for Doxygen include

#ifndef HPP_ALOX_SYMBOLS
#define HPP_ALOX_SYMBOLS 1


// #################################################################################################
// Definition of doxygen groups
// - GrpALoxCompilerSymbols
// - GrpALoxCodeSelectorSymbols
// #################################################################################################
/**
 * @defgroup GrpALoxCompilerSymbols ALox Compilation Symbols
 *
 * Symbols (C++ preprocessor macros) listed here, support adding or removing features to ALox when
 * compiling the library and any code unit that uses ALox.
 * These symbols must be used only within makefiles or IDE project files to be passed to the
 * C++ compiler (normally -D option). In other words: they must not be defined or undefined within
 * the source code that uses the ALox library.<p>
 *
 * For most of them, a version with a suffix "_ON" and "_OFF" exists. If both are set at the same
 * time, a compiler error is generated by ALox header files. If none of them is passed to the
 * compiler, a default value is assumed.<p>
 *
 * As a convenience, to get default behavior, no symbol needs to be passed to the compiler.<p>
 *
 * ALox inherits further compiler symbols from underlying library ALIB. These are documented in:
 * \ref GrpALibCompilerSymbols.
 *
 * \note Some of the compilation symbols are changing the definition of classes and methods which
 *       makes binaries incompatible with binaries that do not use the same setting for each of
 *       these symbols.<br>
 *       Symbols that have to be kept equal across compilation units are denoted in this
 *       documentation.
 *
 * @{ @}
 */

/**
 * @defgroup GrpALoxCodeSelectorSymbols     ALox Symbols to Select Code Fragments
 *
 *  Symbols (C++ preprocessor macros) listed here, are mostly generated out of
 * \ref GrpALoxCompilerSymbols. In this case, their name corresponds to these symbols, excluding
 * the suffixes "_ON" and "_OFF".<p>
 *
 * These symbols must not be passed to the compiler and must also not be set manually from within
 * code entities that use ALox libraries. They can be used in code entities that use ALox to select
 * code depending on the same settings of the ALox library compilation. Selection takes place,
 * using preprocessor directives:<p>
 * \code{.cpp}
 * #if defined(ALOX_XYZ_SELECTOR)
 *   // your selective code
 * #endif
 * \endcode
 *
 * ALox inherits further compiler symbols from underlying library ALIB. These are documented in:
 * \ref GrpALibCodeSelectorSymbols.
 *
 * (Besides ALox specific symbols of that type, ALox internally uses also platform specific symbols
 * to select code, e.g. \_\_GLIBCXX\_\_, _MSC_VER, etc.).
 * @{ @}
 */



// #################################################################################################
// DBG_LOG / REL_LOG
// #################################################################################################


/**
 * @addtogroup GrpALoxCompilerSymbols
 * @{ \def  ALOX_DBG_LOG_ON
 *  Explicitly enables debug logging by defining the code selection symbol \ref ALOX_DBG_LOG.
 *
 *  Must not be used in conjunction with \ref ALOX_DBG_LOG_OFF.<p>
 *
 *  If neither <b>ALOX_DBG_LOG_ON</b> nor <b>ALOX_DBG_LOG_OFF</b> are specified, ALox will determine the
 *  inclusion of debug <em>Log Statements</em> by evaluating the (partly standardized) NDEBUG macro that is
 *  used by most 'assert' libraries. Hence, if NDEBUG is set, ALOX_DBG_LOG will not be defined.<p>
 *
 * @}
 *
 * @addtogroup GrpALoxCompilerSymbols
 * @{ \def  ALOX_DBG_LOG_OFF
 *  Explicitly disables debug logging by leaving the code selection symbol \ref ALOX_DBG_LOG undefined.
 *  Any debug <em>Log Statements</em> that are embedded in code by using the \ref GrpMacrosDebugLog, will
 *  get pruned.<p>
 *
 *  Must not be used in conjunction with \ref ALOX_DBG_LOG_ON.<p>
 *
 *  If neither <b>ALOX_DBG_LOG_ON</b> nor <b>ALOX_DBG_LOG_OFF</b> are specified, ALox will determine the
 *  inclusion of debug <em>Log Statements</em> by evaluating the (partly standardized) NDEBUG macro that is
 *  used by most 'assert' libraries. Hence, if NDEBUG is set, ALOX_DBG_LOG will not be defined.
 * @}
 *
 * @addtogroup GrpALoxCodeSelectorSymbols
 * @{ \def  ALOX_DBG_LOG
 *  If defined, debug <em>Log Statements</em> are enabled. Hence, users of ALox can conditionally
 *  compile special debug logging code that normally belongs to corresponding
 *  debug logging statements.
 *  This is useful for example, to conditionally compile code that calculates and gathers
 *  information to do some more complex log output.
 *
 *  See \ref ALOX_DBG_LOG_ON and \ref ALOX_DBG_LOG_OFF for information about how to manipulate
 *  this symbol.
 * @}
 *
*/
#if defined(ALOX_DBG_LOG)
    #error "ALOX_DBG_LOG must not be set from outside"
#endif

#if defined(ALOX_DBG_LOG_OFF) && defined(ALOX_DBG_LOG_ON)
    #error "ALOX_DBG_LOG_OFF / ALOX_DBG_LOG_ON are both set"
#endif

#if defined(ALOX_DBG_LOG_ON) || ( !defined(ALOX_DBG_LOG_OFF) && !defined(NDEBUG) )
    #define ALOX_DBG_LOG
#endif


/**
 * @addtogroup GrpALoxCompilerSymbols
 * @{ \def  ALOX_REL_LOG_ON
 *  Explicitly enables release logging by defining the code selection symbol \ref ALOX_REL_LOG.
 *
 *  Must not be used in conjunction with \ref ALOX_REL_LOG_OFF.<p>
 *
 *  If neither <b>ALOX_REL_LOG_ON</b> nor <b>ALOX_REL_LOG_OFF</b> are specified, release logging implemented by
 *  ALox release log macros is enabled. Therefore, this symbol is provided only for completeness.
 *  There is no real need to pass it to the compiler.<p>
 *
 *  Remember, that any release logging that is not implemented using the ALox release logging macros,
 *  but instead using the ALox classes in the source code directly, is not affected by this
 *  macro.
 *
 * @}
 *
 * @addtogroup GrpALoxCompilerSymbols
 * @{ \def  ALOX_REL_LOG_OFF
 *  Explicitly disables release logging by leaving the code selection symbol \ref ALOX_REL_LOG
 *  undefined.
 *  Any release <em>Log Statements</em> that are embedded in code by using the \ref GrpMacrosReleaseLog, will
 *  get pruned.<p>
 *
 *  Must not be used in conjunction with \ref ALOX_REL_LOG_ON.<p>
 *
 *  If neither <b>ALOX_REL_LOG_ON</b> nor <b>ALOX_REL_LOG_OFF</b> are specified, release logging implemented by
 *  ALox release log macros is enabled. Therefore, this symbol is provided only for completeness.
 *  There is no real need to pass it to the compiler.<p>
 *
 *  Remember, that any release logging that is not implemented using the ALox release logging macros,
 *  but instead using the ALox classes in the source code directly, is not affected by this
 *  macro.
 * @}
 *
 * @addtogroup GrpALoxCodeSelectorSymbols
 * @{ \def  ALOX_REL_LOG
 *  If defined, release <em>Log Statements</em> are enabled. Hence, users of ALox can conditionally
 *  compile special debug logging code that normally belongs to corresponding
 *  debug logging statements.
 *  This is useful for example, to conditionally compile code that calculates and gathers
 *  information to do some more complex log output.
 *
 *  See \ref ALOX_REL_LOG_ON and \ref ALOX_REL_LOG_OFF for information about how to manipulate
 *  this symbol.
 * @}
 *
*/


#if defined(ALOX_REL_LOG)
    #error "ALOX_REL_LOG must not be set from outside"
#endif

#if defined(ALOX_REL_LOG_OFF) && defined(ALOX_REL_LOG_ON)
    #error "ALOX_REL_LOG_OFF / ALOX_REL_LOG_ON are both set"
#endif

#if !defined(ALOX_REL_LOG_OFF)
    #define ALOX_REL_LOG
#endif


// #################################################################################################
// DBG_LOG_CI / REL_LOG_CI
// #################################################################################################

/**
 * @addtogroup GrpALoxCompilerSymbols
 * @{ \def  ALOX_DBG_LOG_CI_ON
 *  Explicitly enables the generation of scope information using the built-in preprocessor
 *  macros like "__FILE__" and "__LINE__" for ALox debug logging statements<p>
 *
 *  Passing this Symbol to the compiler, makes ALox define the code selection symbol
 *  \ref ALOX_DBG_LOG_CI.
 *  Must not be used in conjunction with \ref ALOX_DBG_LOG_CI_OFF.<p>
 *  If neither <b>ALOX_DBG_LOG_CI_ON</b> nor <b>ALOX_DBG_LOG_CIO_OFF</b> are specified, ALox will enable
 *  caller source information for debug logging. In other words, <b>ALOX_DBG_LOG_CI_ON</b> is the
 *  default and passing the symbol to the compiler is not mandatory.<p>
 *
 *  \note The ALox <em>Scope Domain</em> mechanism as well as the ALox features \e Lox.Once and
 *  <em>Log Data</em> rely on caller information.
 *  These mechanisms are therefore not available to debug logging unless this symbol is set.
 * @}
 *
 *
 * @addtogroup GrpALoxCompilerSymbols
 * @{ \def  ALOX_DBG_LOG_CI_OFF
 *  Disables the generation of scope information using the built-in preprocessor
 *  macros like "__FILE__" and "__LINE__" for ALox debug logging statements<p>
 *
 *  Passing this Symbol to the compiler, makes ALox to leave the code selection symbol
 *  \ref ALOX_DBG_LOG_CI undefined.<br>
 *  Must not be used in conjunction with \ref ALOX_DBG_LOG_CI_ON.<p>
 *  If neither <b>ALOX_DBG_LOG_CI_ON</b> nor <b>ALOX_DBG_LOG_CIO_OFF</b> are specified, then
 *  \ref ALOX_DBG_LOG_CI_ON is the default.<p>
 *
 *  \note The ALox Scope Domain mechanism as well as the ALox features \e Lox.Once and
 *  <em>Log Data</em> rely on caller information.
 *  These mechanisms are therefore not available to debug logging when this symbol is set.
 * @}
 *
 * @addtogroup GrpALoxCodeSelectorSymbols
 * @{ \def  ALOX_DBG_LOG_CI
 *  If defined, scope information is passed to ALox on invocation of debug
 *  <em>Log Statements</em>.
 *  Hence, users of ALox can conditionally compile special debug logging code that normally
 *  belongs to corresponding debug logging statements based on this symbol. A sample would be
 *  the definition of different log line meta information formats, depending on the availability
 *  of scope information.<p>
 *
 *  \note The ALox <em>Scope Domain</em> mechanism as well as the ALox features \e Lox.Once and
 *  <em>Log Data</em> rely on caller information.
 *  These mechanisms are therefore not available to debug logging unless this symbol is set.
 *
 *
 *  See \ref ALOX_DBG_LOG_CI_ON and \ref ALOX_DBG_LOG_CI_OFF for information about how to manipulate
 *  this symbol.
 * @}
 *
*/

#if defined(ALOX_DBG_LOG_CI)
    #error "ALOX_DBG_LOG_CI must not be set from outside"
#endif

#if defined(ALOX_DBG_LOG_CI_OFF) && defined(ALOX_DBG_LOG_CI_ON)
    #error "ALOX_DBG_LOG_CI_OFF / ALOX_DBG_LOG_CI_ON are both set"
#endif

#if defined( ALOX_DBG_LOG ) && !defined( ALOX_DBG_LOG_CI_OFF )
    #define ALOX_DBG_LOG_CI
#endif


/**
 * @addtogroup GrpALoxCompilerSymbols
 * @{ \def  ALOX_REL_LOG_CI_ON
 *  Explicitly enables the generation of scope information using the built-in preprocessor
 *  macros like "__FILE__" and "__LINE__" for ALox release logging statements<p>
 *
 *  Passing this Symbol to the compiler, makes ALox define the code selection symbol
 *  \ref ALOX_REL_LOG_CI.<br>
 *  Must not be used in conjunction with \ref ALOX_REL_LOG_CI_OFF.<p>
 *  If neither <b>ALOX_REL_LOG_CI_ON</b> nor <b>ALOX_REL_LOG_CIO_OFF</b> are specified, ALox will disable
 *  caller source information for release logging.
 *
 *  Enabling source info for for release logging is seldom wanted. Release executables should
 *  not generate log output that an end user is not able to understand.
 *  It can make sense however, if release log information from the field goes back to the
 *  software development team.
 *  Furthermore, it can be very helpful to enable scope information for release <em>Log Statements</em>
 *  in the debug version of a compilation unit.<p>
 *
 *  \note The ALox <em>Scope Domain</em> mechanism as well as the ALox features \e Lox.Once and
 *  <em>Log Data</em> rely on caller information.
 *  These mechanisms are therefore not available to debug logging unless this symbol is set.
 *
 * @}
 *
 *
 * @addtogroup GrpALoxCompilerSymbols
 * @{ \def  ALOX_REL_LOG_CI_OFF
 *  Disables the generation of scope information using the built-in preprocessor
 *  macros like "__FILE__" and "__LINE__" for ALox release logging statements<p>
 *
 *  Passing this Symbol to the compiler, makes ALox to leave the code selection symbol
 *  \ref ALOX_REL_LOG_CI undefined.<br>
 *  Must not be used in conjunction with \ref ALOX_REL_LOG_CI_ON.<p>
 *  If neither <b>ALOX_REL_LOG_CI_ON</b> nor <b>ALOX_REL_LOG_CIO_OFF</b> are specified, then
 *  \ref ALOX_REL_LOG_CI_OFF is the default.  In other words: passing the symbol to the
 *  compiler is not mandatory.<p>
 *
 *  \note The ALox <em>Scope Domain</em> mechanism as well as the ALox features \e Lox.Once and
 *  <em>Log Data</em> rely on caller information.
 * These mechanisms are therefore not available to release logging unless
 *  \ref ALOX_REL_LOG_CI_ON is set.
 *
 * @}
 *
 * @addtogroup GrpALoxCodeSelectorSymbols
 * @{ \def  ALOX_REL_LOG_CI
 *  If defined, scope information is passed to ALox on invocation of release log
 *  statements when using the \ref GrpMacrosReleaseLog.<br>
 *  Hence, users of ALox can conditionally compile special debug logging code that normally
 *  belongs to corresponding debug logging statements based on this symbol. A sample would be
 *  the definition of different log line meta-information formats, depending on the availability of
 *  scope information.<p>
 *
 *  \note The ALox <em>Scope Domain</em> mechanism as well as the ALox features \e Lox.Once and
 *  <em>Log Data</em> rely on caller information.
 *  These mechanisms are therefore not available to debug logging unless this symbol is set.
 *
 *  See \ref ALOX_REL_LOG_CI_ON and \ref ALOX_REL_LOG_CI_OFF for information about how to manipulate
 *  this symbol.
 * @}
 *
*/

#if defined(ALOX_REL_LOG_CI)
    #error "ALOX_REL_LOG_CI must not be set from outside"
#endif

#if defined(ALOX_REL_LOG_CI_OFF) && defined(ALOX_REL_LOG_CI_ON)
    #error "ALOX_REL_LOG_CI_OFF / ALOX_REL_LOG_CI_ON are both set"
#endif

#if defined( ALOX_REL_LOG ) && defined( ALOX_REL_LOG_CI_ON )
    #define ALOX_REL_LOG_CI
#endif



#endif // HPP_ALOX_SYMBOLS
