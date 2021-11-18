## TransactionCategorizer

### Description
Categorizes transactions based on export of BCU banking history.

### Run
```
./gradlew clean build
java -jar .\build\libs\TransactionCategorizer-0.0.1-SNAPSHOT.jar --input-file=ExportedTransactions.csv --output-file=out.csv --transaction-type=CREDIT
```

### Requirements
* Java 11