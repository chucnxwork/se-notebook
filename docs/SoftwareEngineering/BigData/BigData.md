## Components of a Big Data system

### Data

Click stream, social public API, crawling system, server generated logs, mobile apps, telco data (location information, CDR), call center logs, videoaudio logs, transactional database.

### Data collector

FlumeFluentKafka for streaming logs, Sqoop for RDBMS (transferring data between relational databases and Hadoop), Nutch for crawling, custom application to harvest from API

### Data storage

SAN, Datawarehouse, RDBMS, NoSQL DB, HDFS

### Data processor

GRID computing, Hadoop (MR, Pig, Hive), Spark

### Other supporting modules

Cluster management, Solr (enterprise-search platform), interface, Ozzie for workflow management (server-based workflow scheduling system to manage Hadoop jobs)

## Softwares

### Hive - data query and analysis

Hive gives a SQL-like interface to query data stored in various databases and file systems that integrate with Hadoop.

### Impala -  [massively parallel processing](httpsen.wikipedia.orgwikiMassively_parallel_processing) (MPP) SQL query engine

for data stored in a [computer cluster](httpsen.wikipedia.orgwikiComputer_cluster) running [Apache Hadoop](httpsen.wikipedia.orgwikiApache_Hadoop).
Inspired by Google F1, Google Dremel project

### Hue - SQL Assistant for querying Databases & Data Warehouses and collaborating

Its goal is to make self service data querying more widespread in organizations.
