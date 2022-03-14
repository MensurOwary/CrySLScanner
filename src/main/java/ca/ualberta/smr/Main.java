package ca.ualberta.smr;

import de.tu_darmstadt.stg.mubench.cli.*;

import static crypto.Runner.getDetectorOutput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        DetectionStrategy detectionStrategy = (detectorArgs, builder) -> {
            System.out.println("TARGET SRC: " + Arrays.toString(detectorArgs.getTargetSrcPaths()));
            System.out.println("TARGET CLS: " + Arrays.toString(detectorArgs.getTargetClassPath().getPaths()));

            String targetSrcPath = detectorArgs.getTargetSrcPaths()[0];
            String targetClassPath = detectorArgs.getTargetClassPath().getClasspath();

            String[] newArgs = new String[]{
                    "--appPath",
                    targetClassPath
            };

            List<DetectorFinding> detectorFindings = getDetectorOutput(newArgs).stream()
                    .peek(df -> {
                        String file = (String) df.get("file");
                        df.put("file", targetSrcPath + file);

                        String lineNumber = (String) df.get("line_number");
                        df.put("startline", Integer.parseInt(lineNumber));
                    }).collect(Collectors.toList());

            return builder.withFindings(detectorFindings);
        };

        new MuBenchRunner()
                .withDetectOnlyStrategy(detectionStrategy)
                .withMineAndDetectStrategy(detectionStrategy)
                .run(args);
    }

}
