CREATE TABLE "xpGrowthRate" (
    "name"      TEXT NOT NULL UNIQUE,
    "fullName"  TEXT NOT NULL,
    "maxExp"    INTEGER NOT NULL,
    "_id"       INTEGER PRIMARY KEY
);

INSERT INTO "xpGrowthRate" ("name", "fullName", "maxExp") VALUES
    ('slow', 'Slow', 1250000),
    ('medium-slow', 'Medium Slow', 1059860),
    ('medium', 'Medium Fast', 1000000),
    ('fast', 'Fast', 800000),
    ('slow-then-very-fast', 'Erratic', 600000),
    ('fast-then-very-slow', 'Fluctuating', 1640000);

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
    "sprite1"       TEXT NOT NULL,
    "sprite2"       TEXT NOT NULL,
    "nameJp"        TEXT NOT NULL,
    "nameJpEng"     TEXT NOT NULL,
    "nameFr"        TEXT NOT NULL,
    "nameGer"       TEXT NOT NULL,
    "nameKor"       TEXT NOT NULL,
    "type1"         TEXT NOT NULL REFERENCES "type" ("name"),
    "type2"         TEXT NOT NULL REFERENCES "type" ("name"),
    "class"         TEXT NOT NULL,
    "height"        INTEGER NOT NULL,
    "weight"        INTEGER NOT NULL,
    "capRate"       INTEGER NOT NULL,
    "xpRate"        TEXT NOT NULL,
    "baseHP"        INTEGER NOT NULL,
    "minHp50"       INTEGER NOT NULL,
    "maxHp50"       INTEGER NOT NULL,
    "minHp100"      INTEGER NOT NULL,
    "maxHp100"      INTEGER NOT NULL,
    "baseAtk"       INTEGER NOT NULL,
    "minAtk50"       INTEGER NOT NULL,
    "maxAtk50"       INTEGER NOT NULL,
    "minAtk100"      INTEGER NOT NULL,
    "maxAtk100"      INTEGER NOT NULL,
    "baseDef"       INTEGER NOT NULL,
    "minDef50"       INTEGER NOT NULL,
    "maxDef50"       INTEGER NOT NULL,
    "minDef100"      INTEGER NOT NULL,
    "maxDef100"      INTEGER NOT NULL,
    "baseSpc"       INTEGER NOT NULL,
    "minSpc50"       INTEGER NOT NULL,
    "maxSpc50"       INTEGER NOT NULL,
    "minSpc100"      INTEGER NOT NULL,
    "maxSpc100"      INTEGER NOT NULL,
    "baseSpe"       INTEGER NOT NULL,
    "minSpe50"       INTEGER NOT NULL,
    "maxSpe50"       INTEGER NOT NULL,
    "minSpe100"      INTEGER NOT NULL,
    "maxSpe100"      INTEGER NOT NULL,
    "_id"           INTEGER PRIMARY KEY
);

CREATE TABLE "movesG1" (
    "name"          TEXT NOT NULL UNIQUE,
    "nameJp"        TEXT NOT NULL,
    "type"          TEXT NOT NULL REFERENCES "type" ("name"),
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
    "dexNum"        INTEGER NOT NULL REFERENCES "pokemonG1" ("dexNum"),
    "name"          TEXT NOT NULL REFERENCES "movesG1" ("name"),
    "pos"           INTEGER NOT NULL,
    "method"        INTEGER NOT NULL,   -- 0 for level, 1 for tm, 2 for prevo
    "isYellow"      INTEGER NOT NULL,   -- 0 for no, 1 for yes
    "level"         INTEGER,
    "tmNum"         INTEGER,
    "prEvo"         INTEGER,
    "source"        TEXT,
    "_id"           INTEGER PRIMARY KEY
);

CREATE TABLE "encountersG1" (
    "_id"           INTEGER PRIMARY KEY,
    "dexNum"        INTEGER NOT NULL,
    "locName"       TEXT NOT NULL,
    "subLocName"    TEXT,
    "method"        TEXT NOT NULL,
    "chance"        INTEGER,
    "minLevel"      INTEGER,
    "maxLevel"      INTEGER,
    "version"       TEXT NOT NULL
);