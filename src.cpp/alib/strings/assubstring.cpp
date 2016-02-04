﻿// #################################################################################################
//  ALib - A-Worx Utility Library
//
//  (c) 2013-2016 A-Worx GmbH, Germany
//  Published under MIT License (Open Source License, see LICENSE.txt)
// #################################################################################################
#include "alib/stdafx_alib.h"

#include "alib/alib.hpp"

#if !defined (HPP_AWORX_LIB_STRINGS_ASSUBSTRING)
    #include "alib/strings/assubstring.hpp"
#endif

#if !defined (HPP_AWORX_LIB_STRINGS_NUMBERFORMAT)
    #include "alib/strings/numberformat.hpp"
#endif

#if !defined (_GLIBCXX_OSTREAM)
    #include <ostream>
#endif


// Windows.h might bring in max/min macros
#if defined( max )
    #undef max
    #undef min
#endif

using namespace std;
using namespace aworx::lib::strings;

namespace aworx {
namespace           lib {
namespace                   strings {



// #################################################################################################
// Substring
// #################################################################################################
bool   ASSubstring::ConsumeLong( int64_t& result, const ASTerminatable& whitespaces )
{
    if (length <= 0 )
    {
        result= 0;
        return false;
    }

    const char* origBuffer= buffer;
    int         origLength= length;
    int         idx=        0;

    TrimStart( whitespaces );
    result=    NumberFormat::Global.StringToInteger( *this, idx );

    if ( idx == 0 )
    {
        buffer=  origBuffer;
        length=  origLength;
        return false;
    }
    buffer+=  idx;
    length-=  idx;
    return true;
}

bool    ASSubstring::ConsumeFloat( double&                  result,
                                   NumberFormat*            numberFormat,
                                   const ASTerminatable&    whitespaces    )
{
    if (length <= 0 )
    {
        result= 0;
        return  0.0;
    }

    const char* origBuffer= buffer;
    int         origLength= length;
    int         idx=        0;
    TrimStart( whitespaces );
    result=    ( numberFormat != nullptr ? numberFormat
                                         : &NumberFormat::Global )  -> StringToFloat( *this, idx );

    if ( idx == 0 )
    {
        buffer=  origBuffer;
        length=  origLength;
        return false;
    }
    buffer+=  idx;
    length-=  idx;
    return true;
}


}}}// namespace aworx::lib::strings
