SELECT *   FROM Frequency f  WHERE f.docid='10398_txt_earn';


 SELECT count(*)
  FROM Frequency f
  WHERE f.term = 'parliament';


 SELECT f.docid,sum(f.count)
  FROM Frequency f
  GROUP BY f.docid
  HAVING sum(f.count) > 300;

 SELECT distinct f1.docid
  FROM Frequency f1, Frequency f2
  WHERE f1.docid = f2.docid AND
      f1.term = 'transactions' and f2.term = 'world';
