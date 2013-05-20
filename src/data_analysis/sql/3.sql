SELECT op1.docid,
       op2.docid,
       sum( op1.count * op2.count )
  FROM Frequency op1,
       Frequency op2
 WHERE op1.term = op2.term and
     op1.docid = '10080_txt_crude' and op2.docid = '17035_txt_earn'

 GROUP BY op1.docid,
          op2.docid;
