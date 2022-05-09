%{
 
%}

digit [0-9]+
letter [a-zA-z]+

%%
[\t ]+ 
[\; ]
[\{ | }]
[\= ]

if |
for |
while |
static { printf("%s: This is a java keyword\n",yytext); }

public | 
protected | 
private { printf("%s: This is a access specifier\n",yytext); }

int | 
float | 
char | 
byte { printf("%s: This is a data type\n",yytext); }

{digit}+ { printf("%s, is a number\n",yytext); }

{letter}({letter}|{digit})* { printf("%s: is an identifier\n",yytext); } 

%%

main(){
	yyin=fopen("sample.java","r");
	yylex();
	yywrap();
}

int yywrap(void){
return 1;
}
