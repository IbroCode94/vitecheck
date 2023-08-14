select m.id, m.name, n.name, mi.score, mi.evidence
from medication m
left join medication_interaction mi on mi.medication_id = m.id
left join nutrient n on n.id = mi.nutrient_id
order by m."name"