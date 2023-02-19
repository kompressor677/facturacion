SELECT 
p.productoid,
p.producto,
p.codigobarra,
p.precio,
b.marca
FROM 
productos p
JOIN
marcas b ON
p.marcaid=b.marcaid
ORDER BY p.productoid asc;