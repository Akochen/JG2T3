SELECT rentableId, type, title FROM `rentable`
WHERE rentableId = 1111110
    AND rentableId NOT IN ( SELECT rentableId FROM `rental` );