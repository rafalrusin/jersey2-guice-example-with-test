set -e

printcp() {
  for i; do echo -n $i; echo -n ';'; done
}

java -Dtomcat.port=$1 -cp `printcp target/dependency/*.jar target/classes/` example.Main
