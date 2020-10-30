# Compiler
#Basic compiler (based on PASCAL)
Basic compiler for a little grammar language. ⋅⋅⋅ (Outputs are SICXE file, Symbol tabl, token analyzer) ⋅⋅⋅ (INPUTS --> PROGRAM it self) ⋅⋅⋅ Note : this is a little grammar language which doesn't support calculations on integers/floats :D and MO LOOPS :D ⋅⋅⋅ Note : this project is done by me, merna and ahmed.

Tables	Number	Status
PROGRAM	1	Implemented
VAR	2	Implemented
BEGIN	3	Implemented
END	4	Not-Implemented
END.	5	Implemented
FOR	6	Not-Implemented
READ	7	Implemented
WRITE	8	Implemented
TO	9	Not-Implemented
DO	10	Not-Implemented
;	11	Implemented
=	12	Implemented
+	13	Implemented
,	14	Implemented
(	15	Implemented
)	16	Implemented
id	17	Implemented
*	18	Implemented
-	19	Implemented
/	20	Implemented

 PROGRAM BASICS
 VAR
 X,Y,A,B,C,Z
 BEGIN
 READ(X,Y,Z,B)
 A = X+B;
 C = X/ Z;
 C = C - B;
 Z = A+B+C+Y;
 WRITE(A,C,Z)
 END.
