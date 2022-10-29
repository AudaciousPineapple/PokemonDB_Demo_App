=================
===***Intro***=== Code by Joshua Schwamb
=================
This is a basic Pokémon Database Android App I'm working on in my spare time.

========================
===***PokemonDB.db***===
========================

==Movesets==
- if level = -1, then the moved is learned via TM/HM
- if level = -2, then the move is a special move, and the source column will be NonNull
- if prEvo = 0, then level refers to the level the prEvo learns the move
- if yellow is true, the move is part of the Pokémon's yellow-version level up

==Pokemon==
- location data is stored as a string, with individial locations seperated by commas
- If the location is an integer, then it is on a route (ex: "3" means "Route 3")
- evo & prEvo will =0 when a pokemon has no evolution or pre-evolution
- evoLvl Key:
    - 0     = No Evolution
    - 1-100 = Normal level-up evolution
    - 101   = Trade Evolution
    - 102   = Thunder Stone
    - 103   = Moon Stone
    - 104   = Fire Stone
    - 105   = Leaf Stone
    - 106   = Water Stone
    - 107   = Eevee (Special Case)
- xpGrowth Key:
   - S  = Slow (1,250,000 XP)
   - MS = Medium Slow (1,059,860 XP)
   - MF = Medium Fast (1,000,000 XP)
   - F  = Fast (800,000 XP)

==Moves==
- detailEffect implies a more detailed description of the effect that will need to be handled at runtime. Most moves will have a NULL value for this.
