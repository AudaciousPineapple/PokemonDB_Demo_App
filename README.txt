==Movesets==
- if level = -1, then the moved is learned via TM/HM
- if level = -2, then the move is a special move, and the source column will be NonNull
- if prEvo is NonNull, then level refers to the level the prEvo learns the move

==Pokemon==
- location data is stored as a string, with individial locations seperated by comma's
- If the location is an integer, then it is on a route (ex: "3" means "Route 3")
- evo & prEvo data will be Null when a pokemon has no evolution or pre-evolution
- evoLvl Key:
    - 0     = Eevee (Special Case)
    - 1-100 = Normal level-up evolution
    - 101   = Trade Evolution
    - 102   = Thunder Stone
    - 103   = Moon Stone
    - 104   = Fire Stone
    - 105   = Leaf Stone
    - 106   = Water Stone

==Moves==
- detailEffect implies a more detailed description of the effect that will need to be handled at runtime. Most moves will have a NULL value for this.
