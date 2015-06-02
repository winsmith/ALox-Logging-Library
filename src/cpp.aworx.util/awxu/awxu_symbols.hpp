// #################################################################################################
//  aworx::util - Classes we need
//
//  (c) 2013-2015 A-Worx GmbH, Germany
//  Published under MIT License (Open Source License, see LICENSE.txt)
// #################################################################################################

#ifndef HPP_AWORX_UTIL_AWXU_CONDCOMPILE
#define HPP_AWORX_UTIL_AWXU_CONDCOMPILE 1

#if !defined(HPP_AWORX_UTIL_AWXU)
    #error "include awxu.hpp instead of awxu_cond_comp_symbols.hpp"
#endif


/**
 * @defgroup GrpAWXUCompilerSymbols AWXU Compilation Symbols
 *
 * Symbols (C++ preprocessor macros) listed here, support adding or removing features to AWXU when
 * compiling the library and any code unit that uses AWXU.
 * These symbols must be used <em>only within makefiles or IDE project files to be passed to the
 * C++ compiler (!) </em> (normally -D option). In other words: they must not be defined or undefined
 * within the source code that uses the AWXU library.<p>
 *
 * AWXU Symbols are prefixed with <em>AWXU_</em>.
 * In addition to the AWXU specific symbols, the AWXU package and headers files also support symbols
 * that are not specific to AWXU functionality but are general symbols used in many A-Worx projects.
 * Those symbols are prefixed <em>AWORX_</em>.
 *
 * For most of the symbols, a version with a postfix <em>_ON</em> and for some also one with the postfix
 * <em>_OFF</em> exists. These symbol decide about the definition, respectively the avoidance of the
 * definition of a corresponding <em>code selection symbol</em> that is named the same without the
 * postfix.<br>
 * If both are set at the same time, a compiler error is generated by AWXU header files.
 * If none of them is passed to the compiler, a default value is assumed.<p>
 *
 * As a convenience, to get default behavior, no symbol needs to be passed to the compiler.<p>
 *
 * \note Some of the compilation symbols are changing the definition of classes and methods which
 *       makes binaries incompatible with binaries that do not use the same setting for each of
 *       these symbols.<br>
 *       Symbols that have to be kept equal across compilation units are denoted in this
 *       documentation.
 * @{ @}
*/

/**
 * @defgroup GrpAWXUCodeSelectorSymbols AWXU Symbols To Select Code Fragments
 *
 *  Symbols (C++ preprocessor macros) listed here, are mostly generated out of
 * \ref GrpAWXUCompilerSymbols. In this case, their name corresponds to these symbols, excluding
 * the postfixes <em>_ON</em> and <em>_OFF</em>.<p>
 *
 * These symbols must not be passed to the compiler and must also not be set manually from within
 * code entities that use AWXU libraries. They can be used in code entities that use AWXU to select
 * code depending on the same settings of the AWXU library compilation. Selection takes place,
 * using preprocessor directives:<p>
 * \code{.cpp}
 * #if defined(AWXU_XYZ_SELECTOR)
 *   // your selective code
 * #endif
 * \endcode
 *
 * \note Besides AWXU specific symbols of that type, AWXU internally uses also platform specific symbols
 * to select code, e.g. __GLIBCXX__, _MSC_VER, etc.
 *
 * AWXU Symbols are prefixed with <em>AWXU_</em>.<p>
 *
 * In addition to the AWXU specific symbols, the AWXU package and headers files also support symbols
 * that are not specific to AWXU functionality but are really general symbols used in many projects.
 * Those symbols are prefixed <em>AWORX_</em>.
 * @{ @}
 */


// #################################################################################################
// AWORX_ Symbols
// #################################################################################################
/**
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWORX_GTEST_ON
 *  This compiler symbol is passed to the compiler to define code selection symbol
 *  \ref AWORX_GTEST which selects unit test code in accordance to the Google Test libraries.
 *  In respect to the selection of the test library, this symbol has priority over
 *  \ref AWORX_VSTUDIO_ON.
 * @}
 *
 * @addtogroup GrpAWXUCodeSelectorSymbols
 * @{ \def  AWORX_GTEST
 *  Selects unit test code in accordance to the Google Test libraries.
 *  Use \ref AWORX_GTEST_ON to define this symbol.
 * @}
 */
