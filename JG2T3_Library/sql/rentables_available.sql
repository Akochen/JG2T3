SELECT sku, type, title FROM `rentable`
WHERE sku NOT IN ( SELECT sku FROM `rental` );