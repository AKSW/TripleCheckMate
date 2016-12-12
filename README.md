TripleCheckMate
===============

Linked Data Quality Evaluation software

    Note: that the code use to run the original DBpedia 
    evaluation campaign with triple check mate is here:
    https://github.com/AKSW/TripleCheckMate/releases/tag/DBpediaCampaign

This software was developed as a general purpose tool for Linked Data Quality Evaluation.
It's first use was for DBpedia but (almost) everything is configurable or can easily change.

Accordingly, it was [forked](https://github.com/IMISE/TripleCheckMate) for the [SNIK Project](http://snik.eu/) ontology of the [IMISE](http://www.imise.uni-leipzig.de/) institute. General, non-SNIK-specific updates have been merged back into this project. The major difference is that the SNIK ontology largely consists of classes and nearly no instances, so that classes are randomly fetched and presented to the user for evaluation instead of instances. Thus, consider forking the SNIK adapted TripleCheckMate if you have a similar use case.

In the future it will be shipped with an h2 database inside the war file as a ready to go tool but till then you can also use MySQL or (hopefully) any JDBC enabled database. The default database file is in war/db/database.default.sql

A demo of TripleCheckMate can be found here: http://nl.dbpedia.org:8080/TripleCheckMate-Demo/

You can also watch a demo video here: http://www.youtube.com/watch?v=l-StthTvjFI

See the project wiki for more installation details: https://github.com/AKSW/TripleCheckMate/wiki

The software was built with Google Web Toolkit
https://developers.google.com/web-toolkit/

Homepage: http://aksw.org/Projects/TripleCheckMate.html