#if defined(AWORX_GTEST)
    #error "AWORX_GTEST must not be set from outside. Use AWORX_GTEST_ON instead!"
#endif

#if defined(AWORX_GTEST_ON)
    #define AWORX_GTEST
#endif

/**
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWORX_QTCREATOR_ON
 *  This compiler symbol is passed to the compiler to define code selection symbol
 *  \ref AWORX_QTCREATOR. This symbol should be set in all QT Creator project files
 *  which include A-Worx libraries.<p>
 * @}
 *
 * @addtogroup GrpAWXUCodeSelectorSymbols
 * @{ \def  AWORX_QTCREATOR
 *  Used to select code fragments which are specific to running (and debugging) software from within
 *  the IDE QT Creator.<p>
 * @}
 */
#if defined(AWORX_QTCREATOR)
    #error "AWORX_GTEST must not be set from outside. Use AWORX_QTCREATOR_ON instead!"
#endif

#if defined(AWORX_QTCREATOR_ON)
    #define AWORX_QTCREATOR
#endif

/**
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWORX_VSTUDIO_ON
 *  This compiler symbol is passed to the compiler to define code selection symbol
 *  \ref AWORX_VSTUDIO. This symbol should be set in all Microsoft Visual Studio project
 *  files which include A-Worx libraries.<p>
 *  \note In respect to the selection of the unit testing framework, the symbol
 *  \ref AWORX_GTEST_ON supersedes this symbol.
 * @}
 *
 * @addtogroup GrpAWXUCodeSelectorSymbols
 * @{ \def  AWORX_VSTUDIO
 *  Used to select code fragments which are specific to running (and debugging) software from within
 *  Microsoft Visual Studio.<p>
 *  For example, A-Worx software uses this symbol to select the Visual Studio unit test framework.
 *  Use \ref AWORX_VSTUDIO_ON to define this symbol.
 * @}
 */
#if defined(AWORX_VSTUDIO)
    #error "AWORX_VSTUDIO must not be set from outside. Use AWORX_VSTUDIO_ON instead!"
#endif

#if defined(AWORX_VSTUDIO_ON)
    #define AWORX_VSTUDIO
#endif



// #################################################################################################
// AWXU_DLL_EXPORTS
// #################################################################################################
/**
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWXU_DLL_EXPORTS
 *  This compiler symbol has to be defined when compiling AWorx util classes into a DLL under
 *  Windows/MSC.
 *  Within code units that use a DLL that contains AWorx util, this symbol must not be defined.
 * @}
 *
 * @addtogroup GrpAWXUMacros
 * @{ \def  AWXU_API
 *  Used to export/import C++ symbols into a dynamic link library.
 *  Defined when compiling AWorx util classes into a DLL under Windows/MSC.
 *  Dependent on \ref AWXU_DLL_EXPORTS.<br>
 * @}
 */
#if defined( _MSC_VER )
    #ifdef AWXU_DLL_EXPORTS
        #define AWXU_API  __declspec(dllexport)
    #else
        #define AWXU_API  __declspec(dllimport)
    #endif
#else
    #define AWXU_API
#endif


/**
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWXU_DEBUG_ON
 *  This compiler symbol forces AWXU to compile in debug mode by defining the code selection symbol
 *  \ref AWXU_DEBUG. Plausibility and assertion checks are performed in this mode.<br>
 *  If neither this symbol nor \ref AWXU_DEBUG_OFF is provided, then the AWXU headers rely on
 *  the symbol <b>NDEBUG</b>. If NDEBUG is given, \ref AWXU_DEBUG is not set. Otherwise it is.
 * @}
 *
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWXU_DEBUG_OFF
 *  This compiler symbol forces AWXU to compile without internal plausibility and assertion checks,
 *  by leaving the code selection symbol \ref AWXU_DEBUG undefined.
 *  If neither this symbol nor \ref AWXU_DEBUG_OFF is provided, then the AWXU headers rely on
 *  the symbol <b>NDEBUG</b>: \ref AWXU_DEBUG is set unless NDEBUG is set.
 * @}
 *
 * @addtogroup GrpAWXUCodeSelectorSymbols
 * @{ \def  AWXU_DEBUG
 *  If defined, plausibility checks and AWXU assertions are enabled.
 *  See \ref AWXU_DEBUG_ON and \ref AWXU_DEBUG_OFF for information about how to manipulate
 *  this symbol.
 * @}
 */
