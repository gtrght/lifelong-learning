SELECT op1.docid,
       op2.docid,
       sum( op1.count * op2.count )
  FROM search_view op1,
       search_view op2
 WHERE op1.term = op2.term
       AND
       op2.docid = 'q'
 GROUP BY op1.docid,
          op2.docid
 ORDER BY sum( op1.count * op2.count ) DESC;
