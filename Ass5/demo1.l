%{
/* variables come here */
%}

%%

[\t ]+ ;
is | 
am | 
are |
were |
was { printf("%s: is a verb \n",yytext); }
[a-zA-Z]+ { printf("%s: is not a verb \n",yytext); }
[0-9]+ { printf("%s: is a number \n",yytext); }
.|\n { ECHO;}

%%

main(){
	printf("Enter a string ");
	yylex();
	yywrap();
}

int yywrap(void){
return 1;
}