#if defined(AWXU_DEBUG)
    #error "AWXU_DEBUG must not be set from outside"
#endif

#if defined(AWXU_DEBUG_OFF) && defined(AWXU_DEBUG_ON)
    #error "AWXU_DEBUG_OFF / AWXU_DEBUG_ON are both set"
#endif

#if !defined(AWXU_DEBUG_OFF)
    #if defined(AWXU_DEBUG_ON) || !defined(NDEBUG) || defined(_DEBUG) || defined(DEBUG)
        #define AWXU_DEBUG
    #endif
#endif


/**
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWXU_DEBUG_ASTRING_ON
 *  This compiler symbol enables additional debug code within class AString. When provided,
 *  it defines \ref AWXU_DEBUG_ASTRING. This is useful when extending or specifically debugging
 *  class AString.
 *  This symbol may only be set in conjunction with setting \ref AWXU_DEBUG.
 * @}
 *
 * @addtogroup GrpAWXUCodeSelectorSymbols
 * @{ \def  AWXU_DEBUG_ASTRING
 *  Selects extra debug code within class AString. Gets defined by compiler symbol
 *  \ref AWXU_DEBUG_ASTRING_ON.
 * @}
 */

#if defined( AWXU_DEBUG_ASTRING_ON )
    #if !defined (AWXU_DEBUG)
        #error "Compiler symbol AWXU_DEBUG_ASTRING_ON set, while AWXU_DEBUG is off. Consider to \
provide AWXU_DEBUG_ON or to not provide AWXU_DEBUG_ASTRING_ON to the compiler."
    #else
        #define    AWXU_DEBUG_ASTRING
    #endif

#endif


/**
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWXU_FEAT_THREADS_ON
 *  This compiler symbol enables the use of threads within A-Worx utility class library AWXU, by.
 *  defining source selection symbol \ref AWXU_FEAT_THREADS.
 *  This is the default, hence the symbol is not needed to be passed, but it is available for
 *  completeness.<br>
 *  See \ref AWXU_FEAT_THREADS_OFF for more information.
 * @}
 *
 * @addtogroup GrpAWXUCompilerSymbols
 * @{ \def  AWXU_FEAT_THREADS_OFF
 *  This compiler symbol disables the use of threads within A-Worx utility class library AWXU, by
 *  preventing the definition of source selection symbol \ref AWXU_FEAT_THREADS.
 *  This might be useful for platforms where no thread library is available or to minimize code
 *  size. In general, if unsure, do not use this symbol and use the multi-threaded version of AWXU.<br>
 *  When this symbol is set, classes
 *  \ref aworx::util::Thread "Thread" and
 *  \ref aworx::util::Runnable "Runnable" are not available. However, the classes
 *  \ref aworx::util::ThreadLockNR "ThreadLockNR" and
 *  \ref aworx::util::ThreadLock "ThreadLock" are!
 *  Their interface remains the same, but they do not perform any locking. Due to this behavior,
 *  it is possible to write code that performs due locking while still, in single threaded
 *  compilation, the code does not suffer any performance drawbacks, because lock code  will be
 *  <em>pruned</em>.
 * @}
 *
 * @addtogroup GrpAWXUCodeSelectorSymbols
 * @{ \def  AWXU_FEAT_THREADS
 *  Selects code within AWXU (or source entities that use AWXU) that cares about threads and
 *  due locking of shared resources.
 *  See compiler symbol \ref AWXU_FEAT_THREADS_OFF for more information.
 * @}
 */


#if defined(AWXU_FEAT_THREADS)
    #error "AWXU_DEBUG must not be set from outside"
#endif

#if defined(AWXU_FEAT_THREADS_ON) && defined(AWXU_FEAT_THREADS_OFF)
    #error "AWXU_FEAT_THREADS_ON / AWXU_FEAT_THREADS_OFF are both set"
#endif

#if !defined(AWXU_FEAT_THREADS_OFF)
    #define AWXU_FEAT_THREADS
#endif



#endif // HPP_AWORX_UTIL_AWXU_CONDCOMPILE