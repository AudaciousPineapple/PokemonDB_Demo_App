CREATE TABLE "xpGrowthRate" (
    "name"      TEXT NOT NULL UNIQUE,
    "fullName"  TEXT NOT NULL,
    "maxExp"    INTEGER NOT NULL,
    "formula"   TEXT NOT NULL,
    "_id"       INTEGER PRIMARY KEY
);

INSERT INTO "xpGrowthRate" ("name", "fullName", "maxExp", "formula") VALUES
    ('slow', 'Slow', 1250000, '\\frac{5x^3}{4}'),
    ('medium-slow', 'Medium Slow', 1059860, '\\frac{6x^3}{5} - 15x^2 + 100x - 140'),
    ('medium', 'Medium Fast', 1000000, 'x^3'),
    ('fast', 'Fast', 800000, '\\frac{4x^3}{5}'),
    ('slow-then-very-fast', 'Erratic', 600000, '\\begin{cases}\n\\frac{ x^3 \\left( 100 - x \\right) }{50},    & \\text{if } x \\leq 50  \\\\\n\\frac{ x^3 \\left( 150 - x \\right) }{100},   & \\text{if } 50 < x \\leq 68  \\\\\n\\frac{ x^3 \\left( 1274 + (x \\bmod 3)^2 - 9 (x \\bmod 3) - 20 \\left\\lfloor \\frac{x}{3} \\right\\rfloor \\right) }{1000}, & \\text{if } 68 < x \\leq 98  \\\\\n\\frac{ x^3 \\left( 160 - x \\right) }{100},   & \\text{if } x > 98  \\\\\n\\end{cases}'),
    ('fast-then-very-slow', 'Fluctuating', 1640000, '\\begin{cases}\n\\frac{ x^3 \\left( 24 + \\left\\lfloor \\frac{x+1}{3} \\right\\rfloor \\right) }{50},  & \\text{if } x \\leq 15  \\\\\n\\frac{ x^3 \\left( 14 + x \\right) }{50},     & \\text{if } 15 < x \\leq 35  \\\\\n\\frac{ x^3 \\left( 32 + \\left\\lfloor \\frac{x}{2} \\right\\rfloor \\right ) }{50},   & \\text{if } x > 35  \\\\\n\\end{cases}');

CREATE TABLE "type" (
    "name"  TEXT NOT NULL UNIQUE,
    "_id"   INTEGER PRIMARY KEY
);

INSERT INTO "type" ("name") VALUES
    ('normal'),
    ('fighting'),
    ('flying'),
    ('poison'),
    ('ground'),
    ('rock'),
    ('bug'),
    ('ghost'),
    ('steel'),
    ('fire'),
    ('water'),
    ('grass'),
    ('electric'),
    ('psychic'),
    ('ice'),
    ('dragon'),
    ('dark'),
    ('fairy'),
    ('unknown'),
    ('shadow');

CREATE TABLE "pokemonG1" (
    "dexNum"        INTEGER NOT NULL UNIQUE,
    "name"          TEXT NOT NULL,
    "sprite1"       BLOB NOT NULL,
    "sprite2"       BLOB NOT NULL,
    "nameJp"        TEXT NOT NULL,
    "nameJpEng"     TEXT NOT NULL,
    "nameFr"        TEXT NOT NULL,
    "nameGer"       TEXT NOT NULL,
    "nameKor"       TEXT NOT NULL,
    "type1"         TEXT NOT NULL REFERENCES type.name,
    "type2"         TEXT REFERENCES type.name,
    "class"         TEXT NOT NULL,
    "height"        INTEGER NOT NULL,
    "weight"        INTEGER NOT NULL,
    "capRate"       INTEGER NOT NULL,
    "xpRate"        TEXT NOT NULL,
    "baseHP"        INTEGER NOT NULL,
    "baseAtk"       INTEGER NOT NULL,
    "baseDef"       INTEGER NOT NULL,
    "baseSpc"       INTEGER NOT NULL,
    "baseSpe"       INTEGER NOT NULL,
    "_id"           INTEGER PRIMARY KEY
);

CREATE TABLE "movesG1" (
    "name"          TEXT NOT NULL UNIQUE,
    "nameJp"        TEXT NOT NULL,
    "type"          TEXT NOT NULL REFERENCES type.name,
    "pp"            INTEGER NOT NULL,
    "power"         INTEGER NOT NULL,
    "accuracy"      INTEGER NOT NULL,
    "effect"        TEXT NOT NULL,
    "effectRate"    INTEGER,
    "tmNum"         INTEGER,
    "priority"      INTEGER NOT NULL,
    "target"        TEXT NOT NULL,
    "_id"           INTEGER PRIMARY KEY
);

CREATE TABLE "movesetsG1" (
    "dexNum"        INTEGER NOT NULL REFERENCES pokemonG1.dexNum,
    "name"          TEXT NOT NULL REFERENCES movesG1.name
    "pos"           INTEGER NOT NULL,
    "method"        INTEGER NOT NULL,   -- 0 for level, 1 for tm, 2 for prevo
    "isYellow"      INTEGER NOT NULL,   -- 0 for no, 1 for yes
    "level"         INTEGER,
    "tmNum"         INTEGER,
    "prEvo"         INTEGER,
    "source"        TEXT,
    "_id"           INTEGER PRIMARY KEY
);
