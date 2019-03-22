# Introduction
This is a springboot command-line application to calculate loan interest.


# Build Environment
* jdk 1.8.0_152-b16
* maven 3.3.9

# Assumptions
* A loan/quote can be built from multiple offers;
* An offer can be bundled to a loan partially (i.e. an offer providing £100 can be selected but only borrow £10 from it.)
* If multiple offers provided with the same rate, the small amount will be selected first. Avoid to split a lender's offer
which may cause difficulty for future work;

# Limitation and improvment
Some works have been considered but did not get time to complete:
* Multithread concurrency is not support;
* Run the app will generate one loan only and does not support to generate another loan(The offers selected are not
removed from available. Can be improved.)
* CommandLine input arguments number and format are not validated. Can be improved. 
* CSV loader is copied from internet and not fully covered by tests. 
* Some improvement can be done (validating, testing) if time allows, put some comments there

# Execution
* mvn install from the project's root folder to build the jar file (target/rate-calculation-system-0.0.1-SNAPSHOT.jar)
* run the java -jar <jar_file> <absolute_path_to_csv_file> <request_amount>
example:
java -jar target/rate-calculation-system-0.0.1-SNAPSHOT.jar target/test-classes/test_market_data.csv 1000
