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

NUMBER   = "0" | [1-9] [0-9]*
OPTION_NAME = [^ \t\n\r,.+={}#]+
HEX_DIGITS      = [0-9a-fA-F]{4}
FORMAT          = "number" | "date" | "time" | "plural" | "selectordinal" | "select"
LEFT_BRACE      = "{"
RIGHT_BRACE     = "}"
QUOTE           = "\""
COMMA           = ","
EQUALS          = "="
HASH            = "#"

ID      = [a-zA-Z][a-zA-Z0-9]*
ESCAPED = "\\\\" | "\\\"" | "\\#" | "\\{" | "\\}" | ("\\u" {HEX_DIGITS})
INVALID_ESCAPE = ("\\" [^\" \t\n\r])
STRING  = ([^{}\\\0-\x1F\x7f\" \t\n\r#])+

END_OF_LINE_COMMENT=("//")[^\r\n]*

%state IN_BLOCK
%state IN_STRING
%state IN_ARG_STRING
%state IN_ARGUMENT
%state IN_ARGUMENT_TYPE
%state IN_ARGUMENT_PARAMS

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
    {HASH}           { return IcuTypes.STRING; } // Not a placeholder!
    {ESCAPED}        { return IcuTypes.VALID_ESCAPE_STRING; }
    {INVALID_ESCAPE} { return IcuTypes.INVALID_ESCAPE_STRING; }
    {QUOTE}          { yybegin(IN_BLOCK); return IcuTypes.QUOTE; }
    {LEFT_BRACE}     { yypushState(IN_ARGUMENT); return IcuTypes.ARG_LEFT_BRACE; }
}

// This is the same as IN_STRING apart from QUOTE does not go back to the IN_BLOCK and we have a RIGHT_BRACE
// which pops state - also HASH is different
<IN_ARG_STRING> {
    {STRING}         { return IcuTypes.STRING; }
    {HASH}           { return IcuTypes.PLACEHOLDER; } // Placeholder
    {ESCAPED}        { return IcuTypes.VALID_ESCAPE_STRING; }
    {INVALID_ESCAPE} { return IcuTypes.INVALID_ESCAPE_STRING; }
    {QUOTE}          { return IcuTypes.QUOTE; }
    {LEFT_BRACE}     { yypushState(IN_ARGUMENT); return IcuTypes.ARG_LEFT_BRACE; }
    {RIGHT_BRACE}    { yypopState(); return IcuTypes.ARG_RIGHT_BRACE; }
}

<IN_ARGUMENT> {
    {RIGHT_BRACE}    { yypopState(); return IcuTypes.ARG_RIGHT_BRACE; }
    {QUOTE}          { return IcuTypes.QUOTE; }
    {ID}             { return IcuTypes.PARAMETER; }
    {COMMA}          { yybegin(IN_ARGUMENT_TYPE); return IcuTypes.COMMA; }
}

<IN_ARGUMENT_TYPE> {
    {RIGHT_BRACE}    { yypopState(); return IcuTypes.ARG_RIGHT_BRACE; }
    {QUOTE}          { return IcuTypes.QUOTE; }
    {COMMA}          { yybegin(IN_ARGUMENT_PARAMS); return IcuTypes.COMMA; }
    {FORMAT}         { return IcuTypes.FORMAT_TYPE; }
}

<IN_ARGUMENT_PARAMS> {
    {LEFT_BRACE}     { yypushState(IN_ARG_STRING); return IcuTypes.ARG_LEFT_BRACE; }
    {RIGHT_BRACE}    { yypopState(); return IcuTypes.ARG_RIGHT_BRACE; }
    {QUOTE}          { return IcuTypes.QUOTE; }
    {EQUALS}         { return IcuTypes.EQUALS; }
    {NUMBER}         { return IcuTypes.NUMBER; }
    {OPTION_NAME}    { return IcuTypes.OPTION_NAME; }
}

{END_OF_LINE_COMMENT}                     { return IcuTypes.COMMENT; }
({CRLF}|{WHITE_SPACE})+                   { return TokenType.WHITE_SPACE; }

.                                         { return TokenType.BAD_CHARACTER; }
