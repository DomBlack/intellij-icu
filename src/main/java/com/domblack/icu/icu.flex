package com.domblack.icu;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.domblack.icu.psi.IcuTypes;
import com.intellij.psi.TokenType;
import com.intellij.util.containers.Stack;


%%

%class IcuLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

%{
      StringBuffer string = new StringBuffer();

   private Stack<Integer> stack = new Stack<>();

   public void yypushState(int newState) {
     stack.push(yystate());
     yybegin(newState);
   }

   public void yypopState() {
     yybegin(stack.pop());
   }
%}

CRLF=\R
WHITE_SPACE=[\ \n\t\f]

//NUMBER   = '0' | [1-9] [0-9]*
//ARGUMENT = [^ \t\n\r,.+={}#]+ | {NUMBER}
//OPTION_NAME = [^ \t\n\r,.+={}#]+
//
HEX_DIGITS      = [0-9a-fA-F]{4}
CHARS          = ([^{}\\\0-\x1F\x7f \t\n\r] | "\\\\" | "\\#" | "\\{" | "\\}" | ("\\u" {HEX_DIGITS}))+
//FORMAT         = "number" | "date" | "time" | "plural" | "selectordinal" | "select"
LEFT_BRACE     = "{"
RIGHT_BRACE    = "}"
QUOTE          = "\""
//COMMA          = ","
//EQUALS         = '='

ID      = [a-zA-Z][a-zA-Z0-9]*
ESCAPED = "\\\\" | "\\\"" | "\\#" | "\\{" | "\\}" | ("\\u" {HEX_DIGITS})
INVALID_ESCAPE = ("\\" [^\" \t\n\r])
STRING  = ([^\\\"])+

END_OF_LINE_COMMENT=("//")[^\r\n]*

//%state IN_NESTED_FORMAT
//%state IN_ARGUMENT_ELEMENT
//%state IN_ARGUMENT_TYPE
//%state IN_FORMAT

%state IN_BLOCK
%state IN_STRING

%%

<YYINITIAL> {
    [a-z]{2} { yybegin(IN_BLOCK); return IcuTypes.LANG_ID; }
}

<IN_BLOCK> {
    {LEFT_BRACE}    { return IcuTypes.LEFT_BRACE; }
    {RIGHT_BRACE}   { return IcuTypes.RIGHT_BRACE; }
    {QUOTE}         { yybegin(IN_STRING); return IcuTypes.QUOTE; }
    {ID}            { return IcuTypes.ID; }
}

<IN_STRING> {
    {STRING}         { return IcuTypes.STRING; }
    {ESCAPED}        { return IcuTypes.VALID_ESCAPE_STRING; }
    {INVALID_ESCAPE} { return IcuTypes.INVALID_ESCAPE_STRING; }
    {QUOTE}          { yybegin(IN_BLOCK); return IcuTypes.QUOTE; }
}

//<IN_NESTED_FORMAT> {
//    {CHARS}      { return IcuTypes.CHARS; }
//    {RIGHT_BRACE} { yypopState(); return IcuTypes.RIGHT_BRACE; }
//    {LEFT_BRACE} { yypushState(IN_ARGUMENT_ELEMENT); return IcuTypes.LEFT_BRACE; }
//}
//
//<IN_ARGUMENT_ELEMENT> {
//    {ARGUMENT}          { yybegin(IN_ARGUMENT_TYPE); return IcuTypes.ARGUMENT; }
//}
//
//<IN_ARGUMENT_TYPE> {
//    {RIGHT_BRACE}       { yypopState(); return IcuTypes.RIGHT_BRACE; }
//    {COMMA}             { return IcuTypes.COMMA; }
//    {FORMAT}            { yybegin(IN_FORMAT); return IcuTypes.FORMAT_TYPE; }
//}
//
//<IN_FORMAT> {
//    {LEFT_BRACE}        { yypushState(IN_NESTED_FORMAT); return IcuTypes.LEFT_BRACE; }
//    {RIGHT_BRACE}       { yypopState(); return IcuTypes.RIGHT_BRACE; }
//    {COMMA}             { return IcuTypes.COMMA; }
//    {EQUALS}            { return IcuTypes.EQUALS; }
//    {NUMBER}            { return IcuTypes.NUMBER; }
//    {OPTION_NAME}       { return IcuTypes.OPTION_NAME; }
//}


{END_OF_LINE_COMMENT}                     { return IcuTypes.COMMENT; }
({CRLF}|{WHITE_SPACE})+                   { return TokenType.WHITE_SPACE; }

.                                         { return TokenType.BAD_CHARACTER; }
