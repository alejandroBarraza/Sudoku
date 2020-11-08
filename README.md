# SudokuSolver

Resolvedor de sudoku para una matriz de nxn usando hilos.

# Calculo de la eficienia y el speedUp
Si bien la eficiencia y speedUp tienden a generar una gráfica decreciente en vez de tender al numero de nucleo para el SpeedUp y tender a uno para 
la eficiencia. Con esto se puede concluir que no se obtiene un beneficio alguno al general un hilo para la columna,fila y la grilla.Para un sudoku de este temañio tiene un menor
costo iterrar sobre las filas y columnas para cada instacia en vez de generar un hilo para la fila,columna y la grilla.
#imagenes 

![speedup](https://user-images.githubusercontent.com/71998273/98455632-10ee0000-2152-11eb-84c8-f04547ed94fa.PNG)
