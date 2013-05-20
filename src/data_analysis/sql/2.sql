SELECT op1.row_num,
       op2.col_num,
       sum( op1.value * op2.value )
  FROM a op1,
       b op2
 WHERE op1.col_num = op2.row_num
 GROUP BY op1.row_num,
          op2.col_num;
