# pipeline run ex2 CrySLDetector --datasets TSE17-ExPrecision --java-options Xmx12G --java-options Xss256m
# pipeline publish ex2 CrySLDetector --datasets TSE17-ExPrecision -s http://localhost -u owary -p owary --java-options Xmx10G --java-options Xss128m
# pipeline publish metadata -s http://localhost -u reviewer1 -p standalone --datasets TSE17-ExPrecision
mvn clean package -DskipTests
cp target/CrySLDetector.jar CrySLDetector/latest/CrySLDetector.jar
docker run -it -v mubench-checkouts:/mubench/checkouts -v mubench-findings:/mubench/findings -v /home/owary/Programming/Research/APIMisuse/AmalgamScanner/CrySLDetector:/mubench/detectors/CrySLDetector --rm -p 8080:80 mansur/mubench:stable